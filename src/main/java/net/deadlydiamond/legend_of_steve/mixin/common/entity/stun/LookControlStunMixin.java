package net.deadlydiamond.legend_of_steve.mixin.common.entity.stun;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LookControl.class)
public class LookControlStunMixin {
    @Shadow @Final protected MobEntity entity;

    @WrapMethod(method = "lookAt(DDDFF)V")
    public void legend_of_steve$lookAt(double x, double y, double z, float maxYawChange, float maxPitchChange, Operation<Void> original) {
        if (!(this.entity instanceof IZeldaStunned stunned && stunned.legend_of_steve$isStunned())) {
            original.call(x, y, z, maxYawChange, maxPitchChange);
        }
    }
}
