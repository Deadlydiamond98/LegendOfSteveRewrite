package net.deadlydiamond.legend_of_steve.mixin.client.rendering;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow protected abstract void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices);

    @WrapWithCondition(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/ItemRenderer;renderBakedItemModel(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V"
            )
    )
    private boolean legend_of_steve$renderItem(ItemRenderer instance, BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices, @Local(argsOnly = true) VertexConsumerProvider vertexConsumers, @Local RenderLayer renderLayer) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (stack.isIn(ZeldaTags.CHARGED) && client.player != null) {
            matrices.push();

            VertexConsumer vertexConsumer = VertexConsumers.union(vertexConsumers.getBuffer(
                    ZeldaRenderLayers.getChargedGlint()
            ), vertexConsumers.getBuffer(renderLayer));

            this.renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
            matrices.pop();
            return false;
        }
        return true;
    }

    @Unique
    private Identifier legend_of_steve$getEnergySwirlTexture() {
        return new Identifier("textures/entity/creeper/creeper_armor.png");
    }


    @Unique
    private float legend_of_steve$getEnergySwirlX(float partialAge) {
        return partialAge * 0.01F;
    }
}
