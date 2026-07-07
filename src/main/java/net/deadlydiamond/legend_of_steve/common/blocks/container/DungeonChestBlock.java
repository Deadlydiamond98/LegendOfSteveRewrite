package net.deadlydiamond.legend_of_steve.common.blocks.container;

import net.deadlydiamond.legend_of_steve.common.bes.container.DungeonChestBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class DungeonChestBlock extends ChestBlock {
    public DungeonChestBlock(Settings settings) {
        super(settings, () -> ZeldaBlockEntities.DUNGEON_CHEST);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DungeonChestBlockEntity(pos, state);
    }
}
