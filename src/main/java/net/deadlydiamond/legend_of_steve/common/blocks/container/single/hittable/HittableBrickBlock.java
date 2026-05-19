package net.deadlydiamond.legend_of_steve.common.blocks.container.single.hittable;

import net.deadlydiamond.legend_of_steve.common.bes.container.single.HittableContainerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HittableBrickBlock extends HittableContainerBlock {

    public HittableBrickBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean startHit() {
        return false;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return ActionResult.PASS;
    }

    // HITTING /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected boolean activatedByRedstone() {
        return false;
    }

    @Override
    public boolean canMineBlock(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return true;
    }

    @Override
    protected boolean canAttackTrigger(BlockState blockState, BlockPos blockPos, World world, PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public void postBlockHit(World world, BlockPos pos, BlockState blockState, HittableContainerBlockEntity blockEntity) {
        world.breakBlock(pos, false);
    }

    @Override
    public int getBounceTimer() {
        return 5;
    }
}
