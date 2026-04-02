package net.deadlydiamond.legend_of_steve.common.bes.container.single;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LootPotBlockEntity extends SingleSlotBlockEntity {
    public LootPotBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ZeldaBlockEntities.LOOT_POT, blockPos, blockState);
    }
}
