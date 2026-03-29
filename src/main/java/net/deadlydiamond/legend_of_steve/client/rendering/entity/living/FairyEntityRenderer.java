package net.deadlydiamond.legend_of_steve.client.rendering.entity.living;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.FairyEntityModel;
import net.deadlydiamond.legend_of_steve.common.entities.living.fairy.FairyEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.util.Identifier;

public class FairyEntityRenderer extends LivingEntityRenderer<FairyEntity, FairyEntityModel<FairyEntity>> {
    private static final Identifier WING_TEXTURE = LegendOfSteve.id("textures/entity/fairy/fairy_wing.png");

    public FairyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new FairyEntityModel<>(ctx.getPart(FairyEntityModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public Identifier getTexture(FairyEntity entity) {
        return WING_TEXTURE;
    }
}
