package net.deadlydiamond.legend_of_steve.mixin.client.item;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.client.rendering.player.itemmodel.CustomHeldItemRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HeldItemFeatureRenderer.class)
public class HeldItemFeatureRendererMixin {
    @Shadow @Final private HeldItemRenderer heldItemRenderer;

    @WrapMethod(method = "renderItem")
    private void legend_of_steve$renderItem(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        for (CustomHeldItemRenderer customHeldRenderer : CustomHeldItemRenderer.CUSTOM_HELD_ITEM_RENDERER) {
            if (customHeldRenderer.isValid(entity, arm, stack)) {
                customHeldRenderer.render(entity, stack, transformationMode, arm, matrices, vertexConsumers, light, (HeldItemFeatureRenderer) (Object) this, this.heldItemRenderer);
                return;
            }
        }
        original.call(entity, stack, transformationMode, arm, matrices, vertexConsumers, light);
    }
}
