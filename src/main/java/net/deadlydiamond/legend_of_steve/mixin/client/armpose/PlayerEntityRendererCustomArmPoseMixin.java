package net.deadlydiamond.legend_of_steve.mixin.client.armpose;

import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.client.rendering.player.armpos.ArmPose;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.armpose.ICustomArmPose;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererCustomArmPoseMixin {
    @Shadow private static BipedEntityModel.ArmPose getArmPose(AbstractClientPlayerEntity player, Hand hand) {throw new AssertionError();}

    @Inject(method = "setModelPose", at = @At("TAIL"))
    private void legend_of_steve$setModelPose(AbstractClientPlayerEntity player, CallbackInfo ci, @Local PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel) {
        if (!player.isSpectator()) {
            BipedEntityModel.ArmPose armPose = getArmPose(player, Hand.MAIN_HAND);
            BipedEntityModel.ArmPose armPose2 = getArmPose(player, Hand.OFF_HAND);
            ArmPose customArmPose;
            ArmPose customArmPose2;

            if (armPose == BipedEntityModel.ArmPose.ITEM) {
                customArmPose = legend_of_steve$getCustomArmPose(player, Hand.MAIN_HAND);
                if (customArmPose != null && customArmPose.isTwoHanded()) {
                    armPose2 = player.getOffHandStack().isEmpty() ? BipedEntityModel.ArmPose.EMPTY : BipedEntityModel.ArmPose.ITEM;
                    if (!player.isUsingItem()) {
                        if (player.getMainArm() == Arm.RIGHT) {
                            playerEntityModel.leftArmPose = armPose2;
                        } else {
                            playerEntityModel.rightArmPose = armPose2;
                        }
                    }
                }
            } else {
                customArmPose = null;
            }

            if (armPose2 == BipedEntityModel.ArmPose.ITEM && !armPose.isTwoHanded() && (customArmPose == null || !customArmPose.isTwoHanded())) {
                customArmPose2 = legend_of_steve$getCustomArmPose(player, Hand.OFF_HAND);
            } else {
                customArmPose2 = null;
            }

            ICustomArmPose armPoseModel = (ICustomArmPose) playerEntityModel;
            if (player.getMainArm() == Arm.RIGHT) {
                armPoseModel.legend_of_steve$setCustomRightArmPose(customArmPose);
                armPoseModel.legend_of_steve$setCustomLeftArmPose(customArmPose2);
            } else {
                armPoseModel.legend_of_steve$setCustomRightArmPose(customArmPose2);
                armPoseModel.legend_of_steve$setCustomLeftArmPose(customArmPose);
            }
        }
    }

    @Unique
    private ArmPose legend_of_steve$getCustomArmPose(AbstractClientPlayerEntity player, Hand hand) {
        for (ArmPose customArmPose : ArmPose.CUSTOM_ARM_POSES) {
            if (customArmPose.isValid(player, hand, player.getStackInHand(hand))) {
                return customArmPose;
            }
        }
        return null;
    }
}
