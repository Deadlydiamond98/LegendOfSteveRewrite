package net.deadlydiamond.legend_of_steve.common.blocks;

import net.deadlydiamond.legend_of_steve.common.particles.MagicSparkleParticleEffect;
import net.deadlydiamond.legend_of_steve.common.particles.SparkParticleEffect;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SpringWaterBlock extends FluidBlock {
    public SpringWaterBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        float height = world.getFluidState(pos).getHeight();

        if (random.nextFloat() <= 0.025) {
            MagicSparkleParticleEffect.createFountainSparkles(world, pos.toCenterPos().add(0, -0.5, 0).add(
                    random.nextFloat() - 0.5,
                    height,
                    random.nextFloat() - 0.5
            ), 1, 0.025, 0.01);
        } else if (random.nextFloat() <= 0.01) {
            SparkParticleEffect.createSparks(world, SparkParticleEffect.SOUL, pos.toCenterPos().add(
                    random.nextFloat() - 0.5,
                    random.nextFloat() - 0.5,
                    random.nextFloat() - 0.5
            ), 1);
        }
    }
}
