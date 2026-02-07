package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.client.models.entity.BombEntityModel;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombEntitySlimeModel;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.BombEntityRenderer;
import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ZeldaRenderers {
    public static void register() {
        registerEntityRenderers();
        registerModelLayers();
    }

    private static void registerEntityRenderers() {
        EntityRendererRegistry.register(ZeldaEntityTypes.BOMB, BombEntityRenderer::new);
    }

    private static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(BombEntityModel.LAYER_LOCATION, BombEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BombEntitySlimeModel.LAYER_LOCATION, BombEntitySlimeModel::getTexturedModelData);
    }
}
