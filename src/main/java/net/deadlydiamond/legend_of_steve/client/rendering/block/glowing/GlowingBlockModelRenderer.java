package net.deadlydiamond.legend_of_steve.client.rendering.block.glowing;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class GlowingBlockModelRenderer {
    private static GlowingBlockModelRenderer modelRenderer;
    private static BlockRenderManager blockRenderManager;
    private static final Direction[] DIRECTIONS = Direction.values();
    private final BlockColors colors;

    public GlowingBlockModelRenderer(BlockColors colors) {
        this.colors = colors;
    }

    public static GlowingBlockModelRenderer getGlowingBlockModelRenderer(WorldRenderContext context) {
        if (modelRenderer == null) {
            modelRenderer = new GlowingBlockModelRenderer(context.gameRenderer().getClient().getBlockColors());
        }
        blockRenderManager = context.gameRenderer().getClient().getBlockRenderManager();
        return modelRenderer;
    }

    public void render(BlockRenderView world, BlockState state, BlockPos pos,
            MatrixStack matrices, VertexConsumer vertexConsumer,
            Random random, long seed, Camera camera) {
        BakedModel model = blockRenderManager.getModel(state);

        Vec3d vec3d = state.getModelOffset(world, pos);
        matrices.translate(vec3d.x, vec3d.y, vec3d.z);

        try {
            this.renderFlat(world, model, state, pos, matrices, vertexConsumer, random, seed, camera);
        } catch (Throwable var17) {
            CrashReport crashReport = CrashReport.create(var17, "Tesselating block model");
            CrashReportSection crashReportSection = crashReport.addElement("Block model being tesselated");
            CrashReportSection.addBlockInfo(crashReportSection, world, pos, state);
            throw new CrashException(crashReport);
        }
    }

    public void renderFlat(
            BlockRenderView world,
            BakedModel model,
            BlockState state,
            BlockPos pos,
            MatrixStack matrices,
            VertexConsumer vertexConsumer,
            Random random,
            long seed,
            Camera camera
    ) {
        BitSet bitSet = new BitSet(3);
        BlockPos.Mutable mutable = pos.mutableCopy();

        List<Direction> directions = new ArrayList<>();
        for (Direction direction : DIRECTIONS) {
            random.setSeed(seed);
            List<BakedQuad> list = model.getQuads(state, direction, random);
            if (!list.isEmpty()) {
                mutable.set(pos, direction);
                if (shouldRenderFace(camera, direction, 90 + 45) && Block.shouldDrawSide(state, world, pos, direction, mutable)) {
                    int i = WorldRenderer.getLightmapCoordinates(world, state, mutable);
                    this.renderQuadsFlat(world, state, pos, i, false, matrices, vertexConsumer, list, bitSet);
                    directions.add(direction);
                }
            }
        }
//        LegendOfSteve.LOGGER.info("Rendering: {}", directions);

//        random.setSeed(seed);
//        List<BakedQuad> list2 = model.getQuads(state, null, random);
//        if (!list2.isEmpty()) {
//            this.renderQuadsFlat(world, state, pos, -1, true, matrices, vertexConsumer, list2, bitSet);
//        }
    }

    private boolean shouldRenderFace(Camera camera, Direction direction, float angle) {
//        Quaternionf cameraRotation = camera.getRotation();
//        Quaternionf faceRotation = direction.getOpposite().getRotationQuaternion();
//
//        cameraRotation.normalize();
//        faceRotation.normalize();
//
//        float dot = cameraRotation.dot(faceRotation);
//
//        float angleRad = (float) (2.0 * Math.acos(Math.abs(dot)));
//        float angleDeg = (float) Math.toDegrees(angleRad);
//
//        return angleDeg <= angle;
        return true;
    }

    private void renderQuadsFlat(
            BlockRenderView world,
            BlockState state,
            BlockPos pos,
            int light,
            boolean useWorldLight,
            MatrixStack matrices,
            VertexConsumer vertexConsumer,
            List<BakedQuad> quads,
            BitSet flags
    ) {
        for (BakedQuad bakedQuad : quads) {
            if (useWorldLight) {
                this.getQuadDimensions(world, state, pos, bakedQuad.getVertexData(), bakedQuad.getFace(), null, flags);
                BlockPos blockPos = flags.get(0) ? pos.offset(bakedQuad.getFace()) : pos;
                light = WorldRenderer.getLightmapCoordinates(world, state, blockPos);
            }

            float f = world.getBrightness(bakedQuad.getFace(), bakedQuad.hasShade());
            this.renderQuad(world, state, pos, vertexConsumer, matrices.peek(), bakedQuad, f, f, f, f, light, light, light, light);
        }
    }

    private void renderQuad(
            BlockRenderView world,
            BlockState state,
            BlockPos pos,
            VertexConsumer vertexConsumer,
            MatrixStack.Entry matrixEntry,
            BakedQuad quad,
            float brightness0,
            float brightness1,
            float brightness2,
            float brightness3,
            int light0,
            int light1,
            int light2,
            int light3
    ) {
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

        vertexConsumer.quad(
                matrixEntry, quad, new float[]{brightness0, brightness1, brightness2, brightness3}, f, g, h, new int[]{light0, light1, light2, light3}, OverlayTexture.DEFAULT_UV, true
        );
    }

    private void getQuadDimensions(BlockRenderView world, BlockState state, BlockPos pos, int[] vertexData, Direction face, @Nullable float[] box, BitSet flags) {
        float f = 32.0F;
        float g = 32.0F;
        float h = 32.0F;
        float i = -32.0F;
        float j = -32.0F;
        float k = -32.0F;

        for (int l = 0; l < 4; l++) {
            float m = Float.intBitsToFloat(vertexData[l * 8]);
            float n = Float.intBitsToFloat(vertexData[l * 8 + 1]);
            float o = Float.intBitsToFloat(vertexData[l * 8 + 2]);
            f = Math.min(f, m);
            g = Math.min(g, n);
            h = Math.min(h, o);
            i = Math.max(i, m);
            j = Math.max(j, n);
            k = Math.max(k, o);
        }

        if (box != null) {
            box[Direction.WEST.getId()] = f;
            box[Direction.EAST.getId()] = i;
            box[Direction.DOWN.getId()] = g;
            box[Direction.UP.getId()] = j;
            box[Direction.NORTH.getId()] = h;
            box[Direction.SOUTH.getId()] = k;
            int l = DIRECTIONS.length;
            box[Direction.WEST.getId() + l] = 1.0F - f;
            box[Direction.EAST.getId() + l] = 1.0F - i;
            box[Direction.DOWN.getId() + l] = 1.0F - g;
            box[Direction.UP.getId() + l] = 1.0F - j;
            box[Direction.NORTH.getId() + l] = 1.0F - h;
            box[Direction.SOUTH.getId() + l] = 1.0F - k;
        }

        float p = 1.0E-4F;
        float m = 0.9999F;
        switch (face) {
            case DOWN:
                flags.set(1, f >= 1.0E-4F || h >= 1.0E-4F || i <= 0.9999F || k <= 0.9999F);
                flags.set(0, g == j && (g < 1.0E-4F || state.isFullCube(world, pos)));
                break;
            case UP:
                flags.set(1, f >= 1.0E-4F || h >= 1.0E-4F || i <= 0.9999F || k <= 0.9999F);
                flags.set(0, g == j && (j > 0.9999F || state.isFullCube(world, pos)));
                break;
            case NORTH:
                flags.set(1, f >= 1.0E-4F || g >= 1.0E-4F || i <= 0.9999F || j <= 0.9999F);
                flags.set(0, h == k && (h < 1.0E-4F || state.isFullCube(world, pos)));
                break;
            case SOUTH:
                flags.set(1, f >= 1.0E-4F || g >= 1.0E-4F || i <= 0.9999F || j <= 0.9999F);
                flags.set(0, h == k && (k > 0.9999F || state.isFullCube(world, pos)));
                break;
            case WEST:
                flags.set(1, g >= 1.0E-4F || h >= 1.0E-4F || j <= 0.9999F || k <= 0.9999F);
                flags.set(0, f == i && (f < 1.0E-4F || state.isFullCube(world, pos)));
                break;
            case EAST:
                flags.set(1, g >= 1.0E-4F || h >= 1.0E-4F || j <= 0.9999F || k <= 0.9999F);
                flags.set(0, f == i && (i > 0.9999F || state.isFullCube(world, pos)));
        }
    }
}
