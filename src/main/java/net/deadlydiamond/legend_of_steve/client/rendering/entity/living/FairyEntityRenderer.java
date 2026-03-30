package net.deadlydiamond.legend_of_steve.client.rendering.entity.living;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.FairyEntityModel;
import net.deadlydiamond.legend_of_steve.common.entities.living.FairyColor;
import net.deadlydiamond.legend_of_steve.common.entities.living.fairy.FairyEntity;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FairyEntityRenderer extends MobEntityRenderer<FairyEntity, FairyEntityModel<FairyEntity>> {
    private static final Identifier WING_TEXTURE = LegendOfSteve.id("textures/entity/fairy/fairy_wing.png");

    public FairyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new FairyEntityModel<>(ctx.getPart(FairyEntityModel.LAYER_LOCATION)), 0.125f);
    }

    @Override
    public void render(FairyEntity mobEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isAlive()) {
            super.render(mobEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, i);
            Identifier center = mobEntity.getColor().getTexture();

            float pulse = (float) (Math.sin((mobEntity.age + tickDelta) * 0.025) * 0.015 + 0.015);
            VertexConsumer glowVCon = vertexConsumerProvider.getBuffer(ZeldaRenderLayers.getGlowing(center));
            this.model.renderCenterPart(matrixStack, glowVCon, 0.17f + pulse, i, getAlpha(mobEntity, tickDelta));

            VertexConsumer mainVCon = vertexConsumerProvider.getBuffer(ZeldaRenderLayers.getEntityUnlit(center));
            this.model.renderCenterPart(matrixStack, mainVCon, 0.15f, i, 255);
        }
    }

    // This Determines the Intensity of the glowing Layer!
    private int getAlpha(FairyEntity entity, float tickDelta) {
        int maxAlpha = 125;

        World world = entity.getWorld();
        float time = (int) (world.getTimeOfDay() % 24000) + tickDelta;
        int blockLight = world.getLightLevel(LightType.BLOCK, entity.getBlockPos());
        int skyLight = world.getLightLevel(LightType.SKY, entity.getBlockPos());

        float alpha;
        if (time >= 22782) {
            alpha = (0.123153f * time) + 2955.66502f;
        } else {
            alpha = (0.0912964f * time) - 1056.84723f;
        }

        alpha = Math.max(0, Math.min(alpha, maxAlpha));
        alpha += (-10 * skyLight) + maxAlpha;

        alpha = Math.max(0, Math.min(alpha, maxAlpha));
        alpha -= (10 * blockLight);

        return (int) Math.floor(Math.max(0, Math.min(alpha, maxAlpha)));
    }

    @Override
    protected void scale(FairyEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(0.5f, 0.5f, 0.5f);
    }

    @Nullable
    @Override
    protected RenderLayer getRenderLayer(FairyEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        return ZeldaRenderLayers.getEntityUnlit(this.getTexture(entity));
    }

    @Override
    public Identifier getTexture(FairyEntity entity) {
        return WING_TEXTURE;
    }
}
