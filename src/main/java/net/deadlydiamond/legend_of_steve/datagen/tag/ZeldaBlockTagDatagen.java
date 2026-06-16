package net.deadlydiamond.legend_of_steve.datagen.tag;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.init.KoalaLibTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ZeldaBlockTagDatagen extends FabricTagProvider.BlockTagProvider {

    public ZeldaBlockTagDatagen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        ZeldaBlocks.DEKU_WOOD.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));

        ZeldaBlocks.CHISELED_OAK_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_BIRCH_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_SPRUCE_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_JUNGLE_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_ACACIA_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_DARK_OAK_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_CRIMSON_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_WARPED_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_MANGROVE_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_BAMBOO_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_CHERRY_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        ZeldaBlocks.CHISELED_DEKU_BRICKS.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));

        createMinables(
                // DUNGEONCITE
                ZeldaBlocks.BROWN_DUNGEONCITE,
                // TILES
                ZeldaBlocks.STONE_TILES,
                // FAIRY MARBLE
                ZeldaBlocks.FAIRY_MARBLE,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.FAIRY_MARBLE_TILES,
                // STRANGE DIRT
                ZeldaBlocks.STRANGE_DIRT,
                ZeldaBlocks.STRANGE_DIRT_BRICKS,
                ZeldaBlocks.POLISHED_STRANGE_DIRT,
                ZeldaBlocks.REINFORCED_STRANGE_DIRT,
                ZeldaBlocks.STRANGE_BLUE_DIRT,
                ZeldaBlocks.STRANGE_BLUE_DIRT_BRICKS,
                ZeldaBlocks.POLISHED_BLUE_STRANGE_DIRT,
                ZeldaBlocks.REINFORCED_STRANGE_BLUE_DIRT,
                // TEKTILES
                ZeldaBlocks.RED_TEKTILES,
                ZeldaBlocks.SMALL_RED_TEKTILES,
                ZeldaBlocks.RED_TEKTILE_BRICKS,
                ZeldaBlocks.BLUE_TEKTILES,
                ZeldaBlocks.SMALL_BLUE_TEKTILES,
                ZeldaBlocks.BLUE_TEKTILE_BRICKS,
                // SWITCH BLOCKS
                ZeldaBlocks.RED_SWITCH_BLOCKS,
                ZeldaBlocks.BLUE_SWITCH_BLOCKS
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
                ZeldaBlocks.MASTER_BARREL,
                // Strange Dirt
                ZeldaBlocks.STRANGE_DIRT_PILLAR,
                ZeldaBlocks.STRANGE_BLUE_DIRT_PILLAR,
                ZeldaBlocks.QUESTION_BLOCK,
                ZeldaBlocks.BLUE_QUESTION_BLOCK,
                // Switch Blocks
                ZeldaBlocks.CRYSTAL_SWITCH,
                // Sword Pedestal
                ZeldaBlocks.STONE_SWORD_PEDESTAL,
                ZeldaBlocks.DEEPSLATE_SWORD_PEDESTAL,
                ZeldaBlocks.BLACKSTONE_SWORD_PEDESTAL,
                ZeldaBlocks.QUARTZ_SWORD_PEDESTAL,
                ZeldaBlocks.FAIRY_MARBLE_SWORD_PEDESTAL,
                ZeldaBlocks.STRANGE_DIRT_SWORD_PEDESTAL,
                ZeldaBlocks.STRANGE_BLUE_DIRT_SWORD_PEDESTAL
        );

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(
                ZeldaBlocks.CHISELED_OAK_PLANKS,
                ZeldaBlocks.CHISELED_BIRCH_PLANKS,
                ZeldaBlocks.CHISELED_SPRUCE_PLANKS,
                ZeldaBlocks.CHISELED_JUNGLE_PLANKS,
                ZeldaBlocks.CHISELED_ACACIA_PLANKS,
                ZeldaBlocks.CHISELED_DARK_OAK_PLANKS,
                ZeldaBlocks.CHISELED_CRIMSON_PLANKS,
                ZeldaBlocks.CHISELED_WARPED_PLANKS,
                ZeldaBlocks.CHISELED_MANGROVE_PLANKS,
                ZeldaBlocks.CHISELED_BAMBOO_PLANKS,
                ZeldaBlocks.CHISELED_CHERRY_PLANKS,
                ZeldaBlocks.CHISELED_DEKU_PLANKS,
                ZeldaBlocks.CRATE,
                ZeldaBlocks.DUNGEON_TABLE
        );

        // MINING LEVEL ////////////////////////////////////////////////////////////////////////////////////////////////

        createMinables(BlockTags.NEEDS_DIAMOND_TOOL,
                // MASTER
                ZeldaBlocks.MASTER_PLATE,
                ZeldaBlocks.MASTER_BRICK,
                ZeldaBlocks.MASTER_TILE
        );

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(
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

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(
                ZeldaBlocks.QUESTION_BLOCK,
                ZeldaBlocks.BLUE_QUESTION_BLOCK
        );

        // TREE RELATED ////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(ZeldaTags.CHISELED_PLANKS_BLOCK).add(
                ZeldaBlocks.CHISELED_OAK_PLANKS,
                ZeldaBlocks.CHISELED_BIRCH_PLANKS,
                ZeldaBlocks.CHISELED_SPRUCE_PLANKS,
                ZeldaBlocks.CHISELED_JUNGLE_PLANKS,
                ZeldaBlocks.CHISELED_ACACIA_PLANKS,
                ZeldaBlocks.CHISELED_DARK_OAK_PLANKS,
                ZeldaBlocks.CHISELED_CRIMSON_PLANKS,
                ZeldaBlocks.CHISELED_WARPED_PLANKS,
                ZeldaBlocks.CHISELED_MANGROVE_PLANKS,
                ZeldaBlocks.CHISELED_BAMBOO_PLANKS,
                ZeldaBlocks.CHISELED_CHERRY_PLANKS,
                ZeldaBlocks.CHISELED_DEKU_PLANKS
        );

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

        // OTHER ///////////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(KoalaLibTags.CRACKED_BRICKS).add(
                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS
        );

        getOrCreateTagBuilder(ZeldaTags.LOCKABLE).add(
                Blocks.CHEST,
                Blocks.TRAPPED_CHEST
        );

        getOrCreateTagBuilder(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(
                ZeldaBlocks.BOMB_FLOWER
        );

        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS).add(
                ZeldaBlocks.MASTER_BLOCK
        );
    }


    private void createMinables(AbstractBlockset... abstractBlocksets) {
        for (AbstractBlockset abstractBlockset : abstractBlocksets) {
            abstractBlockset.generateBlockTags(
                    (blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block),
                    BlockTags.PICKAXE_MINEABLE
            );
        }
    }

    private void createMinables(TagKey<Block> miningLevel, AbstractBlockset... abstractBlocksets) {
        for (AbstractBlockset abstractBlockset : abstractBlocksets) {
            abstractBlockset.generateBlockTags(
                    (blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block),
                    BlockTags.PICKAXE_MINEABLE,
                    miningLevel
            );
        }
    }
}
