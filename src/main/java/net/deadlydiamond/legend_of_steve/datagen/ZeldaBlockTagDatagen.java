package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ZeldaBlockTagDatagen extends FabricTagProvider.BlockTagProvider {

    public ZeldaBlockTagDatagen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        ZeldaBlocks.DEKU_WOOD.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));

        createPickaxeMinables(
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

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
                // LAMP
                ZeldaBlocks.PINK_FAIRY_LAMP,
                ZeldaBlocks.RED_FAIRY_LAMP,
                ZeldaBlocks.ORANGE_FAIRY_LAMP,
                ZeldaBlocks.YELLOW_FAIRY_LAMP,
                ZeldaBlocks.GREEN_FAIRY_LAMP,
                ZeldaBlocks.BLUE_FAIRY_LAMP,
                ZeldaBlocks.PURPLE_FAIRY_LAMP,
                // Fairy Marble
                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.CHISELED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_PILLAR,
                ZeldaBlocks.SMOOTH_FAIRY_MARBLE,
                // Master
                ZeldaBlocks.MASTER_ORE,
                ZeldaBlocks.DEEPSLATE_MASTER_ORE,
                ZeldaBlocks.MASTER_SCRAP_BLOCK,
                ZeldaBlocks.MASTER_BLOCK,

                ZeldaBlocks.CUT_MASTER_PLATE,
                ZeldaBlocks.MASTER_PILLAR,
                ZeldaBlocks.MASTER_BARS,
                ZeldaBlocks.MASTER_CHAIN,
                ZeldaBlocks.MASTER_DOOR,
                ZeldaBlocks.MASTER_TRAPDOOR,
                ZeldaBlocks.MASTER_GIRDER,
                ZeldaBlocks.MASTER_BARREL
        );

        // TREE RELATED ////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(BlockTags.LEAVES).add(
                ZeldaBlocks.DEKU_LEAVES,
                ZeldaBlocks.FRUITING_DEKU_LEAVES
        );

        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(
                ZeldaBlocks.DEKU_SAPLING
        );

        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(
                ZeldaBlocks.POTTED_DEKU_SAPLING
        );
    }

    private void createPickaxeMinables(AbstractBlockset... abstractBlocksets) {
        for (AbstractBlockset abstractBlockset : abstractBlocksets) {
            abstractBlockset.generateBlockTags(
                    (blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block),
                    BlockTags.PICKAXE_MINEABLE
            );
        }
    }
}
