package net.deadlydiamond.legend_of_steve.worldgen.feature;

import com.mojang.serialization.Codec;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class LargeDekuTreeFeature extends AbstractDekuTreeFeature {
    public LargeDekuTreeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        int trunkHeight = 5 + random.nextBetween(0, 1);

        if (!canGenerate(world, pos, pos.add(2, trunkHeight + 7, 2))) {
            return false;
        }

        placeWithDirt(world, pos, ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.east(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south().east(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.east().east(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south().east().east(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south().south(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south().south().east(), ZeldaBlocks.DEKU_WOOD.log);
        placeWithDirt(world, pos.south().south().east().east(), ZeldaBlocks.DEKU_WOOD.log);

        // Protruding Root Things //////////////////////////////////////////////////////////////////////////////////////
        placeRoots(world, pos);

        // Trunk ///////////////////////////////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < trunkHeight; i++) {
            pos = placeTrunkSection(world, pos.up());
        }

        place(world, pos.up().east(), ZeldaBlocks.DEKU_WOOD.log);
        place(world, pos.up().south(), ZeldaBlocks.DEKU_WOOD.log);
        place(world, pos.up().south().south().east(), ZeldaBlocks.DEKU_WOOD.log);
        place(world, pos.up().south().east().east(), ZeldaBlocks.DEKU_WOOD.log);

        place(world, pos.up().up().south().east(), ZeldaBlocks.DEKU_WOOD.log);

        // Branches ////////////////////////////////////////////////////////////////////////////////////////////////////
        placeBranches(world, pos);

        // Leaves //////////////////////////////////////////////////////////////////////////////////////////////////////
        pos = pos.south().east();


        BlockPos leafPos = random.nextBoolean() ? pos : pos.up();

        createLeafCircle(world, leafPos.down(), 4);
        createLeafCircle(world, leafPos, 5);
        createLeafCircle(world, leafPos.up(), 5);
        createLeafCircle(world, leafPos.up().up(), 4.9);
        createLeafCircle(world, leafPos.up().up().up(), 3);

        return true;
    }

    private void placeRoots(StructureWorldAccess world, BlockPos pos) {
        BlockPos rootPos = pos.south();
        if (placeWithDirt(world, rootPos.west(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.X))) {
            place(world, rootPos.west().up(), ZeldaBlocks.DEKU_WOOD.wood);
            placeWithDirt(world, rootPos.west().west(), ZeldaBlocks.DEKU_WOOD.wood.getDefaultState().with(Properties.AXIS, Direction.Axis.X));
        }

        rootPos = pos.south().east().east();
        if (placeWithDirt(world, rootPos.east(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.X))) {
            place(world, rootPos.east().up(), ZeldaBlocks.DEKU_WOOD.wood);
            placeWithDirt(world, rootPos.east().east(), ZeldaBlocks.DEKU_WOOD.wood.getDefaultState().with(Properties.AXIS, Direction.Axis.X));
        }

        rootPos = pos.east();
        if (placeWithDirt(world, rootPos.north(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.Z))) {
            place(world, rootPos.north().up(), ZeldaBlocks.DEKU_WOOD.wood);
            placeWithDirt(world, rootPos.north().north(), ZeldaBlocks.DEKU_WOOD.wood.getDefaultState().with(Properties.AXIS, Direction.Axis.Z));
        }

        rootPos = pos.south().south().east();
        if (placeWithDirt(world, rootPos.south(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.Z))) {
            place(world, rootPos.south().up(), ZeldaBlocks.DEKU_WOOD.wood);
            placeWithDirt(world, rootPos.south().south(), ZeldaBlocks.DEKU_WOOD.wood.getDefaultState().with(Properties.AXIS, Direction.Axis.Z));
        }


        placeWithDirt(world, pos.north().west(), ZeldaBlocks.DEKU_WOOD.wood);
        placeWithDirt(world, pos.south().south().south().west(), ZeldaBlocks.DEKU_WOOD.wood);
        placeWithDirt(world, pos.east().east().east().north(), ZeldaBlocks.DEKU_WOOD.wood);
        placeWithDirt(world, pos.south().south().south().east().east().east(), ZeldaBlocks.DEKU_WOOD.wood);
    }

    private void placeBranches(StructureWorldAccess world, BlockPos pos) {
        pos = pos.down(1);

        BlockPos branchPos = pos.south();
        place(world, branchPos.west().up(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.X));
        place(world, branchPos.west().west().up(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.X));
        place(world, branchPos.west(), ZeldaBlocks.DEKU_WOOD.wood.getDefaultState().with(Properties.AXIS, Direction.Axis.X));

        branchPos = pos.south().east().east();
        place(world, branchPos.east().up(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.X));
        place(world, branchPos.east().east().up(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.X));
        place(world, branchPos.east(), ZeldaBlocks.DEKU_WOOD.wood.getDefaultState().with(Properties.AXIS, Direction.Axis.X));

        branchPos = pos.east();
        place(world, branchPos.north().up(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.Z));
        place(world, branchPos.north().north().up(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.Z));
        place(world, branchPos.north(), ZeldaBlocks.DEKU_WOOD.wood.getDefaultState().with(Properties.AXIS, Direction.Axis.Z));

        branchPos = pos.south().south().east();
        place(world, branchPos.south().up(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.Z));
        place(world, branchPos.south().south().up(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState().with(Properties.AXIS, Direction.Axis.Z));
        place(world, branchPos.south(), ZeldaBlocks.DEKU_WOOD.wood.getDefaultState().with(Properties.AXIS, Direction.Axis.Z));
    }


    private BlockPos placeTrunkSection(StructureWorldAccess world, BlockPos pos) {
        place(world, pos, ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        place(world, pos.east(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        place(world, pos.south(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        place(world, pos.south().east(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        place(world, pos.east().east(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        place(world, pos.south().east().east(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        place(world, pos.south().south(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        place(world, pos.south().south().east(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        place(world, pos.south().south().east().east(), ZeldaBlocks.DEKU_WOOD.log.getDefaultState());
        return pos;
    }

    private void createLeafCircle(StructureWorldAccess world, BlockPos pos, double radius) {
        double x = 0;
        double z = radius;
        double d = 3 - 2 * radius;

        createCirclePart(world, pos, x, z);

        while (z >= x) {
            if (d > 0) {
                z--;
                d = d + 4 * (x - z) + 10;
            } else {
                d = d + 4 * x + 6;
            }

            x++;
            createCirclePart(world, pos, x, z);
        }
    }

    private void createCirclePart(StructureWorldAccess world, BlockPos pos, double x, double z) {
        fillHorizontalLine(world, pos, z, x);
        fillHorizontalLine(world, pos, -z, x);

        fillHorizontalLine(world, pos, x, z);
        fillHorizontalLine(world, pos, -x, z);
    }

    private void fillHorizontalLine(StructureWorldAccess world, BlockPos pos, double zOffset, double radius) {
        for (double i = -radius; i <= radius; i++) {
            int offsetA = (int) (Math.signum(i) > 0 ? Math.floor(i) : Math.ceil(i));
            int offsetB = (int) (Math.signum(zOffset) > 0 ? Math.floor(zOffset) : Math.ceil(zOffset));
            placeLeaf(world, new BlockPos(pos.getX() + offsetA, pos.getY(), pos.getZ() + offsetB));
        }
    }
}
