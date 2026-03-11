package net.deadlydiamond.legend_of_steve.client.rendering.item;

import net.deadlydiamond.legend_of_steve.common.items.bag.BombBagItem;
import net.deadlydiamond.legend_of_steve.common.items.projectile.explosive.IGuiRotation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class BombBagItemRenderer {
    public static boolean renderBombBag(ItemStack bundleStack, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ModelTransformationMode renderMode) {
        ItemStack bombStack = BombBagItem.getFirstStack(bundleStack);
        if (!bombStack.isEmpty()) {
            if (renderMode == ModelTransformationMode.GUI) {
                matrices.push();
                renderBombBagDisplay(bundleStack, bombStack, light, overlay, matrices, vertexConsumers, renderMode);
                matrices.pop();
                return false;
            }
        }
        return true;
    }

    private static void renderBombBagDisplay(ItemStack bundleStack, ItemStack bombStack, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ModelTransformationMode renderMode) {
        MinecraftClient client = MinecraftClient.getInstance();
        BakedModel bakedModel = client.getItemRenderer().getModel(bombStack, client.world, client.player, 0);

        renderBagFace(matrices, vertexConsumers, 1, bundleStack, "front", light, overlay);
        matrices.push();

        matrices.translate(0.5, 0.5 + (0.0625 * 1), 0);
        
        if (bombStack.getItem() instanceof IGuiRotation guiRotation) {
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(guiRotation.getGUIRotation()));
        }
        
        client.getItemRenderer().renderItem(
                bombStack,
                ModelTransformationMode.GUI,
                false,
                matrices,
                vertexConsumers,
                light,
                overlay,
                bakedModel
        );

        matrices.pop();
        renderBagFace(matrices, vertexConsumers, 0, bundleStack, "back", light, overlay);
    }


    // GUI Bag rendering ///////////////////////////////////////////////////////////////////////////////////////////////

    private static void renderBagFace(MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int zOffset, ItemStack bag, String viewType, int light, int overlay) {
        Identifier bombID = Registries.ITEM.getId(bag.getItem());
        Identifier texture = new Identifier(bombID.getNamespace(), "textures/item/bomb_bag/" + bombID.getPath() + "_view_" + viewType + ".png");

        VertexConsumer vertexConsumer = bag.hasEnchantments() ? ItemRenderer.getDirectDynamicDisplayGlintConsumer(
                vertexConsumerProvider, RenderLayer.getEntityCutout(texture), matrices.peek()
        ) : vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(texture));

        renderFace(matrices, vertexConsumer, 0, 1, 0, 1, zOffset, 0, 1, 0, 1, light, overlay);
    }

    private static void renderFace(MatrixStack matrices, VertexConsumer vertexConsumer, float x1, float x2, float y1, float y2, float z, float u1, float u2, float v1, float v2, int light, int overlay) {
        MatrixStack.Entry pose = matrices.peek();
        Matrix4f m4f = pose.getPositionMatrix();
        Matrix3f m3f = pose.getNormalMatrix();

        vertex(vertexConsumer, m4f, m3f, x1, y1, u1, v2, z, light, overlay);
        vertex(vertexConsumer, m4f, m3f, x2, y1, u2, v2, z, light, overlay);
        vertex(vertexConsumer, m4f, m3f, x2, y2, u2, v1, z, light, overlay);
        vertex(vertexConsumer, m4f, m3f, x1, y2, u1, v1, z, light, overlay);
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, float x, float y, float u, float v, float z, int light, int overlay) {
        vertexConsumer.vertex(matrix4f, x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(overlay).light(light).normal(matrix3f, 0, 1, 0).next();
    }
}
