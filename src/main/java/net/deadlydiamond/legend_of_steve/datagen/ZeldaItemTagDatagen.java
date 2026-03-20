package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.init.KoalaLibTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

import static net.deadlydiamond.legend_of_steve.init.ZeldaBlockSettings.FAIRY_MARBLE_SETTINGS;

public class ZeldaItemTagDatagen extends FabricTagProvider.ItemTagProvider {

    public ZeldaItemTagDatagen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        // BLOCKSETS ///////////////////////////////////////////////////////////////////////////////////////////////////

        createItemTags(
                // DEKU WOOD
                ZeldaBlocks.DEKU_WOOD,
                // FAIRY MARBLE
                ZeldaBlocks.FAIRY_MARBLE,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.FAIRY_MARBLE_TILES,
                // MASTER
                ZeldaBlocks.MASTER_PLATE,
                ZeldaBlocks.MASTER_BRICK,
                ZeldaBlocks.MASTER_TILE,
                // TEKTILES
                ZeldaBlocks.RED_TEKTILES,
                ZeldaBlocks.SMALL_RED_TEKTILES,
                ZeldaBlocks.BLUE_TEKTILES,
                ZeldaBlocks.SMALL_BLUE_TEKTILES
        );

        // WOOD ////////////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(ZeldaTags.CHISELED_PLANKS_ITEM).add(
                ZeldaBlocks.CHISELED_OAK_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_BIRCH_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_SPRUCE_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_JUNGLE_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_ACACIA_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_DARK_OAK_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_CRIMSON_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_WARPED_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_MANGROVE_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_BAMBOO_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_CHERRY_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_DEKU_PLANKS.asItem()
        );

        getOrCreateTagBuilder(ItemTags.NON_FLAMMABLE_WOOD).add(
                ZeldaBlocks.CHISELED_CRIMSON_PLANKS.asItem(),
                ZeldaBlocks.CHISELED_WARPED_PLANKS.asItem()
        );

        getOrCreateTagBuilder(ItemTags.SAPLINGS).add(
                ZeldaBlocks.DEKU_SAPLING.asItem()
        );

        getOrCreateTagBuilder(ItemTags.LEAVES).add(
                ZeldaBlocks.DEKU_LEAVES.asItem(),
                ZeldaBlocks.FRUITING_DEKU_LEAVES.asItem()
        );

        // OTHER ///////////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(ZeldaTags.IRIDESCENT_ITEM).add(
                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS.asItem(),
                ZeldaBlocks.CHISELED_FAIRY_MARBLE.asItem(),
                ZeldaBlocks.FAIRY_MARBLE_PILLAR.asItem(),
                ZeldaBlocks.SMOOTH_FAIRY_MARBLE.asItem()
        );

        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED).add(
                ZeldaItems.GILDED_QUIVER
        );

        getOrCreateTagBuilder(KoalaLibTags.PIGLIN_GOLD_ARMOR).add(
                ZeldaItems.GILDED_QUIVER
        );
    }

    private void createItemTags(AbstractBlockset... blocksets) {
        for (AbstractBlockset blockset : blocksets) {
            blockset.generateItemTags((itemTagKey, itemConvertible) -> getOrCreateTagBuilder(itemTagKey).add(itemConvertible.asItem()));
        }
    }
}
