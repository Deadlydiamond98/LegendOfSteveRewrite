package net.deadlydiamond.legend_of_steve.common.bes.container.single;

import net.deadlydiamond.legend_of_steve.common.blocks.container.single.SingleSlotBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class SingleSlotBlockEntity extends LootableContainerBlockEntity {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public SingleSlotBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState state) {
        super(blockEntityType, blockPos, state);
    }

    @Override
    public DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    protected Text getContainerName() {
        return getBlock().getName();
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
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

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        this.checkLootInteraction(null);
        return Inventories.splitStack(this.getInvStackList(), slot, amount);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.checkLootInteraction(null);
        this.getInvStackList().set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
    }

    public boolean insertStack(ItemStack stack) {
        ItemStack currentStack = getStack(0);
        int i = Math.min(stack.getCount(), stack.getMaxCount() - currentStack.getCount());

        if (i <= 0) {
            return false;
        }

        if (currentStack.isEmpty()) {
            setStack(0, stack.split(i));
            return true;
        } else if (ItemStack.canCombine(stack, currentStack)) {
            stack.decrement(i);
            currentStack.increment(i);
            setStack(0, currentStack);
            return true;
        }
        return false;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return slot == 0 && getBlock().isValid(stack);
    }

    protected SingleSlotBlock getBlock() {
        return (SingleSlotBlock) this.getCachedState().getBlock();
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}
