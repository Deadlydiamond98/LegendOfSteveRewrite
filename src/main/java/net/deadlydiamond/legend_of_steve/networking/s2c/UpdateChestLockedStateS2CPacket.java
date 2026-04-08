package net.deadlydiamond.legend_of_steve.networking.s2c;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IBlockEntityLocking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class UpdateChestLockedStateS2CPacket {
    public static final Identifier ID = LegendOfSteve.id("update_locked_state");

    public static void send(ServerPlayerEntity player, BlockPos pos, ItemStack item) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeItemStack(item);
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            BlockPos pos = buf.readBlockPos();
            ItemStack item = buf.readItemStack();
            client.execute(() -> {
                if (client.world != null && client.world.getBlockEntity(pos) instanceof IBlockEntityLocking locking) {
                    locking.legend_of_steve$setLockItem(item);
                }
            });
        }
    }
}
