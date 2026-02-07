package net.deadlydiamond.legend_of_steve.client.rendering.player.armpos;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;

public abstract class ArmPose {
    public static final List<ArmPose> CUSTOM_ARM_POSES = new ArrayList<>();
    private final boolean twoHanded;

    public ArmPose(boolean twoHanded) {
        this.twoHanded = twoHanded;
        CUSTOM_ARM_POSES.add(this);
    }

    public abstract boolean isValid(AbstractClientPlayerEntity player, Hand hand, ItemStack itemStack);
    public abstract <T extends LivingEntity> void positionRightArm(T entity, BipedEntityModel<T> model);
    public abstract <T extends LivingEntity> void positionLeftArm(T entity, BipedEntityModel<T> model);

    public boolean isTwoHanded() {
        return this.twoHanded;
    }
}
