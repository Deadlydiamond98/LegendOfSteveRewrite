package net.deadlydiamond.legend_of_steve.common.blocks.deco.connected;

import net.deadlydiamond.legend_of_steve.common.blocks.deco.oriented.TileBlock;
import net.deadlydiamond.legend_of_steve.common.ZeldaProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class TriforceTileBlock extends TileBlock {
    public static final EnumProperty<TriforceType> TRIFORCE_TYPE = EnumProperty.of("triforce_type", TriforceType.class);
    public static final BooleanProperty CONNECTS = ZeldaProperties.CONNECTS;

    public TriforceTileBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(TRIFORCE_TYPE, TriforceType.SINGLE).with(CONNECTS, true));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        PlayerEntity player = ctx.getPlayer();
        if (player != null) {
            state = state.with(CONNECTS, !player.isSneaking());
        }
        return connectPlaced(state, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return connectPlaced(super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos), world, pos);
    }

    public BlockState connectPlaced(BlockState state, WorldAccess world, BlockPos pos) {
        BlockState stateCopy = connectDoubles(state, world, pos, false);

        if (stateCopy.get(TRIFORCE_TYPE) != TriforceType.SINGLE) {
            BlockState aboveBlock = world.getBlockState(pos.offset(stateCopy.get(FACING).getOpposite()));
            BlockState belowBlock = world.getBlockState(pos.offset(stateCopy.get(FACING)));

            if (canConnect(stateCopy, aboveBlock)) {
                BlockState aboveBlockChecked = connectDoubles(aboveBlock, world, pos.offset(stateCopy.get(FACING).getOpposite()), false);


                if (!triforce(aboveBlockChecked, TriforceType.SINGLE) && !aboveBlock.get(TRIFORCE_TYPE).isBottom() && !state.get(TRIFORCE_TYPE).isTop()) {
                    if (triforce(stateCopy, TriforceType.DOUBLE_RIGHT) && triforce(aboveBlock, TriforceType.DOUBLE_RIGHT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_BOTTOM_RIGHT);
                    } else if (triforce(stateCopy, TriforceType.DOUBLE_RIGHT) && triforce(aboveBlock, TriforceType.BIG_TOP_RIGHT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_BOTTOM_RIGHT);
                    }

                    if (triforce(stateCopy, TriforceType.DOUBLE_LEFT) && triforce(aboveBlock, TriforceType.DOUBLE_LEFT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_BOTTOM_LEFT);
                    } else if (triforce(stateCopy, TriforceType.DOUBLE_LEFT) && triforce(aboveBlock, TriforceType.BIG_TOP_LEFT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_BOTTOM_LEFT);
                    }
                }
            }

            if (canConnect(stateCopy, belowBlock)) {
                BlockState belowBlockChecked = connectDoubles(belowBlock, world, pos.offset(stateCopy.get(FACING)), false);

                if (!triforce(belowBlockChecked, TriforceType.SINGLE) && !belowBlock.get(TRIFORCE_TYPE).isTop() && !state.get(TRIFORCE_TYPE).isBottom()) {
                    if (triforce(stateCopy, TriforceType.DOUBLE_RIGHT) && triforce(belowBlock, TriforceType.DOUBLE_RIGHT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_TOP_RIGHT);
                    } else if (triforce(stateCopy, TriforceType.DOUBLE_RIGHT) && triforce(belowBlock, TriforceType.BIG_BOTTOM_RIGHT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_TOP_RIGHT);
                    }

                    if (triforce(stateCopy, TriforceType.DOUBLE_LEFT) && triforce(belowBlock, TriforceType.DOUBLE_LEFT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_TOP_LEFT);
                    } else if (triforce(stateCopy, TriforceType.DOUBLE_LEFT) && triforce(belowBlock, TriforceType.BIG_BOTTOM_LEFT)) {
                        return stateCopy.with(TRIFORCE_TYPE, TriforceType.BIG_TOP_LEFT);
                    }
                }
            }

            if (stateCopy.get(TRIFORCE_TYPE).isLeft()) {
                return stateCopy.with(TRIFORCE_TYPE, TriforceType.DOUBLE_LEFT);
            } else {
                return stateCopy.with(TRIFORCE_TYPE, TriforceType.DOUBLE_RIGHT);
            }
        }

        return connectDoubles(state, world, pos, true);
    }

    public BlockState connectDoubles(BlockState state, WorldAccess world, BlockPos pos, boolean strict) {
        BlockState leftBlock = world.getBlockState(pos.offset(state.get(FACING).rotateYClockwise()));
        BlockState rightBlock = world.getBlockState(pos.offset(state.get(FACING).rotateYCounterclockwise()));

        boolean matchesLeft = canConnect(state, leftBlock) && (
                (triforce(state, TriforceType.SINGLE) && triforce(leftBlock, TriforceType.SINGLE))
                        || (strict ? triforce(leftBlock, TriforceType.DOUBLE_LEFT) : leftBlock.get(TRIFORCE_TYPE).isLeft())
        );
        boolean matchesRight = canConnect(state, rightBlock) && (
                (triforce(state, TriforceType.SINGLE) && triforce(rightBlock, TriforceType.SINGLE))
                        || (strict ? triforce(rightBlock, TriforceType.DOUBLE_RIGHT) : rightBlock.get(TRIFORCE_TYPE).isRight())
        );

        if (matchesLeft) {
            return state.with(TRIFORCE_TYPE, TriforceType.DOUBLE_RIGHT);
        } else if (matchesRight) {
            return state.with(TRIFORCE_TYPE, TriforceType.DOUBLE_LEFT);
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
        if (state.isOf(otherState.getBlock()) && state.get(FACING) == otherState.get(FACING)) {
            return state.get(CONNECTS) && otherState.get(CONNECTS);
        }
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(TRIFORCE_TYPE, CONNECTS);
    }
}
