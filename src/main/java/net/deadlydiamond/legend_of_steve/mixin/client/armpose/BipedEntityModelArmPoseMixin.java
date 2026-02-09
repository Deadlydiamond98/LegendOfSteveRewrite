package net.deadlydiamond.legend_of_steve.mixin.client.armpose;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.deadlydiamond.legend_of_steve.client.rendering.player.armpos.ArmPose;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.client.ICustomArmPose;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelArmPoseMixin<T extends LivingEntity> implements ICustomArmPose {
    @Unique @Nullable private ArmPose legend_of_steve$customRightArmPose;
    @Unique @Nullable private ArmPose legend_of_steve$customLeftArmPose;

    @WrapMethod(method = "positionRightArm")
    private void legend_of_steve$positionRightArm(T entity, Operation<Void> original) {
        if (this.legend_of_steve$customRightArmPose != null) {
            this.legend_of_steve$customRightArmPose.positionRightArm(entity, (BipedEntityModel<T>) (Object) this);
        } else {
            original.call(entity); // dw, this error is fine
        }
    }

    @WrapMethod(method = "positionLeftArm")
    private void legend_of_steve$positionLeftArm(T entity, Operation<Void> original) {
        if (this.legend_of_steve$customLeftArmPose != null) {
            this.legend_of_steve$customLeftArmPose.positionLeftArm(entity, (BipedEntityModel<T>) (Object) this);
        } else {
            original.call(entity); // dw, this error is fine
        }
    }

    @WrapOperation(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;isTwoHanded()Z", ordinal = 0))
    private boolean legend_of_steve$setAnglesLeftArm(BipedEntityModel.ArmPose instance, Operation<Boolean> original) {
        boolean bl = original.call(instance);
        if (this.legend_of_steve$customLeftArmPose != null) {
            return bl || this.legend_of_steve$customLeftArmPose.isTwoHanded();
        }
        return bl;
    }

    @WrapOperation(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;isTwoHanded()Z", ordinal = 1))
    private boolean legend_of_steve$setAnglesRightArm(BipedEntityModel.ArmPose instance, Operation<Boolean> original) {
        boolean bl = original.call(instance);
        if (this.legend_of_steve$customRightArmPose != null) {
            return bl || this.legend_of_steve$customRightArmPose.isTwoHanded();
        }
        return bl;
    }

    @Override
    public void legend_of_steve$setCustomRightArmPose(@Nullable ArmPose armPose) {
        this.legend_of_steve$customRightArmPose = armPose;
    }

    @Override
    public void legend_of_steve$setCustomLeftArmPose(@Nullable ArmPose armPose) {
        this.legend_of_steve$customLeftArmPose = armPose;
    }
}
