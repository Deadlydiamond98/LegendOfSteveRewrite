package net.deadlydiamond.legend_of_steve;

import net.deadlydiamond.legend_of_steve.init.*;
import net.deadlydiamond.legend_of_steve.client.rendering.player.ZeldaPlayerRendering;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegendOfSteve implements ModInitializer {
	public static final String MOD_ID = "legend_of_steve";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Registry
		ZeldaItems.register();
		ZeldaBlocks.register();
		ZeldaEntityTypes.register();
		ZeldaSounds.register();
		ZeldaPlayerRendering.register();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}
}