package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.screen_handlers.DungeonTableScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ZeldaScreenHandlers {
    public static final ScreenHandlerType<DungeonTableScreenHandler> DUNGEON_TABLE = register("dungeon_table", DungeonTableScreenHandler::new);


    // REGISTRATION METHODS ////////////////////////////////////////////////////////////////////////////////////////////

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, LegendOfSteve.id(id), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void register() {}
}
