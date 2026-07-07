package net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock;

import net.deadlydiamond.legend_of_steve.common.bes.locks.LockedChestBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaAdvancements;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LockedChestBlock extends ChestBlock implements ILockedBlock {
    private final TagKey<Item> keyTag;

    public LockedChestBlock(Settings settings, TagKey<Item> keyTag) {
        super(settings, () -> ZeldaBlockEntities.LOCKED_CHEST);
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

        return ActionResult.PASS;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockState state1 = getLockedBlock(world, pos);
        return state1.getBlock().getPickStack(world, pos, state1);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getLockedBlock(world, pos).getOutlineShape(world, pos);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getLockedBlock(world, pos).getCollisionShape(world, pos);
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getLockedBlock(world, pos).getCameraCollisionShape(world, pos, context);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return getLockedBlock(world, pos).getRaycastShape(world, pos);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return getLockedBlock(world, pos).getCullingShape(world, pos);
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return getLockedBlock(world, pos).getSidesShape(world, pos);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LockedChestBlockEntity(pos, state);
    }

    @Override
    public boolean wrappedBlockModel() {
        return false;
    }
}
