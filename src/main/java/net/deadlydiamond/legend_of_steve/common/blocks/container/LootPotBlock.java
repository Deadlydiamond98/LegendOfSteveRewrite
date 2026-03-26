package net.deadlydiamond.legend_of_steve.common.blocks.container;

import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class LootPotBlock extends SingleSlotBlock {
    public static final VoxelShape SHAPE = Block.createCuboidShape(5, 0, 5, 11, 8, 11);

    public LootPotBlock(Settings settings) {
        super(settings);
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
