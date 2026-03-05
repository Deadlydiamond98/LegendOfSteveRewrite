package net.deadlydiamond.legend_of_steve.common.blocks.deco;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class GirderBlock extends Block {

    private static final VoxelShape X_SHAPE = VoxelShapes.union(
            createCuboidShape(0, 0, 0, 16, 3, 16),
            createCuboidShape(0, 13, 0, 16, 16, 16),
            createCuboidShape(0, 0, 0, 16, 16, 3),
            createCuboidShape(0, 0, 13, 16, 16, 16)
    );
    private static final VoxelShape Z_SHAPE = VoxelShapes.union(
            createCuboidShape(0, 0, 0, 16, 3, 16),
            createCuboidShape(0, 13, 0, 16, 16, 16),
            createCuboidShape(0, 0, 0, 3, 16, 16),
            createCuboidShape(13, 0, 0, 16, 16, 16)
    );
    private static final VoxelShape Y_SHAPE = VoxelShapes.union(
            createCuboidShape(0, 0, 0, 16, 16, 3),
            createCuboidShape(0, 0, 13, 16, 16, 16),
            createCuboidShape(0, 0, 0, 3, 16, 16),
            createCuboidShape(13, 0, 0, 16, 16, 16)
    );

    public static final EnumProperty<Direction.Axis> HORIZONTAL_FACING = Properties.HORIZONTAL_AXIS;
    public static final BooleanProperty VERTICAL = BooleanProperty.of("vertical");

    public GirderBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HORIZONTAL_FACING, Direction.Axis.X).with(VERTICAL, false));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerLookDirection().getOpposite().getOpposite();

        return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getAxis())
                .with(VERTICAL, direction.getAxis() == Direction.Axis.Y);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.get(HORIZONTAL_FACING)) {
                case Z -> state.with(HORIZONTAL_FACING, Direction.Axis.X);
                case X -> state.with(HORIZONTAL_FACING, Direction.Axis.Z);
                default -> state;
            };
            default -> state;
        };
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(VERTICAL)) {
            return Y_SHAPE;
        } else if (state.get(HORIZONTAL_FACING) == Direction.Axis.Z) {
            return Z_SHAPE;
        }
        return X_SHAPE;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, VERTICAL);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
