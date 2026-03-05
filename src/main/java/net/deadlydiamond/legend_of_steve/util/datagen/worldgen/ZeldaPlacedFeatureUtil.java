package net.deadlydiamond.legend_of_steve.util.datagen.worldgen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ZeldaPlacedFeatureUtil {

    // Ore Helpers

    public static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    public static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    public static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }

    // Default Registration Method

    public static void registerPlaced(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> placedKey, RegistryKey<ConfiguredFeature<?, ?>> configKey, List<PlacementModifier> modifiers) {
        context.register(placedKey, new PlacedFeature(context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE).getOrThrow(configKey), List.copyOf(modifiers)));
    }
}
