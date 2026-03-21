package net.deadlydiamond.legend_of_steve.mixin.common.entity.base;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.deadlydiamond.legend_of_steve.init.ZeldaAdvancements;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.ISpringWaterInteraction;
import net.deadlydiamond98.koalalib.util.magic.MagicBarHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements ISpringWaterInteraction {
    @Shadow protected boolean firstUpdate;
    @Shadow protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;
    @Shadow public abstract boolean updateMovementInFluid(TagKey<Fluid> tag, double speed);
    @Shadow public abstract World getWorld();

    @Unique private int legend_of_steve$springWaterTicks;

    // SPRING WATER INTERACTION ////////////////////////////////////////////////////////////////////////////////////////

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attemptTickInVoid()V", shift = At.Shift.BEFORE))
    private void legend_of_steve$baseTickSpringWater(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (legend_of_steve$isInSpringWater()) {
            legend_of_steve$tickSpringWaterInteraction(entity, this.legend_of_steve$springWaterTicks++);
        } else {
            this.legend_of_steve$springWaterTicks = 0;
        }
    }

    @WrapMethod(method = "updateWaterState")
    private boolean legend_of_steve$updateWaterState(Operation<Boolean> original) {
        boolean bl = original.call();
        boolean updateSpringWaterMovement = this.updateMovementInFluid(ZeldaTags.ENCHANTED_SPRING_WATER, 0);
        return bl || updateSpringWaterMovement;
    }

    @Override
    public void legend_of_steve$tickSpringWaterInteraction(Entity entity, int submergedTime) {
        if (!this.getWorld().isClient && entity instanceof LivingEntity living && submergedTime >= 140) {
            if (submergedTime % 5 == 0) {
                living.heal(1);
                MagicBarHelper.addMana(living, 5);
                if (living instanceof PlayerEntity player) {
                    ZeldaAdvancements.RELAX_IN_SPRING_WATER.trigger(player);
                }
            }
        }
    }

    @Override
    public boolean legend_of_steve$isInSpringWater() {
        return !this.firstUpdate && this.fluidHeight.getDouble(ZeldaTags.ENCHANTED_SPRING_WATER) > 0.0;
    }
}
