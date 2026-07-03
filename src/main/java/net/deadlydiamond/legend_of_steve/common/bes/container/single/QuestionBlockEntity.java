package net.deadlydiamond.legend_of_steve.common.bes.container.single;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.common.ZeldaProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.math.BlockPos;

public class QuestionBlockEntity extends SingleSlotBlockEntity {

    public QuestionBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ZeldaBlockEntities.QUESTION_BLOCK, blockPos, blockState);
    }

    protected QuestionBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    protected int getMaxAmount(ItemStack stack) {
        if (stack.getItem() instanceof SpawnEggItem) {
            return 1;
        }

        return super.getMaxAmount(stack);
    }

    public boolean isHit() {
        if (this.lootTableId != null) {
            return false;
        }
        return this.isEmpty();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (getWorld() != null) {
            getWorld().setBlockState(getPos(), getCachedState().with(ZeldaProperties.HIT, isHit()));
        }
    }
}
