package net.deadlydiamond.legend_of_steve.mixin.common.entity.potion_effects;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.deadlydiamond.legend_of_steve.init.ZeldaAdvancements;
import net.deadlydiamond.legend_of_steve.init.ZeldaEffects;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityPotionEffectMixin {
    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);
    @Shadow protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;
    @Shadow protected boolean firstUpdate;
    @Shadow @Final protected Random random;

    // FLUID EFFECTS ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Inject(method = "tick", at = @At("HEAD"))
    private void legend_of_steve$tick(CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof PlayerEntity player) {
            if (player.hasStatusEffect(ZeldaEffects.PONDSTRIDING) && !player.isTouchingWater() &&
                    player.getWorld().getFluidState(player.getLandingPos()).isIn(FluidTags.WATER)
            ) {
                ZeldaAdvancements.WATER_WALKING.trigger(player);
            }
        }
    }

    @Inject(method = "playStepSounds", at = @At("HEAD"))
    private void legend_of_steve$playStepSounds(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof LivingEntity living) {
            if (living.hasStatusEffect(ZeldaEffects.PONDSTRIDING) && living.getWorld().getFluidState(pos).isIn(FluidTags.WATER)) {
                this.playSound(ZeldaSounds.WATER_STEP, 0.05f, 1);
            }
            if (living.hasStatusEffect(ZeldaEffects.HOTSTRIDING) && living.getWorld().getFluidState(pos).isIn(FluidTags.LAVA)) {
                this.playSound(ZeldaSounds.LAVA_STEP, 0.1f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            }
        }
    }

    @ModifyReturnValue(method = "isInLava", at = @At("RETURN"))
    private boolean legend_of_steve$isInLava(boolean original) {
        if (((Entity) (Object) this) instanceof LivingEntity living) {
            if (living.hasStatusEffect(ZeldaEffects.HOTSTRIDING)) {
                return !this.firstUpdate && this.fluidHeight.getDouble(FluidTags.LAVA) > 0.4;
            }
        }
        return original;
    }

    @ModifyReturnValue(method = "isTouchingWater", at = @At("RETURN"))
    private boolean legend_of_steve$isTouchingWater(boolean original) {
        if (((Entity) (Object) this) instanceof LivingEntity living) {
            if (living.hasStatusEffect(ZeldaEffects.WATER_WEIGHT)) {
                return false;
            }

            if (living.hasStatusEffect(ZeldaEffects.PONDSTRIDING)) {
                return !this.firstUpdate && this.fluidHeight.getDouble(FluidTags.WATER) > 0.4;
            }
        }
        return original;
    }

    @WrapMethod(method = "setSwimming")
    private void legend_of_steve$setSwimming(boolean swimming, Operation<Void> original) {
        if (((Entity) (Object) this) instanceof LivingEntity living) {
            if (living.hasStatusEffect(ZeldaEffects.WATER_WEIGHT)) {
                return;
            }
        }
        original.call(swimming);
    }
}
