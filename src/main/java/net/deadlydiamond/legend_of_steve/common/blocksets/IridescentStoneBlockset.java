package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.datagen.model.IridescentBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.StoneBlockset;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.TagKey;

import java.util.function.BiConsumer;

public class IridescentStoneBlockset extends StoneBlockset {
    public IridescentStoneBlockset(String modID, String id, AbstractBlock.Settings settings) {
        super(modID, id, settings);
    }

    public IridescentStoneBlockset(String modID, String id, AbstractBlock.Settings settings, BlockSetType blockSetTypeReference) {
        super(modID, id, settings, blockSetTypeReference);
    }

    public IridescentStoneBlockset(String modID, String id, AbstractBlock.Settings settings, BlockSetType blockSetTypeReference, boolean stripEndS) {
        super(modID, id, settings, blockSetTypeReference, stripEndS);
    }

    @Override
    public void generateItemTags(BiConsumer<TagKey<Item>, ItemConvertible> tagConsumer) {
        super.generateItemTags(tagConsumer);
        for (Block block : getAll()) {
            tagConsumer.accept(ZeldaTags.IRIDESCENT_ITEM, block);
        }
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, boolean uniqueSlab) {
        IridescentBlockModelDatagenUtil.registerIridescentBlock(modelGen, this.base);
        IridescentBlockModelDatagenUtil.registerIridescentSlab(modelGen, this.slab, this.base);
        IridescentBlockModelDatagenUtil.registerIridescentStairs(modelGen, this.stair, this.base);
        IridescentBlockModelDatagenUtil.registerIridescentWall(modelGen, this.wall, this.base);
        IridescentBlockModelDatagenUtil.registerIridescentButton(modelGen, this.button, this.base);
        IridescentBlockModelDatagenUtil.registerIridescentPressurePlate(modelGen, this.plate, this.base);
    }
}
