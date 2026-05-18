package net.deadlydiamond.legend_of_steve.client.rendering.entity.living;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.TektiteEntityModel;
import net.deadlydiamond.legend_of_steve.common.entities.living.TektiteEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TektiteEntityRenderer extends MobEntityRenderer<TektiteEntity, TektiteEntityModel<TektiteEntity>> {
    public TektiteEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new TektiteEntityModel<>(ctx.getPart(TektiteEntityModel.LAYER_LOCATION)), 0.8f);
    }

    @Override
    public Identifier getTexture(TektiteEntity entity) {
        return LegendOfSteve.id("textures/entity/tektite/blue_tektite.png");
    }

    @Override
    protected float getLyingAngle(TektiteEntity entity) {
        return 180;
    }
}
