package net.deadlydiamond.legend_of_steve.mixin.client.model;

import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;

@Mixin(JsonUnbakedModel.class)
public abstract class JsonUnbakedModelMixin {
    @WrapMethod(method = "resolveSprite")
    private SpriteIdentifier resolveSpriteWithAtlas(String spriteName, Operation<SpriteIdentifier> original) {
        int atIndex = spriteName.indexOf('@');

        if (atIndex == -1) return original.call(spriteName);

        Identifier sprite = Identifier.tryParse(spriteName.substring(0, atIndex - 1));
        Identifier atlas = Identifier.tryParse(spriteName.substring(atIndex + 1));

        if (sprite == null) throw new InvalidIdentifierException(spriteName.substring(0, atIndex - 1) + " is not a valid Identifier");
        if (atlas == null) throw new InvalidIdentifierException(spriteName.substring(atIndex + 1) + " is not a valid Identifier");

        return new SpriteIdentifier(atlas, sprite);
    }

    @Mixin(JsonUnbakedModel.Deserializer.class)
    public static class DeserializerMixin {
        @WrapMethod(method = "resolveReference")
        private static Either<SpriteIdentifier, String> resolveSpriteWithAtlas(Identifier ignored, String name, Operation<Either<SpriteIdentifier, String>> original) {
            int atIndex = name.indexOf('@');

            if (atIndex == -1) return original.call(ignored, name);

            Identifier sprite = Identifier.tryParse(name.substring(0, atIndex));
            Identifier atlas = Identifier.tryParse(name.substring(atIndex + 1));

            if (sprite == null) throw new InvalidIdentifierException(name.substring(0, atIndex) + " is not a valid Identifier");
            if (atlas == null) throw new InvalidIdentifierException(name.substring(atIndex + 1) + " is not a valid Identifier");

            return Either.left(new SpriteIdentifier(atlas, sprite));
        }
    }
}