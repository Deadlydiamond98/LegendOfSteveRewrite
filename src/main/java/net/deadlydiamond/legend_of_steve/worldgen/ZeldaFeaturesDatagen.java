package net.deadlydiamond.legend_of_steve.worldgen;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import static net.deadlydiamond.legend_of_steve.util.datagen.worldgen.ZeldaFeatureConfigUtil.*;
import static net.deadlydiamond.legend_of_steve.util.datagen.worldgen.ZeldaPlacedFeatureUtil.*;

public class ZeldaFeaturesDatagen {

    // KEYS ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Config
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEKU_TREE_CFG = registerKeyCFG("deku_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MASTER_ORE_CFG = registerKeyCFG("master_ore");

    // Placed
    public static final RegistryKey<PlacedFeature> MASTER_ORE_PLACED = registerPlacedKey("master_ore_placed");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FEATURE CONFIGS /////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void registerFeatureConfigs(Registerable<ConfiguredFeature<?, ?>> context) {

        // Deku Tree
        registerCFG(context, DEKU_TREE_CFG, ZeldaFeatures.DEKU_TREE_FEATURE, new DefaultFeatureConfig());

        // Master Ore
        registerOverworldOre(context, MASTER_ORE_CFG,
                5, 0.5f,
                ZeldaBlocks.MASTER_ORE,
                ZeldaBlocks.DEEPSLATE_MASTER_ORE
        );
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PLACED FEATURES /////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void registerPlacedFeatures(Registerable<PlacedFeature> context) {
        // Master Ore
        registerPlaced(context, MASTER_ORE_PLACED, MASTER_ORE_CFG,
                modifiersWithCount(4, HeightRangePlacementModifier.trapezoid(
                        YOffset.aboveBottom(-80), YOffset.aboveBottom(80))
                )
        );
    }

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    private static RegistryKey<ConfiguredFeature<?, ?>> registerKeyCFG(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, LegendOfSteve.id(id));
    }

    private static RegistryKey<PlacedFeature> registerPlacedKey(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, LegendOfSteve.id(id));
    }
}
