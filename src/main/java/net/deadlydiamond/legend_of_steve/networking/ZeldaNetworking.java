package net.deadlydiamond.legend_of_steve.networking;

import net.deadlydiamond.legend_of_steve.networking.c2s.GuiScrollItemActionC2SPacket;
import net.deadlydiamond.legend_of_steve.networking.c2s.HudScrollItemActionC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ZeldaNetworking {

    public static void registerC2SReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(GuiScrollItemActionC2SPacket.ID, GuiScrollItemActionC2SPacket.Handler::receive);
        ServerPlayNetworking.registerGlobalReceiver(HudScrollItemActionC2SPacket.ID, HudScrollItemActionC2SPacket.Handler::receive);
    }

    public static class Client {
        public static void registerS2CReceivers() {}
    }
}
