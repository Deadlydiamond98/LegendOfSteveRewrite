package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.block.SwitchBlockUnbakedModel;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.util.Identifier;

import java.util.Set;

public class ZeldaBlockModels {
    private static final Set<Identifier> SWITCH_BLOCK_MODEL_LOCATIONS = Set.of(
            LegendOfSteve.id("block/red_switch_block")
    );

    public static void register() {
        ModelLoadingPlugin.register(context -> {
            context.resolveModel().register(context1 -> {
                Identifier id = context1.id();

                if (SWITCH_BLOCK_MODEL_LOCATIONS.contains(id)) {
                    return new SwitchBlockUnbakedModel();
                }

                return null;
            });
        });
    }
}
