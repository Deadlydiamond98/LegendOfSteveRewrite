package net.deadlydiamond.legend_of_steve.worldgen.feature;

import com.mojang.serialization.Codec;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.function.Consumer;

public class SmallDekuTreeFeature extends Feature<DefaultFeatureConfig> {
    public SmallDekuTreeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        int trunkHeight = 3 + random.nextBetween(0, 1);

        // TREE BASE
        placeWithDirt(world, pos, ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.east(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south().east(), ZeldaBlocks.DEKU_WOOD.log);

        // TREE SIDE ROOT THINGS
        Direction direction = Direction.Type.HORIZONTAL.random(random);

        placeSpiral(direction, pos, pos1 -> {
            placeWithDirt(world, pos1, ZeldaBlocks.DEKU_WOOD.wood);
        });

        for (int i = 0; i < trunkHeight; i++) {
            pos = placeTrunkSection(world, pos);
        }

        placeSpiral(direction.rotateYClockwise(), pos, pos1 -> {
            if (trunkHeight > 3 && random.nextBoolean()) {
                place(world, pos1, ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
                place(world, pos1.down(), ZeldaBlocks.DEKU_WOOD.wood.getDefaultState());
            } else {
                place(world, pos1, ZeldaBlocks.DEKU_WOOD.wood.getDefaultState());
            }
        });

        pos = pos.up();


        BlockPos cornerTL = pos.offset(Direction.NORTH, 2).offset(Direction.WEST, 2);
        BlockPos cornerTR = cornerTL.offset(Direction.EAST, 5);
        BlockPos cornerBL = cornerTL.offset(Direction.SOUTH, 5);
        BlockPos cornerBR = cornerTL.offset(Direction.SOUTH, 5).offset(Direction.EAST, 5);

        BlockPos.iterate(cornerTL, cornerBR).forEach(pos1 -> {
            place(world, pos1, ZeldaBlocks.DEKU_LEAVES.getDefaultState());
        });

        place(world, cornerTL, Blocks.AIR.getDefaultState());
        place(world, cornerTR, Blocks.AIR.getDefaultState());
        place(world, cornerBL, Blocks.AIR.getDefaultState());
        place(world, cornerBR, Blocks.AIR.getDefaultState());

        placeSpiral(direction.rotateYClockwise(), pos, pos1 -> {
            place(world, pos1, ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        });

//        createBresenhamCircle(world, pos.up(4), Blocks.RED_WOOL.getDefaultState(), 4);
//        createBresenhamCircle(world, pos.up(5), Blocks.RED_WOOL.getDefaultState(), 5);
//        createBresenhamCircle(world, pos.up(6), Blocks.RED_WOOL.getDefaultState(), 4);


        return true;
    }

    private BlockPos placeTrunkSection(StructureWorldAccess world, BlockPos pos) {
        pos = pos.up();
        placeWithDirt(world, pos, ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.east(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south().east(), ZeldaBlocks.DEKU_WOOD.log);
        return pos;
    }

    private void placeSpiral(Direction direction, BlockPos pos, Consumer<BlockPos> consumer) {
        if (direction.getAxis() == Direction.Axis.X) {
            consumer.accept(pos.west());
            consumer.accept(pos.east().north());
            consumer.accept(pos.south().south());
            consumer.accept(pos.south().east().east());
        } else {
            consumer.accept(pos.north());
            consumer.accept(pos.east().east());
            consumer.accept(pos.south().west());
            consumer.accept(pos.south().east().south());
        }
    }

    private void placeWithDirt(StructureWorldAccess world, BlockPos pos, Block block) {
        BlockState belowBlock = world.getBlockState(pos.down());
        if (belowBlock.isFullCube(world, pos.down()) && world.getBlockState(pos).isReplaceable()) {
            if (supportsTrees(belowBlock)) {
                place(world, pos.down(), Blocks.DIRT.getDefaultState());
            }

            place(world, pos, block.getDefaultState());
        }
    }

    private void place(StructureWorldAccess world, BlockPos pos, BlockState block) {
        world.setBlockState(pos, block, Block.NOTIFY_LISTENERS);
    }

    private boolean supportsTrees(BlockState block) {
        return block.isIn(BlockTags.DIRT) || block.isOf(Blocks.FARMLAND);
    }

    private void createBresenhamCircle(StructureWorldAccess world, BlockPos pos, BlockState block, double radius) {
        double x = 0;
        double z = radius;
        double d = 3 - 2 * radius;

        createCirclePart(world, pos, block, x, z);

        while (z >= x) {
            if (d > 0) {
                z--;
                d = d + 4 * (x - z) + 10;
            } else {
                d = d + 4 * x + 6;
            }

            x++;
            createCirclePart(world, pos, block, x, z);
        }
    }

    private void createCirclePart(StructureWorldAccess world, BlockPos pos, BlockState block, double x, double z) {
        fillHorizontalLine(world, pos, block, z, x);
        fillHorizontalLine(world, pos, block, -z, x);

        fillHorizontalLine(world, pos, block, x, z);
        fillHorizontalLine(world, pos, block, -x, z);
    }

    private void fillHorizontalLine(StructureWorldAccess world, BlockPos pos, BlockState block, double zOffset, double radius) {
        for (double i = -radius; i <= radius; i++) {
            place(world, new BlockPos((int) Math.round(pos.getX() + i), pos.getY(), (int) Math.round(pos.getZ() + zOffset)), block);
        }
    }

}
