package net.deadlydiamond.legend_of_steve.common.entities.projectile;

import net.deadlydiamond.legend_of_steve.init.*;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class DekuNutProjectileEntity extends ThrownItemEntity {
    public DekuNutProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (getWorld() instanceof ServerWorld server) {

            for (LivingEntity entity : getWorld().getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(3, 3, 3),
                    EntityPredicates.EXCEPT_SPECTATOR.and(entity -> !entity.getType().isIn(ZeldaTags.IMMUNE_TO_STUNNING)))) {
                if (entity instanceof IZeldaStunned stunned) {
                    stunned.legend_of_steve$setStunned(60);
                    entity.playSound(ZeldaSounds.DEKU_NUT_STUN, 2, 1);
                    if (getOwner() instanceof PlayerEntity player) {
                        ZeldaAdvancements.STUN_ENTITY_WITH_NUT.trigger(player);
                    }
                }
            }

            server.spawnParticles(ZeldaParticleTypes.DEKU_NUT_FLASH, getX(), getY() + 0.25f, getZ(), 1, 0, 0, 0, 0);

            playSound(ZeldaSounds.DEKU_NUT_SNAP, 2, 1);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ZeldaItems.DEKU_NUT;
    }
}
