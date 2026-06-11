package net.deadlydiamond.legend_of_steve.client.switches;

import net.minecraft.client.MinecraftClient;

public class SwitchRenderManager {
    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    private static void reload() {
        CLIENT.worldRenderer.reload();
    }
}
