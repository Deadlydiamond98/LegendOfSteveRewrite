package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond98.koalalib.common.blocksets.BaseStairSlabBlockset;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.BiConsumer;

public class ChiseledWoodBrickBlockset extends BaseStairSlabBlockset {
    private final boolean flammable;

    public ChiseledWoodBrickBlockset(String modID, String id, AbstractBlock.Settings settings, boolean flammable) {
        super(modID, id, settings);
        this.flammable = flammable;

        if (flammable) {
            FlammableBlockRegistry.getDefaultInstance().add(this.slab, 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(this.stair, 5, 20);
        }
    }

    @Override
    public void generateBlockTags(BiConsumer<TagKey<Block>, Block> tagConsumer, TagKey<Block>... mineableTags) {
        super.generateBlockTags(tagConsumer, mineableTags);
        tagConsumer.accept(ZeldaTags.CHISELED_PLANKS_BLOCK, this.base);
        tagConsumer.accept(BlockTags.WOODEN_SLABS, this.slab);
        tagConsumer.accept(BlockTags.WOODEN_STAIRS, this.stair);
    }

    @Override
    public void generateItemTags(BiConsumer<TagKey<Item>, ItemConvertible> tagConsumer) {
        super.generateItemTags(tagConsumer);

        if (!this.flammable) {
            tagConsumer.accept(ItemTags.NON_FLAMMABLE_WOOD, this.slab.asItem());
            tagConsumer.accept(ItemTags.NON_FLAMMABLE_WOOD, this.stair.asItem());
        }

        tagConsumer.accept(ZeldaTags.CHISELED_PLANKS_ITEM, this.base);
        tagConsumer.accept(ItemTags.WOODEN_SLABS, this.slab.asItem());
        tagConsumer.accept(ItemTags.WOODEN_STAIRS, this.stair.asItem());
    }
}
