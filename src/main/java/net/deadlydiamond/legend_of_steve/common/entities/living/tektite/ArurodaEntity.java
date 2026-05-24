package net.deadlydiamond.legend_of_steve.common.entities.living.tektite;

import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.RandomlyHopGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.aruroda.ArurodaAttackGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.aruroda.ArurodaHopGoal;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ArurodaEntity extends BaseTektiteEntity {
    private static final TrackedData<Integer> ATTACKING_TIMER = DataTracker.registerData(ArurodaEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public final AnimationState attackAnimationState = new AnimationState();

    public ArurodaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new MobNavigation(this, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(3, new ArurodaHopGoal(this, 0.8, 0.3, 40, 15, 3));
        this.goalSelector.add(4, new ArurodaAttackGoal(this, 1.5, false));

        this.goalSelector.add(7, new RandomlyHopGoal(this, 1, 0.3, 30));
        super.initGoals();
    }

    public static DefaultAttributeContainer.Builder attributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.175);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (this.getArurodaAttackTimer() > 0) {
            this.setArurodaAttackTimer(this.getArurodaAttackTimer() - 1);
        }
    }

    @Override
    protected void updateAnimations() {
        super.updateAnimations();
        this.attackAnimationState.setRunning(this.getArurodaAttackTimer() > 0, this.age);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (this.getArurodaAttackTimer() > 0) {
            return false;
        }

        this.setArurodaAttackTimer(7);
        if (super.tryAttack(target)) {
            if (target instanceof LivingEntity) {
                int i = 0;
                if (this.getWorld().getDifficulty() == Difficulty.NORMAL) {
                    i = 4;
                } else if (this.getWorld().getDifficulty() == Difficulty.HARD) {
                    i = 8;
                }

                if (i > 0) {
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, i * 20, 0), this);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING_TIMER, 0);
    }

    protected void setArurodaAttackTimer(int val) {
        this.dataTracker.set(ATTACKING_TIMER, val);
    }

    public int getArurodaAttackTimer() {
        return this.dataTracker.get(ATTACKING_TIMER);
    }

    // SOUNDS //////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ZeldaSounds.ARURODA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ZeldaSounds.ARURODA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ZeldaSounds.ARURODA_DEATH;
    }

    @Override
    public SoundEvent getHopSound() {
        return ZeldaSounds.ARURODA_HOP;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ZeldaSounds.ARURODA_STEP, 0.25f, 1);
    }
}
