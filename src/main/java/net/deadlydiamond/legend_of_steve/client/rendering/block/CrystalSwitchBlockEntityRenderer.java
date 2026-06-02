package net.deadlydiamond.legend_of_steve.client.rendering.block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.block.CrystalSwitchOrbModel;
import net.deadlydiamond.legend_of_steve.common.bes.switches.CrystalSwitchBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class CrystalSwitchBlockEntityRenderer implements BlockEntityRenderer<CrystalSwitchBlockEntity> {
    private static final Identifier ON_TEXTURE = LegendOfSteve.id("textures/entity/switch/crystal_switch_orb_on.png");
    private static final Identifier OFF_TEXTURE = LegendOfSteve.id("textures/entity/switch/crystal_switch_orb_off.png");
    private static final Identifier OUTLINE_TEXTURE = LegendOfSteve.id("textures/entity/switch/crystal_switch_orb_outline.png");
    private final CrystalSwitchOrbModel model;

    public CrystalSwitchBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new CrystalSwitchOrbModel(ctx.getLayerModelPart(CrystalSwitchOrbModel.LAYER_LOCATION));
    }

    @Override
    public void render(CrystalSwitchBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.firstTick) {
            return;
        }

        matrices.push();
        matrices.scale(-1, -1, 1);
        matrices.translate(-0.5, -2.33, 0.5);

        float g = entity.ticks + tickDelta;
        matrices.translate(0, MathHelper.sin(g * 0.1f) * 0.01, 0);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.OrbYaw + tickDelta));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(
                entity.isOn() ? ON_TEXTURE : OFF_TEXTURE
        ));
        this.model.render(matrices, vertexConsumer, light, overlay, 1, 1, 1, 1);

        matrices.translate(0.0, 2.53, 0.0);
        matrices.scale(-1.025f, -1.025f, -1.025f);

        VertexConsumer outlineVertexConsumer = vertexConsumers.getBuffer(
                RenderLayer.getBeaconBeam(OUTLINE_TEXTURE, false)
        );
        this.model.render(matrices, outlineVertexConsumer, light, overlay, 1, 1, 1, 1);

        matrices.pop();
    }
}
