package net.deadlydiamond.legend_of_steve.client.rendering.entity.projectile.bomb;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombfishEntityModel;
import net.deadlydiamond.legend_of_steve.common.entities.living.fish.BombfishVarients;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.WaterBombEntity;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class WaterBombRenderer<T extends WaterBombEntity> extends EntityRenderer<T> {
    private static final Identifier TEXTURE = LegendOfSteve.id("textures/entity/bombfish/cyan_red_bombfish.png");
    private static final Identifier EYES_TEXTURE = LegendOfSteve.id("textures/entity/bombfish/bombfish_eyes.png");
    private static final Identifier FUSE_OVERLAY = LegendOfSteve.id("textures/entity/bombfish/bombfish_fuse_overlay.png");
    private final BombfishEntityModel<T> model;

    public WaterBombRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new BombfishEntityModel<>(ctx.getPart(BombfishEntityModel.LAYER_LOCATION));
    }


    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        float time = entity.isPrimed() ? (entity.getLitTime() + tickDelta) : entity.getLitTime();
        float fuse = entity.getFuse() - tickDelta;
        float scale = 1 + 0.05f * MathHelper.sin(time * 0.2f);

        if (fuse <= 3) {
            scale += (-fuse + 3) * 0.35f;
        }

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-(entity.getYaw() + 90)));
        matrices.translate(0.3, 0.3, 0);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
        matrices.scale(-scale, -scale, scale);
        matrices.translate(0, -1.501, 0);

        // Main Model //////////////////////////////////////////////////////////////////////////////////////////////////
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(getTexture(entity)));
        this.model.setDeadAngles(entity, tickDelta);
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);

        VertexConsumer eyesVertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEyes(EYES_TEXTURE));
        this.model.render(matrices, eyesVertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);

        // Overlay Model ///////////////////////////////////////////////////////////////////////////////////////////////
        if (entity.isCharged()) {
            matrices.push();
            matrices.translate(0, -0.25 - 0.0625, 0);
            matrices.scale(1.25f, 1.25f, 1.25f);
            VertexConsumer chargedOverlay = BombRenderHelper.getChargedLayer(entity, vertexConsumers, tickDelta);
            this.model.renderChargedLayer(matrices, chargedOverlay, 15728640, OverlayTexture.DEFAULT_UV, 0.5f, 0.5f, 0.5f, 1);
            matrices.pop();
        }

        // Red Flash Warning ///////////////////////////////////////////////////////////////////////////////////////////
        int startFlash = 15;
        if (fuse <= startFlash && entity.isPrimed()) {
            float lowFuseFlash = (float) Math.abs(Math.sin(fuse * 0.4) * 0.5);

            VertexConsumer warningFlashVCon = vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(FUSE_OVERLAY, true));
            this.model.render(matrices, warningFlashVCon, 15728640, OverlayTexture.DEFAULT_UV, 1, 1, 1, lowFuseFlash);

            VertexConsumer glow = vertexConsumers.getBuffer(ZeldaRenderLayers.getEntityBloomGlow(FUSE_OVERLAY));
            this.model.render(matrices, glow, 15728640, OverlayTexture.DEFAULT_UV, 1, 1, 1,
                    Math.max(0, lowFuseFlash - 0.15f));
        }

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(T entity) {
        return BombfishVarients.TEXTURES.get(entity.getColor());
    }
}
