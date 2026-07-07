package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.client.models.block.LockedBlockBakedModel;
import net.deadlydiamond.legend_of_steve.client.models.block.connected.ConnectedPillarUnbakedModel;
import net.deadlydiamond.legend_of_steve.client.models.block.SwitchBlockUnbakedModel;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.connected.ConnectedPillarBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock.ILockedBlock;
import net.deadlydiamond.legend_of_steve.common.blocksets.SwitchBlockset;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.block.Block;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ZeldaBlockModels {

    private static void connectedPillars(ModelLoadingPlugin.Context context) {
        context.resolveModel().register(context1 -> {
            Identifier id = context1.id();
            if (ConnectedPillarBlock.isPresent(id)) {
                return new ConnectedPillarUnbakedModel(id);
            }
            return null;
        });
    }

    private static void switchBlocks(ModelLoadingPlugin.Context context) {
        for (Identifier id : SwitchBlockset.MODEL_LOCATIONS) {
            context.addModels(id.withSuffixedPath("_base"));
        }

        context.resolveModel().register(context1 -> {
            Identifier id = context1.id();

            if (SwitchBlockset.MODEL_LOCATIONS.contains(id)) {
                return new SwitchBlockUnbakedModel(id);
            }

            return null;
        });
    }

    private static void lockedModels(ModelLoadingPlugin.Context context) {
        context.modifyModelAfterBake().register((model, context1) -> {
            Identifier identifier = context1.id();
            if (identifier instanceof ModelIdentifier modelId) {
                Block block = Registries.BLOCK.get(Identifier.of(modelId.getNamespace(), modelId.getPath()));
                if (block instanceof ILockedBlock lockedBlock && lockedBlock.wrappedBlockModel()) {
                    return new LockedBlockBakedModel(model);
                }
            }
            return model;
        });
    }

    public static void register() {
        ModelLoadingPlugin.register(context -> {
            switchBlocks(context);
            connectedPillars(context);
            lockedModels(context);
        });
    }
}
