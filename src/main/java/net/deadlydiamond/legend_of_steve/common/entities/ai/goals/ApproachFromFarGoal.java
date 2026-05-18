package net.deadlydiamond.legend_of_steve.common.entities.ai.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class ApproachFromFarGoal extends MeleeAttackGoal {

    private LivingEntity target;
    private final double range;

    public ApproachFromFarGoal(PathAwareEntity mob, double speed, double range) {
        super(mob, speed, false);
        this.range = range * range;
    }

    @Override
    public boolean canStart() {
        this.target = this.mob.getTarget();
        if (this.target == null) {
            return false;
        }

        double d = this.mob.squaredDistanceTo(this.target);
        return d > this.range && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        this.target = this.mob.getTarget();
        if (this.target == null) {
            return false;
        }

        double d = this.mob.squaredDistanceTo(this.target);
        return d > this.range && super.shouldContinue();
    }
}
