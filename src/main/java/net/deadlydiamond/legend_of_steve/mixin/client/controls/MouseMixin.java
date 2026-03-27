package net.deadlydiamond.legend_of_steve.mixin.client.controls;


import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow private double cursorDeltaX;
    @Shadow private double cursorDeltaY;


    @WrapMethod(method = "updateMouse")
    private void legend_of_steve$updateMouse(Operation<Void> original) {
        if (client.player instanceof IZeldaStunned stunned && stunned.legend_of_steve$isStunned()) {
            this.cursorDeltaX = 0;
            this.cursorDeltaY = 0;
        } else {
            original.call();
        }
    }
}
