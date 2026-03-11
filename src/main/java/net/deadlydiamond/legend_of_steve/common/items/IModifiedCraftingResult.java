package net.deadlydiamond.legend_of_steve.common.items;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

/**
 * Used for modifying the Crafting Result of an Item
 */
public interface IModifiedCraftingResult {
    void modifyCraftingResult(ItemStack result, Inventory craftingTableSlots);
}
