package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base;

import net.deadlydiamond.legend_of_steve.common.bes.BouncingBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.common.items.IExtraCanMine;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BouncingTransitionBlock extends BlockWithEntity implements IExtraCanMine {
    public BouncingTransitionBlock(Settings settings) {
        super(settings);
    }

    public static void create(World world, BlockPos pos, BlockState startState, BlockState endState,
                              Direction direction, int bounceTime, @Nullable Entity owner, BounceType type) {
        world.setBlockState(pos, ZeldaBlocks.BOUNCING_BLOCK.getDefaultState());
        world.addBlockEntity(new BouncingBlockEntity(
                pos, world.getBlockState(pos), startState, endState, direction, bounceTime, owner, type
        ));
    }

    @Override
    protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {}

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        return BlockSoundGroup.INTENTIONALLY_EMPTY;
    }

    @Override
    public boolean canMineBlock(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (world.getBlockEntity(pos) instanceof BouncingBlockEntity bouncingBlock) {
            return bouncingBlock.getCollision(world, pos);
        }
        return VoxelShapes.empty();
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Nullable @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeldaBlockEntities.BOUNCING_BLOCK, BouncingBlockEntity::tick);
    }
}
