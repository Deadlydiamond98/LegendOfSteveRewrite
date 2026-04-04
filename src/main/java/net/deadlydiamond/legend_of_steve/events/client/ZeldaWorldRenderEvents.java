package net.deadlydiamond.legend_of_steve.events.client;

import net.deadlydiamond.legend_of_steve.client.rendering.block.SwordPedestalBlockEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class ZeldaWorldRenderEvents {
    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            SwordPedestalBlockEntityRenderer.renderSword(context.matrixStack(), context.consumers());
        });
    }
}