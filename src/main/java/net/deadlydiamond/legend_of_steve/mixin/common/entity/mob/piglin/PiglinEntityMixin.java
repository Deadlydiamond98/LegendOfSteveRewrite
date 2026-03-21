package net.deadlydiamond.legend_of_steve.mixin.common.entity.mob.piglin;

import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.common.items.bag.QuiverItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PiglinEntity.class)
public class PiglinEntityMixin {
    @ModifyArgs(method = "initEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PiglinEntity;equipAtChance(Lnet/minecraft/entity/EquipmentSlot;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/random/Random;)V", ordinal = 1))
    private void legend_of_steve$equipAtChance(Args args, @Local(argsOnly = true) Random random) {
        PiglinEntity piglin = (PiglinEntity) (Object) this;

        if (piglin.getMainHandStack().getItem() instanceof RangedWeaponItem && random.nextFloat() < 0.5) {
            args.set(1, QuiverItem.getFilledQuiver(piglin, random, ZeldaItems.GILDED_QUIVER));
        }
    }
}
