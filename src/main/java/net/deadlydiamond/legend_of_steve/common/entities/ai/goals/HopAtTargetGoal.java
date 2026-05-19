package net.deadlydiamond.legend_of_steve.common.entities.ai.goals;

import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.BaseTektiteEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
                return d <= this.range && this.mob.isOnGround() && this.mob.attackHopDelay <= 0 && !this.isBlockedHorizontally() && this.mob.getRandom().nextInt(toGoalTicks(5)) == 0;
            }
        }
    }

    protected boolean isBlockedHorizontally() {
        World world = this.mob.getWorld();
        Vec3d direction = new Vec3d(
                this.target.getX() - this.mob.getX(),
                0,
                this.target.getZ() - this.mob.getZ()
        ).normalize();

        Vec3d startPos = this.mob.getPos();
        Vec3d endPos = startPos.add(direction);

        Box checkBox = new Box(
                startPos.x - 0.3, startPos.y, startPos.z - 0.3,
                endPos.x + 0.3, startPos.y + this.mob.getHeight(), endPos.z + 0.3
        );

        return !world.isSpaceEmpty(this.mob, checkBox);
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.isOnGround();
    }

    @Override
    public void tick() {
        super.tick();
        this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
        this.mob.setVelocity(this.mob.getVelocity().add(this.mob.getRotationVec(0).normalize().multiply(0.1)));
    }

    @Override
    public void start() {
        Vec3d direction = new Vec3d(
                this.target.getX() - this.mob.getX(),
                0,
                this.target.getZ() - this.mob.getZ()
        ).normalize().multiply(this.hopDistance);

        this.mob.attackHopDelay = this.maxHoppingDelay + this.mob.getRandom().nextBetween(0, 3);
        double angle = Math.atan2(direction.z, direction.x);
        this.mob.setYaw((float) Math.toDegrees(angle) - 90);

        this.mob.setVelocity(direction.x, this.hopHeight, direction.z);
        this.mob.setTektiteJumping(true);
        this.mob.playSound(ZeldaSounds.TEKTITE_HOP, 1, 1 + (this.mob.getRandom().nextFloat() * 0.25f) - 0.125f);
    }
}
