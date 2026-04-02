package net.deadlydiamond.legend_of_steve.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface IJumpIntoAction {
    void jumpIntoBlock(World world, BlockPos pos, BlockState state,  @Nullable Entity entity);

    default boolean canHitBlockWithHead(World world, BlockPos pos, BlockState state, Entity entity) {
        return !(entity instanceof PlayerEntity player) || !player.getAbilities().flying;
    }
}
