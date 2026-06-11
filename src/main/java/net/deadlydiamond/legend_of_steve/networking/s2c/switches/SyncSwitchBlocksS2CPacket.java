package net.deadlydiamond.legend_of_steve.networking.s2c.switches;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.switches.SwitchBlockAtlas;
import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.Map;

public class SyncSwitchBlocksS2CPacket {

    public static final Identifier ID = LegendOfSteve.id("sync_crystal_switches");

    public static void send(ServerWorld serverWorld, String key, Boolean val) {
        send(serverWorld, Map.of(key, val));
    }

    public static void send(ServerWorld serverWorld, Map<String, Boolean> values) {
        serverWorld.getServer().getWorlds().forEach(
                serverWorld1 -> serverWorld1.getPlayers().forEach(player -> send(player, values))
        );
    }

    public static void send(ServerPlayerEntity player, Map<String, Boolean> values) {
        SwitchBlockManager.SYNCED_SWITCH_GROUPS.putAll(values);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeMap(values, PacketByteBuf::writeString, PacketByteBuf::writeBoolean);
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            Map<String, Boolean> values = buf.readMap(PacketByteBuf::readString, PacketByteBuf::readBoolean);
            client.execute(() -> SwitchBlockAtlas.updateSprites(values));
        }
    }
}
