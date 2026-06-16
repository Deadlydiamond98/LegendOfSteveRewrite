package net.deadlydiamond.legend_of_steve.networking.c2s;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.deadlydiamond.legend_of_steve.networking.s2c.switches.SyncSwitchBlocksS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class RequestSwitchBlockValuesC2SPacket {
    public static final Identifier ID = LegendOfSteve.id("request_switch_block_sync");

    public static void send(PlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(player.getId());
        ClientPlayNetworking.send(ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            server.execute(() -> SyncSwitchBlocksS2CPacket.send(player, SwitchBlockManager.getManager(server.getOverworld()).getAll()));
        }
    }
}
