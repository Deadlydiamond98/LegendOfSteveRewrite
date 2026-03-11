package net.deadlydiamond.legend_of_steve.networking.c2s;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.items.IScrollAction;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class HudScrollItemActionC2SPacket {
    public static final Identifier ID = LegendOfSteve.id("hud_scroll_item_action");

    public static void send(int slot, double scroll) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeShort(slot);
        buf.writeDouble(scroll);
        ClientPlayNetworking.send(ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            int slot = buf.readShort();
            double scroll = buf.readDouble();

            server.execute(() -> {
                ItemStack bundleStack = player.getInventory().getStack(slot);
                if (bundleStack.getItem() instanceof IScrollAction scrollAction) {
                    scrollAction.onItemScrolledHotbar(bundleStack, slot, player, scroll);
                }
            });
        }
    }
}
