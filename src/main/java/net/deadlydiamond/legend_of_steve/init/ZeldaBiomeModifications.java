package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.worldgen.ZeldaFeaturesDatagen;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.gen.GenerationStep;

public class ZeldaBiomeModifications {

    public static void register() {
        registerFeatures();
        registerSpawns();
    }

    private static void registerFeatures() {
        // Master Ore
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ZeldaFeaturesDatagen.MASTER_ORE_PLACED
        );

        // Bomb Flower
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                ZeldaFeaturesDatagen.BOMB_FLOWER_PLACED
        );

        // Deku Tree
        BiomeModifications.addFeature(
                BiomeSelectors.tag(ZeldaTags.GENERATES_DEKU_TREES),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ZeldaFeaturesDatagen.LARGE_DEKU_TREE_PLACED
        );

        BiomeModifications.addFeature(
                BiomeSelectors.tag(ZeldaTags.GENERATES_DEKU_TREES),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ZeldaFeaturesDatagen.DEKU_TREE_PLACED
        );

        // Loot Grass
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ZeldaFeaturesDatagen.LOOT_GRASS_PLACED
        );
    }

    private static void registerSpawns() {
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                SpawnGroup.AMBIENT,
                ZeldaEntityTypes.FAIRY,
                2,
                1,
                2
        );
    }
}
