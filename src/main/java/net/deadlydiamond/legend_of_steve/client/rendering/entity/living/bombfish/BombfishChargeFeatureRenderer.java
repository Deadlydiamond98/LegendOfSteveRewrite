package net.deadlydiamond.legend_of_steve.client.rendering.entity.living.bombfish;

import net.deadlydiamond.legend_of_steve.client.models.entity.BombfishEntityModel;
import net.deadlydiamond.legend_of_steve.common.entities.living.fish.BombfishEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BombfishChargeFeatureRenderer extends EnergySwirlOverlayFeatureRenderer<BombfishEntity, BombfishEntityModel<BombfishEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/creeper/creeper_armor.png");
    private final BombfishEntityModel<BombfishEntity> model;

    public BombfishChargeFeatureRenderer(FeatureRendererContext<BombfishEntity, BombfishEntityModel<BombfishEntity>> context, EntityModelLoader loader) {
        super(context);
        this.model = new BombfishEntityModel<>(loader.getModelPart(BombfishEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BombfishEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.shouldRenderOverlay()) {

            matrices.push();

            matrices.translate(0, -0.25 - 0.0625, 0);
            matrices.scale(1.25f, 1.25f, 1.25f);

            float f = entity.age + tickDelta;
            BombfishEntityModel<BombfishEntity> entityModel = this.getEnergySwirlModel();
            entityModel.animateModel(entity, limbAngle, limbDistance, tickDelta);
            this.getContextModel().copyStateTo(entityModel);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(
                    RenderLayer.getEnergySwirl(this.getEnergySwirlTexture(), this.getEnergySwirlX(f) % 1, f * 0.01f % 1)
            );
            entityModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            entityModel.renderChargedLayer(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0.5f, 0.5f, 0.5f, 1);

            matrices.pop();
        }
    }

    @Override
    protected float getEnergySwirlX(float partialAge) {
        return partialAge * 0.01F;
    }

    @Override
    protected Identifier getEnergySwirlTexture() {
        return SKIN;
    }

    @Override
    protected BombfishEntityModel<BombfishEntity> getEnergySwirlModel() {
        return this.model;
    }
}
