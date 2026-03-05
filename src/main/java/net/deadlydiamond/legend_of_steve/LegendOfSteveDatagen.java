package net.deadlydiamond.legend_of_steve;

import net.deadlydiamond.legend_of_steve.datagen.*;
import net.deadlydiamond.legend_of_steve.worldgen.ZeldaFeaturesDatagen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class LegendOfSteveDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ZeldaModelDatagen::new);
		pack.addProvider(ZeldaItemTagDatagen::new);
		pack.addProvider(ZeldaBlockTagDatagen::new);
		pack.addProvider(ZeldaLootTableDatagen::new);
		pack.addProvider(ZeldaWorldGenDatagen::new);
		pack.addProvider(ZeldaRecipeDatagen::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ZeldaFeaturesDatagen::registerFeatureConfigs);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ZeldaFeaturesDatagen::registerPlacedFeatures);
	}
}
