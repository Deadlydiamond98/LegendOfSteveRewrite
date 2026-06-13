package net.deadlydiamond.legend_of_steve.common.blocks.deco.connected.temp;

import net.deadlydiamond.legend_of_steve.common.blocks.deco.dungeoncite.DungeonciteTileBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class TriforceTileBlock extends DungeonciteTileBlock {
    public static final EnumProperty<TriforceType> TRIFORCE_TYPE = EnumProperty.of("triforce_type", TriforceType.class);

    public TriforceTileBlock(Settings settings, String advancementID) {
        super(settings, advancementID);
        this.setDefaultState(getDefaultState().with(TRIFORCE_TYPE, TriforceType.SINGLE));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return connectPlaced(super.getPlacementState(ctx), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return connectPlaced(super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos), world, pos);
    }

    public BlockState connectPlaced(BlockState state, WorldAccess world, BlockPos pos) {
        BlockState stateCopy = connectDoubles(state, world, pos, false);

        if (stateCopy.get(TRIFORCE_TYPE) != TriforceType.SINGLE) {
            BlockState leftBlock = world.getBlockState(pos.offset(stateCopy.get(FACING).rotateYClockwise()));
            BlockState rightBlock = world.getBlockState(pos.offset(stateCopy.get(FACING).rotateYCounterclockwise()));

            if (canConnect(stateCopy, leftBlock)) {
                BlockState leftBlockChecked = connectDoubles(leftBlock, world, pos.offset(stateCopy.get(FACING).rotateYClockwise()), false);

                if (!triforce(leftBlockChecked, TriforceType.SINGLE) && !leftBlock.get(TRIFORCE_TYPE).isRight() && !state.get(TRIFORCE_TYPE).isLeft()) {
                    if (triforce(stateCopy, TriforceType.DOUBLE_BOTTOM) && triforce(leftBlock, TriforceType.DOUBLE_BOTTOM)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_BOTTOM_RIGHT);
                    } else if (triforce(stateCopy, TriforceType.DOUBLE_BOTTOM) && triforce(leftBlock, TriforceType.BIG_BOTTOM_LEFT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_BOTTOM_RIGHT);
                    }

                    if (triforce(stateCopy, TriforceType.DOUBLE_TOP) && triforce(leftBlock, TriforceType.DOUBLE_TOP)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_TOP_RIGHT);
                    } else if (triforce(stateCopy, TriforceType.DOUBLE_TOP) && triforce(leftBlock, TriforceType.BIG_TOP_LEFT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_TOP_RIGHT);
                    }
                }
            }

            if (canConnect(stateCopy, rightBlock)) {
                BlockState rightBlockChecked = connectDoubles(rightBlock, world, pos.offset(stateCopy.get(FACING).rotateYCounterclockwise()), false);

                if (!triforce(rightBlockChecked, TriforceType.SINGLE) && !rightBlock.get(TRIFORCE_TYPE).isLeft() && !state.get(TRIFORCE_TYPE).isRight()) {
                    if (triforce(stateCopy, TriforceType.DOUBLE_BOTTOM) && triforce(rightBlock, TriforceType.DOUBLE_BOTTOM)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_BOTTOM_LEFT);
                    } else if (triforce(stateCopy, TriforceType.DOUBLE_BOTTOM) && triforce(rightBlock, TriforceType.BIG_BOTTOM_RIGHT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_BOTTOM_LEFT);
                    }

                    if (triforce(stateCopy, TriforceType.DOUBLE_TOP) && triforce(rightBlock, TriforceType.DOUBLE_TOP)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_TOP_LEFT);
                    } else if (triforce(stateCopy, TriforceType.DOUBLE_TOP) && triforce(rightBlock, TriforceType.BIG_TOP_RIGHT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_TOP_LEFT);
                    }
                }
            }

            if (stateCopy.get(TRIFORCE_TYPE).isTop()) {
                return stateCopy.with(TRIFORCE_TYPE, TriforceType.DOUBLE_TOP);
            } else {
                return stateCopy.with(TRIFORCE_TYPE, TriforceType.DOUBLE_BOTTOM);
            }
        }

        return connectDoubles(state, world, pos, true);
    }

    public BlockState connectDoubles(BlockState state, WorldAccess world, BlockPos pos, boolean strict) {
        BlockState aboveBlock = world.getBlockState(pos.offset(state.get(FACING).getOpposite()));
        BlockState belowBlock = world.getBlockState(pos.offset(state.get(FACING)));

        boolean matchesAbove = canConnect(state, aboveBlock) && (
                (triforce(state, TriforceType.SINGLE) && triforce(aboveBlock, TriforceType.SINGLE))
                        || (strict ? triforce(aboveBlock, TriforceType.DOUBLE_TOP) : aboveBlock.get(TRIFORCE_TYPE).isTop())
        );
        boolean matchesBelow = canConnect(state, belowBlock) && (
                (triforce(state, TriforceType.SINGLE) && triforce(belowBlock, TriforceType.SINGLE))
                        || (strict ? triforce(belowBlock, TriforceType.DOUBLE_BOTTOM) : belowBlock.get(TRIFORCE_TYPE).isBottom())
        );

        if (matchesAbove) {
            return state.with(TRIFORCE_TYPE, TriforceType.DOUBLE_BOTTOM);
        } else if (matchesBelow) {
            return state.with(TRIFORCE_TYPE, TriforceType.DOUBLE_TOP);
        }
        return state.with(TRIFORCE_TYPE, TriforceType.SINGLE);
    }

    public boolean triforce(BlockState state, TriforceType type) {
        if (state.isOf(this)) {
            return state.get(TRIFORCE_TYPE) == type;
        }
        return false;
    }

    public boolean canConnect(BlockState state, BlockState otherState) {
        return state.isOf(otherState.getBlock()) && state.get(FACING) == otherState.get(FACING);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(TRIFORCE_TYPE);
    }
}
