package net.deadlydiamond.legend_of_steve.mixin.client.gui;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {

    @Inject(method = "keyPressed", at = @At("TAIL"))
    private void legend_of_steve$render(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {

    }
}
