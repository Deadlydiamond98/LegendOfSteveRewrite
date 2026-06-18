package net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.switches.toggle;

import net.deadlydiamond.legend_of_steve.common.bes.switches.CrystalSwitchBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.switches.SwitchBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.IExplodedInteraction;
import net.deadlydiamond.legend_of_steve.common.blocks.IModifiedOutlineRender;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.BoundBlockUtil;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.switches.ISwitchBlock;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.AbstractBombEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaAdvancements;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond98.koalalib.common.blocks.interaction.IHitBlockAction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrystalSwitchBlock extends Block implements Waterloggable, ISwitchBlock, IModifiedOutlineRender, IExplodedInteraction, IHitBlockAction {
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public CrystalSwitchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(HALF, DoubleBlockHalf.LOWER)
                .with(WATERLOGGED, false)
        );
    }

    public boolean isBottom(BlockState state) {
        return state.contains(HALF) && state.get(HALF) == DoubleBlockHalf.LOWER;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HALF, WATERLOGGED);
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return !isBottom(state) ? BlockSoundGroup.INTENTIONALLY_EMPTY : super.getSoundGroup(state);
    }

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
    }

    @Override
    public boolean startOn() {
        return true;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return BoundBlockUtil.applyGroupOnPickStack(super.getPickStack(world, pos, state), world, pos);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        BoundBlockUtil.addTooltip(stack, tooltip);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (isBottom(state) && random.nextFloat() < 0.25) {
            createSwitchParticle(world, pos, CrystalSwitchCollisions.ENTIRE_ORB_SHAPE, 0.125f, isOn(world, pos), true);
        }

        if (random.nextInt(500) == 0) {
            world.playSoundAtBlockCenter(pos, ZeldaSounds.CRYSTAL_SWITCH_AMBIENT, SoundCategory.BLOCKS, 0.5f, 1, true);
        }
    }

    // Comparator Interaction //////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if (!world.isClient() && world.getBlockEntity(pos) instanceof SwitchBlockEntity switchBlock) {
            return switchBlock.isOn() ? 15 : 0;
        }
        return 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Interactions ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * This Method just checks if the box being hit is the Orb's
     */
    public boolean hitOrb(BlockState state, HitResult hit) {
        if (hit instanceof BlockHitResult hitResult) {
            return !isBottom(state) || hitResult.getPos().y - hitResult.getBlockPos().getY() > 0.69;
        }
        return false;
    }

    public boolean hitOrb(BlockState state, Entity entity) {
        return hitOrb(state, entity.raycast(5, 0, false));
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        // Breaking is stopped when mining the orb so that attacking the switch doesn't result in mining it
        return hitOrb(state, player) ? 0 : super.calcBlockBreakingDelta(state, player, world, pos);
    }

    private void triggerAdvancement(@Nullable Entity entity) {
        if (entity instanceof PlayerEntity player) {
            ZeldaAdvancements.TRIGGER_CRYSTAL_SWITCH.trigger(player);
        }
    }

    // Attack //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void attack(BlockState blockState, BlockPos blockPos, World world, PlayerEntity playerEntity) {
        if (hitOrb(blockState, playerEntity) && !world.isClient()) {
            triggerAdvancement(playerEntity);
            triggerSwitch(world, blockPos);
        }
    }

    @Override
    public boolean allowAttackHolding() {
        return false;
    }

    // Bomb ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBombExploded(World world, BlockPos blockPos, Explosion explosion) {
        if (!world.isClient()) {
            triggerAdvancement(explosion.getCausingEntity());
            triggerSwitch(world, blockPos);
        }
    }

    // Projectiles /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (hitOrb(state, hit) && !world.isClient()) {
            triggerAdvancement(projectile.getOwner());
            triggerSwitch(world, hit.getBlockPos());
        }
    }

    // Trigger Switch //////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public <T extends SwitchBlockEntity> void onSwitchTriggered(World world, BlockPos pos, BlockState state, T blockEntity, boolean newOnState) {
        if (!world.isClient() && !blockEntity.firstTick) {
            world.playSound(null, pos, newOnState ? ZeldaSounds.CRYSTAL_SWITCH_ON : ZeldaSounds.CRYSTAL_SWITCH_OFF, SoundCategory.BLOCKS, 1, 1);
        } else {
            createSwitchParticles(world, pos, CrystalSwitchCollisions.ENTIRE_ORB_SHAPE, 10, 0.125f, newOnState, true);
        }
    }

    @Override
    public int getTriggerCooldown() {
        return 3;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Block Entity ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return isBottom(state) ? super.getRenderType(state) : BlockRenderType.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return isBottom(state) ? new CrystalSwitchBlockEntity(pos, state) : null;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return isBottom(state) ? checkType(type, ZeldaBlockEntities.CRYSTAL_SWITCH, CrystalSwitchBlockEntity::tick) : null;
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(
            BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker
    ) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public @Nullable BlockEntity getBlockEntity(BlockView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        BlockPos newPos = !isBottom(state) && world.getBlockState(pos.down()).isOf(this) ? pos.down() : pos;
        return ISwitchBlock.super.getBlockEntity(world, newPos);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PLACE & BREAKING ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        // Copied from TallPlantBlock
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction.getAxis() != Direction.Axis.Y
                || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP)
                || neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf) {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)
                    ? Blocks.AIR.getDefaultState()
                    : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockState waterloggedState = this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        return blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx) ? waterloggedState : null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        BlockPos blockPos = pos.up();
        FluidState fluidState = world.getFluidState(blockPos);
        world.setBlockState(blockPos, this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER), Block.NOTIFY_ALL);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (!isBottom(state)) {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && isBottom(blockState);
        }
        return super.canPlaceAt(state, world, pos);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative()) {
            DoubleBlockHalf doubleBlockHalf = state.get(HALF);
            if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
                BlockPos blockPos = pos.down();
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isOf(state.getBlock()) && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
                    world.breakBlock(blockPos, false, player);
                }
            }
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // COLLISION SHAPES ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext ctx && (!(ctx.getEntity() instanceof ProjectileEntity) || ctx.getEntity() instanceof AbstractBombEntity)) {
            return isBottom(state) ? CrystalSwitchCollisions.BASE_SHAPE : VoxelShapes.empty();
        }
        return isBottom(state) ? CrystalSwitchCollisions.PROJECTILE_COLLISION_BASE_SHAPE : CrystalSwitchCollisions.TOP_ORB_SHAPES[0];
    }

    @Override
    public VoxelShape getRenderedOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return CrystalSwitchCollisions.BASE_SHAPE;
    }

    @Override
    public Vec3i getOffset(BlockPos pos, BlockState state) {
        return isBottom(state) ? Vec3i.ZERO : new Vec3i(0, -1, 0);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return CrystalSwitchCollisions.getOrbShape(getBlockEntity(world, pos), isBottom(state));
    }
}
