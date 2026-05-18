package net.deadlydiamond.legend_of_steve.common.entities.ai.goals;

import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.BaseTektiteEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class HopAtTargetGoal extends Goal {
    private final BaseTektiteEntity mob;
    private LivingEntity target;

    private final double range;
    private final double hopDistance;
    private final double hopHeight;
    private final int maxHoppingDelay;

    public HopAtTargetGoal(BaseTektiteEntity mob, double hopDistance, double hopHeight, int maxHoppingDelay, double range) {
        this.mob = mob;
        this.hopDistance = hopDistance;
        this.hopHeight = hopHeight;
        this.maxHoppingDelay = maxHoppingDelay;
        this.range = range * range;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (this.mob.hasPassengers()) {
            return false;
        } else {
            this.target = this.mob.getTarget();
            if (this.target == null) {
                return false;
            } else {
                double d = this.mob.squaredDistanceTo(this.target);
                return d <= this.range && this.mob.isOnGround() && this.mob.hopDelay <= 0 && this.mob.getRandom().nextInt(toGoalTicks(5)) == 0;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.isOnGround();
    }

    @Override
    public void tick() {
        super.tick();
        this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
    }

    @Override
    public void start() {
        this.mob.hopDelay = this.maxHoppingDelay;

        Vec3d direction = new Vec3d(
                this.target.getX() - this.mob.getX(),
                0,
                this.target.getZ() - this.mob.getZ()
        ).normalize().multiply(this.hopDistance);

        double angle = Math.atan2(direction.z, direction.x);
        this.mob.setYaw((float) Math.toDegrees(angle) - 90);

        this.mob.setVelocity(direction.x, this.hopHeight, direction.z);
        this.mob.setTektiteJumping(true);
    }
}
