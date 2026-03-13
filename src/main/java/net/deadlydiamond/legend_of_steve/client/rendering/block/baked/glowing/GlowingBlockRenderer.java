package net.deadlydiamond.legend_of_steve.client.rendering.block.baked.glowing;

import net.deadlydiamond.legend_of_steve.client.rendering.block.baked.BakedBlockEntityRenderer;
import net.deadlydiamond.legend_of_steve.common.bes.visual.GlowingBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.IGlowingBlock;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class GlowingBlockRenderer extends BakedBlockEntityRenderer<GlowingBlockEntity> {
    private static final Direction[] DIRECTIONS = Direction.values();

    public GlowingBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void renderUnbaked(GlowingBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.renderDirty) {
            entity.renderDirty = false;
            Manager.markForRebuild(entity.getPos());
        }
    }

    @Override
    public void renderBaked(GlowingBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState state = entity.getCachedState();
        BlockPos pos = entity.getPos();
        World world = entity.getWorld();

        if (state.getBlock() instanceof IGlowingBlock glowingBlock) {

            matrices.push();

            float scaleOffset = (float) (((Math.sin(entity.getPos().toCenterPos().length() * 2) * 0.05f) + 0.05f) * 0.5f) + 0.01f;
            float glowScale = glowingBlock.getGlowScale();
            float scale = glowingBlock.stopZFighting() ? glowScale + scaleOffset : glowScale;

            matrices.translate(0.5, 0.5, 0.5);
            matrices.scale(scale, scale, scale);
            matrices.translate(-0.5, -0.5, -0.5);

            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ZeldaRenderLayers.BLOOM_GLOW);

            GlowingBlockModelRenderer.getGlowingBlockModelRenderer(this.context).render(
                    world,
                    state,
                    pos,
                    matrices,
                    vertexConsumer,
                    Random.create(),
                    state.getRenderingSeed(pos),
                    glowingBlock.bloomIntensity()
            );

            matrices.pop();
        }
    }

    @Override
    public boolean shouldBake(GlowingBlockEntity entity) {
        BlockState state = entity.getCachedState();
        BlockPos pos = entity.getPos();
        World world = entity.getWorld();

        for (Direction direction : DIRECTIONS) {
            if (Block.shouldDrawSide(state, world, pos, direction, pos.mutableCopy())) {
                return false;
            }
        }
        return state.getBlock() instanceof IGlowingBlock;
    }
}
