package net.deadlydiamond.legend_of_steve.common.items.block_item;

import net.deadlydiamond.legend_of_steve.common.entities.projectile.ThrownPotEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaDispenserBehaviors;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class LootPotItem extends BlockItem {
    public static final Map<Item, Integer> COOLDOWNS = new HashMap<>();

    public LootPotItem(Block block, Settings settings) {
        super(block, settings.maxCount(1));
        COOLDOWNS.put(this, 5);
        DispenserBlock.registerBehavior(this, ZeldaDispenserBehaviors.lootPot());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            ItemStack stack = user.getStackInHand(hand);

            ThrownPotEntity thrownPot = new ThrownPotEntity(world, user, stack);
            world.spawnEntity(thrownPot);

            stack.decrement(1);
            COOLDOWNS.forEach((item, integer) -> user.getItemCooldownManager().set(item, integer));
            user.playSound(ZeldaSounds.LOOT_POT_THROWN, SoundCategory.PLAYERS, 0.5f, 0.75f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

            return TypedActionResult.success(stack);
        }
        return super.use(world, user, hand);
    }
}
