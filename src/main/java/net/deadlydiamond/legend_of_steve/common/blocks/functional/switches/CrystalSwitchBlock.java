package net.deadlydiamond.legend_of_steve.common.blocks.functional.switches;

import net.deadlydiamond.legend_of_steve.common.bes.CrystalSwitchBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.IExplodedInteraction;
import net.deadlydiamond.legend_of_steve.common.blocks.IModifiedOutlineRender;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.AbstractBombEntity;
import net.deadlydiamond.legend_of_steve.common.items.IExtraCanMine;
import net.deadlydiamond.legend_of_steve.common.particles.SparkParticleEffect;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.networking.s2c.UpdateCrystalSwitchHitS2CPacket;
import net.deadlydiamond98.koalalib.common.blocks.interaction.IHitBlockAction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class CrystalSwitchBlock extends AbstractSwitchBlock implements IHitBlockAction, IModifiedOutlineRender, IExtraCanMine, IExplodedInteraction {
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    public CrystalSwitchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (isBottom(state) && world.random.nextBoolean()) {
            if (getBlockEntity(world, pos, state) instanceof CrystalSwitchBlockEntity switchBlock) {
                createSwitchParticles(world, pos, 1, switchBlock.isOn());
            }
        }
    }

    public boolean isBottom(BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER;
    }

    // PLACEMENT ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
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
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        return blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx) ? super.getPlacementState(ctx) : null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        BlockPos blockPos = pos.up();
        world.setBlockState(blockPos, withWaterloggedState(world, blockPos, this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER)), Block.NOTIFY_ALL);
    }

    public static BlockState withWaterloggedState(WorldView world, BlockPos pos, BlockState state) {
        return state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, world.isWater(pos)) : state;
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
        if (!world.isClient && player.isCreative() && isBottom(state)) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(state.getBlock()) && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockState2 = blockState.getFluidState().isOf(Fluids.WATER) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
                world.setBlockState(blockPos, blockState2, Block.NOTIFY_ALL | Block.SKIP_DROPS);
                world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
            }
        }

        super.onBreak(world, pos, state, player);
    }

    // BLOCK ENTITY STUFFS /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return isBottom(state) ? super.getRenderType(state) : BlockRenderType.INVISIBLE;
    }

    @Nullable
    protected BlockEntity getBlockEntity(BlockView world, BlockPos pos, BlockState state) {
        if (!isBottom(state) && world.getBlockState(pos.down()).isOf(this)) {
            return world.getBlockEntity(pos.down());
        }
        return world.getBlockEntity(pos);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return isBottom(state) ? super.createBlockEntity(pos, state) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return isBottom(state) ? super.getTicker(world, state, type) : null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HALF);
    }

    // INTERACTION /////////////////////////////////////////////////////////////////////////////////////////////////////

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
    public boolean canMineBlock(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !hitOrb(state, miner);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (hitOrb(state, hit) && !world.isClient()) {
            triggerSwitch(world, hit.getBlockPos(), state, true);
        }
    }

    @Override
    public void attack(BlockState blockState, BlockPos blockPos, World world, PlayerEntity playerEntity) {
        if (hitOrb(blockState, playerEntity) && !world.isClient()) {
            triggerSwitch(world, blockPos, blockState, true);
        }
    }

    @Override
    public void onBombExploded(World world, BlockPos blockPos, Explosion explosion) {
        if (!world.isClient()) {
            triggerSwitch(world, blockPos, world.getBlockState(blockPos), true);
        }
    }

    public void triggerSwitch(World world, BlockPos pos, BlockState state, boolean newState) {
        if (getBlockEntity(world, pos, state) instanceof CrystalSwitchBlockEntity switchBlock) {
            if (switchBlock.getTriggerCooldown() > 0) {
                return;
            }

            if (!world.isClient()) {
                world.getPlayers().forEach(player -> UpdateCrystalSwitchHitS2CPacket.send(player, pos, !switchBlock.isOn()));
                newState = !switchBlock.isOn();

                switchBlock.updateOnState(newState, true);
                world.playSound(null, pos,
                        newState ? ZeldaSounds.CRYSTAL_SWITCH_ON : ZeldaSounds.CRYSTAL_SWITCH_OFF,
                        SoundCategory.BLOCKS, 1, 1
                );
            } else {
                BlockPos particlePos = isBottom(state) ? pos : pos.down();
                createSwitchParticles(world, particlePos, world.random.nextBetween(10, 15), newState);
                SparkParticleEffect.createSparks(
                        world, newState ? new SparkParticleEffect(0xf8f8f8, 0xe6566f) : new SparkParticleEffect(0xf8f8f8, 0x509aee),
                        particlePos.toCenterPos().add(0, 0.5625, 0), 10
                );
            }
            switchBlock.setTriggerCooldown(3);
        }
    }

    @Override
    public boolean allowAttackHolding() {
        return false;
    }

    // COLLISION SHAPES ////////////////////////////////////////////////////////////////////////////////////////////////

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
        return CrystalSwitchCollisions.getOrbShape(getBlockEntity(world, pos, state), isBottom(state));
    }

    // OTHER ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if (!world.isClient() && getBlockEntity(world, pos, state) instanceof CrystalSwitchBlockEntity switchBlock) {
            return switchBlock.isOn() ? 15 : 0;
        }
        return 0;
    }
}
