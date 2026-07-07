package net.deadlydiamond.legend_of_steve.common.items.locking;

import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond98.koalalib.common.items.interaction.PickupSoundItem;
import net.minecraft.item.ItemStack;

public class KeyItem extends PickupSoundItem {
    public KeyItem(Settings settings) {
        super(settings, ZeldaSounds.KEY_PICKED_UP);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.isOf(ZeldaItems.CREATIVE_KEY);
    }
}
