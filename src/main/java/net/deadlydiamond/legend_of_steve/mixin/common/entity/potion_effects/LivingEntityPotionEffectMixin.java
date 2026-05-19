package net.deadlydiamond.legend_of_steve.mixin.common.entity.potion_effects;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.init.ZeldaEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityPotionEffectMixin {

    @ModifyReturnValue(method = "canWalkOnFluid", at = @At(value = "RETURN"))
    private boolean legend_of_steve$canWalkOnFluid(boolean original, @Local(ordinal = 0, argsOnly = true) FluidState state) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (!living.isSneaking()) {
            if (living.hasStatusEffect(ZeldaEffects.PONDSTRIDING) && state.isIn(FluidTags.WATER)) {
                return !living.isTouchingWater();
            }
            if (living.hasStatusEffect(ZeldaEffects.HOTSTRIDING) && state.isIn(FluidTags.LAVA)) {
                return !living.isInLava();
            }
        }
        return original;
    }
}
