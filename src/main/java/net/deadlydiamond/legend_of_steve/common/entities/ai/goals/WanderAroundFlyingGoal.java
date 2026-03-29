package net.deadlydiamond.legend_of_steve.common.entities.ai.goals;

import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class WanderAroundFlyingGoal extends Goal {
    private final PathAwareEntity mob;

    public WanderAroundFlyingGoal(PathAwareEntity mob) {
        this.setControls(EnumSet.of(Control.MOVE));
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        return this.mob.getNavigation().isIdle() && this.mob.getRandom().nextInt(10) == 0;
    }

    @Override
    public boolean shouldContinue() {
        return this.mob.getNavigation().isFollowingPath();
    }

    public void start() {
        Vec3d vec3d = this.getRandomLocation();
        if (vec3d != null) {
            this.mob.getNavigation().startMovingAlong(this.mob.getNavigation().findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
        }
    }

    private Vec3d getRandomLocation() {
        Vec3d vec3d2 = this.mob.getRotationVec(0.0F);

        Vec3d vec3d3 = AboveGroundTargeting.find(this.mob, 8, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
        return vec3d3 != null ? vec3d3 : NoPenaltySolidTargeting.find(this.mob, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
    }
}
