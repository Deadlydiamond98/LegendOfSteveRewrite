package net.deadlydiamond.legend_of_steve.client.rendering.player.armpos;

import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.rendering.BombRenderHelper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class OverHeadArmPose extends ArmPose {

    /**
     * Sets the Arms to stick straight up to hold something over the head
     */

    public OverHeadArmPose(boolean twoHanded) {
        super(twoHanded);
    }

    @Override
    public boolean isValid(AbstractClientPlayerEntity player, Hand hand, ItemStack itemStack) {
        return (BombRenderHelper.canShowNicerBombModel(itemStack, player) || itemStack.isIn(ZeldaTags.HELD_OVER_HEAD))
                && !player.getItemCooldownManager().isCoolingDown(itemStack.getItem());
    }

    @Override
    public <T extends LivingEntity> void positionRightArm(T entity, BipedEntityModel<T> model) {
        model.rightArm.yaw = 0;
        model.leftArm.yaw = 0;

        float pitch = ((float) (-Math.PI / 2) + model.head.pitch) * 0.7f - ((float) Math.PI * 0.56f);
        pitch = Math.min(-2.54f, pitch);

        float yawValue = model.head.yaw * ((-1 * Math.abs(model.head.pitch)) + 1.5f);

        model.rightArm.pitch = pitch - Math.min(0.65f, Math.max(yawValue, 0));
        model.leftArm.pitch = pitch + Math.max(-0.65f, Math.min(yawValue, 0));

        if (entity.isInSneakingPose()) {
            model.rightArm.pitch -= 0.4f;
            model.leftArm.pitch -= 0.4f;
        }
    }

    @Override
    public <T extends LivingEntity> void positionLeftArm(T entity, BipedEntityModel<T> model) {
        model.rightArm.yaw = 0;
        model.leftArm.yaw = 0;

        float pitch = ((float) (-Math.PI / 2) + model.head.pitch) * 0.8f - ((float) Math.PI * 0.56f);
        pitch = Math.min(-2.54f, pitch);

        float yawValue = model.head.yaw * ((-1 * Math.abs(model.head.pitch)) + 1.5f);

        model.rightArm.pitch = pitch - Math.min(0.65f, Math.max(yawValue, 0));
        model.leftArm.pitch = pitch + Math.max(-0.65f, Math.min(yawValue, 0));
    }
}
