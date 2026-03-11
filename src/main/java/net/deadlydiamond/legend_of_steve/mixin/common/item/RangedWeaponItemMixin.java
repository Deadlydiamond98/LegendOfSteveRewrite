package net.deadlydiamond.legend_of_steve.mixin.common.item;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.common.items.IScrollAction;
import net.deadlydiamond.legend_of_steve.common.items.bag.QuiverItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Predicate;

@Mixin(RangedWeaponItem.class)
public class RangedWeaponItemMixin implements IScrollAction {

    @WrapMethod(method = "getHeldProjectile")
    private static ItemStack legend_of_steve$getHeldProjectile(LivingEntity entity, Predicate<ItemStack> predicate, Operation<ItemStack> original) {
        if (entity instanceof PlayerEntity player) {
            ItemStack arrowStack = QuiverItem.getArrow(player, false);
            if (!arrowStack.isEmpty()) {
                return arrowStack;
            }
        }
        return original.call(entity, predicate);
    }

    @Override
    public void onItemScrolled(ItemStack stack, int slot, PlayerEntity player, double verticalAmount) {}

    @Override
    public void onItemScrolledHotbar(ItemStack stack, int slot, PlayerEntity player, double direction) {
        ItemStack itemStack = QuiverItem.getQuiverStack(player);
        if (itemStack.getItem() instanceof QuiverItem quiver) {
            quiver.onItemScrolledHotbar(itemStack, -1, player, direction);
        }
    }

    @Override
    public boolean canScrollHotbar(ItemStack stack, int slot, PlayerEntity player, double direction) {
        return !QuiverItem.getQuiverStack(player).isEmpty() && player.isSneaking();
    }
}