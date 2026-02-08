package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond98.koalalib.client.PostProcessingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ZeldaRenderLayers extends RenderLayer {

    // SHADER PROGRAMS /////////////////////////////////////////////////////////////////////////////////////////////////
    private static final RenderPhase.ShaderProgram BOMB_FUSE_PROGRAM = new ShaderProgram(() -> ZeldaShaders.bombFuseShader);

    // SHADER TARGETS //////////////////////////////////////////////////////////////////////////////////////////////////
    private static final RenderPhase.Target GLOWING_TARGET = new Target("legend_of_steve$rendertype_glowing", () -> {
        Framebuffer target = PostProcessingRegistry.getRenderTargetFor(ZeldaShaders.GLOWING_SHADER_ID);
        if (target != null) {
            target.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
            target.beginWrite(false);
        }
    }, () -> MinecraftClient.getInstance().getFramebuffer().beginWrite(false));

    public ZeldaRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    private static final Function<Identifier, RenderLayer> FANCY_GLOWING = Util.memoize(texture -> {
        MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder()
                .program(RenderPhase.POSITION_COLOR_TEXTURE_PROGRAM)
                .texture(new Texture(texture, false, false))
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .cull(DISABLE_CULLING)
                .depthTest(LEQUAL_DEPTH_TEST)
                .target(GLOWING_TARGET)
                .build(false);
        return of(
                "legend_of_steve$rendertype_glowing",
                VertexFormats.POSITION_COLOR_TEXTURE,
                VertexFormat.DrawMode.QUADS,
                256,
                true,
                false,
                multiPhaseParameters
        );
    });

    private static final Function<Identifier, RenderLayer> BOMB_FUSE = Util.memoize(
            texture -> {
                RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
                        .program(BOMB_FUSE_PROGRAM)
                        .texture(new RenderPhase.Texture(texture, false, false))
                        .transparency(TRANSLUCENT_TRANSPARENCY)
                        .depthTest(LEQUAL_DEPTH_TEST)
                        .lightmap(ENABLE_LIGHTMAP)
                        .overlay(ENABLE_OVERLAY_COLOR)
                        .build(true);
                return of(
                        "legend_of_steve$bomb_fuse",
                        VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                        VertexFormat.DrawMode.QUADS,
                        256,
                        true,
                        false,
                        multiPhaseParameters
                );
            }
    );

    private static final BiFunction<Identifier, Boolean, RenderLayer> CUSTOM_GLINT = (identifier, blur) -> of(
            "legend_of_steve$custom_glint",
            VertexFormats.POSITION_TEXTURE,
            VertexFormat.DrawMode.QUADS,
            256,
            MultiPhaseParameters.builder()
                    .program(GLINT_PROGRAM)
                    .texture(new Texture(identifier, blur, false))
                    .writeMaskState(COLOR_MASK)
                    .cull(DISABLE_CULLING)
                    .depthTest(EQUAL_DEPTH_TEST)
                    .transparency(GLINT_TRANSPARENCY)
                    .texturing(GLINT_TEXTURING)
                    .build(false)
    );

    private static final RenderLayer CHARGED_GLINT = CUSTOM_GLINT.apply(LegendOfSteve.id("textures/misc/charge_glint.png"), false);

    public static RenderLayer getChargedGlint() {
        return CHARGED_GLINT;
    }

    public static RenderLayer getBombFuse(Identifier texture) {
        return BOMB_FUSE.apply(texture);
    }

    public static RenderLayer getGlowing(Identifier texture) {
        PostProcessingRegistry.renderEffectForNextTick(ZeldaShaders.GLOWING_SHADER_ID);
        return FANCY_GLOWING.apply(texture);
    }
}
