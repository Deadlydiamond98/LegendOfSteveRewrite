package net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock;

import net.deadlydiamond.legend_of_steve.common.bes.locks.LockedBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaAdvancements;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LockedDoorBlock extends DoorBlock implements ILockedBlock, BlockEntityProvider {
    private final TagKey<Item> keyTag;

    public LockedDoorBlock(Settings settings, TagKey<Item> keyTag) {
        super(settings, BlockSetType.IRON);
        this.keyTag = keyTag;
    }

    @Override
    public TagKey<Item> getKeyTag() {
        return this.keyTag;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack key = player.getStackInHand(hand);

        if (removeLock(world, pos, key)) {
            if (!world.isClient && !player.isCreative() && !key.isOf(ZeldaItems.CREATIVE_KEY)) {
                key.decrement(1);
            }

            ZeldaAdvancements.LOCKE_AND_KEY.trigger(player);
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockState state1 = getLockedBlock(world, pos);
        return state1.getBlock().getPickStack(world, pos, state1);
    }

    @Override
    public void setOpen(@Nullable Entity entity, World world, BlockState state, BlockPos pos, boolean open) {}

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {}

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
    }

    @Nullable @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LockedBlockEntity(pos, state);
    }
}
