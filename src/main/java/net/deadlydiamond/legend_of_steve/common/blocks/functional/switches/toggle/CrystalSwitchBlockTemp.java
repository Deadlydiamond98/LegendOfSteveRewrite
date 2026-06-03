package net.deadlydiamond.legend_of_steve.common.blocks.functional.switches.toggle;

import net.deadlydiamond.legend_of_steve.common.bes.switches.CrystalSwitchBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.IExplodedInteraction;
import net.deadlydiamond.legend_of_steve.common.blocks.IModifiedOutlineRender;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.AbstractBombEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.networking.s2c.switches.SwitchToggleS2CPacket;
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
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
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

public class CrystalSwitchBlockTemp extends AbstractSwitchBlock implements IHitBlockAction, IModifiedOutlineRender, IExplodedInteraction {
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    public CrystalSwitchBlockTemp(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (isBottom(state) && random.nextBoolean()) {
            if (getBlockEntity(world, pos, state) instanceof CrystalSwitchBlockEntity switchBlock) {
                createSwitchParticles(world, pos, 1, 0.375f, switchBlock.isOn());
            }
        }

        if (random.nextInt(750) == 0) {
            world.playSoundAtBlockCenter(pos, ZeldaSounds.CRYSTAL_SWITCH_AMBIENT, SoundCategory.BLOCKS, 0.5f, 1, true);
        }
    }

    public boolean isBottom(BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER;
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return !isBottom(state) ? BlockSoundGroup.INTENTIONALLY_EMPTY : super.getSoundGroup(state);
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
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        return hitOrb(state, player) ? 0 : super.calcBlockBreakingDelta(state, player, world, pos);
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
                world.getPlayers().forEach(player -> SwitchToggleS2CPacket.send(player, pos, !switchBlock.isOn()));
                newState = !switchBlock.isOn();

                switchBlock.triggerSwitch();
                world.playSound(null, pos,
                        newState ? ZeldaSounds.CRYSTAL_SWITCH_ON : ZeldaSounds.CRYSTAL_SWITCH_OFF,
                        SoundCategory.BLOCKS, 1, 1
                );
            } else {
                BlockPos particlePos = isBottom(state) ? pos : pos.down();
                createSwitchParticles(world, particlePos, world.random.nextBetween(12, 20), 0.4f, newState);
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
}
