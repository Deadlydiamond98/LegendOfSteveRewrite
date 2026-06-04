package net.deadlydiamond.legend_of_steve.events.common;

import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public class ZeldaServerWorldEvents {
    public static void register() {
        ServerWorldEvents.UNLOAD.register(ZeldaServerWorldEvents::onUnload);
    }

    private static void onUnload(MinecraftServer server, ServerWorld serverWorld) {
        SwitchBlockManager.reset();
    }
}
