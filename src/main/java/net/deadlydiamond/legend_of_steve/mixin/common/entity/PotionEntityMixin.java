package net.deadlydiamond.legend_of_steve.mixin.common.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.AbstractBombEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionEntity.class)
public class PotionEntityMixin {

    @Inject(method = "applyWater", at = @At("TAIL"))
    private void legend_of_steve$applyWater(CallbackInfo ci, @Local Box box) {
        PotionEntity potionEntity = (PotionEntity) (Object) this;

        for (AbstractBombEntity bombEntity : potionEntity.getWorld().getNonSpectatingEntities(AbstractBombEntity.class, box)) {
            if (bombEntity.shouldWaterBottleExtinguish()) {
                bombEntity.extinguishBomb();
            }
        }
    }
}
