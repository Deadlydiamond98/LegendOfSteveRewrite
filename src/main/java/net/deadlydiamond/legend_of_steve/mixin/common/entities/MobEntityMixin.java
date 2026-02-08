package net.deadlydiamond.legend_of_steve.mixin.common.entities;

import net.deadlydiamond.legend_of_steve.common.entities.bomb.BombEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Inject(method = "dropEquipment", at = @At("TAIL"))
    private void legend_of_steve$dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
        MobEntity entity = (MobEntity) (Object) this;

        if (source.getSource() instanceof BombEntity bomb && bomb.isCharged()) {
            if (entity instanceof CreeperEntity) {
                entity.dropItem(Items.CREEPER_HEAD);
            } else if (entity instanceof ZombieEntity) {
                entity.dropItem(Items.ZOMBIE_HEAD);
            } else if (entity instanceof SkeletonEntity) {
                entity.dropItem(Items.SKELETON_SKULL);
            } else if (entity instanceof PiglinEntity) {
                entity.dropItem(Items.PIGLIN_HEAD);
            } else if (entity instanceof WitherSkeletonEntity) {
                entity.dropItem(Items.WITHER_SKELETON_SKULL);
            }
        }
    }
}
