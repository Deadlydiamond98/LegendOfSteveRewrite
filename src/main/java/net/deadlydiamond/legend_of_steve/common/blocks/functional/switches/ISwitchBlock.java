package net.deadlydiamond.legend_of_steve.common.blocks.functional.switches;

import net.deadlydiamond.legend_of_steve.common.bes.switches.SwitchBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaParticleTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface ISwitchBlock extends BlockEntityProvider {

    // SWITCH TOGGLING /////////////////////////////////////////////////////////////////////////////////////////////////

    boolean startOn();

    default boolean isOn(BlockView world, BlockPos pos) {
        if (getBlockEntity(world, pos) instanceof SwitchBlockEntity switchBlock) {
            return switchBlock.isOn();
        }
        return true;
    }

    default void triggerSwitch(World world, BlockPos pos) {
        if (getBlockEntity(world, pos) instanceof SwitchBlockEntity switchBlock) {
            switchBlock.triggerSwitch(getTriggerCooldown());
        }
    }

    default <T extends SwitchBlockEntity> void onSwitchTriggered(World world, BlockPos pos, BlockState state, T blockEntity, boolean newOnState) {
        if (world.isClient()) {
            createCulledParticles(world, pos, state.getOutlineShape(world, pos), 10, 0.125f, startOn(), false);
        }
    }

    default int getTriggerCooldown() {
        return 0;
    }

    // BLOCK ENTITY STUFF //////////////////////////////////////////////////////////////////////////////////////////////

    @Nullable
    default BlockEntity getBlockEntity(BlockView world, BlockPos pos) {
        return world.getBlockEntity(pos);
    }

    @Nullable
    @Override
    default BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SwitchBlockEntity(pos, state);
    }

    // PARTICLE STUFF //////////////////////////////////////////////////////////////////////////////////////////////////

    default void createCulledParticles(World world, BlockPos pos, VoxelShape shape, int count, float distance, boolean on, boolean keepOutBox) {
        BlockState state = world.getBlockState(pos);
        boolean showParticles = false;
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (Direction direction : Direction.values()) {
            mutable.set(pos, direction);
            if (Block.shouldDrawSide(state, world, pos, direction, mutable)) {
                showParticles = true;
                break;
            }
        }

        if (showParticles) {
            createSwitchParticles(world, pos, shape, count, distance, on, keepOutBox);
        }
    }

    default void createSwitchParticles(World world, BlockPos blockPos, VoxelShape shape, int count, float distance, boolean on, boolean keepOutBox) {
        for (int i = 0; i < count; i++) {
            createSwitchParticle(world, blockPos, shape, distance, on, keepOutBox);
        }
    }

    default void createSwitchParticle(World world, BlockPos blockPos, VoxelShape shape, float distance, boolean on, boolean keepOutBox) {
        if (distance <= 0) {
            return;
        }

        Vec3d blockPosOffset = blockPos.toCenterPos().add(-0.5, -0.5, -0.5);
        Box box = shape.getBoundingBoxes().get(world.random.nextBetween(0, shape.getBoundingBoxes().size() - 1)).offset(blockPosOffset);

        Vec3d centerPos = box.getCenter();
        Box expandedBox = box.expand(distance);
        double dx = (expandedBox.maxX - expandedBox.minX) / 2;
        double dy = (expandedBox.maxY - expandedBox.minY) / 2;
        double dz = (expandedBox.maxZ - expandedBox.minZ) / 2;

        Vec3d particlePos;
        for (int i = 0; i < 5; i++) {
            particlePos = centerPos.add(
                    (world.random.nextFloat() * dx) * (world.random.nextBoolean() ? -1 : 1),
                    (world.random.nextFloat() * dy) * (world.random.nextBoolean() ? -1 : 1),
                    (world.random.nextFloat() * dz) * (world.random.nextBoolean() ? -1 : 1)
            );

            if (!keepOutBox || !box.contains(particlePos)) {
                createSwitchParticle(world, particlePos, on);
                break;
            }
        }
    }

    default void createSwitchParticle(World world, Vec3d particlePos, boolean on) {
        ParticleEffect effect = on ? ZeldaParticleTypes.CRYSTAL_SWITCH_ON_PARTICLE : ZeldaParticleTypes.CRYSTAL_SWITCH_OFF_PARTICLE;
        world.addParticle(effect, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0);
    }
}
