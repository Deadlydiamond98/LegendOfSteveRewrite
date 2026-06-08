package net.deadlydiamond.legend_of_steve.common.bes.container;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractZeldaContainerBlockEntity extends LootableContainerBlockEntity {
    private DefaultedList<ItemStack> inventory;

    protected AbstractZeldaContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
    }

    public boolean insertStack(ItemStack stack) {
        boolean bl = false;

        for (int i = 0; i < size(); i++) {
            ItemStack currentStack = getStack(i);
            int count = Math.min(stack.getCount(), stack.getMaxCount() - currentStack.getCount());

            if (count <= 0) {
                continue;
            }

            if (currentStack.isEmpty()) {
                setStack(i, stack.split(count));
                bl = true;
            } else if (ItemStack.canCombine(stack, currentStack)) {
                stack.decrement(count);
                currentStack.increment(count);
                setStack(i, currentStack);
                bl = true;
            }
        }

        return bl;
    }

    // CONTAINER ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Text getContainerName() {
        return this.getCachedState().getBlock().getName();
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    // INVENTORY ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }
    }
}
