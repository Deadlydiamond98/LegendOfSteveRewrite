package net.deadlydiamond.legend_of_steve.common.entities.ai.goals.bombfish;

import net.deadlydiamond.legend_of_steve.common.entities.living.fish.BombfishEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class BombfishAttackGoal extends MeleeAttackGoal {

    private final BombfishEntity bombfish;

    public BombfishAttackGoal(BombfishEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        this.bombfish = mob;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && canReachTarget(this.mob.getTarget());
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && canReachTarget(this.mob.getTarget());
    }

    @Override
    public void start() {
        super.start();
        this.bombfish.chasingTarget = true;
    }

    @Override
    public void stop() {
        super.stop();
        this.bombfish.chasingTarget = false;
    }

    public boolean canReachTarget(@Nullable LivingEntity target) {
        if (target != null) {
            AtomicBoolean nearWater = new AtomicBoolean(false);
            BlockPos.iterateOutwards(target.getBlockPos().down(), 1, 0, 1).forEach(pos -> {
                if (target.getWorld().getFluidState(pos).isIn(FluidTags.WATER)) {
                    nearWater.set(true);
                }
            });

            return target.isTouchingWater() || nearWater.get();
        }

        return false;
    }
}
