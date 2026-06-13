package net.deadlydiamond.legend_of_steve.client.models.block.connected;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class ConnectedPillarUnbakedModel implements UnbakedModel {
    private final List<SpriteIdentifier> sprites;

    public ConnectedPillarUnbakedModel(Identifier block) {
        this.sprites = ConnectedTextureTypes.getSprites(block, ConnectedTextureTypes.Pillar.class);
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        Sprite[] sprites = new Sprite[this.sprites.size()];

        for (int i = 0; i < sprites.length; ++i) {
            sprites[i] = textureGetter.apply(this.sprites.get(i));
        }
        return new ConnectedPillarBakedModel(sprites);
    }
}
