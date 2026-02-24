package net.deadlydiamond.legend_of_steve.mixin.common.entity;

import net.deadlydiamond.legend_of_steve.common.items.bag.QuiverItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin {
    @Shadow public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

    @Inject(method = "initEquipment", at = @At("TAIL"))
    private void legend_of_steve$initEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        AbstractSkeletonEntity skeleton = (AbstractSkeletonEntity) (Object) this;

        if (skeleton.getMainHandStack().getItem() instanceof RangedWeaponItem && random.nextFloat() < 0.1) {
            this.equipStack(EquipmentSlot.CHEST, QuiverItem.getFilledQuiver(skeleton, random, ZeldaItems.QUIVER));
        }
    }
}
