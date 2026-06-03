package net.deadlydiamond.legend_of_steve.events.client;

import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.deadlydiamond.legend_of_steve.networking.c2s.RequestSwitchBlockValuesS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class ZeldaClientPlayConnectionEvents {
    public static void register() {
        ClientPlayConnectionEvents.JOIN.register(ZeldaClientPlayConnectionEvents::onJoin);
        ClientPlayConnectionEvents.DISCONNECT.register(ZeldaClientPlayConnectionEvents::onLeave);
    }

    private static void onJoin(ClientPlayNetworkHandler clientPlayNetworkHandler, PacketSender packetSender, MinecraftClient minecraftClient) {
        if (minecraftClient.player != null) {
            RequestSwitchBlockValuesS2CPacket.send(minecraftClient.player);
        }
    }

    private static void onLeave(ClientPlayNetworkHandler clientPlayNetworkHandler, MinecraftClient minecraftClient) {
        SwitchBlockManager.SYNCED_SWITCH_GROUPS.clear();
    }
}
