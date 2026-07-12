package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.brick;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base.IBouncableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StrangeBrickStairsBlock extends StairsBlock implements IBouncableBlock {
    public static final BooleanProperty POWERED = Properties.POWERED;

    public StrangeBrickStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
        setDefaultState(getDefaultState().with(POWERED, false));
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
}
