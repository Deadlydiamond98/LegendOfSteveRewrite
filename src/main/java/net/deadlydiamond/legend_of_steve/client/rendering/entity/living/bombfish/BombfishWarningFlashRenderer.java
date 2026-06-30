package net.deadlydiamond.legend_of_steve.client.rendering.entity.living.bombfish;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombfishEntityModel;
import net.deadlydiamond.legend_of_steve.common.entities.living.fish.BombfishEntity;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BombfishWarningFlashRenderer extends FeatureRenderer<BombfishEntity, BombfishEntityModel<BombfishEntity>> {

    private static final Identifier FUSE_OVERLAY = LegendOfSteve.id("textures/entity/bombfish/bombfish_fuse_overlay.png");

    public BombfishWarningFlashRenderer(FeatureRendererContext<BombfishEntity, BombfishEntityModel<BombfishEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BombfishEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        float fuse = entity.getFuse() - tickDelta;

        int startFlash = 15;
        if (fuse <= startFlash && entity.isPrimed()) {
            float lowFuseFlash = (float) Math.abs(Math.sin(fuse * 0.4) * 0.5);

            VertexConsumer warningFlashVCon = vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(FUSE_OVERLAY, true));
            this.getContextModel().render(matrices, warningFlashVCon, 15728640, OverlayTexture.DEFAULT_UV, 1, 1, 1, lowFuseFlash);

            VertexConsumer glow = vertexConsumers.getBuffer(ZeldaRenderLayers.getEntityBloomGlow(FUSE_OVERLAY));
            this.getContextModel().render(matrices, glow, 15728640, OverlayTexture.DEFAULT_UV, 1, 1, 1,
                    Math.max(0, lowFuseFlash - 0.15f));
        }
        matrices.pop();
    }
}
