package net.deadlydiamond.legend_of_steve.common.entities.ai.goals;

import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.BaseTektiteEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class RandomlyHopGoal extends Goal {
    private final BaseTektiteEntity mob;

    private final double hopDistance;
    private final double hopHeight;
    private final int chance;
    private Vec3d direction = Vec3d.ZERO;

    public RandomlyHopGoal(BaseTektiteEntity mob, double hopDistance, double hopHeight, int chance) {
        this.mob = mob;
        this.hopDistance = hopDistance;
        this.hopHeight = hopHeight;
        this.chance = chance;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (this.mob.hasPassengers()) {
            return false;
        } else {
            if (this.mob.getTarget() != null) {
                return false;
            } else {
                return this.mob.isOnGround() && this.mob.wanderingHopDelay <= 0 && this.mob.getRandom().nextInt(toGoalTicks(this.chance)) == 0 &&
                        this.mob.getWorld().isSpaceEmpty(this.mob, this.mob.getBoundingBox().expand(1, 0.5, 1).offset(0, 0.5, 0));
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.mob.getLookControl().lookAt(this.mob.getPos().add(this.direction));
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.isOnGround();
    }

    @Override
    public void start() {
        Vec3d direction = this.mob.getRotationVec(0)
                .rotateY((float) Math.toRadians(this.mob.getRandom().nextBetween(-30, 30)))
                .normalize().multiply(this.hopDistance + (this.mob.getRandom().nextFloat() * 0.5));

        this.direction = direction;
        this.mob.wanderingHopDelay = this.mob.getRandom().nextBetween(100, 600);

        this.mob.setVelocity(direction.x, this.hopHeight + (this.mob.getRandom().nextFloat() * 0.25), direction.z);
        this.mob.setTektiteJumping(true);
        this.mob.playSound(ZeldaSounds.TEKTITE_HOP, 1, 1 + (this.mob.getRandom().nextFloat() * 0.25f) - 0.125f);
    }
}
