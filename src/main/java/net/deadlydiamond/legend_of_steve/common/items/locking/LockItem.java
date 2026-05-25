package net.deadlydiamond.legend_of_steve.common.items.locking;

import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.util.LockUtil;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IBlockEntityLocking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LockItem extends ContainerModifyingItem {
    public LockItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean modifyContainer(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult hit, IBlockEntityLocking locking) {
        if (!world.isClient && LockUtil.getLockItemForBlock(world.getBlockEntity(blockPos), blockState, world, blockPos).isEmpty()) {
            world.playSound(null, blockPos, ZeldaSounds.LOCK, SoundCategory.BLOCKS);
            if (!player.isCreative()) {
                player.getStackInHand(hand).decrement(1);
            }
            LockUtil.setLockItemForBlock(world.getBlockEntity(blockPos), blockState, world, blockPos, this.getDefaultStack());
            locking.legend_of_steve$setLockItem(this.getDefaultStack());
            return true;
        }
        return false;
    }
}
