package net.deadlydiamond.legend_of_steve.util.wood;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class WoodVariantUtil {
    private static final Map<WoodVariant, Set<Block>> WOOD_BLOCKS = new LinkedHashMap<>();
    private static final Map<WoodVariant, Set<AbstractBlockset>> WOOD_BLOCKSETS = new LinkedHashMap<>();

    public static Map<WoodVariant, Set<AbstractBlockset>> getWoodBlocksets() {
        return WOOD_BLOCKSETS;
    }

    public static Map<WoodVariant, Set<Block>> getWoodBlocks() {
        return WOOD_BLOCKS;
    }

    private static Map<WoodVariant, Set<Block>> getOrganized() {
        Map<WoodVariant, Set<Block>> blockMap = new LinkedHashMap<>(getWoodBlocks());
        getWoodBlocksets().forEach((woodVariant, abstractBlocksets) -> {
            Set<Block> blocks = blockMap.getOrDefault(woodVariant, new LinkedHashSet<>());
            for (AbstractBlockset abstractBlockset : abstractBlocksets) {
                blocks.addAll(Arrays.asList(abstractBlockset.getAll()));
            }
            blockMap.put(woodVariant, blocks);
        });
        return blockMap;
    }

    public static void addToCreative(ItemGroup.Entries entry) {
        getOrganized().forEach((woodVariant, blocks) -> blocks.forEach(entry::add));
    }

    // BLOCKSET DATAGEN

    private static void applyToAllBlocksets(Consumer<AbstractBlockset> blocksetConsumer) {
        getWoodBlocksets().forEach((variant, abstractBlocksets) -> abstractBlocksets.forEach(blocksetConsumer));
    }

    public static void generateWoodModels(BlockStateModelGenerator generator) {
        applyToAllBlocksets(blockset -> blockset.generateModels(generator));
    }

    public static void generateWoodItemTags(BiConsumer<TagKey<Item>, ItemConvertible> tagConsumer) {
        applyToAllBlocksets(blockset -> blockset.generateItemTags(tagConsumer));
    }

    @SuppressWarnings("unchecked")
    public static void generateWoodBlockTags(BiConsumer<TagKey<Block>, Block> tagConsumer) {
        applyToAllBlocksets(blockset -> blockset.generateBlockTags(tagConsumer, BlockTags.AXE_MINEABLE));
    }

    public static void generateLootTables(FabricBlockLootTableProvider lootTableProvider) {
        getWoodBlocks().forEach((woodVariant, blocks) -> blocks.forEach(block -> {
            if (block instanceof DoorBlock) {
                lootTableProvider.addDrop(block, lootTableProvider.doorDrops(block));
            } else if (block instanceof SlabBlock) {
                lootTableProvider.addDrop(block, lootTableProvider.slabDrops(block));
            } else {
                lootTableProvider.addDrop(block);
            }
        }));

        applyToAllBlocksets(blockset -> blockset.generateLootTables(lootTableProvider));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REGISTRY ////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static <T extends AbstractBlockset> Map<WoodVariant, T> registerWoodVariantsBlockset(Function<WoodVariant, T> function) {
        Map<WoodVariant, T> blocksets = new HashMap<>();

        for (WoodVariant woodVariant : WoodVariants.getAll()) {
            T blockset = function.apply(woodVariant);
            Set<AbstractBlockset> savedBlocksets = WOOD_BLOCKSETS.getOrDefault(woodVariant, new LinkedHashSet<>());
            savedBlocksets.add(blockset);
            WOOD_BLOCKSETS.put(woodVariant, savedBlocksets);
            blocksets.put(woodVariant, blockset);
        }

        return blocksets;
    }

    public static Map<WoodVariant, Block> registerWoodVariantsBlock(Function<WoodVariant, Block> blockFunction) {
        Map<WoodVariant, Block> blocks = new HashMap<>();

        for (WoodVariant woodVariant : WoodVariants.getAll()) {
            Block block = blockFunction.apply(woodVariant);
            Set<Block> savedBlocks = WOOD_BLOCKS.getOrDefault(woodVariant, new LinkedHashSet<>());
            savedBlocks.add(block);
            WOOD_BLOCKS.put(woodVariant, savedBlocks);
            blocks.put(woodVariant, block);
        }
        return blocks;
    }
}
