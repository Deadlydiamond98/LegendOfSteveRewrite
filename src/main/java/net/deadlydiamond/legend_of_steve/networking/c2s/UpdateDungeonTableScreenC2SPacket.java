package net.deadlydiamond.legend_of_steve.networking.c2s;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class UpdateDungeonTableScreenC2SPacket {
    public static final Identifier ID = LegendOfSteve.id("update_dungeon_table_c2s");

    public static void send(String switchID) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(switchID);
        ClientPlayNetworking.send(ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            String switchId = buf.readString();

            server.execute(() -> {
//                if (player.currentScreenHandler instanceof DungeonTableScreenHandlerTemp screenHandler) {
//                    screenHandler.setSwitchId(switchId);
//                }
            });
        }
    }
}
