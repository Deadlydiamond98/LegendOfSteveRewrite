package net.deadlydiamond.legend_of_steve;

import net.deadlydiamond.legend_of_steve.client.rendering.player.ZeldaPlayerRendering;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaFluids;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderers;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaShaders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class LegendOfSteveClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ZeldaShaders.register();
        ZeldaRenderers.register();
        ZeldaPlayerRendering.register();

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ZeldaBlocks.BOMB_FLOWER
        );

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ZeldaFluids.ENCHANTED_SPRING_WATER,
                ZeldaFluids.FLOWING_ENCHANTED_SPRING_WATER
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(ZeldaRenderLayers.IRIDESCENCE,
                ZeldaBlocks.DEBUG
//                ZeldaFluids.FLOWING_ENCHANTED_SPRING_WATER
        );
    }
}
