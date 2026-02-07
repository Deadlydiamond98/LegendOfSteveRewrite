package net.deadlydiamond.legend_of_steve;

import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderers;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaShaders;
import net.fabricmc.api.ClientModInitializer;

public class LegendOfSteveClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ZeldaShaders.register();
        ZeldaRenderers.register();
    }
}
