package net.deadlydiamond.legend_of_steve.mixin.common.entity.stun;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.deadlydiamond.legend_of_steve.networking.s2c.UpdateEntityStunS2CPacket;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityStunMixin extends Entity implements IZeldaStunned {
    @Shadow public float lastHandSwingProgress;
    @Shadow protected float stepBobbingAmount;
    @Shadow protected float prevStepBobbingAmount;
    @Shadow protected double serverYaw;
    @Shadow protected double serverHeadYaw;
    @Shadow private float leaningPitch;
    @Shadow private float lastLeaningPitch;
    @Shadow public float headYaw;
    @Shadow public float bodyYaw;
    @Shadow public float prevBodyYaw;
    @Shadow public float prevHeadYaw;
    @Shadow public float handSwingProgress;
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
    @Shadow public abstract Vec3d applyMovementInput(Vec3d movementInput, float slipperiness);
    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow protected abstract float getMovementSpeed(float slipperiness);

    @Unique private int legend_of_steve$stunnedTimer;

    public LivingEntityStunMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @WrapMethod(method = "tick")
    private void legend_of_steve$tick(Operation<Void> original) {

        original.call();
        if (legend_of_steve$isStunned()) {
            this.headYaw = this.prevHeadYaw;
            this.bodyYaw = this.prevBodyYaw;
            this.serverYaw = this.prevYaw;
            this.serverHeadYaw = this.prevHeadYaw;
            this.leaningPitch = this.lastLeaningPitch;
            this.lastHandSwingProgress = this.handSwingProgress;
            this.setYaw(this.prevYaw);
            this.setPitch(this.prevPitch);
            this.stepBobbingAmount = this.prevStepBobbingAmount;
            if (!this.getWorld().isClient()) {
                legend_of_steve$setStunned(--this.legend_of_steve$stunnedTimer);
            }
        }
    }

    @WrapMethod(method = "jump")
    private void legend_of_steve$jump(Operation<Void> original) {
        if (!legend_of_steve$isStunned()) {
            original.call();
        }
    }

    @WrapOperation(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LimbAnimator;setSpeed(F)V"))
    private void legend_of_steve$updateLimbSpeedDamage(LimbAnimator instance, float speed, Operation<Void> original) {
        if (!legend_of_steve$isStunned()) {
            original.call(instance, speed);
        }
    }

    @WrapOperation(method = "onDamaged", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LimbAnimator;setSpeed(F)V"))
    private void legend_of_steve$updateLimbSpeedOnDamaged(LimbAnimator instance, float speed, Operation<Void> original) {
        if (!legend_of_steve$isStunned()) {
            original.call(instance, speed);
        }
    }

    @WrapMethod(method = "canSee")
    private boolean legend_of_steve$canSee(Entity entity, Operation<Boolean> original) {
        if (legend_of_steve$isStunned()) {
            return false;
        }
        return original.call(entity);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void legend_of_steve$onDeath(DamageSource damageSource, CallbackInfo ci) {
        legend_of_steve$setStunned(0);
    }

    @WrapMethod(method = "updateLimbs(F)V")
    private void legend_of_steve$updateLimbs(float posDelta, Operation<Void> original) {
        if (!legend_of_steve$isStunned()) {
            original.call(posDelta);
        }
    }

    @WrapMethod(method = "tickHandSwing")
    private void legend_of_steve$tickHandSwing(Operation<Void> original) {
        if (!legend_of_steve$isStunned()) {
            original.call();
        }
    }

    @WrapMethod(method = "getHandSwingProgress")
    private float legend_of_steve$getHandSwingProgress(float tickDelta, Operation<Float> original) {
        if (!legend_of_steve$isStunned()) {
            return original.call(tickDelta);
        }
        return this.lastHandSwingProgress;
    }

    @WrapMethod(method = "travel")
    private void legend_of_steve$travel(Vec3d movementInput, Operation<Void> original) {
        if (legend_of_steve$isStunned() && this.isLogicalSideForUpdatingMovement()) {
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

    @WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;canMoveVoluntarily()Z", ordinal = 1))
    private boolean legend_of_steve$tickMovement(LivingEntity instance, Operation<Boolean> original) {
        return original.call(instance) && !legend_of_steve$isStunned();
    }

    // GETTERS & SETTERS ///////////////////////////////////////////////////////////////////////////////////////////////

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void legend_of_steve$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        nbt.putInt("ZeldaStunTimer", this.legend_of_steve$stunnedTimer);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void legend_of_steve$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("ZeldaStunTimer")) {
            this.legend_of_steve$setStunned(nbt.getInt("ZeldaStunTimer"));
        }
    }

    @Override
    public boolean legend_of_steve$isStunned() {
        return this.legend_of_steve$stunnedTimer > 0;
    }

    @Override
    public void legend_of_steve$setStunned(int time) {
        LivingEntity entity = (LivingEntity) (Object) this;
        this.legend_of_steve$stunnedTimer = time;
        if (!entity.getWorld().isClient()) {
            entity.getWorld().getPlayers().forEach(player -> UpdateEntityStunS2CPacket.send((ServerPlayerEntity) player, time, entity));
        }
    }
}
