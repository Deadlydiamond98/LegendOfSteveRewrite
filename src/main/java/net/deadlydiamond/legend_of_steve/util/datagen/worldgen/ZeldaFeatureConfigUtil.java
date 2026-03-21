package net.deadlydiamond.legend_of_steve.util.datagen.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class ZeldaFeatureConfigUtil {

    // Ore Registration
    public static void registerOverworldOre(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, int size, float discardOnAirChance, Block stoneOre, Block deepslateOre) {
        registerOre(context, key, size, discardOnAirChance,
                OreFeatureConfig.createTarget(new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES), stoneOre.getDefaultState()),
                OreFeatureConfig.createTarget(new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), deepslateOre.getDefaultState())
        );
    }

    public static void registerOre(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, int size, float discardOnAirChance, OreFeatureConfig.Target... targets) {
        registerCFG(context, key, Feature.ORE, new OreFeatureConfig(List.of(targets), size, discardOnAirChance));
    }

    public static void registerRandomPatch(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, int tries, int xZSpread, int ySpread, BlockState... states) {
        DataPool.Builder<BlockState> builder = DataPool.builder();
        for (BlockState state : states) {
            builder.add(state, 1);
        }

        registerCFG(context, key, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(
                tries, xZSpread, ySpread, PlacedFeatures.createEntry(
                Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(builder)))
        ));
    }

    // Default Registration Method

    public static <FC extends FeatureConfig, F extends Feature<FC>> void registerCFG(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
