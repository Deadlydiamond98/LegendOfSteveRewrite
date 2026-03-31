package net.deadlydiamond.legend_of_steve.networking.s2c.pushable_block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.mixin.client.ClientPlayerInteractionManagerAccessor;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AddBlockBreakCooldownS2CPacket {
    public static final Identifier ID = LegendOfSteve.id("add_player_block_break_cooldown");

    public static void send(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, ID, PacketByteBufs.create());
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            ((ClientPlayerInteractionManagerAccessor) client.interactionManager).legend_of_steve$setBlockBreakingCooldown(5);
        }
    }
}
