package net.deadlydiamond.legend_of_steve.common.bes.locks;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock.LockedBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class LockedChestBlockEntity extends ChestBlockEntity implements ILockedBlockEntity {
    protected BlockState lockedBlock = Blocks.CHEST.getDefaultState();
    protected NbtCompound wrappedNBT = new NbtCompound();

    public LockedChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ZeldaBlockEntities.LOCKED_CHEST, blockPos, blockState);
    }

    @Override
    public BlockState getLockedBlock() {
        if (this.lockedBlock.getBlock() instanceof LockedBlock) {
            return Blocks.CHEST.getDefaultState();
        }

        return this.lockedBlock;
    }

    @Override
    public void setLockedBlock(BlockState lockedBlock) {
        this.lockedBlock = lockedBlock;
        markDirty();
    }

    @Override
    public NbtCompound getWrappedNBT() {
        return this.wrappedNBT;
    }

    @Override
    public void setWrappedNBT(NbtCompound nbt) {
        this.wrappedNBT = nbt;
        markDirty();
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxCountPerStack() {
        return 0;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        RegistryEntryLookup<Block> registryEntryLookup = this.world != null ?
                this.world.createCommandRegistryWrapper(RegistryKeys.BLOCK) :
                Registries.BLOCK.getReadOnlyWrapper();

        setLockedBlock(NbtHelper.toBlockState(registryEntryLookup, nbt.getCompound("LockedBlock")));
        setWrappedNBT(nbt.getCompound("WrappedNbt"));
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put("LockedBlock", NbtHelper.fromBlockState(getLockedBlock()));
        nbt.put("WrappedNbt", getWrappedNBT());
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Override
    public @Nullable Object getRenderData() {
        return this.lockedBlock;
    }
}
