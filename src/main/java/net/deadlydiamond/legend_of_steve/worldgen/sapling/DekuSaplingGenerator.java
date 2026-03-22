package net.deadlydiamond.legend_of_steve.worldgen.sapling;

import net.deadlydiamond.legend_of_steve.worldgen.ZeldaFeaturesDatagen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class DekuSaplingGenerator extends LargeTreeSaplingGenerator {

    @Override
    public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
        for (int i = 0; i >= -2; i--) {
            for (int j = 0; j >= -2; j--) {
                if (canGenerateMassiveTree(state, world, pos, i, j)) {
                    return this.generateMassiveTree(world, chunkGenerator, pos, state, random, i, j);
                }
            }
        }
        return super.generate(world, chunkGenerator, pos, state, random);
    }

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

    protected RegistryKey<ConfiguredFeature<?, ?>> getMassiveTreeFeature() {
        return ZeldaFeaturesDatagen.LARGE_DEKU_TREE_CFG;
    }

    public boolean generateMassiveTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random, int x, int z) {
        RegistryKey<ConfiguredFeature<?, ?>> registryKey = this.getMassiveTreeFeature();
        if (registryKey == null) {
            return false;
        } else {
            RegistryEntry<ConfiguredFeature<?, ?>> registryEntry = world.getRegistryManager()
                    .get(RegistryKeys.CONFIGURED_FEATURE)
                    .getEntry(registryKey)
                    .orElse(null);
            if (registryEntry == null) {
                return false;
            } else {
                ConfiguredFeature<?, ?> configuredFeature = registryEntry.value();
                BlockState blockState = Blocks.AIR.getDefaultState();

                for (int dx = 0; dx <= 2; dx++) {
                    for (int dz = 0; dz <= 2; dz++) {
                        world.setBlockState(pos.add(x + dx, 0, z + dz), blockState, Block.NO_REDRAW);
                    }
                }

                if (configuredFeature.generate(world, chunkGenerator, random, pos.add(x, 0, z))) {
                    return true;
                } else {
                    for (int dx = 0; dx <= 2; dx++) {
                        for (int dz = 0; dz <= 2; dz++) {
                            world.setBlockState(pos.add(x + dx, 0, z + dz), state, Block.NO_REDRAW);
                        }
                    }
                    return false;
                }
            }
        }
    }


    public static boolean canGenerateMassiveTree(BlockState state, BlockView world, BlockPos pos, int x, int z) {
        Block block = state.getBlock();
        return world.getBlockState(pos.add(x, 0, z)).isOf(block)
                && world.getBlockState(pos.add(x + 1, 0, z)).isOf(block)
                && world.getBlockState(pos.add(x + 2, 0, z)).isOf(block)
                && world.getBlockState(pos.add(x, 0, z + 1)).isOf(block)
                && world.getBlockState(pos.add(x + 1, 0, z + 1)).isOf(block)
                && world.getBlockState(pos.add(x + 2, 0, z + 1)).isOf(block)
                && world.getBlockState(pos.add(x, 0, z + 2)).isOf(block)
                && world.getBlockState(pos.add(x + 1, 0, z + 2)).isOf(block)
                && world.getBlockState(pos.add(x + 2, 0, z + 2)).isOf(block);
    }
}
