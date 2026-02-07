package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond98.koalalib.client.PostProcessingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class ZeldaShaders {

    // POST
    public static final Identifier GLOWING_SHADER_ID = LegendOfSteve.id(post("rendertype_glowing"));

    // CORE
    public static ShaderProgram bombFuseShader;
    public static ShaderProgram glowingShader;

    public static void register() {
        // POST
        PostProcessingRegistry.registerEffect(GLOWING_SHADER_ID);

        // CORE
        CoreShaderRegistrationCallback.EVENT.register(context -> {
            context.register(
                    LegendOfSteve.id("rendertype_bomb_fuse"),
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    program -> bombFuseShader = program
            );
            context.register(
                    LegendOfSteve.id("rendertype_glowing"),
                    VertexFormats.POSITION_COLOR_TEXTURE,
                    program -> glowingShader = program
            );
        });
    }

    private static String post(String name) {
        return "shaders/post/" + name + ".json";
    }
}
