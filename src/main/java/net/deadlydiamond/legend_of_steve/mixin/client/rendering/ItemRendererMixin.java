package net.deadlydiamond.legend_of_steve.mixin.client.rendering;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.client.rendering.item.BombBagItemRenderer;
import net.deadlydiamond.legend_of_steve.common.items.projectile.explosive.ICharged;
import net.deadlydiamond.legend_of_steve.common.items.bag.BombBagItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow protected abstract void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices);

    @Shadow public abstract void renderItem(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model);

    @WrapOperation(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayers;getItemLayer(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer legend_of_steve$renderItem(ItemStack stack, boolean direct, Operation<RenderLayer> original, @Local ModelTransformationMode renderMode) {
        // Renders the Iridescence Layer on Iridescent Items
        if (stack.isIn(ZeldaTags.IRIDESCENT)) {
            return renderMode == ModelTransformationMode.GUI ? ZeldaRenderLayers.GUI_ITEM_IRIDESCENCE : ZeldaRenderLayers.ENTITY_IRIDESCENCE_TEXTURED;
        }
        return original.call(stack, direct);
    }

    @WrapMethod(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V")
    private void legend_of_steve$renderItem(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, Operation<Void> original) {
        MinecraftClient client = MinecraftClient.getInstance();

        // Replaces Bomb Bag with Bomb Item Renderer In Some Instances
        if (stack.getItem() instanceof BombBagItem) {
            ItemStack bombStack = BombBagItem.getFirstStack(stack);
            boolean isThirdPerson = renderMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || renderMode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
            if (!bombStack.isEmpty() && (renderMode.isFirstPerson() || isThirdPerson)) {
                BakedModel bakedModel = client.getItemRenderer().getModel(bombStack, client.world, client.player, 0);
                renderItem(bombStack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, bakedModel);
                return;
            }
        }
        original.call(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model);
    }

    @WrapWithCondition(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderBakedItemModel(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V"))
    private boolean legend_of_steve$renderItem(ItemRenderer instance, BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices, @Local(argsOnly = true) VertexConsumerProvider vertexConsumers, @Local ModelTransformationMode renderMode) {

        // RENDERS CHARGED OVERLAY
        if (stack.getItem() instanceof ICharged) {
            matrices.push();
            VertexConsumer vertexConsumer = VertexConsumers.union(vertexConsumers.getBuffer(ZeldaRenderLayers.getChargedGlint()), vertices);
            this.renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
            matrices.pop();
            return false;
        }

        // RENDERS BOMB BAG ITEM STUFF
        if (stack.getItem() instanceof BombBagItem) {
            return BombBagItemRenderer.renderBombBag(stack, light, overlay, matrices, vertexConsumers, renderMode);
        }

        return true;
    }
}
