package net.deadlydiamond.legend_of_steve.worldgen;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.worldgen.feature.LargeDekuTreeFeature;
import net.deadlydiamond.legend_of_steve.worldgen.feature.SmallDekuTreeFeature;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class ZeldaFeatures {
    public static final SmallDekuTreeFeature DEKU_TREE_FEATURE = register("deku_tree", new SmallDekuTreeFeature(DefaultFeatureConfig.CODEC));
    public static final LargeDekuTreeFeature LARGE_DEKU_TREE_FEATURE = register("large_deku_tree", new LargeDekuTreeFeature(DefaultFeatureConfig.CODEC));

    public static <T extends Feature<?>> T register(String id, T feature) {
        return Registry.register(Registries.FEATURE, LegendOfSteve.id(id), feature);
    }

    public static void register() {}
}
