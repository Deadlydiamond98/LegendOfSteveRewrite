package net.deadlydiamond.legend_of_steve.common.entities.bomb;

import net.deadlydiamond.legend_of_steve.common.items.bag.BombBagItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond98.koalalib.common.entity.PhysicsItemProjectile;
import net.deadlydiamond98.koalalib.util.IgnitionHelper;
import net.deadlydiamond98.koalalib.util.KoalaNbtHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class AbstractBombEntity extends PhysicsItemProjectile implements IZeldaBomb {
    private static final TrackedData<Boolean> PRIMED = DataTracker.registerData(AbstractBombEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> MAX_FUSE = DataTracker.registerData(AbstractBombEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> FUSE = DataTracker.registerData(AbstractBombEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> LIT_TIME = DataTracker.registerData(AbstractBombEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private TagKey<Block> breakableBlocks;
    private float power;

    public AbstractBombEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.setPower(3);
        this.setBreakableBlocks(ZeldaTags.BOMB_BREAKABLE);

        this.setYaw(360); // This prevents weird rotation issue when throwing

        this.setGravity(0.05f);
        this.setDrag(0.95f);
        this.setBounciness(0.4f);
        this.setBuoyancy(0.1f);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.isSneaking() && canPickupBomb(player)) {
            if (!getWorld().isClient()) {
                player.playSound(ZeldaSounds.BOMB_PICKED_UP, SoundCategory.NEUTRAL, 0.4f, 0.8f);
                this.despawn();
            }
            return ActionResult.SUCCESS;
        } else if (!isPrimed() && IgnitionHelper.canUseIgniter(getWorld(), getBlockPos(), player, hand)) {
            if (!getWorld().isClient()) {
                playSound(SoundEvents.ENTITY_TNT_PRIMED, 0.4f, 0.8f);
                this.setPrimed(true);
            }
            return ActionResult.SUCCESS;
        }
        return super.interact(player, hand);
    }

    public boolean canPickupBomb(PlayerEntity player) {
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        if (stack.getItem() instanceof BombBagItem) {
            if (!getWorld().isClient() && isPrimed()) {
                playSound(ZeldaSounds.BOMB_EXTINGUISH, 1, 1);
            }
            return true;
        }
        return !isPrimed() && stack.isEmpty();
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    protected void tickDespawn() {
        int fuse = this.isInLava() ? 0 : this.getFuse();
        boolean lit = isPrimed();

        if (lit) {
            fuse -= 1;
            setLitTime(getLitTime() + 1);
        } else {
            if (!this.getWorld().isClient && this.age >= 6000) {
                despawn();
            }

            if (isOnFire() && fuse > 0) {
                setPrimed(true);
                setMaxFuse(getMaxFuse() / 2);
                fuse = getMaxFuse();
                this.playSound(SoundEvents.ENTITY_TNT_PRIMED, 0.4f, 0.8f);
            }
        }

        this.setFuse(fuse);

        if (fuse <= 0) {
            this.explode(this);
            this.discard();
        } else if (this.getWorld().isClient && lit) {
            createBombFuseParticles();
        }
    }

    protected void createBombFuseParticles() {
        if (getFuseBurnTimer(0) <= -2) {
            this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    public void despawn() {
        getWorld().spawnEntity(new ItemEntity(getWorld(), this.getX(), this.getY(), this.getZ(), this.getStack()));
        this.discard();
    }

    public float getFuseBurnTimer(float tickDelta) {
        return ((getFuse() - tickDelta) / getMaxFuse()) - (0.5f * (getLitTime() + tickDelta));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!isPrimed()) {
            if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
                triggerChainExplode();
                return true;
            }
        }
        return super.damage(source, amount);
    }

    public void triggerChainExplode() {
        int i = getMaxFuse();
        this.setFuse(this.random.nextInt(Math.max(0, i / 4) + i / 8) + 5);
        this.setPrimed(true);
        this.setMaxFuse(this.getFuse());
    }

    @Override
    protected Item getDefaultItem() {
        return ZeldaItems.BOMB;
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    // GETTERS & SETTERS

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("BreakableBlocks", KoalaNbtHelper.identifierToNBT(this.breakableBlocks.id()));
        nbt.putFloat("ExplosivePower", this.power);
        nbt.putInt("Fuse", getFuse());
        nbt.putInt("MaxFuse", getMaxFuse());
        nbt.putBoolean("Primed", isPrimed());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.breakableBlocks = TagKey.of(RegistryKeys.BLOCK, KoalaNbtHelper.identifierFromNBT((NbtCompound) nbt.get("BreakableBlocks")));
        setPower(nbt.getFloat("ExplosivePower"));
        setFuse(nbt.getInt("Fuse"));
        setMaxFuse(nbt.getInt("MaxFuse"));
        setPrimed(nbt.getBoolean("Primed"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FUSE, 80);
        this.dataTracker.startTracking(MAX_FUSE, 80);
        this.dataTracker.startTracking(PRIMED, false);
        this.dataTracker.startTracking(LIT_TIME, 0);
    }

    public int getFuse() {
        return this.dataTracker.get(FUSE);
    }

    public void setFuse(int fuse) {
        this.dataTracker.set(FUSE, fuse);
    }

    public int getMaxFuse() {
        return this.dataTracker.get(MAX_FUSE);
    }

    public void setMaxFuse(int fuse) {
        this.dataTracker.set(MAX_FUSE, fuse);
    }

    @Override
    public float getPower() {
        return this.power;
    }

    public void setPower(float power) {
        this.power = power;
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
    public TagKey<Block> getBreakableBlocks() {
        return this.breakableBlocks;
    }

    public void setBreakableBlocks(TagKey<Block> breakableBlocks) {
        this.breakableBlocks = breakableBlocks;
    }

    @Override
    public Entity getBombOwner() {
        return getOwner();
    }


    // TODO: THIS WILL BE REMOVED ONCE I PUSH FIX FOR KOALA LIB
    @Override
    protected void tickMovement() {
        this.velocityDirty = true;
        super.tickMovement();
    }
}
