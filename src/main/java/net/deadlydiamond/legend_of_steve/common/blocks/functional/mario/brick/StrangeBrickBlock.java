package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.brick;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base.BouncableBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base.IBouncableBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StrangeBrickBlock extends BouncableBlock {
    public StrangeBrickBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPunchTrigger(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public void afterBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction bouncedDirection, BounceType bounceType, @Nullable DefaultedList<ItemStack> inventory) {
        breakBricks(world, pos, bounceType);
    }

    public static void breakBricks(World world, BlockPos pos, BounceType bounceType) {
        if (!world.isClient() && world.getBlockState(pos).getBlock() instanceof IBouncableBlock) {
            world.breakBlock(pos, world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS));
        }
    }
}
