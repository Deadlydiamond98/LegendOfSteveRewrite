package net.deadlydiamond.legend_of_steve.events.client;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.items.IScrollAction;
import net.deadlydiamond.legend_of_steve.networking.c2s.ScrollItemActionC2SPacket;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ZeldaScreenEvents {
    public static void register() {
        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            ScreenMouseEvents.beforeMouseScroll(screen).register((screen1, mouseX, mouseY, horizontalAmount, verticalAmount) -> {
                if (screen instanceof HandledScreen<?> handledScreen) {
                    scrollItem(handledScreen, mouseX, mouseY, verticalAmount);
                }
            });
        });
    }

    private static void scrollItem(HandledScreen<?> handledScreen, double mouseX, double mouseY, double verticalAmount) {
        MinecraftClient client = MinecraftClient.getInstance();
        Slot slot = handledScreen.getSlotAt(mouseX, mouseY);
        ScreenHandler handler = handledScreen.getScreenHandler();
        int syncId = handler.syncId;

        ScreenHandler screenHandler = client.player.currentScreenHandler;
        if (syncId == screenHandler.syncId) {
            if (slot != null) {
                if (slot.getStack().getItem() instanceof IScrollAction scrollAction) {

                    scrollAction.onItemScrolled(slot.getStack(), slot.id, client.player, verticalAmount);

                    if (handledScreen instanceof CreativeInventoryScreen) {
                        // Fun Fact, it took me longer than it realize that the Creative Inventory Cares about what the
                        // Client says rather than the server, and doesn't have proper slot ids
                        client.player.playerScreenHandler.sendContentUpdates();
                    } else {
                        ScrollItemActionC2SPacket.send(syncId, handler.getRevision(), slot.id, verticalAmount);
                    }
                }
            }
        }
    }
}
