package net.deadlydiamond.legend_of_steve.client.rendering.entity.living.bombfish;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombfishEntityModel;
import net.deadlydiamond.legend_of_steve.common.entities.living.fish.BombfishEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;

public class BombfishEyesFeatureRenderer<T extends BombfishEntity, M extends BombfishEntityModel<T>> extends EyesFeatureRenderer<T, M> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(LegendOfSteve.id("textures/entity/bombfish/bombfish_eyes.png"));

    public BombfishEyesFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
