package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.client.models.BasicCubeModel;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombEntityModel;
import net.deadlydiamond.legend_of_steve.client.models.feature.QuiverModel;
import net.deadlydiamond.legend_of_steve.client.rendering.block.BombFlowerRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.block.baked.glowing.GlowingBlockRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.BombEntityRenderer;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class ZeldaRenderers {
    public static void register() {
        registerEntityRenderers();
        registerBlockEntityRenderers();
        registerModelLayers();
    }

    private static void registerEntityRenderers() {
        EntityRendererRegistry.register(ZeldaEntityTypes.BOMB, BombEntityRenderer::new);
    }

    private static void registerBlockEntityRenderers() {
        BlockEntityRendererFactories.register(ZeldaBlockEntities.BOMB_FLOWER, BombFlowerRenderer::new);
        BlockEntityRendererFactories.register(ZeldaBlockEntities.GLOWING_BLOCK, GlowingBlockRenderer::new);
    }

    private static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(BombEntityModel.LAYER_LOCATION, BombEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(QuiverModel.LAYER_LOCATION, QuiverModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BasicCubeModel.LAYER_LOCATION, BasicCubeModel::getTexturedModelData);
    }
}
