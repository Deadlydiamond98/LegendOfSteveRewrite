package net.deadlydiamond.legend_of_steve.common.entities.living.fish;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.bombfish.BombfishAttackGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.bombfish.BombfishIgniteGoal;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.IZeldaBomb;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.util.entity.ZeldaSpawn;
import net.deadlydiamond98.koalalib.util.IgnitionHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BombfishEntity extends HostileFishEntity implements IZeldaBomb, SkinOverlayOwner {
    public static final Identifier CHARGED_LOOT_TABLE = LegendOfSteve.id("entities/charged_bombfish");

    private static final TrackedData<Boolean> CHARGED = DataTracker.registerData(BombfishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> PRIMED = DataTracker.registerData(BombfishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> FUSE = DataTracker.registerData(BombfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> LIT_TIME = DataTracker.registerData(BombfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(BombfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final int MAX_FUSE = 60;

    public boolean chasingTarget;

    public BombfishEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02f, 0.1f, true);
        this.lookControl = new YawAdjustingLookControl(this, 10);

        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new BombfishIgniteGoal(this));
        this.goalSelector.add(3, new BombfishAttackGoal(this, 1.0, true));

        this.goalSelector.add(5, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (canIgniteBomb(player, hand)) {
            if (!getWorld().isClient()) {
                playSound(ZeldaSounds.BOMB_PRIMED, 0.4f, 0.8f);
                this.setPrimed(true);
            }
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    public boolean canIgniteBomb(PlayerEntity player, Hand hand) {
        return !isPrimed() && IgnitionHelper.canUseIgniter(getWorld(), getBlockPos(), player, hand);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        setPitch(0);
        setColor(BombfishVarients.getRandom(world));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            int fuse = this.isInLava() ? 0 : this.getFuse();
            boolean lit = isPrimed();

            if (lit) {
                fuse -= 1;
                setLitTime(getLitTime() + 1);
            }

            this.setFuse(fuse);

            if (fuse <= 0) {
                this.explode(this);
                this.discard();
            }
        }

        super.tick();
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (canMoveVoluntarily() && !isSubmergedInWater() && isTouchingWater()) {
            this.setVelocity(this.getVelocity().add(0, -0.01, 0));
        }
        super.travel(movementInput);
    }

    public static DefaultAttributeContainer.Builder attributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.9)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    public static ZeldaSpawn spawnRestriction() {
        return ZeldaSpawn.water((type, world, spawnReason, pos, random) -> pos.getY() <= world.getSeaLevel() - 33 && world.getBaseLightLevel(pos, 0) == 0 && world.getBlockState(pos).isOf(Blocks.WATER));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FUSE, MAX_FUSE);
        this.dataTracker.startTracking(PRIMED, false);
        this.dataTracker.startTracking(LIT_TIME, 0);
        this.dataTracker.startTracking(COLOR, 0);
        this.dataTracker.startTracking(CHARGED, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Primed", isPrimed());
        nbt.putInt("Fuse", getFuse());
        nbt.putInt("Color", getColor());
        nbt.putBoolean("Charged", isCharged());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Primed")) {
            setPrimed(nbt.getBoolean("Primed"));
        } else {
            setPrimed(false);
        }
        if (nbt.contains("Fuse")) {
            setFuse(nbt.getInt("Fuse"));
        } else {
            setFuse(60);
        }
        setColor(nbt.getInt("Color"));
        setCharged(nbt.getBoolean("Charged"));
    }

    public int getColor() {
        return this.dataTracker.get(COLOR);
    }

    public void setColor(int color) {
        this.dataTracker.set(COLOR, color);
    }

    @Override
    public int getMaxLookPitchChange() {
        return 1;
    }

    @Override
    public int getMaxHeadRotation() {
        return 1;
    }

    @Override
    public boolean tryAttack(Entity target) {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!isPrimed()) {
            if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
                triggerChainExplode();
                return false;
            }
        }
        return super.damage(source, amount);
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        this.damage(this.getDamageSources().lightningBolt(), 3);
        this.setCharged(true);
    }

    @Override
    protected Identifier getLootTableId() {
        return this.isCharged() ? CHARGED_LOOT_TABLE : super.getLootTableId();
    }

    // BOMB STUFF //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void triggerChainExplode() {
        this.setFuse(this.random.nextInt(MAX_FUSE / 4 + MAX_FUSE / 8) + 5);
        this.setPrimed(true);
    }

    public int getFuse() {
        return this.dataTracker.get(FUSE);
    }

    public void setFuse(int fuse) {
        this.dataTracker.set(FUSE, fuse);
    }

    public boolean isPrimed() {
        return this.dataTracker.get(PRIMED);
    }

    public void setPrimed(boolean primed) {
        this.dataTracker.set(PRIMED, primed);
    }

    public int getLitTime() {
        return this.dataTracker.get(LIT_TIME);
    }

    public void setLitTime(int time) {
        this.dataTracker.set(LIT_TIME, time);
    }

    @Override
    public boolean isCharged() {
        return this.dataTracker.get(CHARGED);
    }

    public void setCharged(boolean bl) {
        this.dataTracker.set(CHARGED, bl);
    }

    @Override
    public boolean shouldRenderOverlay() {
        return this.isCharged();
    }

    @Override
    public World.ExplosionSourceType getExplosionType() {
        return World.ExplosionSourceType.MOB;
    }

    @Override
    public float getPower() {
        return 3.5f;
    }

    // SOUNDS //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected SoundEvent getFlopSound() {
        return ZeldaSounds.BOMBFISH_FLOP;
    }

    public void playPrimedSound() {
        this.playSound(ZeldaSounds.BOMB_PRIMED, this.getSoundVolume(), this.getSoundPitch());
    }
}
