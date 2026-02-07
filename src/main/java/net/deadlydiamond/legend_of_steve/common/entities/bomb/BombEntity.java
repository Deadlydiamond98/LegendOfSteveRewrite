package net.deadlydiamond.legend_of_steve.common.entities.bomb;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public class BombEntity extends AbstractBombEntity {
    public BombEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
}
