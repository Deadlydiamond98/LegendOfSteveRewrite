package net.deadlydiamond.legend_of_steve.events.client;

import net.deadlydiamond.legend_of_steve.client.rendering.block.baked.BakedBlockEntityRenderer;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaShaders;
import net.deadlydiamond98.koalalib.client.PostProcessingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.InvalidateRenderStateCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;


public class ZeldaWorldRenderEvents {

    public static void register() {
//        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
//            // This is Used to render baked block entities, so that they are much more performant
//            BakedBlockEntityRenderer.Manager.render(context);
//        });

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            PostProcessingRegistry.renderEffectForNextTick(ZeldaShaders.BLOOM_GLOWING_SHADER_ID);
            BakedBlockEntityRenderer.Manager.render(context);
        });

        InvalidateRenderStateCallback.EVENT.register(BakedBlockEntityRenderer.Manager::reset);
    }
}
