package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.client.screens.DungeonTableScreen;
import net.deadlydiamond.legend_of_steve.init.ZeldaScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ZeldaScreens {
    public static void register() {
        HandledScreens.register(ZeldaScreenHandlers.DUNGEON_TABLE, DungeonTableScreen::new);
    }
}
