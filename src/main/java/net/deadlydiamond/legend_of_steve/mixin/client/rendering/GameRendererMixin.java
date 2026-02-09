package net.deadlydiamond.legend_of_steve.mixin.client.rendering;

import net.deadlydiamond.legend_of_steve.client.GradientsTexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Unique private GradientsTexture deadlys_ores$gradientsTexture;

    // TODO: Might Remove & Just Make Gradients an Asset

    @Inject(method = "<init>", at = @At("RETURN"))
    private void deadlys_ores$init(MinecraftClient client, HeldItemRenderer heldItemRenderer, ResourceManager resourceManager, BufferBuilderStorage buffers, CallbackInfo ci) {
        this.deadlys_ores$gradientsTexture = new GradientsTexture(client);
    }

    @Inject(method = "close", at = @At("RETURN"))
    private void deadlys_ores$close(CallbackInfo ci) {
        this.deadlys_ores$gradientsTexture.close();
    }
}
