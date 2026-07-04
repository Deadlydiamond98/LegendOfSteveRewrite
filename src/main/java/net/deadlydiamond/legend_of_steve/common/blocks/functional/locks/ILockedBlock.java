package net.deadlydiamond.legend_of_steve.common.blocks.functional.locks;

import net.deadlydiamond.legend_of_steve.common.bes.locks.ILockedBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public interface ILockedBlock {

    TagKey<Item> getKeyTag();

    default boolean removeLock(World world, BlockPos pos, ItemStack key, boolean doubleChest) {
        BlockState state = world.getBlockState(pos);

        if (key.isIn(getKeyTag()) || key.isOf(ZeldaItems.CREATIVE_KEY)) {
            world.addBlockBreakParticles(pos, state);
            BlockState lockedBlock = getLockedBlock(world, pos);
            NbtCompound nbt = getWrappedNBT(world, pos);

            if (!doubleChest && lockedBlock.getBlock() instanceof ChestBlock) {
                ChestType type = state.get(Properties.CHEST_TYPE);
                Direction chestFacing = state.get(Properties.HORIZONTAL_FACING);
                if (type.getOpposite() != type) {
                    Direction offset = type == ChestType.LEFT ?
                            chestFacing.rotateYClockwise() :
                            chestFacing.rotateYCounterclockwise();

                    removeLock(world, pos.offset(offset), key, true);
                }

                lockedBlock = lockedBlock.with(Properties.CHEST_TYPE, type);
            }

            world.setBlockState(pos, lockedBlock);

            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.readNbt(nbt);
                blockEntity.markDirty();
            }

            if (!world.isClient) {
                world.playSound(null, pos, ZeldaSounds.UNLOCK, SoundCategory.BLOCKS);
            }
            return true;
        }
        return false;
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
}
