package net.deadlydiamond.legend_of_steve.common.entities.ai.goals.aruroda;

import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.HopAtTargetGoal;
import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.BaseTektiteEntity;

public class ArurodaHopGoal extends HopAtTargetGoal {

    public static final int MIN_DISTANCE = 40;

    public ArurodaHopGoal(BaseTektiteEntity mob, double hopDistance, double hopHeight, int maxHoppingDelay, double range, int jumpDifferenceModifier) {
        super(mob, hopDistance, hopHeight, maxHoppingDelay, range, jumpDifferenceModifier);
    }

    @Override
    protected boolean isInRange() {
        double d = this.mob.squaredDistanceTo(this.target);
        double yDist = Math.max(1, this.target.getY() - this.mob.getY());

        return (d >= MIN_DISTANCE && d <= this.range) || (yDist > 1 && this.mob.forcedHighHopCooldown <= 0);
    }
}
