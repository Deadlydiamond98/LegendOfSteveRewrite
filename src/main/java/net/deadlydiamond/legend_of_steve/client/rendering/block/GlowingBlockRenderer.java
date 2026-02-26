package net.deadlydiamond.legend_of_steve.client.rendering.block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.bes.GlowingBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.light.IGlowingBlock;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaShaders;
import net.deadlydiamond98.koalalib.client.PostProcessingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GlowingBlockRenderer implements BlockEntityRenderer<GlowingBlockEntity> {
    public static final List<GlowingBlockEntity> GLOWING_BLOCKS = new ArrayList<>();

    public GlowingBlockRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(GlowingBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // Due to how un-performant the Glowing Layer can be, the Blocks needed for Rendering are added to a list to be rendered together
        // with only 1 glow layer
        GLOWING_BLOCKS.add(entity);
    }

    public static void renderAllGlowing(WorldRenderContext context) {
        MatrixStack matrices = context.matrixStack();
        VertexConsumerProvider vertexConsumers = context.consumers();
        BlockRenderManager blockRenderManager = context.gameRenderer().getClient().getBlockRenderManager();
        Vec3d cameraPos = context.gameRenderer().getCamera().getPos();

        matrices.push();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        // Renders the Glowing Layers First
        VertexConsumer glowingLayer = vertexConsumers.getBuffer(ZeldaRenderLayers.getGlowing(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
        GLOWING_BLOCKS.forEach(entity -> renderGlowing(entity, matrices, glowingLayer, blockRenderManager));
        // Then the Other part
        GLOWING_BLOCKS.forEach(entity -> renderRegular(entity, matrices, vertexConsumers, blockRenderManager));

        GLOWING_BLOCKS.clear();
        matrices.pop();
    }

    public static void renderGlowing(GlowingBlockEntity entity, MatrixStack matrices, VertexConsumer glowingLayer, BlockRenderManager blockRenderManager) {
        if (entity.getCachedState().getBlock() instanceof IGlowingBlock glowingBlock) {
            renderGlowing(entity, matrices, glowingLayer, blockRenderManager, glowingBlock.getGlowScale(), glowingBlock.stopZFighting());
        } else {
            renderGlowing(entity, matrices, glowingLayer, blockRenderManager, 1, true);
        }
    }

    public static void renderGlowing(GlowingBlockEntity entity, MatrixStack matrices, VertexConsumer glowingLayer, BlockRenderManager blockRenderManager, float glowScale, boolean stopZFighting) {
        matrices.push();
        alignBlock(entity, matrices);

        // This is done to prevent Z-fighting
        float scaleOffset = (float) (((Math.sin(entity.getPos().toCenterPos().length() * 2) * 0.05f) + 0.05f) * 0.5f) + 0.01f;
        float scale = stopZFighting ? glowScale + scaleOffset : glowScale;

        matrices.translate(0.5, 0.5, 0.5);
        matrices.scale(scale, scale, scale);
        matrices.translate(-0.5, -0.5, -0.5);
        renderBlock(entity, matrices, glowingLayer, blockRenderManager);

        matrices.pop();
    }

    public static void renderRegular(GlowingBlockEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, BlockRenderManager blockRenderManager) {
        matrices.push();
        alignBlock(entity, matrices);
        VertexConsumer regular = vertexConsumers.getBuffer(RenderLayers.getBlockLayer(entity.getCachedState()));
//        renderBlock(entity, matrices, regular, blockRenderManager);
        matrices.pop();
    }

    public static void alignBlock(GlowingBlockEntity entity, MatrixStack matrices) {
        BlockPos pos = entity.getPos();
        matrices.translate(pos.getX(), pos.getY(), pos.getZ());
    }

    public static void renderBlock(GlowingBlockEntity entity, MatrixStack matrices, VertexConsumer vertexConsumer, BlockRenderManager blockRenderManager) {
        BlockState state = entity.getCachedState();
        World world = entity.getWorld();
        BlockPos pos = entity.getPos();

        blockRenderManager.getModelRenderer().render(
                world,
                blockRenderManager.getModel(state),
                state,
                pos,
                matrices,
                vertexConsumer,
                true,
                Random.create(),
                state.getRenderingSeed(pos),
                OverlayTexture.DEFAULT_UV
        );
    }

    @Override
    public int getRenderDistance() {
        return 256;
    }
}
