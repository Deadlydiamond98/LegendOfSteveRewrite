package net.deadlydiamond.legend_of_steve.common.items.locking;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.LockBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KeyItem extends Item {
    private final Block lock;

    public KeyItem(Settings settings, Block lock) {
        super(settings);
        this.lock = lock;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();

        if (state.getBlock() instanceof LockBlock lockBlock && state.isOf(this.lock)) {
            world.addBlockBreakParticles(pos, state);
            BlockState lockedBlock = lockBlock.getLockedBlock(world, pos);
            NbtCompound nbt = lockBlock.getWrappedNBT(world, pos);

            world.setBlockState(pos, lockedBlock);

            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.readNbt(nbt);
                blockEntity.markDirty();
            }

            if (player != null && !world.isClient) {
                world.playSound(null, pos, ZeldaSounds.UNLOCK, SoundCategory.BLOCKS);
                if (!player.isCreative()) {
                    player.getStackInHand(context.getHand()).decrement(1);
                }
            }

            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }
}
