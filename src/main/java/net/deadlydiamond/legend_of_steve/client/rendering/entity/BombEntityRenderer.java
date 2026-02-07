package net.deadlydiamond.legend_of_steve.client.rendering.entity;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombEntityModel;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombEntitySlimeModel;
import net.deadlydiamond.legend_of_steve.client.rendering.IBombRenderer;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.BombEntity;
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
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class BombEntityRenderer<T extends BombEntity> extends EntityRenderer<T> implements IBombRenderer {
    private static final Identifier FALLBACK = LegendOfSteve.id("textures/entity/bomb/bomb.png");
    private static final Identifier LOW_FUSE_OVERLAY = LegendOfSteve.id("textures/entity/bomb/fuse_overlay.png");

    private static final Identifier SLIME = LegendOfSteve.id("textures/entity/bomb/slime_overlay.png");
    private static final Identifier HONEY = LegendOfSteve.id("textures/entity/bomb/honey_overlay.png");


    private final BombEntityModel<BombEntity> entityModel;
    private final BombEntitySlimeModel<BombEntity> slimeOverlay;

    public BombEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.entityModel = new BombEntityModel<>(ctx.getPart(BombEntityModel.LAYER_LOCATION));
        this.slimeOverlay = new BombEntitySlimeModel<>(ctx.getPart(BombEntitySlimeModel.LAYER_LOCATION));
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

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-(entity.getYaw(tickDelta) + 90)));
        matrices.scale(-scale, -scale, scale);
        matrices.translate(0.0f, -1.501f, 0.0f);

        // Main Model //////////////////////////////////////////////////////////////////////////////////////////////////
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.entityModel.getLayer(getTexture(entity)));
        this.entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        if (entity.isPrimed()) {
            renderFuse(entity, tickDelta, matrices, vertexConsumers, light);
        } else {
            this.entityModel.renderFuse(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        }

        // Red Flash Warning ///////////////////////////////////////////////////////////////////////////////////////////
        int startFlash = 15;
        if (fuse <= startFlash) {
            float lowFuseFlash = (float) Math.abs(Math.sin(fuse * 0.4) * 0.5);

            VertexConsumer warningFlashVCon = vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(LOW_FUSE_OVERLAY, true));
            this.entityModel.renderOverlay(matrices, warningFlashVCon, 15728640, OverlayTexture.DEFAULT_UV, 1, 1, 1,
                    lowFuseFlash);
            VertexConsumer glow = vertexConsumers.getBuffer(ZeldaRenderLayers.getGlowing(LOW_FUSE_OVERLAY));
            this.entityModel.renderOverlay(matrices, glow, 15728640, OverlayTexture.DEFAULT_UV, 1, 1, 1,
                    Math.max(0, lowFuseFlash - 0.15f));
        }

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void renderFuse(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(180));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
        matrices.translate(0.03125 + (0.03125 / 4), -0.8125 - 0.03125, 0);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-22.5f));

        float minU = 0.53125f;
        float maxU = 0.5625f;
        float minV = 0.5f;
        float maxV = 0.625f;

        float maxY = entity.getFuseBurnTimer(tickDelta);
        float fuseMinV = minV + (maxV - minV) * (1 - maxY) * 0.5f;

        if (maxY > -2) {
            VertexConsumer fuseVCon = vertexConsumers.getBuffer(this.entityModel.getLayer(getTexture(entity)));
            renderFuseFace(matrices, fuseVCon, minU, maxU, fuseMinV, maxV, light, 0, -1, maxY);

            VertexConsumer emberVCon = vertexConsumers.getBuffer(ZeldaRenderLayers.getBombFuse(getTexture(entity)));
            renderFuseFace(matrices, emberVCon, minU + 0.03125f, maxU + 0.03125f, minV, maxV, light, 0.001f, maxY - 1, maxY);

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            renderFuseFace(matrices, emberVCon, minU + 0.03125f, maxU + 0.03125f, minV, maxV, light, 0.001f, maxY - 1, maxY);
        }

        matrices.pop();
    }

    public void renderFuseFace(MatrixStack matrices, VertexConsumer vertexConsumer, float minU, float maxU, float minV, float maxV, int light, float z, float minY, float maxY) {
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f modelMatrix = entry.getPositionMatrix();
        Matrix3f normalMatrix = entry.getNormalMatrix();

        float size = 0.03125f;
        float y0 = size * minY * 4;
        float y1 = size * maxY * 4;
        vertexConsumer.vertex(modelMatrix, size,  y1, z).color(255, 255, 255, 255).texture(maxU, minV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix,  -size,  y1, z).color(255, 255, 255, 255).texture(minU, minV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix,  -size, y0, z).color(255, 255, 255, 255).texture(minU, maxV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, size, y0, z).color(255, 255, 255, 255).texture(maxU, maxV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0, 1, 0).next();
    }

    @Override
    public Identifier getTexture(T entity) {
        return getBombTexture(entity);
    }
}
