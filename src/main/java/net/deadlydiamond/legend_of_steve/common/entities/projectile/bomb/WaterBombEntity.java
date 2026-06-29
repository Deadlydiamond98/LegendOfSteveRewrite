package net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public class WaterBombEntity extends BombEntity {
    public WaterBombEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.setWaterDrag(0.8f);
    }

    @Override
    protected void createBombFuseParticles() {}

    @Override
    public boolean isFuseInWater() {
        return false;
    }

    @Override
    public boolean shouldWaterBottleExtinguish() {
        return false;
    }
}
