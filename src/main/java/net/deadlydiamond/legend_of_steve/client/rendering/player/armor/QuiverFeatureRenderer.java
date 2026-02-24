package net.deadlydiamond.legend_of_steve.client.rendering.player.armor;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.feature.QuiverModel;
import net.deadlydiamond.legend_of_steve.common.items.bag.QuiverItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import java.util.List;
import java.util.Optional;

public class QuiverFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> {
    private final FeatureRendererContext<T, M> context;
    private final EntityRenderDispatcher dispatcher;

    private final QuiverModel<T> quiver;

    public QuiverFeatureRenderer(FeatureRendererContext<T, M> context, EntityRendererFactory.Context ctx) {
        this.context = context;
        this.quiver = new QuiverModel<>(ctx.getModelLoader().getModelPart(QuiverModel.LAYER_LOCATION));
        this.dispatcher = ctx.getRenderDispatcher();
    }

    public void renderQuiver(ItemStack quiver, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        this.context.getModel().copyStateTo(this.quiver);
        this.quiver.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(
                vertexConsumers, RenderLayer.getArmorCutoutNoCull(getQuiverTexture(quiver)), false, quiver.hasGlint()
        );
        this.quiver.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        renderAllArrows(quiver, matrices, vertexConsumers, light, entity, tickDelta);
        matrices.pop();
    }

    private void renderAllArrows(ItemStack quiver, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float tickDelta) {
        List<ItemStack> arrows = QuiverItem.getItemStacks(quiver);
        if (arrows.size() >= 3) {
            renderQuiverArrow(arrows.get(0), matrices, vertexConsumers, light, entity, tickDelta, -0.25, 0.25, -0.21315, 12);
            renderQuiverArrow(arrows.get(1), matrices, vertexConsumers, light, entity, tickDelta, -0.1875, 0.33, -0.34375, 69);
            renderQuiverArrow(arrows.get(2), matrices, vertexConsumers, light, entity, tickDelta, -0.3125, 0.36, -0.34375, 174);
        } else if (arrows.size() == 2) {
            ItemStack firstArrow = arrows.get(0);
            ItemStack secondArrow = arrows.get(1);

            if (firstArrow.getCount() + secondArrow.getCount() >= 3) {
                renderQuiverArrow(firstArrow, matrices, vertexConsumers, light, entity, tickDelta, -0.25, 0.25, -0.21315, 12);
                renderQuiverArrow(secondArrow, matrices, vertexConsumers, light, entity, tickDelta, -0.1875, 0.33, -0.34375, 69);

                ItemStack thirdArrow = firstArrow.getCount() >secondArrow.getCount() ? firstArrow : secondArrow;
                renderQuiverArrow(thirdArrow, matrices, vertexConsumers, light, entity, tickDelta, -0.3125, 0.36, -0.34375, 174);
            } else {
                renderQuiverArrow(firstArrow, matrices, vertexConsumers, light, entity, tickDelta, -0.1875, 0.25, -0.22315, 69);
                renderQuiverArrow(secondArrow, matrices, vertexConsumers, light, entity, tickDelta, -0.3125, 0.36, -0.34375, 174);
            }
        } else {
            ItemStack arrow = QuiverItem.getFirstStack(quiver);
            int count = arrow.getCount();

            if (count >= 3) {
                renderQuiverArrow(arrow, matrices, vertexConsumers, light, entity, tickDelta, -0.25, 0.25, -0.21315, 12);
                renderQuiverArrow(arrow, matrices, vertexConsumers, light, entity, tickDelta, -0.1875, 0.33, -0.34375, 69);
                renderQuiverArrow(arrow, matrices, vertexConsumers, light, entity, tickDelta, -0.3125, 0.36, -0.34375, 174);
            } else if (count == 2) {
                renderQuiverArrow(arrow, matrices, vertexConsumers, light, entity, tickDelta, -0.1875, 0.25, -0.22315, 69);
                renderQuiverArrow(arrow, matrices, vertexConsumers, light, entity, tickDelta, -0.3125, 0.36, -0.34375, 174);
            } else if (count == 1) {
                renderQuiverArrow(arrow, matrices, vertexConsumers, light, entity, tickDelta, -0.25, 0.3, -0.25, 174);
            }
        }
    }

    private void renderQuiverArrow(ItemStack arrowStack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float tickDelta, double xPos, double yPos, double zPos, float roll) {
        if (arrowStack.getItem() instanceof ArrowItem arrow) {
            matrices.push();
            PersistentProjectileEntity arrowEntity =  arrow.createArrow(entity.getWorld(), arrowStack, entity);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            matrices.translate(xPos, 0, zPos);
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(55));
            matrices.translate(0, 0, yPos);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(roll));

            this.dispatcher.render(arrowEntity, 0, 0, 0, 0, tickDelta, matrices, vertexConsumers, light);
            matrices.pop();
        }
    }

    private Identifier getQuiverTexture(ItemStack item) {
        Identifier type = Registries.ITEM.getId(item.getItem());
        Identifier texture = new Identifier(type.getNamespace(), "textures/entity/quiver/" + type.getPath() + ".png");
        Optional<Resource> resource = MinecraftClient.getInstance().getResourceManager().getResource(texture);
        return resource.isPresent() ? texture : LegendOfSteve.id("textures/entity/quiver/quiver.png");
    }
}
