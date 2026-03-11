package net.deadlydiamond.legend_of_steve.common.items.bag;

import net.deadlydiamond.legend_of_steve.common.items.IScrollAction;
import net.deadlydiamond98.koalalib.common.items.vanillamodified.CustomBundleItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Predicate;

public class ScrollableBag extends CustomBundleItem implements IScrollAction {
    public ScrollableBag(Settings settings, int maxStorage, boolean insertOnPickup, TagKey<Item> acceptedItems) {
        super(settings, maxStorage, insertOnPickup, acceptedItems);
    }

    public ScrollableBag(Settings settings, int maxStorage, boolean insertOnPickup, Predicate<ItemStack> stackPredicate) {
        super(settings, maxStorage, insertOnPickup, stackPredicate);
    }

    @Override
    public void onItemScrolled(ItemStack stack, int slot, PlayerEntity player, double scroll) {
        int scrollDir = (int) Math.signum(scroll);

        if (scrollDir != 0) {
            if (player.getWorld().isClient()) {
                playInsertSound(player);
            }

            BombBagItem.cycleInventory(stack, (int) Math.signum(scroll));
        }
    }

    @Override
    public void onItemScrolledHotbar(ItemStack stack, int slot, PlayerEntity player, double direction) {
        onItemScrolled(stack, slot, player, direction);
        if (player.getWorld().isClient() && direction != 0) {
            player.sendMessage(getFirstStack(stack).getName(), true);
        }
    }

    @Override
    public boolean canScrollHotbar(ItemStack stack, int slot, PlayerEntity player, double direction) {
        return player.isSneaking();
    }
}
