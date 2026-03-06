package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.common.blocksets.WoodBlockset;
import net.deadlydiamond98.koalalib.init.KoalaLibTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ZeldaItemTagDatagen extends FabricTagProvider.ItemTagProvider {

    public ZeldaItemTagDatagen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED).add(
                ZeldaItems.GILDED_QUIVER
        );

        getOrCreateTagBuilder(KoalaLibTags.PIGLIN_GOLD_ARMOR).add(
                ZeldaItems.GILDED_QUIVER
        );

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

        getOrCreateTagBuilder(ItemTags.SAPLINGS).add(
                ZeldaBlocks.DEKU_SAPLING.asItem()
        );

        getOrCreateTagBuilder(ItemTags.LEAVES).add(
                ZeldaBlocks.DEKU_LEAVES.asItem(),
                ZeldaBlocks.FRUITING_DEKU_LEAVES.asItem()
        );
    }

    private void createItemTags(AbstractBlockset... blocksets) {
        for (AbstractBlockset blockset : blocksets) {
            blockset.generateItemTags((itemTagKey, itemConvertible) -> getOrCreateTagBuilder(itemTagKey).add(itemConvertible.asItem()));
        }
    }
}
