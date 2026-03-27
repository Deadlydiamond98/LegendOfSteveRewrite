package net.deadlydiamond.legend_of_steve.mixin.client.controls;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyBinding.class)
public class KeybindingMixin {
    @Shadow @Final private String category;
    @Shadow @Final public static String GAMEPLAY_CATEGORY;
    @Shadow @Final public static String MOVEMENT_CATEGORY;

    @ModifyReturnValue(method = "wasPressed", at = @At("RETURN"))
    private boolean legend_of_steve$wasPressed(boolean original) {
        return !legend_of_steve$shouldCancelInput() && original;
    }

    @ModifyReturnValue(method = "isPressed", at = @At("RETURN"))
    private boolean legend_of_steve$isPressed(boolean original) {
        return !legend_of_steve$shouldCancelInput() && original;
    }

    @Unique
    private boolean legend_of_steve$shouldCancelInput() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player instanceof IZeldaStunned stunned && stunned.legend_of_steve$isStunned()) {
            return this.category.equals(GAMEPLAY_CATEGORY) || this.category.equals(MOVEMENT_CATEGORY);
        }
        return false;
    }
}
