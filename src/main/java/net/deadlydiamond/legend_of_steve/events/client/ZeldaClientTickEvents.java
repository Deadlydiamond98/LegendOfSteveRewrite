package net.deadlydiamond.legend_of_steve.events.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class ZeldaClientTickEvents {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ZeldaClientTickEvents::tick);
    }

    private static void tick(MinecraftClient minecraftClient) {
    }
}
