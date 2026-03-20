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

    // TODO: This Class could perhaps use some better organization

    // TEXTURES ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Identifier IRIDESCENT_GRADIENT_TEXTURE = LegendOfSteve.id("textures/misc/iridescence_gradient.png");

    public static final Identifier IRIDESCENT_ATLAS_TEXTURE = LegendOfSteve.id("textures/atlas/iridescent.png");
    public static final Identifier NORMAL_MAPS_ATLAS_TEXTURE = LegendOfSteve.id("textures/atlas/normal_maps.png");

    // SHADER PROGRAMS /////////////////////////////////////////////////////////////////////////////////////////////////
    private static final RenderPhase.ShaderProgram BOMB_FUSE_PROGRAM = new ShaderProgram(() -> ZeldaShaders.bombFuseShader);
    private static final RenderPhase.ShaderProgram IRIDESCENCE_PROGRAM = new ShaderProgram(() -> ZeldaShaders.iridescenceShader);
    private static final RenderPhase.ShaderProgram ENTITY_IRIDESCENCE_PROGRAM = new ShaderProgram(() -> ZeldaShaders.entityIridescenceShader);
    private static final RenderPhase.ShaderProgram ITEM_IRIDESCENCE_PROGRAM = new ShaderProgram(() -> ZeldaShaders.itemIridescenceShader);
    private static final RenderPhase.ShaderProgram FULLBRIGHT_ENTITY_PROGRAM = new ShaderProgram(() -> ZeldaShaders.fullbrightShader);
    private static final RenderPhase.ShaderProgram BLOOM_PROGRAM = new ShaderProgram(() -> ZeldaShaders.bloomShader);

    // SHADER TARGETS //////////////////////////////////////////////////////////////////////////////////////////////////
    private static final RenderPhase.Target BLOOM_TARGET = new Target("legend_of_steve$bloom_glowing", () -> {
        Framebuffer target = PostProcessingRegistry.getRenderTargetFor(ZeldaShaders.BLOOM_GLOWING_SHADER_ID);
        if (target != null) {
            target.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
            target.beginWrite(false);
        }
    }, () -> MinecraftClient.getInstance().getFramebuffer().beginWrite(false));

    public ZeldaRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }


    public static final Function<Identifier, RenderLayer> FULLBRIGHT_ENTITY = Util.memoize(texture -> {
        MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder()
                .program(FULLBRIGHT_ENTITY_PROGRAM)
                .texture(new Texture(texture, false, false))
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .cull(DISABLE_CULLING)
                .build(true);
        return of(
                "legend_of_steve$fullbright",
                VertexFormats.POSITION_COLOR_TEXTURE,
                VertexFormat.DrawMode.QUADS,
                256,
                false,
                true,
                multiPhaseParameters
        );
    });

    public static final RenderLayer BLOOM_GLOW = of(
            "legend_of_steve$bloom_glow",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            131072,
            true,
            false,
            MultiPhaseParameters.builder()
                    .program(BLOOM_PROGRAM)
                    .lightmap(ENABLE_LIGHTMAP)
                    .texture(RenderPhase.MIPMAP_BLOCK_ATLAS_TEXTURE)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .depthTest(LEQUAL_DEPTH_TEST)
                    .target(BLOOM_TARGET)
                    .build(false)
    );

    public static final Function<Identifier, RenderLayer> ENTITY_BLOOM_GLOW = Util.memoize(texture -> {
        MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder()
                .program(RenderPhase.POSITION_COLOR_TEXTURE_PROGRAM)
                .texture(new Texture(texture, false, false))
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .cull(DISABLE_CULLING)
                .depthTest(LEQUAL_DEPTH_TEST)
                .target(BLOOM_TARGET)
                .build(false);
        return of(
                "legend_of_steve$entity_bloom_glow",
                VertexFormats.POSITION_COLOR_TEXTURE,
                VertexFormat.DrawMode.QUADS,
                256,
                true,
                true,
                multiPhaseParameters
        );
    });

    private static final Function<Identifier, RenderLayer> BOMB_FUSE = Util.memoize(texture -> of(
            "legend_of_steve$bomb_fuse",
            VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            256,
            true,
            false,
            MultiPhaseParameters.builder()
                    .program(BOMB_FUSE_PROGRAM)
                    .texture(new Texture(texture, false, false))
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .depthTest(LEQUAL_DEPTH_TEST)
                    .lightmap(ENABLE_LIGHTMAP)
                    .overlay(ENABLE_OVERLAY_COLOR)
                    .build(true))
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

    public static final RenderLayer IRIDESCENCE = of(
            "legend_of_steve$iridescence",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            1 << 12,
            true,
            false,
            RenderLayer.MultiPhaseParameters.builder()
                    .lightmap(ENABLE_LIGHTMAP)
                    .program(IRIDESCENCE_PROGRAM).texture(
                            RenderPhase.Textures.create()
                                    .add(IRIDESCENT_ATLAS_TEXTURE, false, true)
                                    .add(NORMAL_MAPS_ATLAS_TEXTURE, false, true)
                                    .add(IRIDESCENT_GRADIENT_TEXTURE, false, false)
                                    .add(IRIDESCENT_GRADIENT_TEXTURE, false, false)
                                    .build()
                    ).build(true)
    );

    public static final BiFunction<Identifier, Identifier, RenderLayer> ENTITY_IRIDESCENCE = Util.memoize(((texture, normal) ->
            of("legend_of_steve$entity_iridescence",
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    VertexFormat.DrawMode.QUADS,
                    256,
                    true, false,
                    MultiPhaseParameters.builder()
                            .program(ENTITY_IRIDESCENCE_PROGRAM)
                            .texture(Textures.create()
                                    .add(texture, false, false)
                                    .add(IRIDESCENT_GRADIENT_TEXTURE, false, false) // overlay
                                    .add(IRIDESCENT_GRADIENT_TEXTURE, false, false) // light
                                    .add(normal, false, false)
                                    .add(IRIDESCENT_GRADIENT_TEXTURE, false, false)
                                    .build())
                            .transparency(NO_TRANSPARENCY)
                            .cull(DISABLE_CULLING)
                            .lightmap(ENABLE_LIGHTMAP)
                            .overlay(ENABLE_OVERLAY_COLOR)
                            .build(false))
    ));

    public static final RenderLayer GUI_ITEM_IRIDESCENCE = of("legend_of_steve$item_gui_iridescence",
            VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            256,
            true, false,
            MultiPhaseParameters.builder()
                    .program(ITEM_IRIDESCENCE_PROGRAM)
                    .texture(Textures.create()
                            .add(IRIDESCENT_ATLAS_TEXTURE, false, false)
                            .add(IRIDESCENT_GRADIENT_TEXTURE, false, false) // overlay
                            .add(IRIDESCENT_GRADIENT_TEXTURE, false, false) // light
                            .add(NORMAL_MAPS_ATLAS_TEXTURE, false, false)
                            .add(IRIDESCENT_GRADIENT_TEXTURE, false, false)
                            .build())
                    .transparency(NO_TRANSPARENCY)
                    .cull(DISABLE_CULLING)
                    .lightmap(ENABLE_LIGHTMAP)
                    .overlay(ENABLE_OVERLAY_COLOR)
                    .build(false));


    public static final RenderLayer ENTITY_IRIDESCENCE_TEXTURED = ENTITY_IRIDESCENCE.apply(
            IRIDESCENT_ATLAS_TEXTURE,
            NORMAL_MAPS_ATLAS_TEXTURE
    );

    private static final RenderLayer CHARGED_GLINT = CUSTOM_GLINT.apply(LegendOfSteve.id("textures/misc/charge_glint.png"), false);

    public static RenderLayer getChargedGlint() {
        return CHARGED_GLINT;
    }

    public static RenderLayer getBombFuse(Identifier texture) {
        return BOMB_FUSE.apply(texture);
    }

    public static RenderLayer getEntityUnlit(Identifier texture) {
        return FULLBRIGHT_ENTITY.apply(texture);
    }

    public static RenderLayer getGlowing(Identifier texture) {
        PostProcessingRegistry.renderEffectForNextTick(ZeldaShaders.BLOOM_GLOWING_SHADER_ID);
        return ENTITY_BLOOM_GLOW.apply(texture);
    }
}
