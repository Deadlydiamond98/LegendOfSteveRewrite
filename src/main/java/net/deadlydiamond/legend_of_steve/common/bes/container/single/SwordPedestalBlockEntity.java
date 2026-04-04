package net.deadlydiamond.legend_of_steve.common.bes.container.single;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class SwordPedestalBlockEntity extends SingleSlotBlockEntity {
    public SwordPedestalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ZeldaBlockEntities.SWORD_PEDESTAL, blockPos, blockState);
    }

    @Override
    public boolean insertStack(ItemStack stack) {
        boolean bl = super.insertStack(stack);
        updateListeners();
        return bl;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = super.removeStack(slot, amount);
        updateListeners();
        return stack;
    }

    @Override
    public void clear() {
        super.clear();
        updateListeners();
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}
