package net.deadlydiamond.legend_of_steve.mixin.common.item;

import net.deadlydiamond.legend_of_steve.common.items.bag.QuiverItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @Inject(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", shift = At.Shift.AFTER))
    private void legend_of_steve$onStoppedUsing(ItemStack stack, World world, LivingEntity entity, int remainingUseTicks, CallbackInfo ci) {
        if (entity instanceof PlayerEntity user) {
            QuiverItem.getArrow(user);
        }
    }
}
