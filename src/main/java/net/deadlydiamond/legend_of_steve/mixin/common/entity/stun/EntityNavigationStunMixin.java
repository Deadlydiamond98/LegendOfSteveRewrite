package net.deadlydiamond.legend_of_steve.mixin.common.entity.stun;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityNavigation.class)
public class EntityNavigationStunMixin {
    @Shadow @Final protected MobEntity entity;

    @WrapMethod(method = "tick")
    private void legend_of_steve$tick(Operation<Void> original) {
        if (!(this.entity instanceof IZeldaStunned stunned && stunned.legend_of_steve$isStunned())) {
            original.call();
        }
    }
}
