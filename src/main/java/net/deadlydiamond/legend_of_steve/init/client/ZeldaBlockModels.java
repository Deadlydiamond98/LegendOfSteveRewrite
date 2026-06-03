package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.client.models.block.SwitchBlockUnbakedModel;
import net.deadlydiamond.legend_of_steve.common.blocksets.SwitchBlockset;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.util.Identifier;

public class ZeldaBlockModels {
    public static void register() {
        ModelLoadingPlugin.register(context -> {
            switchBlocks(context);
        });
    }

    private static void switchBlocks(ModelLoadingPlugin.Context context) {
        for (Identifier id : SwitchBlockset.MODEL_LOCATIONS) {
            context.addModels(id.withSuffixedPath("_on"));
            context.addModels(id.withSuffixedPath("_off"));
        }

        context.resolveModel().register(context1 -> {
            Identifier id = context1.id();

            if (SwitchBlockset.MODEL_LOCATIONS.contains(id)) {
                return new SwitchBlockUnbakedModel(id);
            }

            return null;
        });
    }
}
