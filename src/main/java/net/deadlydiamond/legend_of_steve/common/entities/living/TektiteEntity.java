package net.deadlydiamond.legend_of_steve.common.entities.living;

import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.ApproachFromFarGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.ApproachFromNearGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.HopAtTargetGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.navigation.TektiteNavigation;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;

import java.util.Objects;

public class TektiteEntity extends HostileEntity {
    private static final TrackedData<Boolean> JUMPING = DataTracker.registerData(TektiteEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> LANDING_TIMER = DataTracker.registerData(TektiteEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();
    public final AnimationState landAnimationState = new AnimationState();

    public int hopDelay;

    public TektiteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(3, new HopAtTargetGoal(this, 1, 0.475, 20, 6));
        this.goalSelector.add(4, new ApproachFromNearGoal(this, 0.5, 6));
        this.goalSelector.add(4, new ApproachFromFarGoal(this, 1.5, 6));

        this.goalSelector.add(7, new WanderAroundGoal(this, 1, 60));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            updateAnimations();
        }

        LivingEntity target = this.getTarget();
        if (this.canMoveVoluntarily() && target != null && this.squaredDistanceTo(target) < 3 && this.getVelocity().horizontalLength() > 0) {
            this.damage(target);
        }

        if (this.isOnGround() && this.isTektiteJumping()) {
            this.setTektiteJumping(false);
            this.setLandingTimer(8);
        }

        this.hopDelay--;
        super.tick();
        this.setLandingTimer(this.getLandingTimer() - 1);
    }

    protected void damage(LivingEntity target) {
        if (this.isAlive()) {
            if (this.canSee(target) && target.damage(this.getDamageSources().mobAttack(this),
                    (float) Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).getValue())) {
                this.applyDamageEffects(this, target);
            }
        }
    }

    private void updateAnimations() {
        this.jumpAnimationState.setRunning(isTektiteJumping(), this.age);
        this.landAnimationState.setRunning(!this.jumpAnimationState.isRunning() && this.getLandingTimer() >= 0, this.age);
        this.idleAnimationState.setRunning(true, this.age);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new TektiteNavigation(this, world);
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return state.isIn(FluidTags.WATER);
    }

    public static DefaultAttributeContainer.Builder attributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.175);
    }

    public boolean isTektiteJumping() {
        return this.dataTracker.get(JUMPING);
    }

    public void setTektiteJumping(boolean jumping) {
        this.dataTracker.set(JUMPING, jumping);
    }

    public int getLandingTimer() {
        return this.dataTracker.get(LANDING_TIMER);
    }

    public void setLandingTimer(int timer) {
        this.dataTracker.set(LANDING_TIMER, timer);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(JUMPING, false);
        this.dataTracker.startTracking(LANDING_TIMER, 0);
    }
}
