package net.deadlydiamond.legend_of_steve.mixin.common.player;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.common.items.IScrollAction;
import net.deadlydiamond.legend_of_steve.networking.c2s.HudScrollItemActionC2SPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Shadow @Final public PlayerEntity player;
    @Shadow public int selectedSlot;

    @WrapMethod(method = "scrollInHotbar")
    private void legend_of_steve$scrollInHotbar(double scrollAmount, Operation<Void> original) {
        ItemStack bundleStack = this.player.getMainHandStack();
        if (bundleStack.getItem() instanceof IScrollAction scrollAction) {
            if (scrollAction.canScrollHotbar(bundleStack, this.selectedSlot, this.player, scrollAmount)) {
                scrollAction.onItemScrolledHotbar(bundleStack, this.selectedSlot, this.player, scrollAmount);
                HudScrollItemActionC2SPacket.send(this.selectedSlot, scrollAmount);
                return;
            }
        }
        original.call(scrollAmount);
    }
}
