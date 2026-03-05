package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
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

        // WOOD ////////////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).add(
                ZeldaBlocks.DEKU_WOOD.log.asItem(),
                ZeldaBlocks.DEKU_WOOD.wood.asItem(),
                ZeldaBlocks.DEKU_WOOD.strippedLog.asItem(),
                ZeldaBlocks.DEKU_WOOD.strippedWood.asItem()
        );

        getOrCreateTagBuilder(ItemTags.PLANKS).add(
                ZeldaBlocks.DEKU_WOOD.plank.asItem()
        );

        getOrCreateTagBuilder(ItemTags.WOODEN_SLABS).add(
                ZeldaBlocks.DEKU_WOOD.slab.asItem()
        );

        getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS).add(
                ZeldaBlocks.DEKU_WOOD.stair.asItem()
        );

        getOrCreateTagBuilder(ItemTags.WOODEN_FENCES).add(
                ZeldaBlocks.DEKU_WOOD.fence.asItem()
        );

        getOrCreateTagBuilder(ItemTags.FENCE_GATES).add(
                ZeldaBlocks.DEKU_WOOD.gate.asItem()
        );

        getOrCreateTagBuilder(ItemTags.WOODEN_DOORS).add(
                ZeldaBlocks.DEKU_WOOD.door.asItem()
        );

        getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(
                ZeldaBlocks.DEKU_WOOD.trapdoor.asItem()
        );

        getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS).add(
                ZeldaBlocks.DEKU_WOOD.button.asItem()
        );

        getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(
                ZeldaBlocks.DEKU_WOOD.plate.asItem()
        );

        getOrCreateTagBuilder(ItemTags.SIGNS).add(
                ZeldaBlocks.DEKU_WOOD.signItem
        );

        getOrCreateTagBuilder(ItemTags.HANGING_SIGNS).add(
                ZeldaBlocks.DEKU_WOOD.hangingSignItem
        );

        getOrCreateTagBuilder(ItemTags.SAPLINGS).add(
                ZeldaBlocks.DEKU_SAPLING.asItem()
        );

        getOrCreateTagBuilder(ItemTags.LEAVES).add(
                ZeldaBlocks.DEKU_LEAVES.asItem(),
                ZeldaBlocks.FRUITING_DEKU_LEAVES.asItem()
        );
    }
}
