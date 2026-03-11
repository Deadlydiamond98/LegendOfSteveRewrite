package net.deadlydiamond.legend_of_steve.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IScrollAction {
    void onItemScrolled(ItemStack stack, int slot, PlayerEntity player, double verticalAmount);
    default void onItemScrolledHotbar(ItemStack stack, int slot, PlayerEntity player, double direction) {}
}
