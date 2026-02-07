package net.deadlydiamond.legend_of_steve.client.rendering.player.itemmodel;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomHeldItemRenderer {
    public static final List<CustomHeldItemRenderer> CUSTOM_HELD_ITEM_RENDERER = new ArrayList<>();

    public CustomHeldItemRenderer() {
        CUSTOM_HELD_ITEM_RENDERER.add(this);
    }

    public abstract boolean isValid(LivingEntity entity, Arm arm, ItemStack itemStack);
    public abstract void render(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel model, HeldItemRenderer playerHeldItemRenderer);
    
    public final void render(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerHeldItemFeatureRenderer playerHeldItemFeatureRenderer, HeldItemRenderer playerHeldItemRenderer) {
        if (playerHeldItemFeatureRenderer.getContextModel() instanceof PlayerEntityModel model) {
            render(entity, stack, transformationMode, arm, matrices, vertexConsumers, light, model, playerHeldItemRenderer);
        }
    }
}
