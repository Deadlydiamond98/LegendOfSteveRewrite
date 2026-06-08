package net.deadlydiamond.legend_of_steve.client.models.block;

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
    private final Identifier onModelID;
    private final Identifier offModelID;

    public SwitchBlockUnbakedModel(Identifier id) {
        this.onModelID = id.withSuffixedPath("_on");
        this.offModelID = id.withSuffixedPath("_off");
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptySet();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {}

    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        BakedModel onModel = baker.bake(this.onModelID, rotationContainer);
        BakedModel offModel = baker.bake(this.offModelID, rotationContainer);
        return new SwitchBlockBakedModel(onModel, offModel);
    }
}
