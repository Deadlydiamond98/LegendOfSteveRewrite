package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.worldgen.ZeldaFeaturesDatagen;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class ZeldaBiomeModifications {

    public static void register() {
        registerFeatures();
    }

    private static void registerFeatures() {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ZeldaFeaturesDatagen.MASTER_ORE_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                ZeldaFeaturesDatagen.BOMB_FLOWER_PLACED
        );
    }
}
