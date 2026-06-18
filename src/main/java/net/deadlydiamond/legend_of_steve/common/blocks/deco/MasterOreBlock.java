package net.deadlydiamond.legend_of_steve.common.blocks.deco;

import net.deadlydiamond.legend_of_steve.init.ZeldaParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MasterOreBlock extends ExperienceDroppingBlock {
    public MasterOreBlock(Settings settings) {
        super(settings, UniformIntProvider.create(3, 7));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.1f) {
            Direction direction = Direction.random(random);
            double d = 0.5625;

            BlockPos blockPos = pos.offset(direction);
            if (!world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + d * direction.getOffsetX() : random.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + d * direction.getOffsetY() : random.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + d * direction.getOffsetZ() : random.nextFloat();
                world.addParticle(
                        ZeldaParticleTypes.CRYSTAL_SWITCH_ON_PARTICLE,
                        pos.getX() + e, pos.getY() + f, pos.getZ() + g,
                        0.0, 0.0, 0.0
                );
            }
        }
    }
}
