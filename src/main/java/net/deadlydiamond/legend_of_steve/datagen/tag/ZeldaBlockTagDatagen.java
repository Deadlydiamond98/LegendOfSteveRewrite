package net.deadlydiamond.legend_of_steve.datagen.tag;

import net.deadlydiamond.legend_of_steve.common.blocksets.LockBlockset;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.wood.WoodVariant;
import net.deadlydiamond.legend_of_steve.util.wood.WoodVariantUtil;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.init.KoalaLibTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class ZeldaBlockTagDatagen extends FabricTagProvider.BlockTagProvider {

    public ZeldaBlockTagDatagen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // WOOD
        ZeldaBlocks.DEKU_WOOD.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block), BlockTags.AXE_MINEABLE);

        WoodVariantUtil.generateWoodBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));

        addWoodToTag(BlockTags.AXE_MINEABLE, ZeldaBlocks.CHISELED_PLANKS);
        addWoodToTag(ZeldaTags.CHISELED_PLANKS_BLOCK, ZeldaBlocks.CHISELED_PLANKS);

        // LOCKS
        generateBlockTags(ZeldaBlocks.LOCKS.toArray(LockBlockset[]::new));

        // Mining //////////////////////////////////////////////////////////////////////////////////////////////////////

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
                // Perlite
                ZeldaBlocks.PERLITE_BRICKS,
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
                // Perlite
                ZeldaBlocks.PERLITE,
                ZeldaBlocks.PERLITE_PILLAR,
                ZeldaBlocks.CHISELED_PERLITE,
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
                ZeldaBlocks.INVISIBLE_QUESTION_BLOCK,
                // Switch Blocks
                ZeldaBlocks.CRYSTAL_SWITCH,
                // Sword Pedestal
                ZeldaBlocks.STONE_SWORD_PEDESTAL,
                ZeldaBlocks.DEEPSLATE_SWORD_PEDESTAL,
                ZeldaBlocks.BLACKSTONE_SWORD_PEDESTAL,
                ZeldaBlocks.QUARTZ_SWORD_PEDESTAL,
                ZeldaBlocks.FAIRY_MARBLE_SWORD_PEDESTAL,
                ZeldaBlocks.STRANGE_DIRT_SWORD_PEDESTAL,
                ZeldaBlocks.STRANGE_BLUE_DIRT_SWORD_PEDESTAL,
                // Other
                ZeldaBlocks.REDSTONE_LOCK_BLOCK
        );

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(
                ZeldaBlocks.CRATE,
                ZeldaBlocks.DUNGEON_TABLE
        );

        // MINING LEVEL ////////////////////////////////////////////////////////////////////////////////////////////////

        createMinables(BlockTags.NEEDS_DIAMOND_TOOL,
                // MASTER
                ZeldaBlocks.MASTER_PLATE,
                ZeldaBlocks.MASTER_BRICK,
                ZeldaBlocks.MASTER_TILE,
                // Perlite
                ZeldaBlocks.PERLITE_BRICKS
        );

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(
                // MASTER
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

                // PERLITE
                ZeldaBlocks.PERLITE,
                ZeldaBlocks.PERLITE_PILLAR,
                ZeldaBlocks.CHISELED_PERLITE
        );

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(
                ZeldaBlocks.QUESTION_BLOCK,
                ZeldaBlocks.BLUE_QUESTION_BLOCK,
                ZeldaBlocks.INVISIBLE_QUESTION_BLOCK
        );

        // TREE RELATED ////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(BlockTags.LEAVES).add(
                ZeldaBlocks.DEKU_LEAVES,
                ZeldaBlocks.FRUITING_DEKU_LEAVES
        );

        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(
                ZeldaBlocks.DEKU_SAPLING
        );

        getOrCreateTagBuilder(BlockTags.FLOWERS).add(
                ZeldaBlocks.SILENT_PRINCESS
        );

        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(
                ZeldaBlocks.POTTED_DEKU_SAPLING,
                ZeldaBlocks.POTTED_SILENT_PRINCESS
        );

        // OTHER ///////////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(KoalaLibTags.CRACKED_BRICKS).add(
                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS
        );

        getOrCreateTagBuilder(ZeldaTags.LOCKABLE).add(
                Blocks.CHEST,
                Blocks.TRAPPED_CHEST,
                Blocks.ENDER_CHEST,
                Blocks.BARREL,
                Blocks.CRAFTING_TABLE,
                Blocks.FURNACE,
                Blocks.BLAST_FURNACE,
                Blocks.SMOKER,
                Blocks.SMITHING_TABLE,
                Blocks.CARTOGRAPHY_TABLE,
                Blocks.CHISELED_BOOKSHELF,
                Blocks.DISPENSER,
                Blocks.DROPPER,
                Blocks.LOOM,
                ZeldaBlocks.QUESTION_BLOCK,
                ZeldaBlocks.BLUE_QUESTION_BLOCK,
                ZeldaBlocks.MASTER_BARREL,
                ZeldaBlocks.DUNGEON_TABLE
        );

        getOrCreateTagBuilder(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(
                ZeldaBlocks.BOMB_FLOWER
        );

        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS).add(
                ZeldaBlocks.MASTER_BLOCK
        );
    }

    private void generateBlockTags(AbstractBlockset... abstractBlocksets) {
        for (AbstractBlockset blockset : abstractBlocksets) {
            blockset.generateBlockTags((blockTagKey, block) -> getOrCreateTagBuilder(blockTagKey).add(block));
        }
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

    private void addWoodToTag(TagKey<Block> tag, Map<WoodVariant, Block>... maps) {
        for (Map<WoodVariant, Block> map : maps) {
            getOrCreateTagBuilder(tag).add(map.values().toArray(Block[]::new));
        }
    }
}
