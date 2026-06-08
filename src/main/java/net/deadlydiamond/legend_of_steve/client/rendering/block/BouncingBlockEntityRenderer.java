package net.deadlydiamond.legend_of_steve.client.rendering.block;

import net.deadlydiamond.legend_of_steve.common.bes.BouncingBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BouncingBlockEntityRenderer implements BlockEntityRenderer<BouncingBlockEntity> {
    private final BlockRenderManager blockRenderManager;

    public BouncingBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.blockRenderManager = ctx.getRenderManager();
    }

    @Override
    public void render(BouncingBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        BlockPos pos = entity.getPos();
        BlockState block = entity.getRenderedBlock();

        matrices.push();
        Vec3d bouncePos = entity.getBouncePos(tickDelta);
        matrices.translate(0.5, 0.5, 0.5);
        matrices.scale(1.005f, 1.005f, 1.005f);
        matrices.translate(-0.5, -0.5, -0.5);

        matrices.translate(bouncePos.x, bouncePos.y, bouncePos.z);

        this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(block),
                block, pos, matrices, vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(block)),
                false, Random.create(), block.getRenderingSeed(pos),
                OverlayTexture.DEFAULT_UV
        );

        matrices.pop();
    }
}
