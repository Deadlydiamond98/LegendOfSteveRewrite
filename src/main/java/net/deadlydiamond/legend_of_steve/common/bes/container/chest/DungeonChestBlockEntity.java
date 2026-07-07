package net.deadlydiamond.legend_of_steve.common.bes.container.chest;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;

public class DungeonChestBlockEntity extends ChestBlockEntity {

    public DungeonChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ZeldaBlockEntities.DUNGEON_CHEST, blockPos, blockState);
    }

    protected DungeonChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
}
