package net.deadlydiamond.legend_of_steve.networking.s2c;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class UpdateEntityStunS2CPacket {
    public static final Identifier ID = LegendOfSteve.id("update_entity_stun");

    public static void send(ServerPlayerEntity player, int time, LivingEntity entity) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(time);
        buf.writeInt(entity.getId());
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            int time = buf.readVarInt();
            int entityID = buf.readInt();
            client.execute(() -> {
                if (client.world != null && client.world.getEntityById(entityID) instanceof IZeldaStunned stunned) {
                    stunned.legend_of_steve$setStunned(time);
                }
            });
        }
    }
}
