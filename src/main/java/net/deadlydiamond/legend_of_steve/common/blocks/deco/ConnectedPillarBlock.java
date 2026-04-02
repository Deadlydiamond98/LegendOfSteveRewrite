package net.deadlydiamond.legend_of_steve.common.blocks.deco;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class ConnectedPillarBlock extends PillarBlock {
    public static final EnumProperty<PillarType> PILLAR_TYPE = EnumProperty.of("pillar_type", PillarType.class);

    public ConnectedPillarBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(PILLAR_TYPE, PillarType.SINGLE));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return createConnectedBlockstate(super.getPlacementState(ctx), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return createConnectedBlockstate(super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos), world, pos);
    }

    public BlockState createConnectedBlockstate(BlockState pillar, WorldAccess world, BlockPos pos) {
        int direction = pillar.get(AXIS) == Direction.Axis.Z ? -1 : 1;

        BlockState topBlock = world.getBlockState(pos.offset(pillar.get(AXIS), direction));
        BlockState bottomBlock = world.getBlockState(pos.offset(pillar.get(AXIS), -1 * direction));

        boolean top = canConnect(pillar, topBlock);
        boolean bottom = canConnect(pillar, bottomBlock);

        if (top && bottom) {
            return pillar.with(PILLAR_TYPE, PillarType.MIDDLE);
        } else if (top) {
            return pillar.with(PILLAR_TYPE, PillarType.BOTTOM);
        } else if (bottom) {
            return pillar.with(PILLAR_TYPE, PillarType.TOP);
        }

        return pillar.with(PILLAR_TYPE, PillarType.SINGLE);
    }

    public boolean canConnect(BlockState pillarState, BlockState otherState) {
        return pillarState.isOf(otherState.getBlock()) && pillarState.get(AXIS) == otherState.get(AXIS);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(PILLAR_TYPE);
    }
}
