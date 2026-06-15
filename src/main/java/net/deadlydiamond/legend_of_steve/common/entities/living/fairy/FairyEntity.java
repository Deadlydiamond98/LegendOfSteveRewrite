package net.deadlydiamond.legend_of_steve.common.entities.living.fairy;

import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.HealPlayerGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.WanderAroundFlyingGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.navigation.FairyEntityNavigation;
import net.deadlydiamond.legend_of_steve.common.entities.ZeldaCustomTrackedData;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.util.entity.ZeldaSpawn;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class FairyEntity extends PathAwareEntity implements Flutterer, IBottleable {
    private static final TrackedData<FairyColor> FAIRY_COLOR = DataTracker.registerData(FairyEntity.class, ZeldaCustomTrackedData.FAIRY_COLOR);

    public FairyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
        this.setNoGravity(true);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (spawnReason == SpawnReason.BUCKET && entityNbt != null && entityNbt.contains("FairyColor")) {
            this.setColor(FairyColor.readNbt(entityNbt));
            return entityData;
        }
        setColor(FairyColor.init(world.getRandom()));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(5, new WanderAroundFlyingGoal(this));
        this.goalSelector.add(3, new HealPlayerGoal(this, 0.6, true));
        this.goalSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void tick() {
        if (getWorld().isClient() && this.getRandom().nextFloat() < 0.2) {
            getColor().createMagicSparkleParticles(getWorld(), new Vec3d(
                    this.getParticleX(0.5),
                    this.getRandomBodyY(),
                    this.getParticleZ(0.5)
            ));
        }
        super.tick();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new FairyEntityNavigation(this, world);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10 : 0;
    }

    @Override
    public boolean isInAir() {
        return !this.isOnGround();
    }

    @Override protected void playStepSound(BlockPos pos, BlockState state) {}
    @Override protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {}

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        this.discard();
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            getColor().createSparkParticles(getWorld(), getPos(), 25);
        }
        if (status == EntityStatuses.ADD_DEATH_PARTICLES) {
            getColor().createSparkParticles(getWorld(), getPos(), 25);
            return;
        }
        super.handleStatus(status);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    public static DefaultAttributeContainer.Builder attributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 3)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48);
    }

    public static ZeldaSpawn spawnRestriction() {
        return ZeldaSpawn.ground((type, world, spawnReason, pos, random) -> {
            boolean bl = world.getBlockState(pos.down()).allowsSpawning(world, pos.down(), type);
            int i = world.getLightLevel(pos);

            return i > random.nextInt(4) ? spawnReason == SpawnReason.SPAWNER : bl;
        });
    }

    // GETTERS & SETTERS ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FAIRY_COLOR, FairyColor.BLUE);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        getColor().writeNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("FairyColor")) {
            setColor(FairyColor.readNbt(nbt));
        }
    }

    public FairyColor getColor() {
        return this.dataTracker.get(FAIRY_COLOR);
    }

    public void setColor(FairyColor color) {
        this.dataTracker.set(FAIRY_COLOR, color);
    }

    // BOTTLING ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return IBottleable.tryBottle(player, hand, this).orElse(super.interactMob(player, hand));
    }

    @Override
    public void copyDataToStack(ItemStack stack) {
        IBottleable.copyDataToStack(this, stack);
        NbtCompound nbt = stack.getOrCreateNbt();
        getColor().writeNbt(nbt);
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        IBottleable.copyDataFromNbt(this, nbt);
        if (nbt.contains("FairyColor")) {
            setColor(FairyColor.readNbt(nbt));
        }
    }

    @Override
    public ItemStack getBottleItem() {
        return new ItemStack(ZeldaItems.FAIRY_BOTTLE);
    }

    // SOUNDS //////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ZeldaSounds.FAIRY_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ZeldaSounds.FAIRY_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ZeldaSounds.FAIRY_AMBIENT;
    }

    public void playHealSound() {
        this.playSound(ZeldaSounds.FAIRY_HEAL, this.getSoundVolume(), this.getSoundPitch());
    }

    @Override
    public void playAmbientSound() {
        if (this.age > 5) {
            super.playAmbientSound();
        }
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 200;
    }
}
