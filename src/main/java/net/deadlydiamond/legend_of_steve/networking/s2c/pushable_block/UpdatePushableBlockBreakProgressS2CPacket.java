package net.deadlydiamond.legend_of_steve.networking.s2c.pushable_block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.block.PushableBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class UpdatePushableBlockBreakProgressS2CPacket {
    public static final Identifier ID = LegendOfSteve.id("update_pushable_block_breaking");

    public static void send(ServerPlayerEntity player, float progress, PushableBlockEntity entity) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeFloat(progress);
        buf.writeInt(entity.getId());
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            float progress = buf.readFloat();
            int entityID = buf.readInt();
            client.execute(() -> {
                if (client.world != null && client.world.getEntityById(entityID) instanceof PushableBlockEntity pushableBlock) {
                    pushableBlock.setBlockBreakProgress(progress);
                }
            });
        }
    }
}
