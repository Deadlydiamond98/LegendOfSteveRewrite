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

public class GuiScrollItemActionC2SPacket {
    public static final Identifier ID = LegendOfSteve.id("gui_scroll_item_action");

    public static void send(int syncId, int revision, int slot, double scroll) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeByte(syncId);
        buf.writeVarInt(revision);
        buf.writeShort(slot);
        buf.writeDouble(scroll);
        ClientPlayNetworking.send(ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            int syncID = buf.readByte();
            int revision = buf.readVarInt();
            int slot = buf.readShort();
            double scroll = buf.readDouble();

            // Almost all of this was copied over from Server Player Network Handler

            server.execute(() -> {
                if (player.currentScreenHandler.syncId == syncID) {
                    if (player.isSpectator()) {
                        player.currentScreenHandler.syncState();
                    } else if (!player.currentScreenHandler.canUse(player)) {
                        LegendOfSteve.LOGGER.debug("Player {} interacted with invalid menu {}", player, player.currentScreenHandler);
                    } else {
                        int i = slot;

                        if (!player.currentScreenHandler.isValid(i)) {
                            LegendOfSteve.LOGGER.debug("Player {} scrolled invalid slot index: {}, available slots: {}", player.getName(), i, player.currentScreenHandler.slots.size());
                        } else {
                            boolean bl = revision != player.currentScreenHandler.getRevision();
                            player.currentScreenHandler.disableSyncing();

                            ItemStack stack = player.currentScreenHandler.getSlot(i).getStack();

                            if (stack.getItem() instanceof IScrollAction scrollAction) {
                                scrollAction.onItemScrolled(stack, slot, player, scroll);
                            }


                            player.currentScreenHandler.enableSyncing();
                            if (bl) {
                                player.currentScreenHandler.updateToClient();
                            } else {
                                player.currentScreenHandler.sendContentUpdates();
                            }
                        }
                    }
                }
            });
        }
    }
}
