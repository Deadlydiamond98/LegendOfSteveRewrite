package net.deadlydiamond.legend_of_steve.common.entities.ai.goals;

import net.deadlydiamond.legend_of_steve.common.entities.living.fairy.FairyEntity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class HealPlayerGoal extends MeleeAttackGoal {

    public HealPlayerGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.mob.getTarget();
        boolean shouldHeal = false;
        if (livingEntity != null) {
            shouldHeal = livingEntity.getHealth() <= 4;
        }

        return super.canStart() && shouldHeal;
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity livingEntity = this.mob.getTarget();
        boolean shouldHeal = false;
        if (livingEntity != null) {
            shouldHeal = livingEntity.getHealth() <= 4;
        }

        return super.shouldContinue() && shouldHeal;
    }

    @Override
    protected void attack(LivingEntity target, double squaredDistance) {
        double d = this.getSquaredMaxAttackDistance(target);
        if (squaredDistance <= d) {
            this.resetCooldown();
            target.heal(target.getRandom().nextBetween(8, 10));
            if (this.mob instanceof FairyEntity fairy) {
                this.mob.getWorld().sendEntityStatus(this.mob, EntityStatuses.ADD_DEATH_PARTICLES);
                fairy.playHealSound();
            }
            this.mob.discard();
        }
    }
}