package net.deadlydiamond.legend_of_steve.common.blocks.deco.oriented;

import net.minecraft.block.BlockState;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.item.ItemPlacementContext;

public class TileBlock extends GlazedTerracottaBlock implements IMimicDirection {
    public TileBlock(Settings settings) {
        super(settings);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getAllignedTile(ctx, getDefaultState(), super.getPlacementState(ctx));
    }
}
