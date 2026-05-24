package net.deadlydiamond.legend_of_steve.common.entities.ai.goals.aruroda;

import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.ArurodaEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class ArurodaAttackGoal extends MeleeAttackGoal {
    public ArurodaAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }

    @Override
    public boolean canStart() {
        return super.canStart() && this.mob instanceof ArurodaEntity aruroda && aruroda.getArurodaAttackTimer() <= 0;
    }
}
