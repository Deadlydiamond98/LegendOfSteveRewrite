package net.deadlydiamond.legend_of_steve.common.blocks.functional.locks;

import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RedstoneLockBlock extends FacingBlock {
    public static final BooleanProperty POWERED = Properties.POWERED;
    private static final int ACTIVE_TIME = 40;

    public RedstoneLockBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (hit.getSide() == state.get(FACING) && !state.get(POWERED) && (stack.isIn(ZeldaTags.KEYS) || stack.isOf(ZeldaItems.CREATIVE_KEY))) {
            world.setBlockState(pos, state.with(POWERED, true), Block.NOTIFY_ALL);
            world.scheduleBlockTick(pos, this, ACTIVE_TIME);
            world.playSound(null, pos, ZeldaSounds.REDSTONE_LOCK_UNLOCK, SoundCategory.BLOCKS, 1, world.getRandom().nextFloat() * 0.1f + 0.9f);
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED)) {
            world.setBlockState(pos, state.with(POWERED, false));
            world.playSound(null, pos, ZeldaSounds.REDSTONE_LOCK_LOCK, SoundCategory.BLOCKS, 1, (world.getRandom().nextFloat() * 0.1f + 0.9f) - 0.1f);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.getWeakRedstonePower(world, pos, direction);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (state.get(POWERED)) {
            boolean[] directions = new boolean[3];
            directions[0] = state.get(FACING) == direction;

            if (state.get(FACING).getHorizontal() == -1) {
                directions[1] = state.get(FACING).rotateClockwise(Direction.Axis.Z) == direction;
                directions[2] = state.get(FACING).rotateCounterclockwise(Direction.Axis.Z) == direction;
            } else {
                directions[1] = state.get(FACING).rotateClockwise(Direction.Axis.Y) == direction;
                directions[2] = state.get(FACING).rotateCounterclockwise(Direction.Axis.Y) == direction;
            }

            return state.get(POWERED) && (directions[0] || directions[1] || directions[2]) ? 15 : 0;
        }
        return 0;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }
}
