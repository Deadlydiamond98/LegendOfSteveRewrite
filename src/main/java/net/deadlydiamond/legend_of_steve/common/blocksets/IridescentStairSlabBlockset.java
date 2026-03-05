package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.util.datagen.model.IridescentBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.BaseStairSlabBlockset;
import net.minecraft.block.AbstractBlock;
import net.minecraft.data.client.BlockStateModelGenerator;

public class IridescentStairSlabBlockset extends BaseStairSlabBlockset {
    public IridescentStairSlabBlockset(String modID, String id, AbstractBlock.Settings settings) {
        super(modID, id, settings);
    }

    public IridescentStairSlabBlockset(String modID, String id, AbstractBlock.Settings settings, boolean stripEndS) {
        super(modID, id, settings, stripEndS);
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, boolean uniqueSlab) {
        IridescentBlockModelDatagenUtil.registerIridescentBlock(modelGen, this.base);
        IridescentBlockModelDatagenUtil.registerIridescentSlab(modelGen, this.slab, this.base);
        IridescentBlockModelDatagenUtil.registerIridescentStairs(modelGen, this.stair, this.base);
    }
}
