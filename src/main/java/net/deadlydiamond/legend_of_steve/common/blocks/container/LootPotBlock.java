package net.deadlydiamond.legend_of_steve.common.blocks.container;

import net.deadlydiamond.legend_of_steve.common.bes.container.single.SingleSlotBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LootPotBlock extends WaterloggableSingleSlotBlock {
    public static final VoxelShape SHAPE = Block.createCuboidShape(5, 0, 5, 11, 8, 11);

    public LootPotBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.isSneaking()) {
            if (player.getStackInHand(hand).isEmpty()) {
                if (world.getBlockEntity(pos) instanceof SingleSlotBlockEntity blockEntity) {
                    ItemStack stack = asItem().getDefaultStack();
                    blockEntity.setStackNbt(stack);
                    player.setStackInHand(hand, stack);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    if (!world.isClient()) {
                        player.playSound(ZeldaSounds.LOOT_POT_GRAB, SoundCategory.BLOCKS, 1, 1);
                    }
                }
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected SoundEvent getInsertSound() {
        return ZeldaSounds.LOOT_POT_DEPOSIT;
    }

    @Override
    protected SoundEvent getRemoveSound() {
        return ZeldaSounds.LOOT_POT_WITHDRAW;
    }
}
