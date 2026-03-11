package net.deadlydiamond.legend_of_steve.networking;

import net.deadlydiamond.legend_of_steve.networking.c2s.ScrollItemActionC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ZeldaNetworking {

    public static void registerC2SReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(ScrollItemActionC2SPacket.ID, ScrollItemActionC2SPacket.Handler::receive);
    }

    public static class Client {
        public static void registerS2CReceivers() {}
    }
}
