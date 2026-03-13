package net.deadlydiamond.legend_of_steve.common.bes.visual;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class GlowingBlockEntity extends BakedBlockEntity {
    public GlowingBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.GLOWING_BLOCK, pos, state);
    }
}
