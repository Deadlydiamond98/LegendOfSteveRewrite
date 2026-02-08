package net.deadlydiamond.legend_of_steve.client.rendering.block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombEntityModel;
import net.deadlydiamond.legend_of_steve.common.bes.BombFlowerBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.BombFlowerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BombFlowerRenderer implements BlockEntityRenderer<BombFlowerBlockEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/creeper/creeper_armor.png");
    private final BombEntityModel<?> model;

    public BombFlowerRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new BombEntityModel<>(ctx.getLayerModelPart(BombEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(BombFlowerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        BlockState state = entity.getCachedState();
        if (client.player != null && state.get(BombFlowerBlock.CHARGED) && !state.hasRandomTicks()) {
            matrices.push();
            float scale = 1;
            matrices.scale(-scale, -scale, scale);
            matrices.translate(-0.5f, -1.501 - 0.375, 0.5f);

            matrices.translate(0, -0.0625, 0);
            matrices.scale(1.25f, 1.25f, 1.25f);

            float f = client.player.age + tickDelta;
            VertexConsumer vertexConsumer =  vertexConsumers.getBuffer(
                    RenderLayer.getEnergySwirl(TEXTURE, getEnergySwirlX(f) % 1.0F, f * 0.01F % 1.0F)
            );

            this.model.renderOverlay(matrices, vertexConsumer, light, overlay, 0.5f, 0.5f, 0.5f, 1);
            matrices.pop();
        }
    }

    private float getEnergySwirlX(float partialAge) {
        return partialAge * 0.01F;
    }
}
