package net.deadlydiamond.legend_of_steve.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedGlintItem extends Item {
    public EnchantedGlintItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
