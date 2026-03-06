package net.deadlydiamond.legend_of_steve;

import net.deadlydiamond.legend_of_steve.client.rendering.player.ZeldaPlayerRendering;
import net.deadlydiamond.legend_of_steve.events.client.ZeldaWorldRenderEvents;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.client.*;
import net.deadlydiamond98.koalalib.client.SpriteIdentifierRegistry;
import net.fabricmc.api.ClientModInitializer;

public class LegendOfSteveClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ZeldaShaders.register();
        ZeldaRenderers.register();
        ZeldaPlayerRendering.register();
        ZeldaBlockRenderLayers.register();
        ZeldaModelPredicates.register();

        SpriteIdentifierRegistry.registerSigns(ZeldaBlocks.DEKU_WOOD);

        ZeldaWorldRenderEvents.register();
    }
}
