package net.deadlydiamond.legend_of_steve.common.blocks.deco.oriented;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.Direction;

public interface IMimicDirection {
    default BlockState getAllignedTile(ItemPlacementContext ctx, BlockState defaultState, BlockState placementState) {
        Direction direction = ctx.getSide();
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(direction.getOpposite()));

        return blockState.getBlock() instanceof IMimicDirection && blockState.get(TileBlock.FACING) != direction && !ctx.getPlayer().isSneaking() ?
                defaultState.with(TileBlock.FACING, blockState.get(TileBlock.FACING)) : placementState;
    }
}
