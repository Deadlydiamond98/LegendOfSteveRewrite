package net.deadlydiamond.legend_of_steve.common.items;

import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DebugSwitchItem extends Item {
    public DebugSwitchItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        SwitchBlockManager.trigger(world, "test");
        return super.use(world, user, hand);
    }
}
