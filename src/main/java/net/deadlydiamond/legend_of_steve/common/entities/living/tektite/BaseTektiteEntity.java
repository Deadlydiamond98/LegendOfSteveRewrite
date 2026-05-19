package net.deadlydiamond.legend_of_steve.common.entities.living.tektite;

import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.TektiteSwimGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.navigation.TektiteNavigation;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.util.entity.ZeldaSpawn;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BaseTektiteEntity extends HostileEntity {
    private static final TrackedData<Boolean> JUMPING = DataTracker.registerData(BaseTektiteEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> LANDING_TIMER = DataTracker.registerData(BaseTektiteEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();
    public final AnimationState landAnimationState = new AnimationState();
    public int attackHopDelay;
    public int wanderingHopDelay;
    public boolean isTektiteSwimming;

    public BaseTektiteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0);
        this.wanderingHopDelay = getRandom().nextBetween(0, 600);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new TektiteNavigation(this, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new TektiteSwimGoal(this));
        this.goalSelector.add(7, new WanderAroundGoal(this, 1.25, 40));
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

        // Landing Animations
        if (this.isOnGround() && this.isTektiteJumping()) {
            this.setTektiteJumping(false);
            this.setLandingTimer(8);
        }

        // Damaging
        LivingEntity target = this.getTarget();
        if (target != null && this.squaredDistanceTo(target) < 3 && this.getVelocity().horizontalLength() > 0 && this.isAlive() && this.canMoveVoluntarily()) {
            tryDamaging(target);
        }

        super.tick();

        this.attackHopDelay--;
        this.wanderingHopDelay--;
        this.setLandingTimer(this.getLandingTimer() - 1);
    }

    protected void tryDamaging(LivingEntity target) {
        if (this.canSee(target) && target.damage(this.getDamageSources().mobAttack(this), (float) this.getAttributeValue(
                EntityAttributes.GENERIC_ATTACK_DAMAGE)
        )) {
            this.applyDamageEffects(this, target);
        }
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        // Done like this b/c swimming doesn't work properly when the fluid can be walked on
        return this.isTektiteSwimming ? super.canWalkOnFluid(state) : state.isIn(FluidTags.WATER);
    }

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return super.computeFallDamage(fallDistance, damageMultiplier) - 11;
    }

    public static ZeldaSpawn spawnRestriction() {
        return ZeldaSpawn.ground((type, world, spawnReason, pos, random) -> {
            BlockPos blockPos = pos.down();

            return (((world.getFluidState(blockPos).isIn(FluidTags.WATER) && world.getBlockState(blockPos.up()).isAir())
                    || world.getBlockState(blockPos).allowsSpawning(world, blockPos, type)) || spawnReason == SpawnReason.SPAWNER)
                    && world.getDifficulty() != Difficulty.PEACEFUL && isSpawnDark(world, pos, random)
                    && pos.getY() > world.getSeaLevel() - 25;
        });
    }

    // ANIMATION ///////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public int getMaxHeadRotation() {
        return 0;
    }

    @Override
    protected void jump() {
        super.jump();
        this.setTektiteJumping(true);
    }

    private void updateAnimations() {
        this.jumpAnimationState.setRunning(isTektiteJumping(), this.age);
        this.landAnimationState.setRunning(!this.jumpAnimationState.isRunning() && this.getLandingTimer() > 0, this.age);
        this.idleAnimationState.setRunning(true, this.age);
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

    // SOUNDS //////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Nullable @Override
    protected SoundEvent getAmbientSound() {
        return ZeldaSounds.TEKTITE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ZeldaSounds.TEKTITE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ZeldaSounds.TEKTITE_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ZeldaSounds.TEKTITE_STEP, 0.25f, 1);

        // Additional Stepping Sounds for Walking on Fluid
        if (!this.isTektiteSwimming && state.isOf(Blocks.WATER)) {
            this.playSound(ZeldaSounds.WATER_STEP, 0.05f, 1);
        }
    }
}
