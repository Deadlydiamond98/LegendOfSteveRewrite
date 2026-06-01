package net.deadlydiamond.legend_of_steve.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SwitchCore extends Item {
    public SwitchCore(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
