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

public class SmallDekuTreeFeature extends AbstractDekuTreeFeature {
    public SmallDekuTreeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        int trunkHeight = 3 + random.nextBetween(0, 1);

        if (!canGenerate(world, pos, pos.add(1, trunkHeight + 5, 1))) {
            return false;
        }

        // tree base
        placeWithDirt(world, pos, ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.east(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south().east(), ZeldaBlocks.DEKU_WOOD.log);

        // Tree root things
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

        placeSpiral(direction.rotateYClockwise(), pos, pos1 -> {
            place(world, pos1, ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        });

        // Leaves

        if (random.nextBoolean()) {
            placeSmallerLeafCircle(world, pos);
            placeLeafCircle(world, pos.up());
            placeSmallerLeafCircle(world, pos.up(2));
        } else {
            pos = random.nextBoolean() ? pos : pos.down();

            placeSmallerLeafCircle(world, pos);
            placeLeafCircle(world, pos.up());
            placeLeafCircle(world, pos.up(2));
            placeSmallerLeafCircle(world, pos.up(3));
        }

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

    /**
     * These Leaf Placing Methods aren't procedural, but it gives results that I've found I like better.
     */

    private void placeSmallerLeafCircle(StructureWorldAccess world, BlockPos pos) {
        BlockPos cornerTL = pos.offset(Direction.NORTH, 2).offset(Direction.WEST, 2);

        placeLeaf(world, cornerTL.east());
        placeLeaf(world, cornerTL.east(2));
        placeLeaf(world, cornerTL.east(3));
        placeLeaf(world, cornerTL.east(4));

        placeLeaf(world, cornerTL.south());
        placeLeaf(world, cornerTL.south().east());
        placeLeaf(world, cornerTL.south().east(2));
        placeLeaf(world, cornerTL.south().east(3));
        placeLeaf(world, cornerTL.south().east(4));
        placeLeaf(world, cornerTL.south().east(5));

        placeLeaf(world, cornerTL.south(2));
        placeLeaf(world, cornerTL.south(2).east());
        placeLeaf(world, cornerTL.south(2).east(2));
        placeLeaf(world, cornerTL.south(2).east(3));
        placeLeaf(world, cornerTL.south(2).east(4));
        placeLeaf(world, cornerTL.south(2).east(5));

        placeLeaf(world, cornerTL.south(3));
        placeLeaf(world, cornerTL.south(3).east());
        placeLeaf(world, cornerTL.south(3).east(2));
        placeLeaf(world, cornerTL.south(3).east(3));
        placeLeaf(world, cornerTL.south(3).east(4));
        placeLeaf(world, cornerTL.south(3).east(5));

        placeLeaf(world, cornerTL.south(4));
        placeLeaf(world, cornerTL.south(4).east());
        placeLeaf(world, cornerTL.south(4).east(2));
        placeLeaf(world, cornerTL.south(4).east(3));
        placeLeaf(world, cornerTL.south(4).east(4));
        placeLeaf(world, cornerTL.south(4).east(5));

        placeLeaf(world, cornerTL.south(5).east());
        placeLeaf(world, cornerTL.south(5).east(2));
        placeLeaf(world, cornerTL.south(5).east(3));
        placeLeaf(world, cornerTL.south(5).east(4));
    }

    private void placeLeafCircle(StructureWorldAccess world, BlockPos pos) {
        BlockPos cornerTL = pos.offset(Direction.NORTH, 3).offset(Direction.WEST, 3);

        placeLeaf(world, cornerTL.east(2));
        placeLeaf(world, cornerTL.east(3));
        placeLeaf(world, cornerTL.east(4));
        placeLeaf(world, cornerTL.east(5));

        placeLeaf(world, cornerTL.south().east());
        placeLeaf(world, cornerTL.south().east(2));
        placeLeaf(world, cornerTL.south().east(3));
        placeLeaf(world, cornerTL.south().east(4));
        placeLeaf(world, cornerTL.south().east(5));
        placeLeaf(world, cornerTL.south().east(6));

        placeLeaf(world, cornerTL.south(2));
        placeLeaf(world, cornerTL.south(2).east());
        placeLeaf(world, cornerTL.south(2).east(2));
        placeLeaf(world, cornerTL.south(2).east(3));
        placeLeaf(world, cornerTL.south(2).east(4));
        placeLeaf(world, cornerTL.south(2).east(5));
        placeLeaf(world, cornerTL.south(2).east(6));
        placeLeaf(world, cornerTL.south(2).east(7));

        placeLeaf(world, cornerTL.south(3));
        placeLeaf(world, cornerTL.south(3).east());
        placeLeaf(world, cornerTL.south(3).east(2));
        placeLeaf(world, cornerTL.south(3).east(3));
        placeLeaf(world, cornerTL.south(3).east(4));
        placeLeaf(world, cornerTL.south(3).east(5));
        placeLeaf(world, cornerTL.south(3).east(6));
        placeLeaf(world, cornerTL.south(3).east(7));

        placeLeaf(world, cornerTL.south(4));
        placeLeaf(world, cornerTL.south(4).east());
        placeLeaf(world, cornerTL.south(4).east(2));
        placeLeaf(world, cornerTL.south(4).east(3));
        placeLeaf(world, cornerTL.south(4).east(4));
        placeLeaf(world, cornerTL.south(4).east(5));
        placeLeaf(world, cornerTL.south(4).east(6));
        placeLeaf(world, cornerTL.south(4).east(7));

        placeLeaf(world, cornerTL.south(5));
        placeLeaf(world, cornerTL.south(5).east());
        placeLeaf(world, cornerTL.south(5).east(2));
        placeLeaf(world, cornerTL.south(5).east(3));
        placeLeaf(world, cornerTL.south(5).east(4));
        placeLeaf(world, cornerTL.south(5).east(5));
        placeLeaf(world, cornerTL.south(5).east(6));
        placeLeaf(world, cornerTL.south(5).east(7));

        placeLeaf(world, cornerTL.south(6).east());
        placeLeaf(world, cornerTL.south(6).east(2));
        placeLeaf(world, cornerTL.south(6).east(3));
        placeLeaf(world, cornerTL.south(6).east(4));
        placeLeaf(world, cornerTL.south(6).east(5));
        placeLeaf(world, cornerTL.south(6).east(6));

        placeLeaf(world, cornerTL.south(7).east(2));
        placeLeaf(world, cornerTL.south(7).east(3));
        placeLeaf(world, cornerTL.south(7).east(4));
        placeLeaf(world, cornerTL.south(7).east(5));
    }
}
