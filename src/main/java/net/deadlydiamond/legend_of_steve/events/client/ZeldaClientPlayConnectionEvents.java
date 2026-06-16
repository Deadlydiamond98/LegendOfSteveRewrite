package net.deadlydiamond.legend_of_steve.events.client;

import net.deadlydiamond.legend_of_steve.client.switches.SwitchBlockAtlas;
import net.deadlydiamond.legend_of_steve.networking.c2s.RequestSwitchBlockValuesC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class ZeldaClientPlayConnectionEvents {
    public static void register() {
        ClientPlayConnectionEvents.INIT.register(ZeldaClientPlayConnectionEvents::onInit);
        ClientPlayConnectionEvents.JOIN.register(ZeldaClientPlayConnectionEvents::onJoin);
        ClientPlayConnectionEvents.DISCONNECT.register(ZeldaClientPlayConnectionEvents::onLeave);
    }

    private static void onInit(ClientPlayNetworkHandler clientPlayNetworkHandler, MinecraftClient client) {
    }

    private static void onJoin(ClientPlayNetworkHandler clientPlayNetworkHandler, PacketSender packetSender, MinecraftClient client) {
        if (client.player != null) {
            RequestSwitchBlockValuesC2SPacket.send(client.player);
        }
    }

    private static void onLeave(ClientPlayNetworkHandler clientPlayNetworkHandler, MinecraftClient minecraftClient) {
        SwitchBlockAtlas.reset();
    }
}
