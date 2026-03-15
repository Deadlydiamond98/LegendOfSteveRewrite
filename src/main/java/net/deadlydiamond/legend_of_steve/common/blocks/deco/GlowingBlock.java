package net.deadlydiamond.legend_of_steve.common.blocks.deco;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;

public class GlowingBlock extends Block implements IGlowingBlock {
    public GlowingBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
