package net.deadlydiamond.legend_of_steve.common.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IExtraCanMine {
    boolean canMineBlock(BlockState state, World world, BlockPos pos, PlayerEntity miner);
}
