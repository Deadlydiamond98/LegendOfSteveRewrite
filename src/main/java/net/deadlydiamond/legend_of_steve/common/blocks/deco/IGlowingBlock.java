package net.deadlydiamond.legend_of_steve.common.blocks.deco;

import net.deadlydiamond.legend_of_steve.common.bes.visual.GlowingBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface IGlowingBlock extends BlockEntityProvider {

    default float getGlowScale() {
        return 1;
    }
    default float bloomIntensity() {return 1;}
    default boolean stopZFighting() {
        return true;
    }

    @Nullable @Override
    default BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GlowingBlockEntity(pos, state);
    }
}
