package net.deadlydiamond.legend_of_steve.common.blocks.deco;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.Direction;

public class TileBlock extends GlazedTerracottaBlock {
    public TileBlock(Settings settings) {
        super(settings);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getAllignedTile(ctx);
    }

    private BlockState getAllignedTile(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(direction.getOpposite()));

        return blockState.getBlock() instanceof TileBlock && blockState.get(FACING) != direction && !ctx.getPlayer().isSneaking() ?
                this.getDefaultState().with(FACING, blockState.get(FACING)) : super.getPlacementState(ctx);
    }
}
