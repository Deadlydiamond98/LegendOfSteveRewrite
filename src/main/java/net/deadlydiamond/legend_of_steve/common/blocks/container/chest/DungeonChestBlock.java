package net.deadlydiamond.legend_of_steve.common.blocks.container.chest;

import net.deadlydiamond.legend_of_steve.common.bes.container.chest.DungeonChestBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Supplier;

public class DungeonChestBlock extends ChestBlock {

    public DungeonChestBlock(Settings settings) {
        this(settings, () -> ZeldaBlockEntities.DUNGEON_CHEST);
    }

    public DungeonChestBlock(AbstractBlock.Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(settings, supplier);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DungeonChestBlockEntity(pos, state);
    }
}
