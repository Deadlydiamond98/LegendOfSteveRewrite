package net.deadlydiamond.legend_of_steve.client.rendering.entity.living.bombfish;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombfishEntityModel;
import net.deadlydiamond.legend_of_steve.common.entities.living.fish.BombfishEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class BombfishEntityRenderer extends MobEntityRenderer<BombfishEntity, BombfishEntityModel<BombfishEntity>> {

    private static final Identifier TEXTURE = LegendOfSteve.id("textures/entity/bombfish/teal_red_bombfish.png");

    public BombfishEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BombfishEntityModel<>(ctx.getPart(BombfishEntityModel.LAYER_LOCATION)), 0.15f);
        this.addFeature(new BombfishEyesFeatureRenderer<>(this));
        this.addFeature(new BombfishWarningFlashRenderer(this));
    }

    @Override
    protected void scale(BombfishEntity entity, MatrixStack matrices, float tickDelta) {
        float time = entity.isPrimed() ? (entity.getLitTime() + tickDelta) : entity.getLitTime();
        float fuse = entity.getFuse() - tickDelta;
        float scale = 1 + 0.05f * MathHelper.sin(time * 0.2f);

        if (fuse <= 3) {
            scale += (-fuse + 3) * 0.35f;
        }

        matrices.scale(scale, scale, scale);
    }

    @Override
    protected void setupTransforms(BombfishEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        float i = 4.3F * MathHelper.sin(0.6F * animationProgress);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(i));
        if (!entity.isTouchingWater()) {
            matrices.translate(0.2F, 0.1F, 0.0F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
        }
    }

    @Override
    public Identifier getTexture(BombfishEntity entity) {
        return TEXTURE;
    }
}
