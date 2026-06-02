package net.deadlydiamond.legend_of_steve.common.blocks.functional.switches;

import net.deadlydiamond.legend_of_steve.common.bes.switches.SwitchBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaParticleTypes;
import net.deadlydiamond.legend_of_steve.util.ZeldaProperties;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
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
        return false;
    }

    default void triggerSwitch(World world, BlockPos pos) {
        if (getBlockEntity(world, pos) instanceof SwitchBlockEntity switchBlock) {
            switchBlock.triggerSwitch(getTriggerCooldown());
        }
    }

    default <T extends SwitchBlockEntity> void onSwitchTriggered(World world, BlockPos pos, BlockState state, T blockEntity, boolean newOnState) {
        if (state.contains(ZeldaProperties.ON)) {
            world.setBlockState(pos, state.with(ZeldaProperties.ON, newOnState));
        }
        createSwitchParticles(world, pos, state.getOutlineShape(world, pos), 20, 0.125f);
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

    @Nullable
    @Override
    default <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, entity) -> {
            if (entity instanceof SwitchBlockEntity switchBlock) {
                SwitchBlockEntity.tick(world, pos, state, switchBlock);
            }
        };
    }

    // PARTICLE STUFF //////////////////////////////////////////////////////////////////////////////////////////////////

    default void createSwitchParticles(World world, BlockPos blockPos, VoxelShape shape, int count, float distance) {
        for (int i = 0; i < count; i++) {
            createSwitchParticle(world, blockPos, shape, distance);
        }
    }

    default void createSwitchParticle(World world, BlockPos blockPos, VoxelShape shape, float distance) {
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
        for (int i = 0; i < 10; i++) {
            particlePos = centerPos.add(
                    (world.random.nextFloat() * dx) * (world.random.nextBoolean() ? -1 : 1),
                    (world.random.nextFloat() * dy) * (world.random.nextBoolean() ? -1 : 1),
                    (world.random.nextFloat() * dz) * (world.random.nextBoolean() ? -1 : 1)
            );

            if (!box.contains(particlePos)) {
                createSwitchParticle(world, blockPos, particlePos);
                break;
            }
        }
    }

    default void createSwitchParticle(World world, BlockPos blockPos, Vec3d particlePos) {
        boolean bl = useOnParticles(world, blockPos, world.getBlockState(blockPos));
        ParticleEffect effect = bl ? ZeldaParticleTypes.CRYSTAL_SWITCH_ON_PARTICLE : ZeldaParticleTypes.CRYSTAL_SWITCH_OFF_PARTICLE;
        world.addParticle(effect, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0);
    }

    default boolean useOnParticles(BlockView world, BlockPos pos, BlockState state) {
        return startOn();
    }
}
