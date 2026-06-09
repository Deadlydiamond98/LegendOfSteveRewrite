package net.deadlydiamond.legend_of_steve.mixin.client.rendering.world;

import net.deadlydiamond.legend_of_steve.client.SwitchBlockAtlasBackup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void legend_of_steve$init(MinecraftClient client, HeldItemRenderer heldItemRenderer, ResourceManager resourceManager, BufferBuilderStorage buffers, CallbackInfo ci) {
        SwitchBlockAtlasBackup.INSTANCE = new SwitchBlockAtlasBackup(client);
    }


    @Inject(method = "close", at = @At("RETURN"))
    private void legend_of_steve$close(CallbackInfo ci) {
        if (SwitchBlockAtlasBackup.INSTANCE != null) {
            SwitchBlockAtlasBackup.INSTANCE.close();
        }
    }
}
