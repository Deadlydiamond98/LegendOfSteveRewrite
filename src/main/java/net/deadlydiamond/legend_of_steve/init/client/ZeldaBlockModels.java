package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.models.block.SwitchBlockUnbakedModel;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ZeldaBlockModels {
    private static final List<Identifier> SWITCH_BLOCK_MODEL_LOCATIONS = new ArrayList<>();

    static {
        addSwitchBlock("block");
        addSwitchBlock("slab");
        addSwitchBlock("slab_top");
        addSwitchBlock("slab_full");
    }

    public static void addSwitchBlock(String suffix) {
        SWITCH_BLOCK_MODEL_LOCATIONS.add(LegendOfSteve.id("block/red_switch_" + suffix));
        SWITCH_BLOCK_MODEL_LOCATIONS.add(LegendOfSteve.id("block/blue_switch_" + suffix));
    }

    public static void register() {
        ModelLoadingPlugin.register(context -> {
            // Switch Blocks

            for (Identifier id : SWITCH_BLOCK_MODEL_LOCATIONS) {
                context.addModels(id.withSuffixedPath("_on"));
                context.addModels(id.withSuffixedPath("_off"));
            }

            context.resolveModel().register(context1 -> {
                Identifier id = context1.id();

                if (SWITCH_BLOCK_MODEL_LOCATIONS.contains(id)) {
                    return new SwitchBlockUnbakedModel(id);
                }

                return null;
            });
        });
    }
}
