package net.deadlydiamond.legend_of_steve.worldgen.sapling;

import net.deadlydiamond.legend_of_steve.worldgen.ZeldaFeaturesDatagen;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class DekuSaplingGenerator extends LargeTreeSaplingGenerator {
    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return null;
    }

    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return ZeldaFeaturesDatagen.DEKU_TREE_CFG;
    }
}
