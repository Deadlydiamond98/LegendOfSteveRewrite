package net.deadlydiamond.legend_of_steve.common.bes.locks;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;

public interface ILockedBlockEntity {
    BlockState getLockedBlock();
    void setLockedBlock(BlockState lockedBlock);
    NbtCompound getWrappedNBT();
    void setWrappedNBT(NbtCompound nbt);
}
