package net.deadlydiamond.legend_of_steve.common.blocks.functional.light;

import net.deadlydiamond98.koalalib.common.blocks.IExtinguish;
import net.deadlydiamond98.koalalib.util.IgnitionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class BrazierBlock extends Block implements IExtinguish, Waterloggable {
    protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 12, 15);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty LIT = Properties.LIT;
    private final int fireDamage;

    public BrazierBlock(Settings settings, int fireDamage) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(LIT, true).with(WATERLOGGED, false));
        this.fireDamage = fireDamage;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (isLit(state)) {
            this.updateNeighbors(world, pos);
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (canLight(state) && IgnitionHelper.canUseIgniterOnBlock(state, world, pos, player, hand)) {
            lightBlock(state, world, pos, player);
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (isLit(state)) {
            this.updateNeighbors(world, pos);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return getCollisionShape(state, world, pos, ShapeContext.absent()).isEmpty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    // FIRE ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean canLight(BlockState state) {
        return !isLit(state) && !state.get(WATERLOGGED);
    }

    protected void lightBlock(BlockState state, World world, BlockPos pos, Entity player) {
        world.setBlockState(pos, state.with(Properties.LIT, true));
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        updateNeighbors(world, pos);
    }

    @Override
    public void extinguish(@Nullable Entity entity, WorldAccess world, BlockPos pos, BlockState state) {
        IExtinguish.super.extinguish(entity, world, pos, state);
        if (world instanceof World world1) {
            updateNeighbors(world1, pos);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (isLit(state) && entity instanceof LivingEntity living && !EnchantmentHelper.hasFrostWalker(living)) {
            entity.damage(world.getDamageSources().inFire(), (float)this.fireDamage);
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        BlockPos blockPos = hit.getBlockPos();
        if (!world.isClient && projectile.isOnFire() && projectile.canModifyAt(world, blockPos) && canLight(state)) {
            lightBlock(state, world, hit.getBlockPos(), projectile.getOwner());
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (isLit(state)) {
            if (random.nextInt(10) == 0) {
                world.playSound(
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS,
                        0.5f + random.nextFloat(), random.nextFloat() * 0.7f + 0.6f, false
                );
            }

            world.addParticle(
                    ParticleTypes.SMOKE,
                    pos.getX() + 0.5 + ((random.nextFloat() - 0.5) * 0.5),
                    pos.getY() + 0.75,
                    pos.getZ() + 0.5 + ((random.nextFloat() - 0.5) * 0.5),
                    0.0, 0.1, 0.0
            );
        }
    }

    // REDSTONE ////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void updateNeighbors(World world, BlockPos pos) {
        BlockPos.iterateOutwards(pos, 1, 0, 1).forEach(pos1 ->
                world.updateNeighborsAlways(pos1, this));
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return isLit(state);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return isLit(state) ? 15 : 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return getWeakRedstonePower(state, world, pos, direction);
    }

    // WATERLOGGING ////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            if (isLit(state)) {
                if (!world.isClient()) {
                    world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1, 1);
                }
                extinguish(null, world, pos, state);
            }

            world.setBlockState(pos, state.with(WATERLOGGED, true).with(LIT, false), Block.NOTIFY_ALL);
            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIT, WATERLOGGED);
    }
}
