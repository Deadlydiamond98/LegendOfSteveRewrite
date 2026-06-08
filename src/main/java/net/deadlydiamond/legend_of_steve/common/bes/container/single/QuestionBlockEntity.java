package net.deadlydiamond.legend_of_steve.common.bes.container.single;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class QuestionBlockEntity extends SingleSlotBlockEntity {

    public QuestionBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ZeldaBlockEntities.QUESTION_BLOCK, blockPos, blockState);
    }

    public boolean isHit() {
        if (this.lootTableId == null) {
            return true;
        }
        return this.isEmpty();
    }
}
