package net.deadlydiamond.legend_of_steve.common.entities.bomb;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;

public class BombEntity extends AbstractBombEntity {
    public enum BombType {
        REGULAR(""),
        BOUNCY("bouncy_"),
        STICKY("sticky_");

        public final String id;

        BombType(String id) {
            this.id = id;
        }
    }

    private static final TrackedData<Integer> BOMB_TYPE = DataTracker.registerData(BombEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> CHARGED = DataTracker.registerData(BombEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public BombEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public float getPower() {
        return this.isCharged() ? super.getPower() + 3 : super.getPower();
    }

    @Override
    public ExplosionBehavior getExplosionBehavior(World world) {
        return this.isCharged() ? null : super.getExplosionBehavior(world);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("BombType", getBombType().ordinal());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setBombType(BombType.values()[nbt.getInt("BombType")]);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BOMB_TYPE, 0);
        this.dataTracker.startTracking(CHARGED, false);
    }

    public BombType getBombType() {
        return BombType.values()[this.dataTracker.get(BOMB_TYPE)];
    }

    public void setBombType(BombType bombType) {
        this.dataTracker.set(BOMB_TYPE, bombType.ordinal());
    }

    public boolean isCharged() {
        return this.dataTracker.get(CHARGED);
    }

    public void setCharged(boolean charged) {
        this.dataTracker.set(CHARGED, charged);
    }
}
