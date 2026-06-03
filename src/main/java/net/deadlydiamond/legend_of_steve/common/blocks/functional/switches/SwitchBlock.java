package net.deadlydiamond.legend_of_steve.common.blocks.functional.switches;

import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.deadlydiamond.legend_of_steve.util.ZeldaProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SwitchBlock extends Block implements ISwitchBlock {
//    protected static final BooleanProperty ON = ZeldaProperties.ON;
    private final boolean startOn;

    public SwitchBlock(Settings settings, boolean startsOn) {
        super(settings);
        this.startOn = startsOn;
//        this.setDefaultState(this.getDefaultState().with(ON, this.startOn));
    }

//    @Override
//    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
//        BlockState state1 = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
//        if (SwitchBlockManager.INSTANCE != null) {
//            state1 = state1.with(ON, startOn() == SwitchBlockManager.INSTANCE.get("Global"));
//        }
//        return state1;
//    }

//    @Nullable
//    @Override
//    public BlockState getPlacementState(ItemPlacementContext ctx) {
//        return super.getPlacementState(ctx).with(ON, getStartOnState(ctx.getWorld()));
//    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return isOn(world, pos) ? super.getCollisionShape(state, world, pos, context) : VoxelShapes.empty();
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return isOn(world, pos) ? super.getAmbientOcclusionLightLevel(state, world, pos) : 1;
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (isOn(world, pos) && random.nextFloat() < 0.125) {
            createCulledParticles(world, pos, state.getOutlineShape(world, pos), 1, 0.25f, startOn(), true);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
//        builder.add(ON);
    }

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
    }

    @Override
    public boolean startOn() {
        return this.startOn;
    }
}
