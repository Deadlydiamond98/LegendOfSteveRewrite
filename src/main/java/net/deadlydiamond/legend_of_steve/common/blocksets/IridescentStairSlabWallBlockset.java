package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.util.datagen.IridescentBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.BaseStairSlabWallBlockset;
import net.minecraft.block.AbstractBlock;
import net.minecraft.data.client.BlockStateModelGenerator;

public class IridescentStairSlabWallBlockset extends BaseStairSlabWallBlockset {
    public IridescentStairSlabWallBlockset(String modID, String id, AbstractBlock.Settings settings) {
        super(modID, id, settings);
    }

    public IridescentStairSlabWallBlockset(String modID, String id, AbstractBlock.Settings settings, boolean stripEndS) {
        super(modID, id, settings, stripEndS);
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, boolean uniqueSlab) {
        IridescentBlockModelDatagenUtil.registerIridescentBlock(modelGen, this.base);
        IridescentBlockModelDatagenUtil.registerIridescentSlab(modelGen, this.slab, this.base);
        IridescentBlockModelDatagenUtil.registerIridescentStairs(modelGen, this.stair, this.base);
        IridescentBlockModelDatagenUtil.registerIridescentWall(modelGen, this.wall, this.base);
    }
}
