package net.deadlydiamond.legend_of_steve.common.blocks.container.hittable;

import net.deadlydiamond.legend_of_steve.common.bes.container.single.HittableContainerBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StrangeDirtBrickContainer extends HittableContainerBlock {
    public StrangeDirtBrickContainer(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean startEmpty() {
        return false;
    }

    @Override
    protected SoundEvent getInsertSound() {
        return ZeldaSounds.QUESTION_BLOCK_DEPOSIT;
    }

    @Override
    protected SoundEvent getHittingSound() {
        return ZeldaSounds.QUESTION_BLOCK_HIT;
    }

    @Override
    protected SoundEvent getEmptyingSound() {
        return ZeldaSounds.QUESTION_BLOCK_EMPTY_CONTENTS;
    }

    @Override
    protected boolean canAttackTrigger(BlockState blockState, BlockPos blockPos, World world, PlayerEntity playerEntity) {
        if (world.getBlockEntity(blockPos) instanceof HittableContainerBlockEntity blockEntity) {
            blockEntity.checkLootInteraction(playerEntity);
            return !blockEntity.isEmpty();
        }
        return false;
    }

    @Override
    public void hitBlock(World world, BlockPos pos, BlockState state, @Nullable Entity entity, Direction direction, boolean additionalHitSound) {
        if (world.getBlockEntity(pos) instanceof HittableContainerBlockEntity blockEntity) {
            blockEntity.checkLootInteraction(entity instanceof PlayerEntity player ? player : null);
            if (blockEntity.isEmpty() && entity instanceof LivingEntity living && living.hasStatusEffect(StatusEffects.STRENGTH)) {
                postBlockHit(world, pos, state, blockEntity);
                world.breakBlock(pos, false);
                return;
            }
        }
        super.hitBlock(world, pos, state, entity, direction, additionalHitSound);
    }

    @Override
    public void postBlockHit(World world, BlockPos pos, BlockState blockState, HittableContainerBlockEntity blockEntity) {
        if (!blockEntity.isEmpty()) {
            super.postBlockHit(world, pos, blockState, blockEntity);
        }
    }
}
