package net.deadlydiamond.legend_of_steve.util.datagen.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Optional;

public class ZeldaBlockModelDatagenUtil {

    public static final Model LOOT_POT = new Model(Optional.of(LegendOfSteve.id("block/template_loot_pot")),
            Optional.empty(), TextureKey.ALL, TextureKey.PARTICLE
    );

    public static void registerPot(BlockStateModelGenerator blockStateModelGenerator, Block block, Block particle) {
        TextureMap textureMap = TextureMap.all(getPrefixedId(block, "loot_pot")).put(TextureKey.PARTICLE, TextureMap.all(particle).getTexture(TextureKey.PARTICLE));

        Identifier pot = LOOT_POT.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, pot));
    }

    public static Identifier getPrefixedId(Block block, String prefix) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return identifier.withPrefixedPath("block/" + prefix + "/");
    }
}
