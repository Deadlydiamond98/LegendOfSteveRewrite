package net.deadlydiamond.legend_of_steve.common.blocks.secret;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond98.koalalib.common.blocks.advancement.AdvancementNeededBlock;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SecretBlock extends Block implements ISecretBlock {
    private static final List<String> MOD_IDS = new ArrayList<>();
    private static final List<Class<?>> VALID_CLASSES = List.of(
            Block.class,
            AdvancementNeededBlock.class
    );

    // TODO: I'm not 100% sure if Auto Creating Secret Blocks is a good idea, might change this...
    // TODO: If I decide to go this route, I need to make the blocks reuse the same model & stuff

    public SecretBlock(Settings settings) {
        super(settings);
    }

    public static void createSecretBlocks() {
        RegistryEntryAddedCallback.event(Registries.BLOCK).register((i, identifier, block) -> {
            if (canRegister(identifier, block)) {
                ZeldaBlocks.register(
                        getSecretBlockID(identifier),
                        new SecretBlock(FabricBlockSettings.copyOf(block))
                );
                displayRegistrationLog(identifier.getNamespace());
            }
        });
    }

    private static String getSecretBlockID(Identifier parentID) {
        String name = "secret_" + parentID.getPath();
        if (!parentID.getNamespace().equals(LegendOfSteve.MOD_ID)) {
            name += "_" + parentID.getNamespace();
        }
        return name;
    }

    private static boolean canRegister(Identifier identifier, Block block) {
        return !identifier.getNamespace().equals(LegendOfSteve.MOD_ID) &&
                !identifier.getNamespace().equals("minecraft") &&
                identifier.getPath().contains("cracked") &&
                VALID_CLASSES.contains(block.getClass());
    }

    private static void displayRegistrationLog(String modId) {
        if (!MOD_IDS.contains(modId)) {
            LegendOfSteve.LOGGER.info("Registering Secret Brick Variants for {}", modId);
            MOD_IDS.add(modId);
        }
    }
}
