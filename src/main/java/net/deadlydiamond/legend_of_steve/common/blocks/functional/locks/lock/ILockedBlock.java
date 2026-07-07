package net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock;

import net.deadlydiamond.legend_of_steve.common.bes.locks.ILockedBlockEntity;
import net.deadlydiamond.legend_of_steve.util.LockManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public interface ILockedBlock {

    TagKey<Item> getKeyTag();

    default boolean removeLock(World world, BlockPos pos, ItemStack key) {
        return LockManager.tryUnlockBlock(world, pos, this, key, getKeyTag());
    }

    default NbtCompound getWrappedNBT(BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof ILockedBlockEntity lockedBlock) {
            return lockedBlock.getWrappedNBT();
        }
        return new NbtCompound();
    }

    default BlockState getLockedBlock(BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof ILockedBlockEntity lockedBlock) {
            return lockedBlock.getLockedBlock();
        }
        return Blocks.AIR.getDefaultState();
    }

    default boolean wrappedBlockModel() {
        return true;
    }
}
