package net.deadlydiamond.legend_of_steve.mixin.common.entity;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.AbstractBombEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {

    @WrapWithCondition(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private boolean legend_of_steve$onEntityHit(PersistentProjectileEntity instance, Vec3d vec3d, @Local(ordinal = 0) Entity entity) {
        if (entity instanceof AbstractBombEntity) {
            instance.setYaw(instance.getYaw() - 180);
            instance.prevYaw -= 180;
            return false;
        }
        return true;
    }
}
