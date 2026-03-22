package net.deadlydiamond.legend_of_steve.worldgen.feature;

import com.mojang.serialization.Codec;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public abstract class AbstractDekuTreeFeature extends Feature<DefaultFeatureConfig>  {
    public AbstractDekuTreeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    protected boolean canGenerate(StructureWorldAccess world, BlockPos pos, BlockPos pos2) {
        for (BlockPos blockPos : BlockPos.iterate(pos, new BlockPos(pos2.getX(), pos.getY(), pos2.getZ()))) {
            if (!supportsTrees(world.getBlockState(blockPos.down()))) {
                return false;
            }
        }

        for (BlockPos blockPos : BlockPos.iterate(pos.add(-1, 0, -1), pos2.add(1, 0, 1))) {
            if (!canGenerateAt(world, blockPos) || world.getTopY() < blockPos.getY()) {
                return false;
            }
        }
        return true;
    }

    private boolean canGenerateAt(StructureWorldAccess world, BlockPos pos) {
        return world.getBlockState(pos).isReplaceable() ||
                world.getBlockState(pos).isIn(BlockTags.LEAVES) ||
                world.getBlockState(pos).isIn(BlockTags.SMALL_FLOWERS);
    }

    protected void placeWithDirt(StructureWorldAccess world, BlockPos pos, Block block) {
        placeWithDirt(world, pos, block.getDefaultState());
    }

    protected boolean placeWithDirt(StructureWorldAccess world, BlockPos pos, BlockState block) {
        BlockState belowBlock = world.getBlockState(pos.down());
        if (belowBlock.isFullCube(world, pos.down()) && canGenerateAt(world, pos)) {
            if (supportsTrees(belowBlock)) {
                place(world, pos.down(), Blocks.DIRT.getDefaultState());
            }

            place(world, pos, block);
            return true;
        }
        return false;
    }

    protected void placeLeaf(StructureWorldAccess world, BlockPos pos) {
        placeLeaf(world, pos, world.getRandom().nextFloat() > 0.01f ?
                ZeldaBlocks.DEKU_LEAVES.getDefaultState() : ZeldaBlocks.FRUITING_DEKU_LEAVES.getDefaultState()
        );
    }

    protected void placeLeaf(StructureWorldAccess world, BlockPos pos, BlockState block) {
        if (world.getBlockState(pos).isReplaceable()) {
            place(world, pos, block);
        }
    }

    protected void place(StructureWorldAccess world, BlockPos pos, Block block) {
        placeLeaf(world, pos, block.getDefaultState());
    }

    protected void place(StructureWorldAccess world, BlockPos pos, BlockState block) {
        world.setBlockState(pos, block, Block.NOTIFY_LISTENERS);
    }

    protected boolean supportsTrees(BlockState block) {
        return block.isIn(BlockTags.DIRT) || block.isOf(Blocks.FARMLAND);
    }
}
