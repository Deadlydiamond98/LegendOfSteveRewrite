package net.deadlydiamond.legend_of_steve.client.rendering.block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.be.FlipBlockEntityModel;
import net.deadlydiamond.legend_of_steve.common.ZeldaProperties;
import net.deadlydiamond.legend_of_steve.common.bes.FlipBlockBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class FlipBlockEntityRenderer implements BlockEntityRenderer<FlipBlockBlockEntity> {
    private static final Identifier TEXTURE = LegendOfSteve.id("textures/entity/flip_block.png");
    private final FlipBlockEntityModel model;

    public FlipBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new FlipBlockEntityModel(ctx.getLayerModelPart(FlipBlockEntityModel.LAYER_LOCATION));
    }


    @Override
    public void render(FlipBlockBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.getCachedState().get(ZeldaProperties.SPINNING)) {
            matrices.push();

            Direction.Axis axis = entity.getCachedState().get(Properties.HORIZONTAL_AXIS);

            matrices.translate(0.5, 0.5, 0.5);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(Direction.from(axis, Direction.AxisDirection.POSITIVE).asRotation()));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getTurnTimer(tickDelta) * 10));

            matrices.translate(0, 1, 0);
            matrices.scale(-1, -1, 1);

            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));
            this.model.render(matrices, vertexConsumer, light, overlay, 1, 1, 1, 1);

            matrices.pop();
        }
    }
}
