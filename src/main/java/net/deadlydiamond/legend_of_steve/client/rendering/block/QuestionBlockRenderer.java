package net.deadlydiamond.legend_of_steve.client.rendering.block;

import net.deadlydiamond.legend_of_steve.common.bes.container.single.HittableContainerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class QuestionBlockRenderer implements BlockEntityRenderer<HittableContainerBlockEntity> {
    private final BlockRenderManager blockRenderManager;

    public QuestionBlockRenderer(BlockEntityRendererFactory.Context ctx) {
        this.blockRenderManager = ctx.getRenderManager();
    }

    @Override
    public void render(HittableContainerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.shouldRenderHit()) {
            World world = entity.getWorld();
            BlockPos pos = entity.getPos();
            BlockState block = entity.getCachedState();

            matrices.push();

            Vec3d bouncePos = entity.getBouncePos(tickDelta);
            matrices.translate(bouncePos.x, bouncePos.y, bouncePos.z);

            this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(block),
                    block, pos, matrices, vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(block)),
                    false, Random.create(), block.getRenderingSeed(pos),
                    OverlayTexture.DEFAULT_UV
            );

            matrices.pop();
        }
    }
}
