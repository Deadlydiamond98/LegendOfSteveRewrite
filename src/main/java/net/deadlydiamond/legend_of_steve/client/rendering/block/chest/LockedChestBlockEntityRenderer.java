package net.deadlydiamond.legend_of_steve.client.rendering.block.chest;

import net.deadlydiamond.legend_of_steve.common.bes.locks.LockedChestBlockEntity;

import net.minecraft.block.enums.ChestType;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;

public class LockedChestBlockEntityRenderer<T extends LockedChestBlockEntity> extends AbstractCustomChestRenderer<T> {
    public LockedChestBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    protected VertexConsumer getChestVertexConsumer(T entity, VertexConsumerProvider vertexConsumers, ChestType chestType) {
        SpriteIdentifier spriteIdentifier = getVanillaChestTexture(entity.getLockedBlock(), chestType);
        return spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
    }

    @Override
    protected void renderChestBlock(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart latch, ModelPart base, float openFactor, int light, int overlay) {
        super.renderChestBlock(matrices, vertices, lid, latch, base, 0, light, overlay);
        latch.visible = false;
    }
}
