package net.deadlydiamond.legend_of_steve.mixin.client.controls;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tickNewAi", at = @At("TAIL"))
    private void legend_of_steve$getYaw(CallbackInfo ci) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (player instanceof IZeldaStunned stunned && stunned.legend_of_steve$isStunned()) {
            player.forwardSpeed = 0;
            player.sidewaysSpeed = 0;
        }
    }
}
