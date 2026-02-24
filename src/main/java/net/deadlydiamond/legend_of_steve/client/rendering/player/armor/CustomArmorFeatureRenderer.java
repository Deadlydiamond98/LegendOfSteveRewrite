package net.deadlydiamond.legend_of_steve.client.rendering.player.armor;

import net.deadlydiamond.legend_of_steve.common.items.bag.QuiverItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class CustomArmorFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final QuiverFeatureRenderer<T, M> quiverRenderer;

    public CustomArmorFeatureRenderer(FeatureRendererContext<T, M> context, EntityRendererFactory.Context ctx) {
        super(context);
        this.quiverRenderer = new QuiverFeatureRenderer<>(context, ctx);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack chestItem = entity.getEquippedStack(EquipmentSlot.CHEST);

        if (chestItem.getItem() instanceof QuiverItem) {
            this.quiverRenderer.renderQuiver(chestItem, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
        }
    }
}
