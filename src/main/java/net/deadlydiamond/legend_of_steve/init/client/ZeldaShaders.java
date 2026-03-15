package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond98.koalalib.client.PostProcessingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class ZeldaShaders {

    // POST
    public static final Identifier BLOOM_GLOWING_SHADER_ID = LegendOfSteve.id(post("bloom_glow"));

    // CORE
    public static ShaderProgram bombFuseShader;
    public static ShaderProgram iridescenceShader;
    public static ShaderProgram entityIridescenceShader;
    public static ShaderProgram itemIridescenceShader;
    public static ShaderProgram fullbrightShader;
    public static ShaderProgram bloomShader;

    public static void register() {
        // POST
        PostProcessingRegistry.registerEffect(BLOOM_GLOWING_SHADER_ID);

        // CORE
        CoreShaderRegistrationCallback.EVENT.register(context -> {
            context.register(
                    LegendOfSteve.id("rendertype_bomb_fuse"),
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    program -> bombFuseShader = program
            );

            context.register(
                    LegendOfSteve.id("rendertype_iridescence"),
                    VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
                    program -> iridescenceShader = program
            );

            context.register(
                    LegendOfSteve.id("rendertype_entity_iridescence"),
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    program -> entityIridescenceShader = program
            );

            context.register(
                    LegendOfSteve.id("rendertype_item_iridescence"),
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    program -> itemIridescenceShader = program
            );

            context.register(
                    LegendOfSteve.id("rendertype_bloom"),
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    program -> bloomShader = program
            );

            context.register(
                    LegendOfSteve.id("rendertype_fullbright"),
                    VertexFormats.POSITION_COLOR_TEXTURE,
                    program -> fullbrightShader = program
            );
        });
    }

    private static String post(String name) {
        return "shaders/post/" + name + ".json";
    }
}
