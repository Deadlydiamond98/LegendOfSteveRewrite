package net.deadlydiamond.legend_of_steve.common.items.locking;

import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.util.LockUtil;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IBlockEntityLocking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KeyItem extends ContainerModifyingItem {
    private final Item lock;
    private final Block block;

    public KeyItem(Settings settings, Item lock, Block block) {
        super(settings);
        this.lock = lock;
        this.block = block;
    }

    @Override
    public boolean modifyContainer(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit, IBlockEntityLocking locking) {
        if (LockUtil.getLockItemForBlock(world.getBlockEntity(blockPos), blockState, world, blockPos).isOf(this.lock)) {
            world.addBlockBreakParticles(blockPos, block.getDefaultState());

            if (!world.isClient) {
                world.playSound(null, blockPos, ZeldaSounds.UNLOCK, SoundCategory.BLOCKS);
                if (!player.isCreative()) {
                    player.getStackInHand(hand).decrement(1);
                }
                LockUtil.setLockItemForBlock(world.getBlockEntity(blockPos), blockState, world, blockPos, ItemStack.EMPTY);
                return true;
            }
        }
        return false;
    }
}
