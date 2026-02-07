package net.deadlydiamond.legend_of_steve.common.blocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public interface IExplodedInteraction {
    default void onExploded(World world, BlockPos blockPos, Explosion explosion) {}
    default void onBombExploded(World world, BlockPos blockPos, Explosion explosion) {}
}