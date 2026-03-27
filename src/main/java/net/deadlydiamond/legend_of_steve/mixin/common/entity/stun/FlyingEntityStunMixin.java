package net.deadlydiamond.legend_of_steve.mixin.common.entity.stun;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FlyingEntity.class)
public abstract class FlyingEntityStunMixin extends MobEntity {
    protected FlyingEntityStunMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapMethod(method = "travel")
    private void temp(Vec3d movementInput, Operation<Void> original) {
        FlyingEntity entity = (FlyingEntity) (Object) this;

        if (entity instanceof IZeldaStunned stunned && stunned.legend_of_steve$isStunned() && this.isLogicalSideForUpdatingMovement()) {
            double d = 0.08;
            boolean bl = this.getVelocity().y <= 0.0;
            if (bl && this.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
                d = 0.01;
            }

            BlockPos blockPos = this.getVelocityAffectingPos();
            float p = this.getWorld().getBlockState(blockPos).getBlock().getSlipperiness();
            float fxx = this.isOnGround() ? p * 0.91F : 0.91F;
            this.move(MovementType.SELF, this.getVelocity());
            Vec3d vec3d6 = this.getVelocity();
            double q = vec3d6.y;
            if (this.hasStatusEffect(StatusEffects.LEVITATION)) {
                q += (0.05 * (this.getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1) - vec3d6.y) * 0.2;
            } else if (this.getWorld().isClient && !this.getWorld().isChunkLoaded(blockPos)) {
                if (this.getY() > this.getWorld().getBottomY()) {
                    q = -0.1;
                } else {
                    q = 0.0;
                }
            } else {
                q -= d;
            }

            this.setVelocity(vec3d6.x * fxx, q * 0.98F, vec3d6.z * fxx);
        } else {
            original.call(movementInput);
        }
    }
}
