package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BouncableBlock extends Block implements IBouncableBlock {
    public BouncableBlock(Settings settings) {
        super(settings);
    }

    protected SoundEvent getHittingSound() {
        return ZeldaSounds.QUESTION_BLOCK_HIT;
    }

    @Override
    public boolean canBounceBlock() {
        return true;
    }

    @Override
    public BlockState getPostBounceState(BlockState originalState) {
        return getDefaultState();
    }

    protected boolean canProjectileTrigger(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        return true;
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (canProjectileTrigger(world, state, hit, projectile) && canBounceBlock() && !world.isClient()) {
            bounceBlock(world, hit.getBlockPos(), state, projectile, hit.getSide().getOpposite(), BounceType.PROJECTILE);
        }
    }

    @Override
    public void bounceBlock(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction direction, BounceType type) {
        IBouncableBlock.super.bounceBlock(world, pos, state, owner, direction, type);
        if (getHittingSound() != null) {
            world.playSound(null, pos, getHittingSound(), SoundCategory.BLOCKS, 1.5f, 1);
        }
    }

    @Override
    public void afterBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction bouncedDirection, BounceType bounceType) {

    }
}
