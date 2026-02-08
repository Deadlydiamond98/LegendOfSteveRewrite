package net.deadlydiamond.legend_of_steve.common.world;

import net.deadlydiamond.legend_of_steve.common.blocks.IExplodedInteraction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Optional;
import java.util.function.Predicate;

public class BombExplosionBehavior extends ExplosionBehavior {
    private final Predicate<Block> breakableBlocks;
    private final boolean canWorkInWater;
    private final World world;

    public BombExplosionBehavior(World world, Predicate<Block> breakableBlocks) {
        this(world, breakableBlocks, false);
    }

    public BombExplosionBehavior(World world, Predicate<Block> breakableBlocks, boolean canWorkInWater) {
        this.world = world;
        this.breakableBlocks = breakableBlocks;
        this.canWorkInWater = canWorkInWater;
    }

    @Override
    public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
        if (fluidState.isIn(FluidTags.LAVA) || (this.canWorkInWater && fluidState.isIn(FluidTags.WATER))) {
            return Optional.empty();
        }
        return super.getBlastResistance(explosion, world, pos, blockState, fluidState);
    }

    @Override
    public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
        if (state.getBlock() instanceof IExplodedInteraction interaction) {
            interaction.onBombExploded(this.world, pos, explosion);
        }
        return breakableBlocks.test(state.getBlock()) && super.canDestroyBlock(explosion, world, pos, state, power);
    }
}
