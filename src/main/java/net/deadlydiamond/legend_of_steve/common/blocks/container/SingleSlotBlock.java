package net.deadlydiamond.legend_of_steve.common.blocks.container;

import net.deadlydiamond.legend_of_steve.common.bes.container.single.SingleSlotBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class SingleSlotBlock extends BlockWithEntity {
    public SingleSlotBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof SingleSlotBlockEntity blockEntity) {
            blockEntity.checkLootInteraction(player);
            ItemScatterer.spawn(world, pos, blockEntity.getInvStackList());
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (world.getBlockEntity(pos) instanceof SingleSlotBlockEntity blockEntity) {
            blockEntity.checkLootInteraction(player);
            if (canRemoveItem(state, world, pos, player, hand, stack, blockEntity)) {
                player.setStackInHand(hand, blockEntity.getStack(0));
                blockEntity.clear();
                if (!world.isClient()) {
                    player.playSound(getRemoveSound(), SoundCategory.BLOCKS, 1, 1);
                }
                blockEntity.markDirty();
                return ActionResult.SUCCESS;
            } else if (canInsertItem(state, world, pos, player, hand, stack, blockEntity)) {
                if (!world.isClient()) {
                    player.playSound(getInsertSound(), SoundCategory.BLOCKS, 1, 1);
                }
                blockEntity.markDirty();
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SingleSlotBlockEntity(pos, state);
    }

    // SOUNDS & OTHER HELPER METHODS ///////////////////////////////////////////////////////////////////////////////////

    public boolean canInsertItem(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, SingleSlotBlockEntity blockEntity) {
        if (player.isCreative()) {
            stack = stack.copy();
        }
        return isValid(stack) && blockEntity.insertStack(stack);
    }

    public boolean canRemoveItem(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, SingleSlotBlockEntity blockEntity) {
        return !blockEntity.isEmpty() && player.getStackInHand(hand).isEmpty();
    }

    protected abstract SoundEvent getInsertSound();
    protected abstract SoundEvent getRemoveSound();

    public boolean isValid(ItemStack stack) {
        return true;
    }
}
