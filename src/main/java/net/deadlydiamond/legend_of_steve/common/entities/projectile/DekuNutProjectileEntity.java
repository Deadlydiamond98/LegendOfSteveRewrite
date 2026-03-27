package net.deadlydiamond.legend_of_steve.common.entities.projectile;

import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class DekuNutProjectileEntity extends ThrownItemEntity {
    public DekuNutProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!getWorld().isClient()) {

            AtomicBoolean playStunSound = new AtomicBoolean(false);
            getWorld().getNonSpectatingEntities(LivingEntity.class, getBoundingBox().expand(3, 3, 3)).forEach(entity -> {
                if (entity instanceof IZeldaStunned stunned) {
                    stunned.legend_of_steve$setStunned(60);
                    playStunSound.set(true);
                    entity.playSound(ZeldaSounds.DEKU_NUT_STUN, 1, 1);
                }
            });
            playSound(ZeldaSounds.DEKU_NUT_SNAP, 1, 1);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ZeldaItems.DEKU_NUT;
    }
}
