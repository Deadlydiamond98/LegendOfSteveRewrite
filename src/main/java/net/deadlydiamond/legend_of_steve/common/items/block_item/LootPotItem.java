package net.deadlydiamond.legend_of_steve.common.items.block_item;

import net.deadlydiamond.legend_of_steve.common.entities.projectile.ThrownPotEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LootPotItem extends BlockItem {

    public LootPotItem(Block block, Settings settings) {
        super(block, settings.maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            ItemStack stack = user.getStackInHand(hand);

            ThrownPotEntity thrownPot = new ThrownPotEntity(world, user, stack);
            world.spawnEntity(thrownPot);

            stack.decrement(1);
            user.playSound(ZeldaSounds.LOOT_POT_THROWN, SoundCategory.PLAYERS, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));

            return TypedActionResult.success(stack);
        }
        return super.use(world, user, hand);
    }
}
