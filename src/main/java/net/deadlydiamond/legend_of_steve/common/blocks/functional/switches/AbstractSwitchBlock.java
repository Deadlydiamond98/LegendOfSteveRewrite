package net.deadlydiamond.legend_of_steve.common.blocks.functional.switches;

import net.deadlydiamond.legend_of_steve.common.bes.CrystalSwitchBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.init.ZeldaParticleTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class AbstractSwitchBlock extends BlockWithEntity implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public AbstractSwitchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.getBlockEntity(pos) instanceof CrystalSwitchBlockEntity switchBlock) {
            switchBlock.syncOnState();
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public void createSwitchParticles(World world, BlockPos blockPos, int amount, boolean isOn) {
        for (int i = 0; i < amount; i++) {
            Vec3d pos = blockPos.toCenterPos().add(0, 0.5625, 0).add(
                    (world.random.nextFloat() * 0.375) * (world.random.nextBoolean() ? -1 : 1),
                    (world.random.nextFloat() * 0.375) * (world.random.nextBoolean() ? -0.85 : 1),
                    (world.random.nextFloat() * 0.375) * (world.random.nextBoolean() ? -1 : 1)
            );

            world.addParticle(
                    isOn ? ZeldaParticleTypes.CRYSTAL_SWITCH_ON_PARTICLE : ZeldaParticleTypes.CRYSTAL_SWITCH_OFF_PARTICLE,
                    pos.x, pos.y, pos.z, 0, 0, 0
            );
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrystalSwitchBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeldaBlockEntities.CRYSTAL_SWITCH, CrystalSwitchBlockEntity::tick);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }
}
