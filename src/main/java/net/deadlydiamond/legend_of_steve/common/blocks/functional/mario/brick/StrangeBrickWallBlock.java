package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.brick;

import com.google.common.collect.ImmutableMap;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base.IBouncableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.WallBlock;
import net.minecraft.block.enums.WallShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class StrangeBrickWallBlock extends WallBlock implements IBouncableBlock {
    public static final BooleanProperty POWERED = Properties.POWERED;

    private final Map<BlockState, VoxelShape> shapeMap;
    private final Map<BlockState, VoxelShape> collisionShapeMap;

    public StrangeBrickWallBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(POWERED, false));

        this.shapeMap = this.getShapeMap(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
        this.collisionShapeMap = this.getShapeMap(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
    }

    @Override
    public boolean canPunchTrigger(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public boolean canBombTrigger(World world, BlockPos blockPos, BlockState blockState, @Nullable Entity entity) {
        return false;
    }

    // BOUNCING ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public BlockState getPostBounceState(BlockState originalState) {
        return originalState;
    }

    @Override
    public boolean canBounceBlock(World world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void beforeBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction direction, BounceType type) {}

    @Override
    public void afterBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction bouncedDirection, BounceType bounceType, @Nullable DefaultedList<ItemStack> inventory) {
        StrangeBrickBlock.breakBricks(world, pos, bounceType);
    }

    // PROJECTILE INTERACTION //////////////////////////////////////////////////////////////////////////////////////////

    protected boolean canProjectileTrigger(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        return true;
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (canProjectileTrigger(world, state, hit, projectile) && canBounceBlock(world, hit.getBlockPos(), state) && !world.isClient()) {
            triggerBounce(world, hit.getBlockPos(), state, projectile, hit.getSide().getOpposite(), BounceType.PROJECTILE);
        }
    }

    // REDSTONE INTERACTION ////////////////////////////////////////////////////////////////////////////////////////////

    protected boolean canRedstoneTrigger(World world, BlockState state, BlockPos pos) {
        return true;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (canRedstoneTrigger(world, state, pos) && canBounceBlock(world, pos, state)) {
            if (!world.isClient()) {
                boolean bl = world.isReceivingRedstonePower(pos);
                boolean bl2 = state.get(POWERED);

                world.setBlockState(pos, state.with(POWERED, bl), Block.NO_REDRAW);

                if (bl && !bl2) {
                    triggerBounce(world, pos, state.with(POWERED, true), null, getRedstoneInputDirection(world, pos).getOpposite(), BounceType.REDSTONE);
                }
            }
        }
    }

    public Direction getRedstoneInputDirection(World world, BlockPos pos) {
        Direction recievedDirection = Direction.UP;
        int i = 0;

        for (Direction direction : DIRECTIONS) {
            int j = world.getEmittedRedstonePower(pos.offset(direction), direction);
            if (j >= 15) {
                return direction;
            }

            if (j > i) {
                i = j;
                recievedDirection = direction;
            }
        }

        return recievedDirection;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POWERED);
    }

    // This shit's here because of the way wall blocks handle collision & all the methods for it being private :(

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapeMap.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.collisionShapeMap.get(state);
    }

    private Map<BlockState, VoxelShape> getShapeMap(float f, float g, float h, float i, float j, float k) {
        float l = 8.0F - f;
        float m = 8.0F + f;
        float n = 8.0F - g;
        float o = 8.0F + g;
        VoxelShape voxelShape = Block.createCuboidShape(l, 0.0, l, m, h, m);
        VoxelShape voxelShape2 = Block.createCuboidShape(n, i, 0.0, o, j, o);
        VoxelShape voxelShape3 = Block.createCuboidShape(n, i, n, o, j, 16.0);
        VoxelShape voxelShape4 = Block.createCuboidShape(0.0, i, n, o, j, o);
        VoxelShape voxelShape5 = Block.createCuboidShape(n, i, n, 16.0, j, o);
        VoxelShape voxelShape6 = Block.createCuboidShape(n, i, 0.0, o, k, o);
        VoxelShape voxelShape7 = Block.createCuboidShape(n, i, n, o, k, 16.0);
        VoxelShape voxelShape8 = Block.createCuboidShape(0.0, i, n, o, k, o);
        VoxelShape voxelShape9 = Block.createCuboidShape(n, i, n, 16.0, k, o);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (Boolean boolean_ : UP.getValues()) {
            for (WallShape wallShape : EAST_SHAPE.getValues()) {
                for (WallShape wallShape2 : NORTH_SHAPE.getValues()) {
                    for (WallShape wallShape3 : WEST_SHAPE.getValues()) {
                        for (WallShape wallShape4 : SOUTH_SHAPE.getValues()) {
                            VoxelShape voxelShape10 = VoxelShapes.empty();
                            voxelShape10 = getVoxelShape(voxelShape10, wallShape, voxelShape5, voxelShape9);
                            voxelShape10 = getVoxelShape(voxelShape10, wallShape3, voxelShape4, voxelShape8);
                            voxelShape10 = getVoxelShape(voxelShape10, wallShape2, voxelShape2, voxelShape6);
                            voxelShape10 = getVoxelShape(voxelShape10, wallShape4, voxelShape3, voxelShape7);
                            if (boolean_) {
                                voxelShape10 = VoxelShapes.union(voxelShape10, voxelShape);
                            }

                            BlockState blockState = this.getDefaultState()
                                    .with(UP, boolean_)
                                    .with(EAST_SHAPE, wallShape)
                                    .with(WEST_SHAPE, wallShape3)
                                    .with(NORTH_SHAPE, wallShape2)
                                    .with(SOUTH_SHAPE, wallShape4);

                            builder.put(blockState.with(WATERLOGGED, false).with(POWERED, false), voxelShape10);
                            builder.put(blockState.with(WATERLOGGED, false).with(POWERED, true), voxelShape10);
                            builder.put(blockState.with(WATERLOGGED, true).with(POWERED, false), voxelShape10);
                            builder.put(blockState.with(WATERLOGGED, true).with(POWERED, true), voxelShape10);
                        }
                    }
                }
            }
        }
        return builder.build();
    }

    private static VoxelShape getVoxelShape(VoxelShape base, WallShape wallShape, VoxelShape tall, VoxelShape low) {
        if (wallShape == WallShape.TALL) {
            return VoxelShapes.union(base, low);
        } else {
            return wallShape == WallShape.LOW ? VoxelShapes.union(base, tall) : base;
        }
    }
}
