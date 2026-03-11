package net.deadlydiamond.legend_of_steve.common.items.bag;

import net.deadlydiamond.legend_of_steve.common.items.IModifiedCraftingResult;
import net.deadlydiamond.legend_of_steve.common.items.IScrollAction;
import net.deadlydiamond.legend_of_steve.common.items.projectile.explosive.BombItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond98.koalalib.common.items.vanillamodified.CustomBundleItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BombBagItem extends CustomBundleItem implements IModifiedCraftingResult, IScrollAction {
    public BombBagItem(Settings settings, int maxStorage) {
        super(settings.maxCount(1), maxStorage, true, ZeldaTags.BOMBS);
        BombItem.COOLDOWNS.put(this, 40);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack bundle = user.getStackInHand(hand);
        ItemStack bombStack = removeFromBundle(bundle, 1, !user.isCreative());
        return bombStack.getItem().use(world, user, hand);
    }

    @Override
    public void modifyCraftingResult(ItemStack result, Inventory craftingTableSlots) {
        for (int i = 0; i < craftingTableSlots.size(); i++) {
            ItemStack inputStack = craftingTableSlots.getStack(i);

            if (inputStack.getItem() instanceof BombBagItem) {
                getItemStacks(inputStack).forEach(itemStack -> addToBundle(result, itemStack.copy()));
            } else if (inputStack.isIn(ZeldaTags.BOMBS)) {
                addToBundle(result, inputStack.copy());
            }
        }
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
        if (player.getWorld().isClient() && direction != 0) {
            player.sendMessage(getFirstStack(stack).getName(), true);
        }
        onItemScrolled(stack, slot, player, direction);
    }
}
