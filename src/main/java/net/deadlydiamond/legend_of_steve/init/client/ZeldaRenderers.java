package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.client.models.block.lock.ChestLockLeftModel;
import net.deadlydiamond.legend_of_steve.client.models.block.lock.ChestLockModel;
import net.deadlydiamond.legend_of_steve.client.models.block.lock.ChestLockRightModel;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombEntityModel;
import net.deadlydiamond.legend_of_steve.client.models.entity.FairyEntityModel;
import net.deadlydiamond.legend_of_steve.client.models.entity.TektiteEntityModel;
import net.deadlydiamond.legend_of_steve.client.models.feature.QuiverModel;
import net.deadlydiamond.legend_of_steve.client.rendering.block.BombFlowerBlockEntityRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.block.HittableContainerBlockEntityRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.block.SwordPedestalBlockEntityRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.PushableBlockEntityRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.living.BlueTektiteEntityRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.living.RedTektiteEntityRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.projectile.BombEntityRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.entity.projectile.SwordBeamEntityRenderer;
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
        EntityRendererRegistry.register(ZeldaEntityTypes.CRATE, PushableBlockEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.DEKU_NUT, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.FAIRY, FairyEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.BLUE_TEKTITE, BlueTektiteEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.RED_TEKTITE, RedTektiteEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.PUSHABLE_BLOCK, PushableBlockEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.SWORD_BEAM, SwordBeamEntityRenderer::new);
        EntityRendererRegistry.register(ZeldaEntityTypes.THROWN_POT, ThrownPotEntityRenderer::new);
    }

    private static void registerBlockEntityRenderers() {
        BlockEntityRendererFactories.register(ZeldaBlockEntities.BOMB_FLOWER, BombFlowerBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ZeldaBlockEntities.HITTABLE_CONTAINER_BLOCK, HittableContainerBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ZeldaBlockEntities.SWORD_PEDESTAL, SwordPedestalBlockEntityRenderer::new);
    }

    private static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(BombEntityModel.LAYER_LOCATION, BombEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FairyEntityModel.LAYER_LOCATION, FairyEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TektiteEntityModel.LAYER_LOCATION, TektiteEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ChestLockLeftModel.LAYER_LOCATION, ChestLockLeftModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ChestLockModel.LAYER_LOCATION, ChestLockModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ChestLockRightModel.LAYER_LOCATION, ChestLockRightModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(QuiverModel.LAYER_LOCATION, QuiverModel::getTexturedModelData);
    }
}
