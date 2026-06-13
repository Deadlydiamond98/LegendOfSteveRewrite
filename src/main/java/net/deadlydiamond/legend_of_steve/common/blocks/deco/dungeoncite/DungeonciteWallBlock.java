package net.deadlydiamond.legend_of_steve.common.blocks.deco.dungeoncite;

import com.google.common.collect.ImmutableMap;
import net.deadlydiamond98.koalalib.common.blocks.advancement.IAdvancementNeeded;
import net.deadlydiamond98.koalalib.init.KoalaLibBlockProperties;
import net.minecraft.block.*;
import net.minecraft.block.enums.WallShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.Map;

public class DungeonciteWallBlock extends WallBlock implements IAdvancementNeeded {
    public static final BooleanProperty PLAYERMADE = KoalaLibBlockProperties.PLAYER_MADE_PROPERTY;
    private final String advancementID;

    private final Map<BlockState, VoxelShape> shapeMap;
    private final Map<BlockState, VoxelShape> collisionShapeMap;

    public DungeonciteWallBlock(AbstractBlock.Settings settings, Identifier advancementID) {
        this(settings, advancementID.toString());
    }

    public DungeonciteWallBlock(AbstractBlock.Settings settings, String advancementID) {
        super(settings);
        this.advancementID = advancementID;
        this.setDefaultState(this.getDefaultState().with(PLAYERMADE, false));

        this.shapeMap = this.getShapeMap(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
        this.collisionShapeMap = this.getShapeMap(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        return state != null ? state.with(PLAYERMADE, this.isPlayerPlaced(ctx)) : null;
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        return !this.hasAdvancment(player, this.advancementID) && !(Boolean)state.get(PLAYERMADE) ? -1.0F : super.calcBlockBreakingDelta(state, player, world, pos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(PLAYERMADE);
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

                            builder.put(blockState.with(WATERLOGGED, false).with(PLAYERMADE, false), voxelShape10);
                            builder.put(blockState.with(WATERLOGGED, false).with(PLAYERMADE, true), voxelShape10);
                            builder.put(blockState.with(WATERLOGGED, true).with(PLAYERMADE, false), voxelShape10);
                            builder.put(blockState.with(WATERLOGGED, true).with(PLAYERMADE, true), voxelShape10);
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
