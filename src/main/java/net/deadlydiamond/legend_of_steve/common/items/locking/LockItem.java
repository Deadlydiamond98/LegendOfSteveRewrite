package net.deadlydiamond.legend_of_steve.common.items.locking;

import net.deadlydiamond.legend_of_steve.common.bes.locks.ILockedBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.locks.LockedBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.ILockedBlock;
import net.deadlydiamond.legend_of_steve.common.blocksets.LockBlockset;
import net.deadlydiamond.legend_of_steve.init.ZeldaDispenserBehaviors;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
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

        if (tryLockBlock(world, pos, direction, false)) {
            if (!world.isClient) {
                if (player != null && !player.isCreative()) {
                    player.getStackInHand(context.getHand()).decrement(1);
                }
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public boolean tryLockBlock(World world, BlockPos pos, Direction facing, boolean doubleChest) {
        BlockState state = world.getBlockState(pos);

        if (!(this.lock.lockedBlock instanceof ILockedBlock) || state.getBlock() instanceof ILockedBlock || !state.isIn(ZeldaTags.LOCKABLE)) {
            return false;
        }

        if (state.getBlock() instanceof PistonBlock && state.get(Properties.EXTENDED)) {
            return false;
        }

        BlockEntity oldBlockEntity = world.getBlockEntity(pos);
        NbtCompound wrappedNBT = new NbtCompound();
        if (oldBlockEntity != null) {
            wrappedNBT = oldBlockEntity.createNbt();
            world.removeBlockEntity(pos);
        }

        world.setBlockState(pos, getState(world, pos, state, facing, doubleChest), Block.NOTIFY_ALL | Block.SKIP_DROPS);
        if (world.getBlockEntity(pos) instanceof ILockedBlockEntity lockedBlock) {
            lockedBlock.setLockedBlock(state);
            lockedBlock.setWrappedNBT(wrappedNBT);
        }

        if (!world.isClient) {
            world.playSound(null, pos, ZeldaSounds.LOCK, SoundCategory.BLOCKS);
        }
        return true;
    }

    private BlockState getState(World world, BlockPos pos, BlockState state, Direction facing, boolean doubleChest) {
        boolean waterlogged = false;

        if (state.contains(Properties.WATERLOGGED)) {
            waterlogged = state.get(Properties.WATERLOGGED);
        }

        if (state.getBlock() instanceof ChestBlock) {
            ChestType type = state.get(Properties.CHEST_TYPE);
            Direction chestFacing = state.get(Properties.HORIZONTAL_FACING);

            if (!doubleChest) {
                if (type.getOpposite() != type) {
                    Direction offset = type == ChestType.LEFT ?
                            chestFacing.rotateYClockwise() :
                            chestFacing.rotateYCounterclockwise();

                    tryLockBlock(world, pos.offset(offset), facing, true);
                }
            }

            return this.lock.lockedChest.getDefaultState()
                    .with(Properties.WATERLOGGED, waterlogged)
                    .with(Properties.HORIZONTAL_FACING, chestFacing)
                    .with(Properties.CHEST_TYPE, type);
        } else {
            Direction direction;

            if (state.contains(Properties.FACING)) {
                direction = state.get(Properties.FACING);
            } else if (state.contains(Properties.HORIZONTAL_FACING)) {
                direction = state.get(Properties.HORIZONTAL_FACING);
            } else {
                direction = facing;
            }

            return this.lock.lockedBlock.getDefaultState()
                    .with(Properties.WATERLOGGED, waterlogged)
                    .with(Properties.FACING, direction);
        }
    }
}
