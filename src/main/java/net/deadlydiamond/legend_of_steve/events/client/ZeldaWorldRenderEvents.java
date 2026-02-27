package net.deadlydiamond.legend_of_steve.events.client;

import net.deadlydiamond.legend_of_steve.client.rendering.block.glowing.GlowingBlockRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;


public class ZeldaWorldRenderEvents {

    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            // This is Used to render glowing block entities, in a way that is much more performant
            GlowingBlockRenderer.renderAllGlowing(context);
        });
    }
}
