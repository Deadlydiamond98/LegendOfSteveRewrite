package net.deadlydiamond.legend_of_steve.common.items;

import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DebugSwitchItem extends Item {
    public DebugSwitchItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        NbtCompound nbt = user.getStackInHand(hand).getOrCreateSubNbt("display");
        SwitchBlockManager.trigger(world, nbt.contains("Name") ? nbt.getString("Name") : "test");
        return super.use(world, user, hand);
    }
}
