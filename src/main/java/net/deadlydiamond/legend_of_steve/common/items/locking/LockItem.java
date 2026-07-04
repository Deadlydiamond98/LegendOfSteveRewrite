package net.deadlydiamond.legend_of_steve.common.items.locking;

import net.deadlydiamond.legend_of_steve.common.bes.LockedBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.LockBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class LockItem extends Item {
    private final Block lock;

    public LockItem(Settings settings, Block lock) {
        super(settings);
        this.lock = lock;
    }

    @Override
    public String getTranslationKey() {
        return this.lock.getTranslationKey();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();

        if (!(this.lock instanceof LockBlock) || state.getBlock() instanceof LockBlock || !state.isIn(ZeldaTags.LOCKABLE)) {
            return ActionResult.PASS;
        }

        Direction direction = Direction.NORTH;
        boolean waterlogged = false;

        LockedBlockEntity lockedBlock = new LockedBlockEntity(pos, state);
        lockedBlock.setLockedBlock(state);

        BlockEntity oldBlockEntity = world.getBlockEntity(pos);
        if (oldBlockEntity != null) {
            lockedBlock.setWrappedNBT(oldBlockEntity.createNbt());
            world.removeBlockEntity(pos);
        }

        if (state.contains(Properties.FACING)) {
            direction = state.get(Properties.FACING);
        } else if (state.contains(Properties.HORIZONTAL_FACING)) {
            direction = state.get(Properties.HORIZONTAL_FACING);
        } else if (player != null) {
            direction = player.getHorizontalFacing().getOpposite();
        }

        if (state.contains(Properties.WATERLOGGED)) {
            waterlogged = state.get(Properties.WATERLOGGED);
        }

        world.setBlockState(pos,
                this.lock.getDefaultState()
                        .with(Properties.FACING, direction)
                        .with(Properties.WATERLOGGED, waterlogged),
                Block.NOTIFY_ALL | Block.SKIP_DROPS
        );
        world.addBlockEntity(lockedBlock);

        if (!world.isClient) {
            world.playSound(null, pos, ZeldaSounds.LOCK, SoundCategory.BLOCKS);
            if (player != null && !player.isCreative()) {
                player.getStackInHand(context.getHand()).decrement(1);
            }
        }

        return ActionResult.SUCCESS;
    }
}
