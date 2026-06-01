package net.deadlydiamond.legend_of_steve.common.blocks.deco.glowing;

import net.deadlydiamond.legend_of_steve.common.entities.living.fairy.FairyColor;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FairyLamp extends GlowingBlock {
    private final FairyColor color;

    public FairyLamp(Settings settings, FairyColor color) {
        super(settings);
        this.color = color;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextBoolean()) {
            this.color.createMagicSparkleParticles(world, pos.toCenterPos().add(
                    (random.nextFloat() - 0.5f) * 1.25,
                    (random.nextFloat() - 0.5f) * 1.25,
                    (random.nextFloat() - 0.5f) * 1.25
            ));
        }
    }
}
