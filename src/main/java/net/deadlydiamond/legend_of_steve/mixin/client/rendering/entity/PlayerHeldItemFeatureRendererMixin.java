package net.deadlydiamond.legend_of_steve.mixin.client.rendering.entity;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.deadlydiamond.legend_of_steve.client.rendering.player.itemmodel.CustomHeldItemRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerHeldItemFeatureRenderer.class)
public class PlayerHeldItemFeatureRendererMixin {
    @Shadow @Final private HeldItemRenderer playerHeldItemRenderer;

    @WrapWithCondition(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/HeldItemFeatureRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private boolean legend_of_steve$renderItem(HeldItemFeatureRenderer instance, LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        for (CustomHeldItemRenderer customHeldRenderer : CustomHeldItemRenderer.CUSTOM_HELD_ITEM_RENDERER) {
            if (customHeldRenderer.isValid(entity, arm, stack)) {
                customHeldRenderer.render(entity, stack, transformationMode, arm, matrices, vertexConsumers, light, (PlayerHeldItemFeatureRenderer) (Object) this, this.playerHeldItemRenderer);
                return false;
            }
        }
        return true;
    }
}
