package net.deadlydiamond.legend_of_steve.mixin.client.armpose;

import net.deadlydiamond.legend_of_steve.client.rendering.player.armpos.ArmPose;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.client.ICustomArmPose;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererArmPoseMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Shadow protected M model;

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/entity/Entity;FFFFF)V", shift = At.Shift.AFTER))
    private void legend_of_steve$render(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (!(livingEntity instanceof PlayerEntity)) {
            ICustomArmPose armPoseModel = (ICustomArmPose) this.model;
            ArmPose customArmPose = legend_of_steve$getCustomArmPose(livingEntity, Hand.MAIN_HAND);
            ArmPose customArmPose2 = legend_of_steve$getCustomArmPose(livingEntity, Hand.OFF_HAND);

            if (livingEntity.getMainArm() == Arm.RIGHT) {
                armPoseModel.legend_of_steve$setCustomRightArmPose(customArmPose);
                armPoseModel.legend_of_steve$setCustomLeftArmPose(customArmPose2);
            } else {
                armPoseModel.legend_of_steve$setCustomRightArmPose(customArmPose2);
                armPoseModel.legend_of_steve$setCustomLeftArmPose(customArmPose);
            }

            if (customArmPose != null && customArmPose.shouldRepositionForMob(livingEntity) ||
                    customArmPose2 != null && customArmPose2.shouldRepositionForMob(livingEntity)) {
                armPoseModel.legend_of_steve$forceArmReposition(livingEntity);
            }
        }
    }

    @Unique
    private ArmPose legend_of_steve$getCustomArmPose(LivingEntity entity, Hand hand) {
        for (ArmPose customArmPose : ArmPose.CUSTOM_ARM_POSES) {
            if (customArmPose.isValidForMob(entity, hand, entity.getStackInHand(hand))) {
                return customArmPose;
            }
        }
        return null;
    }
}
