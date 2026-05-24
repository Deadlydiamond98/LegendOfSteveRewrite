package net.deadlydiamond.legend_of_steve.client.rendering.entity.living;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.ArurodaEntityModel;
import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.ArurodaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ArurodaEntityRenderer extends MobEntityRenderer<ArurodaEntity, ArurodaEntityModel<ArurodaEntity>> {
    public static final Identifier TEXTURE = LegendOfSteve.id("textures/entity/aruroda/aruroda.png");

    public ArurodaEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ArurodaEntityModel<>(ctx.getPart(ArurodaEntityModel.LAYER_LOCATION)), 0.8f);
    }

    @Override
    public Identifier getTexture(ArurodaEntity entity) {
        return TEXTURE;
    }

    @Override
    protected float getLyingAngle(ArurodaEntity entity) {
        return 180;
    }
}
