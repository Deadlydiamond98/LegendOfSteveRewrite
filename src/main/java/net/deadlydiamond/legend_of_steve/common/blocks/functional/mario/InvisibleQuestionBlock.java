package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario;

import net.deadlydiamond.legend_of_steve.common.blocks.IModifiedOutlineRender;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.init.ZeldaAdvancements;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class InvisibleQuestionBlock extends QuestionBlock implements IModifiedOutlineRender {
    public InvisibleQuestionBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canBombTrigger(World world, BlockPos blockPos, BlockState blockState, @Nullable Entity entity) {
        return false;
    }

    @Override
    protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
        super.spawnBreakParticles(world, player, pos, state);
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return state.get(HIT) ? 0.2f : 1;
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HIT) ? VoxelShapes.fullCube() : VoxelShapes.empty();
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return (stateFrom.isOf(this) && state.get(HIT) == stateFrom.get(HIT)) || super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return !state.get(HIT);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!state.get(HIT) && context instanceof EntityShapeContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();
            if (entity != null && !(entity instanceof PlayerEntity player && player.getAbilities().flying)) {
                Vec3d entityTopPos = entity.getPos().add(0, entity.getHeight(), 0);
                Vec3d blockBottomPos = pos.toCenterPos().subtract(0, 0.5, 0);
                return entityTopPos.y <= blockBottomPos.y ? VoxelShapes.fullCube() : VoxelShapes.empty();
            }
        }
        return state.get(HIT) ? VoxelShapes.fullCube() : VoxelShapes.empty();
    }

    @Override
    public VoxelShape getRenderedOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HIT) ? VoxelShapes.fullCube() : VoxelShapes.empty();
    }

    @Override
    public void beforeBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction direction, BounceType type) {
        world.setBlockState(pos, state.with(HIT, true));

        if (!world.isClient()) {
            if (owner instanceof PlayerEntity player && type == BounceType.JUMP) {
                for (int i = 1; i < 5; i++) {
                    if (!world.getBlockState(pos.offset(Direction.DOWN, i)).isAir()) {
                        return;
                    }
                }
                ZeldaAdvancements.KAIZO_TRAP.trigger(player);
            }
        }
    }
}
