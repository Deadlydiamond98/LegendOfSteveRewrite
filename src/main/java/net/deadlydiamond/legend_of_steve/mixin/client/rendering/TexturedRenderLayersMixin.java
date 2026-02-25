package net.deadlydiamond.legend_of_steve.mixin.client.rendering;

import net.deadlydiamond.legend_of_steve.client.SpriteIdentifierRegistry;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
    @Inject(method = "addDefaultTextures", at = @At("RETURN"))
    private static void legend_of_steve$addDefaultTextures(Consumer<SpriteIdentifier> consumer, CallbackInfo info) {
        SpriteIdentifierRegistry.getSpriteIdentifiers().forEach(consumer);
    }
}
