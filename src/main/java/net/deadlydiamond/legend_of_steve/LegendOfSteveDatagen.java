package net.deadlydiamond.legend_of_steve;

import net.deadlydiamond.legend_of_steve.datagen.ZeldaModelDatagen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class LegendOfSteveDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ZeldaModelDatagen::new);
	}
}
