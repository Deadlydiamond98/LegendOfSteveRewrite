package net.deadlydiamond.legend_of_steve.util;

import net.deadlydiamond.legend_of_steve.common.bes.locks.ILockedBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock.ILockedBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock.LockedBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class LockManager {

    public static boolean tryUnlockBlock(World world, BlockPos blockPos, ILockedBlock lock, ItemStack key, TagKey<Item> keyTag) {
        if (key.isIn(keyTag) || key.isOf(ZeldaItems.CREATIVE_KEY)) {
            unlockBlock(world, blockPos, lock);
            return true;
        }
        return false;
    }

    public static void unlockBlock(World world, BlockPos blockPos, ILockedBlock lock) {
        Map<BlockPos, BlockState> lockedBlocksData = getPositions(world, blockPos);
        lockedBlocksData.forEach((pos, state) -> {
            world.addBlockBreakParticles(pos, state);
            BlockState lockedBlock = lock.getLockedBlock(world, pos);
            NbtCompound nbt = lock.getWrappedNBT(world, pos);

            if (lockedBlock.getBlock() instanceof ChestBlock) {
                ChestType type = state.get(Properties.CHEST_TYPE);
                lockedBlock = lockedBlock.with(Properties.CHEST_TYPE, type);
            }

            world.setBlockState(pos, lockedBlock, Block.FORCE_STATE | Block.NOTIFY_LISTENERS);

            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.readNbt(nbt);
                blockEntity.markDirty();
            }
        });

        if (!world.isClient) {
            world.playSound(null, blockPos, ZeldaSounds.UNLOCK, SoundCategory.BLOCKS);
        }
    }

    // LOCKING /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean tryLockBlock(World world, BlockPos pos, Direction facing, BlockState result) {
        if (canApplyLock(world.getBlockState(pos))) {
            Map<BlockPos, BlockState> lockedBlocksData = getPositions(world, pos);
            lockedBlocksData.forEach((blockPos, state) -> lockBlock(world, blockPos, state, result, facing));

            if (!world.isClient) {
                world.playSound(null, pos, ZeldaSounds.LOCK, SoundCategory.BLOCKS);
            }
            return true;
        }
        return false;
    }

    public static boolean canApplyLock(BlockState state) {
        return !(state.getBlock() instanceof ILockedBlock) && state.isIn(ZeldaTags.LOCKABLE) &&
                !(state.getBlock() instanceof PistonBlock && state.get(Properties.EXTENDED));
    }

    public static void lockBlock(World world, BlockPos pos, BlockState oldState, BlockState newState, Direction facing) {
        BlockEntity oldBlockEntity = world.getBlockEntity(pos);
        NbtCompound wrappedNBT = new NbtCompound();
        if (oldBlockEntity != null) {
            wrappedNBT = oldBlockEntity.createNbt();
            world.removeBlockEntity(pos);
        }

        newState = getBlockWithProperties(oldState, newState);

        if (newState.getBlock() instanceof LockedBlock) {
            Direction direction;

            if (oldState.contains(Properties.FACING)) {
                direction = oldState.get(Properties.FACING);
            } else if (oldState.contains(Properties.HORIZONTAL_FACING)) {
                direction = oldState.get(Properties.HORIZONTAL_FACING);
            } else {
                direction = facing;
            }
            newState = newState.with(Properties.FACING, direction);
        }

        world.setBlockState(pos, newState, Block.FORCE_STATE | Block.NOTIFY_LISTENERS);
        if (world.getBlockEntity(pos) instanceof ILockedBlockEntity lockedBlock) {
            lockedBlock.setLockedBlock(oldState);
            lockedBlock.setWrappedNBT(wrappedNBT);
        }
    }

    // APPLY PROPERTIES ////////////////////////////////////////////////////////////////////////////////////////////////

    private static BlockState getBlockWithProperties(BlockState oldState, BlockState newState) {
        for (Property<?> property : oldState.getProperties()) {
            newState = applyProperty(oldState, newState, property);
        }
        return newState;
    }

    private static <T extends Comparable<T>> BlockState applyProperty(BlockState oldState, BlockState newState, Property<T> property) {
        if (newState.contains(property)) {
            return newState.with(property, oldState.get(property));
        }
        return newState;
    }

    // POSITIONS ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns a class that stores positions & blockstates that will be locked
     */
    private static Map<BlockPos, BlockState> getPositions(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof ChestBlock) {
            ChestType type = state.get(Properties.CHEST_TYPE);
            Direction chestDirection = state.get(Properties.HORIZONTAL_FACING);

            if (type.getOpposite() != type) {
                Direction offset = type == ChestType.LEFT ?
                        chestDirection.rotateYClockwise() :
                        chestDirection.rotateYCounterclockwise();
                if (world.getBlockState(pos.offset(offset)).getBlock() instanceof ChestBlock) {
                    return getLockedBlocks(world, pos, pos.offset(offset));
                }
            }
        } else if (state.getBlock() instanceof DoorBlock) {
            DoubleBlockHalf half = state.get(Properties.DOUBLE_BLOCK_HALF);
            BlockPos offsetPos = pos.offset(half == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN);
            if (world.getBlockState(offsetPos).getBlock() instanceof DoorBlock) {
                return getLockedBlocks(world, pos, offsetPos);
            }
        }
        return getLockedBlocks(world, pos);
    }

    private static Map<BlockPos, BlockState> getLockedBlocks(World world, BlockPos... positions) {
        Map<BlockPos, BlockState> data = new HashMap<>();
        for (BlockPos pos : positions) {
            data.put(pos, world.getBlockState(pos));
        }
        return data;
    }
}
