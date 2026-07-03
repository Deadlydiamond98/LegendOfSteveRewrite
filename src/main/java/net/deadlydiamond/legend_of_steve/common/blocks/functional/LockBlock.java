package net.deadlydiamond.legend_of_steve.common.blocks.functional;

import net.deadlydiamond.legend_of_steve.common.bes.LockedBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class LockBlock extends BlockWithEntity implements Waterloggable {
    public static final DirectionProperty FACING = Properties.FACING;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public LockBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockState state1 = getLockedBlock(world, pos);
        return state1.getBlock().getPickStack(world, pos, state1);
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        BlockState state1 = getLockedBlock(world, pos);
        return state1.getBlock().calcBlockBreakingDelta(state1, player, world, pos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getLockedBlock(world, pos).getOutlineShape(world, pos);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getLockedBlock(world, pos).getCollisionShape(world, pos);
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getLockedBlock(world, pos).getCameraCollisionShape(world, pos, context);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return getLockedBlock(world, pos).getRaycastShape(world, pos);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return getLockedBlock(world, pos).getCullingShape(world, pos);
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return getLockedBlock(world, pos).getSidesShape(world, pos);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LockedBlockEntity(pos, state);
    }

    public NbtCompound getWrappedNBT(BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlock) {
            return lockedBlock.getWrappedNBT();
        }
        return new NbtCompound();
    }

    public BlockState getLockedBlock(BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof LockedBlockEntity lockedBlock) {
            return lockedBlock.getLockedBlock();
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
}
