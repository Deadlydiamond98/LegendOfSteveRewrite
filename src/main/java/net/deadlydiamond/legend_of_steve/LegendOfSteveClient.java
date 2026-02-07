package net.deadlydiamond.legend_of_steve;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
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

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ZeldaBlocks.BOMB_FLOWER
        );
    }
}
