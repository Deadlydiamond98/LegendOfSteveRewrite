package net.deadlydiamond.legend_of_steve.util;

import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IBlockEntityLocking;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LockUtil {

    public static void setLockItemForBlock(BlockEntity entity, BlockState blockState, World world, BlockPos blockPos, ItemStack stack) {
        if (blockState.getBlock() instanceof AbstractChestBlock<?> abstractChestBlock) {
            DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> propertySource = abstractChestBlock.getBlockEntitySource(blockState, world, blockPos, true);
            setLockItemForChest(propertySource, stack);
        } else if (entity instanceof IBlockEntityLocking locking) {
            locking.legend_of_steve$setLockItem(stack);
        }
    }

    public static ItemStack getLockItemForBlock(BlockEntity entity, BlockState blockState, World world, BlockPos blockPos) {
        if (blockState.getBlock() instanceof AbstractChestBlock<?> abstractChestBlock) {
            DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> propertySource = abstractChestBlock.getBlockEntitySource(blockState, world, blockPos, true);
            return getLockItemForChest(propertySource);
        } else if (entity instanceof IBlockEntityLocking locking) {
            return locking.legend_of_steve$getLockItem();
        }
        return ItemStack.EMPTY;
    }

    // CHEST ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static ItemStack getLockItemForChest(DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> propertySource) {
        return propertySource.apply(new DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, ItemStack>() {
            public ItemStack getFromBoth(ChestBlockEntity chestBlockEntity, ChestBlockEntity chestBlockEntity2) {
                ItemStack stack = ItemStack.EMPTY;
                ItemStack stack2 = ItemStack.EMPTY;
                if (chestBlockEntity instanceof IBlockEntityLocking locking) {
                    stack = locking.legend_of_steve$getLockItem();
                }
                if (chestBlockEntity2 instanceof IBlockEntityLocking locking) {
                    stack2 = locking.legend_of_steve$getLockItem();
                }
                return stack.isEmpty() ? stack2 : stack;
            }

            public ItemStack getFrom(ChestBlockEntity chestBlockEntity) {
                if (chestBlockEntity instanceof IBlockEntityLocking locking) {
                    return locking.legend_of_steve$getLockItem();
                }
                return getFallback();
            }

            public ItemStack getFallback() {
                return ItemStack.EMPTY;
            }
        });
    }

    public static ItemStack setLockItemForChest(DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> propertySource, ItemStack stack) {
        return propertySource.apply(new DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, ItemStack>() {
            public ItemStack getFromBoth(ChestBlockEntity chestBlockEntity, ChestBlockEntity chestBlockEntity2) {
                if (chestBlockEntity instanceof IBlockEntityLocking locking) {
                    locking.legend_of_steve$setLockItem(stack);
                }
                if (chestBlockEntity2 instanceof IBlockEntityLocking locking) {
                    locking.legend_of_steve$setLockItem(stack);
                }
                return getFallback();
            }

            public ItemStack getFrom(ChestBlockEntity chestBlockEntity) {
                if (chestBlockEntity instanceof IBlockEntityLocking locking) {
                    locking.legend_of_steve$setLockItem(stack);
                }
                return getFallback();
            }

            public ItemStack getFallback() {
                return ItemStack.EMPTY;
            }
        });
    }
}
