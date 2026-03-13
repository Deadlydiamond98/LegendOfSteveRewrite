package net.deadlydiamond.legend_of_steve.common.items.bag;

import net.deadlydiamond.legend_of_steve.common.items.IModifiedCraftingResult;
import net.deadlydiamond.legend_of_steve.common.items.projectile.explosive.BombItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond98.koalalib.common.items.interaction.IAdvancedItemProperties;
import net.deadlydiamond98.koalalib.common.items.vanillamodified.IExtraEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class BombBagItem extends ScrollableBag implements IModifiedCraftingResult, IExtraEnchantments, Vanishable, IAdvancedItemProperties {
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
                addToBundle(result, inputStack.copyWithCount(1));
            }
        }
    }

    @Override
    public int getMaxInsertables(ItemStack bundle) {
        return super.getMaxInsertables(bundle);
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public List<Enchantment> getEnchantments() {
        return List.of(Enchantments.FIRE_ASPECT);
    }

    @Override
    public void onItemEntityTick(ItemEntity entity, ItemStack stack) {
        for (ItemStack itemStack : getItemStacks(stack)) {
            if (itemStack.getItem() instanceof IAdvancedItemProperties advancedItemProperties) {
                advancedItemProperties.onItemEntityTick(entity, stack);
                return;
            }
        }
    }
}
