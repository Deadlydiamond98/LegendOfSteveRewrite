package net.deadlydiamond.legend_of_steve.client.rendering.entity.living;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.BaseTektiteEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class RedTektiteEntityRenderer extends BlueTektiteEntityRenderer {
    public static final Identifier TEXTURE = LegendOfSteve.id("textures/entity/tektite/red_tektite.png");

    public RedTektiteEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(BaseTektiteEntity entity) {
        return TEXTURE;
    }
}
