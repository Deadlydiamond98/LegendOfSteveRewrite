package net.deadlydiamond.legend_of_steve.util.mixinterfaces;

import net.minecraft.item.ItemStack;

public interface IBlockEntityLocking {
    void legend_of_steve$setLockItem(ItemStack lock);
    ItemStack legend_of_steve$getLockItem();
}
