package net.deadlydiamond.legend_of_steve.common.blocks;

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
        if (random.nextFloat() <= 0.1) {
            SparkParticleEffect.createSparks(world, SparkParticleEffect.SOUL, pos.toCenterPos().add(
                    random.nextFloat() / 2.0,
                    random.nextFloat() / 2.0,
                    random.nextFloat() / 2.0
            ), 1);
        }
    }
}
