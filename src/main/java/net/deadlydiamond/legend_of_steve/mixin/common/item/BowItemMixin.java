package net.deadlydiamond.legend_of_steve.mixin.common.item;

import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.common.items.bag.QuiverItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public class BowItemMixin {
    @Inject(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", shift = At.Shift.AFTER), cancellable = true)
    private void legend_of_steve$onStoppedUsing(ItemStack stack, World world, LivingEntity entity, int remainingUseTicks, CallbackInfo ci, @Local PlayerEntity user) {
        if (!QuiverItem.getArrow(user).isEmpty()) {
            user.incrementStat(Stats.USED.getOrCreateStat((Item) (Object) this));
            ci.cancel();
        }
    }
}
