package net.deadlydiamond.legend_of_steve.client.models.block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class SwitchBlockUnbakedModel implements UnbakedModel {
    private static final Identifier ON_MODEL_ID = LegendOfSteve.id("block/red_switch_block_on");
    private static final Identifier OFF_MODEL_ID = LegendOfSteve.id("block/red_switch_block_off");

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptySet();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {
    }

    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        BakedModel onModel = baker.bake(ON_MODEL_ID, rotationContainer);
        BakedModel offModel = baker.bake(OFF_MODEL_ID, rotationContainer);
        return new SwitchBlockBakedModel(onModel, offModel);
    }
}
