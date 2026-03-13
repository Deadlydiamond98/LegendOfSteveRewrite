package net.deadlydiamond.legend_of_steve.client.rendering.block.baked.glowing;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;

public class GlowingBlockModelRenderer {
    private static GlowingBlockModelRenderer modelRenderer;
    private static BlockRenderManager blockRenderManager;
    private static final Direction[] DIRECTIONS = Direction.values();
    private final BlockColors colors;

    /**
     * A Majority of this is Copied from BlockModelRenderer, but with alpha being something that can be defined to determine glow intensity
     */

    public GlowingBlockModelRenderer(BlockColors colors) {
        this.colors = colors;
    }

    public static GlowingBlockModelRenderer getGlowingBlockModelRenderer(BlockEntityRendererFactory.Context context) {
        if (modelRenderer == null) {
            modelRenderer = new GlowingBlockModelRenderer(MinecraftClient.getInstance().getBlockColors());
        }
        blockRenderManager = context.getRenderManager();
        return modelRenderer;
    }

    public void render(BlockRenderView world, BlockState state, BlockPos pos, MatrixStack matrices, VertexConsumer vertexConsumer, Random random, long seed, float alpha) {
        BakedModel model = blockRenderManager.getModel(state);

        Vec3d vec3d = state.getModelOffset(world, pos);
        matrices.translate(vec3d.x, vec3d.y, vec3d.z);

        try {
            this.renderFlat(world, model, state, pos, matrices, vertexConsumer, random, seed, alpha);
        } catch (Throwable var17) {
            CrashReport crashReport = CrashReport.create(var17, "Tesselating block model");
            CrashReportSection crashReportSection = crashReport.addElement("Block model being tesselated");
            CrashReportSection.addBlockInfo(crashReportSection, world, pos, state);
            throw new CrashException(crashReport);
        }
    }

    public void renderFlat(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack matrices, VertexConsumer vertexConsumer, Random random, long seed, float alpha) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (Direction direction : DIRECTIONS) {
            random.setSeed(seed);
            List<BakedQuad> list = model.getQuads(state, direction, random);
            if (!list.isEmpty()) {
                mutable.set(pos, direction);
                if (Block.shouldDrawSide(state, world, pos, direction, mutable)) {
                    int i = WorldRenderer.getLightmapCoordinates(world, state, mutable);
                    this.renderQuadsFlat(world, state, pos, i, matrices, vertexConsumer, list, alpha);
                }
            }
        }
    }

    private void renderQuadsFlat(BlockRenderView world, BlockState state, BlockPos pos, int light, MatrixStack matrices, VertexConsumer vertexConsumer, List<BakedQuad> quads, float alpha) {
        for (BakedQuad bakedQuad : quads) {
            float f = world.getBrightness(bakedQuad.getFace(), bakedQuad.hasShade());
            this.renderQuad(world, state, pos, vertexConsumer, matrices.peek(), bakedQuad, f, f, f, f, light, light, light, light, alpha);
        }
    }

    private void renderQuad(BlockRenderView world, BlockState state, BlockPos pos, VertexConsumer vertexConsumer, MatrixStack.Entry matrixEntry, BakedQuad quad, float brightness0, float brightness1, float brightness2, float brightness3, int light0, int light1, int light2, int light3, float alpha) {
        float f;
        float g;
        float h;
        if (quad.hasColor()) {
            int i = this.colors.getColor(state, world, pos, quad.getColorIndex());
            f = (i >> 16 & 0xFF) / 255.0F;
            g = (i >> 8 & 0xFF) / 255.0F;
            h = (i & 0xFF) / 255.0F;
        } else {
            f = 1.0F;
            g = 1.0F;
            h = 1.0F;
        }

        quad(vertexConsumer, matrixEntry, quad, new float[]{brightness0, brightness1, brightness2, brightness3}, f, g, h,
                new int[]{light0, light1, light2, light3}, OverlayTexture.DEFAULT_UV, true, alpha
        );
    }

    /**
     * Copied from VertexConsumer, used to make quads with alpha (which determines the intensity of the bloom)
     */
    private void quad(VertexConsumer vertexConsumer, MatrixStack.Entry matrixEntry, BakedQuad quad, float[] brightnesses, float red, float green, float blue, int[] lights, int overlay, boolean useQuadColorData, float alpha) {
        float[] fs = new float[]{brightnesses[0], brightnesses[1], brightnesses[2], brightnesses[3]};
        int[] is = new int[]{lights[0], lights[1], lights[2], lights[3]};
        int[] js = quad.getVertexData();
        Vec3i vec3i = quad.getFace().getVector();
        Matrix4f matrix4f = matrixEntry.getPositionMatrix();
        Vector3f vector3f = matrixEntry.getNormalMatrix().transform(new Vector3f(vec3i.getX(), vec3i.getY(), vec3i.getZ()));
        int i = 8;
        int j = js.length / 8;

        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            ByteBuffer byteBuffer = memoryStack.malloc(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL.getVertexSizeByte());
            IntBuffer intBuffer = byteBuffer.asIntBuffer();

            for (int k = 0; k < j; k++) {
                intBuffer.clear();
                intBuffer.put(js, k * 8, 8);
                float f = byteBuffer.getFloat(0);
                float g = byteBuffer.getFloat(4);
                float h = byteBuffer.getFloat(8);
                float o;
                float p;
                float q;
                if (useQuadColorData) {
                    float l = (byteBuffer.get(12) & 255) / 255.0F;
                    float m = (byteBuffer.get(13) & 255) / 255.0F;
                    float n = (byteBuffer.get(14) & 255) / 255.0F;
                    o = l * fs[k] * red;
                    p = m * fs[k] * green;
                    q = n * fs[k] * blue;
                } else {
                    o = fs[k] * red;
                    p = fs[k] * green;
                    q = fs[k] * blue;
                }

                int r = is[k];
                float m = byteBuffer.getFloat(16);
                float n = byteBuffer.getFloat(20);
                Vector4f vector4f = matrix4f.transform(new Vector4f(f, g, h, 1.0F));
                vertexConsumer.vertex(vector4f.x(), vector4f.y(), vector4f.z(), o, p, q, alpha, m, n, overlay, r, vector3f.x(), vector3f.y(), vector3f.z());
            }
        }
    }
}