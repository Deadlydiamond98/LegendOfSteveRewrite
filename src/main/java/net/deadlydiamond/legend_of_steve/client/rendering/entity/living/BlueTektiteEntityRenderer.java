package net.deadlydiamond.legend_of_steve.client.rendering.entity.living;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.TektiteEntityModel;
import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.BaseTektiteEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class BlueTektiteEntityRenderer extends MobEntityRenderer<BaseTektiteEntity, TektiteEntityModel<BaseTektiteEntity>> {
    public static final Identifier TEXTURE = LegendOfSteve.id("textures/entity/tektite/blue_tektite.png");

    public BlueTektiteEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new TektiteEntityModel<>(ctx.getPart(TektiteEntityModel.LAYER_LOCATION)), 0.8f);
    }

    @Override
    public Identifier getTexture(BaseTektiteEntity entity) {
        return TEXTURE;
    }

    @Override
    protected float getLyingAngle(BaseTektiteEntity entity) {
        return 180;
    }
}
