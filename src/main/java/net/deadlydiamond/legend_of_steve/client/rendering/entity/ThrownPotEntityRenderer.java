package net.deadlydiamond.legend_of_steve.client.rendering.entity;

import net.deadlydiamond.legend_of_steve.common.entities.projectile.ThrownPotEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

public class ThrownPotEntityRenderer<T extends ThrownPotEntity> extends EntityRenderer<T> {
    private final BlockRenderManager blockRenderManager;

    public ThrownPotEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.blockRenderManager = ctx.getBlockRenderManager();
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.getStack().getItem() instanceof BlockItem block) {
            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw()));
            matrices.translate(-0.5, 0, -0.5);

            BlockState state = block.getBlock().getDefaultState();
            BlockPos pos = entity.getBlockPos();
            this.blockRenderManager.getModelRenderer().render(
                    entity.getWorld(),
                    this.blockRenderManager.getModel(state),
                    state,
                    pos,
                    matrices,
                    vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(state)),
                    false,
                    Random.create(),
                    state.getRenderingSeed(pos),
                    OverlayTexture.DEFAULT_UV
            );

            matrices.pop();
        }
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(T entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
