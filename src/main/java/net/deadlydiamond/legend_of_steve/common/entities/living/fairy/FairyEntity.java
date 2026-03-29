package net.deadlydiamond.legend_of_steve.common.entities.living.fairy;

import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.WanderAroundFlyingGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.navigation.FairyEntityNavigation;
import net.deadlydiamond.legend_of_steve.common.entities.living.FairyColor;
import net.deadlydiamond.legend_of_steve.common.particles.SparkParticleEffect;
import net.deadlydiamond.legend_of_steve.init.ZeldaCustomTrackedData;
import net.deadlydiamond.legend_of_steve.util.entity.ZeldaSpawn;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class FairyEntity extends PathAwareEntity implements Flutterer {
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
        setColor(FairyColor.init(world.getRandom()));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(5, new WanderAroundFlyingGoal(this));
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
            getColor().createParticles(getWorld(), getPos(), 25);

            for (int i = 0; i < 2; i++) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                this.getWorld().addParticle(ParticleTypes.POOF, this.getParticleX(1.0), this.getRandomBodyY(), this.getParticleZ(1.0), d, e, f);
            }
        }
        super.handleStatus(status);
    }

    public static DefaultAttributeContainer.Builder createCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48);
    }

    public static ZeldaSpawn createCustomSpawnRestriction() {
        return ZeldaSpawn.DEFAULT;
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
}
