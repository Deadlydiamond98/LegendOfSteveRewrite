package net.deadlydiamond.legend_of_steve.client.rendering.entity;

import net.deadlydiamond.legend_of_steve.common.entities.PushableBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.OverlayVertexConsumer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class PushableBlockEntityRenderer extends EntityRenderer<PushableBlockEntity> {
    private final BlockRenderManager blockRenderManager;

    public PushableBlockEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0;
        this.blockRenderManager = ctx.getBlockRenderManager();
    }

    @Override
    public void render(PushableBlockEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        BlockState block = entity.getBlock();

        renderBlock(entity, block, 0, matrices, vertexConsumers);

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void renderBlock(PushableBlockEntity pushBlock, BlockState block, double yOffset, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        World world = pushBlock.getWorld();

        if (block.getRenderType() == BlockRenderType.MODEL) {
            matrices.push();

            BlockPos pos = BlockPos.ofFloored(pushBlock.getX(), pushBlock.getBoundingBox().maxY, pushBlock.getZ());

            matrices.translate(-0.5, yOffset, -0.5);

            this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(block),
                    block, pos, matrices, vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(block)),
                    false, Random.create(), block.getRenderingSeed(pos),
                    OverlayTexture.DEFAULT_UV
            );

            if (pushBlock.getBreakStage() != -1) {
                MatrixStack.Entry entry3 = matrices.peek();
                OverlayVertexConsumer vertexConsumer2 = new OverlayVertexConsumer(
                        vertexConsumers.getBuffer(ModelLoader.BLOCK_DESTRUCTION_RENDER_LAYERS.get(pushBlock.getBreakStage())),
                        entry3.getPositionMatrix(), entry3.getNormalMatrix(),
                        1.0f
                );
                this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(block),
                        block, pos, matrices, vertexConsumer2,
                        false, Random.create(), block.getRenderingSeed(pos),
                        OverlayTexture.DEFAULT_UV
                );
            }

            matrices.pop();
        }
    }

    @Override
    public Identifier getTexture(PushableBlockEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
