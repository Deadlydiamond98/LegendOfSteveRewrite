package net.deadlydiamond.legend_of_steve.common.fluids;

import net.deadlydiamond.legend_of_steve.common.particles.MagicSparkleParticleEffect;
import net.deadlydiamond.legend_of_steve.common.particles.SparkParticleEffect;
import net.deadlydiamond.legend_of_steve.init.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Optional;

public class EnchantedSpringWater extends FlowableFluid {

    public static final BooleanProperty GLOWING = BooleanProperty.of("glowing");

    public static class Flowing extends EnchantedSpringWater {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class Still extends EnchantedSpringWater {
        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
    }

    @Override
    protected void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        super.randomDisplayTick(world, pos, state, random);
        float height = world.getFluidState(pos).getHeight();

        if (random.nextFloat() <= 0.025) {
            MagicSparkleParticleEffect.createFountainSparkle(world, pos.toCenterPos().add(0, -0.5, 0).add(
                    random.nextFloat() - 0.5,
                    height,
                    random.nextFloat() - 0.5
            ), 0.025, 0.01);
        } else if (random.nextFloat() <= 0.01) {
            SparkParticleEffect.createSparks(world, SparkParticleEffect.SOUL, pos.toCenterPos().add(
                    random.nextFloat() - 0.5,
                    random.nextFloat() - 0.5,
                    random.nextFloat() - 0.5
            ), 1);
        }

        if (random.nextInt(750) == 0) {
            world.playSound(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    ZeldaSounds.SPRING_WATER_SPARKLE,
                    SoundCategory.BLOCKS,
                    0.2f + random.nextFloat() * 0.2f,
                    1,
                    false
            );
        }
    }

    @Override
    public Fluid getFlowing() {
        return ZeldaFluids.FLOWING_ENCHANTED_SPRING_WATER;
    }

    @Override
    public Fluid getStill() {
        return ZeldaFluids.ENCHANTED_SPRING_WATER;
    }

    @Override
    protected boolean isInfinite(World world) {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 4;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    @Override
    public Item getBucketItem() {
        return ZeldaItems.SPRING_WATER_BUCKET;
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(ZeldaTags.ENCHANTED_SPRING_WATER);
    }

    @Override
    public int getTickRate(WorldView world) {
        return 5;
    }

    @Override
    protected float getBlastResistance() {
        return 100;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ZeldaBlocks.ENCHANTED_SPRING_WATER.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
        super.appendProperties(builder);
        builder.add(GLOWING);
    }
}
