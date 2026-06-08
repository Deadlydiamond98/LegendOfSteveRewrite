package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BouncableBlock extends Block implements IBouncableBlock {
    public BouncableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canBounceBlock(World world, BlockPos pos, BlockState state) {
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
        if (canProjectileTrigger(world, state, hit, projectile) && canBounceBlock(world, hit.getBlockPos(), state) && !world.isClient()) {
            triggerBounce(world, hit.getBlockPos(), state, projectile, hit.getSide().getOpposite(), BounceType.PROJECTILE);
        }
    }

    @Override
    public void beforeBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction direction, BounceType type) {}

    @Override
    public void afterBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction bouncedDirection, BounceType bounceType, @Nullable DefaultedList<ItemStack> inventory) {

    }
}
