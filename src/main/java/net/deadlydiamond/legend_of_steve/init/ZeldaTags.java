package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class ZeldaTags {

    // ITEMS ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final TagKey<Item> BOMB_BAGS = item("bomb_bags");
    public static final TagKey<Item> BOMBS = item("bombs");
    public static final TagKey<Item> CHISELED_PLANKS_ITEM = item("chiseled_planks");
    public static final TagKey<Item> HELD_OVER_HEAD = item("held_over_head");
    public static final TagKey<Item> IRIDESCENT_ITEM = item("iridescent");
    public static final TagKey<Item> QUIVERS = item("quivers");
    public static final TagKey<Item> LOOT_POTS = item("loot_pots");

    // BLOCKS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final TagKey<Block> BOMB_BREAKABLE = block("bomb_breakable");
    public static final TagKey<Block> BOMB_FLOWER_PLANTABLE = block("bomb_flower_plantable");
    public static final TagKey<Block> CHISELED_PLANKS_BLOCK = block("chiseled_planks");

    // ENTITIES ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final TagKey<EntityType<?>> IMMUNE_TO_STUNNING = entity("immune_to_stunning");

    // FLUIDS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final TagKey<Fluid> ENCHANTED_SPRING_WATER = fluid("enchanted_spring_water");

    // BIOMES //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final TagKey<Biome> GENERATES_DEKU_TREES = biome("has_deku_trees");

    // Registry ////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static TagKey<Item> item(String name) {
        return getTag(RegistryKeys.ITEM, name);
    }

    private static TagKey<Block> block(String name) {
        return getTag(RegistryKeys.BLOCK, name);
    }

    private static TagKey<Fluid> fluid(String name) {
        return getTag(RegistryKeys.FLUID, name);
    }

    private static TagKey<Biome> biome(String name) {
        return getTag(RegistryKeys.BIOME, name);
    }

    private static TagKey<EntityType<?>> entity(String name) {
        return getTag(RegistryKeys.ENTITY_TYPE, name);
    }

    private static <T> TagKey<T> getTag(RegistryKey<? extends Registry<T>> registry, String name) {
        return TagKey.of(registry, LegendOfSteve.id(name));
    }
}
