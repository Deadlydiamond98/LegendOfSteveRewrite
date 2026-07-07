package net.deadlydiamond.legend_of_steve.common.items.locking;

import net.deadlydiamond.legend_of_steve.common.blocksets.LockBlockset;
import net.deadlydiamond.legend_of_steve.init.ZeldaDispenserBehaviors;
import net.deadlydiamond.legend_of_steve.util.LockManager;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class LockItem extends Item {
    private final LockBlockset lock;

    public LockItem(Settings settings, LockBlockset lock) {
        super(settings);
        this.lock = lock;
        DispenserBlock.registerBehavior(this, ZeldaDispenserBehaviors.lock());
    }

    @Override
    public String getTranslationKey() {
        return this.lock.lockedBlock.getTranslationKey();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        Direction direction = player != null ? player.getHorizontalFacing().getOpposite() : Direction.NORTH;

        if (LockManager.tryLockBlock(world, pos, direction, getLockResult(world.getBlockState(pos)))) {
            if (!world.isClient) {
                if (player != null && !player.isCreative()) {
                    player.getStackInHand(context.getHand()).decrement(1);
                }
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public BlockState getLockResult(BlockState input) {
        if (input.getBlock() instanceof ChestBlock) {
            return this.lock.lockedChest.getDefaultState();
        } else if (input.getBlock() instanceof DoorBlock) {
            return this.lock.lockedDoor.getDefaultState();
        } else if (input.getBlock() instanceof TrapdoorBlock) {
            return this.lock.lockedTrapdoor.getDefaultState();
        }
        return this.lock.lockedBlock.getDefaultState();
    }

}
