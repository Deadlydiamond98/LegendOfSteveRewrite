package net.deadlydiamond.legend_of_steve.common.blocks.light;

import net.deadlydiamond.legend_of_steve.common.bes.GlowingBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface IGlowingBlock extends BlockEntityProvider {

    default float getGlowScale() {
        return 1;
    }

    @Nullable
    @Override
    default BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GlowingBlockEntity(pos, state);
    }

    default boolean stopZFighting() {
        return true;
    }
}
