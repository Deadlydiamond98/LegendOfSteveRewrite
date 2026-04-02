package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.client.models.entity.BombEntityModel;
import net.deadlydiamond.legend_of_steve.client.models.entity.FairyEntityModel;
import net.deadlydiamond.legend_of_steve.client.models.feature.QuiverModel;
import net.deadlydiamond.legend_of_steve.client.rendering.block.BombFlowerRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.block.QuestionBlockRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.PushableBlockEntityRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.projectile.BombEntityRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.projectile.ThrownPotEntityRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.living.FairyEntityRenderer;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class ZeldaRenderers {
    public static void register() {
        registerEntityRenderers();
        registerBlockEntityRenderers();
        registerModelLayers();
    }

    private static void registerEntityRenderers() {
        EntityRendererRegistry.register(ZeldaEntityTypes.BOMB, BombEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.DEKU_NUT, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.FAIRY, FairyEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.PUSHABLE_BLOCK, PushableBlockEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.THROWN_POT, ThrownPotEntityRenderer::new);
    }

    private static void registerBlockEntityRenderers() {
        BlockEntityRendererFactories.register(ZeldaBlockEntities.BOMB_FLOWER, BombFlowerRenderer::new);
        BlockEntityRendererFactories.register(ZeldaBlockEntities.HITTABLE_CONTAINER_BLOCK, QuestionBlockRenderer::new);
    }

    private static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(BombEntityModel.LAYER_LOCATION, BombEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FairyEntityModel.LAYER_LOCATION, FairyEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(QuiverModel.LAYER_LOCATION, QuiverModel::getTexturedModelData);
    }
}
