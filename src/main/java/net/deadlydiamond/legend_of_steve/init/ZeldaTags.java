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
    public static final TagKey<Item> LOOT_POTS = item("loot_pots");
    public static final TagKey<Item> QUIVERS = item("quivers");
    public static final TagKey<Item> STRANGE_DIRT = item("strange_dirt");
    public static final TagKey<Item> STRANGE_BLUE_DIRT = item("strange_blue_dirt");

    public static final TagKey<Item> COPPER_KEYS = item("copper_keys");
    public static final TagKey<Item> IRON_KEYS = item("iron_keys");
    public static final TagKey<Item> GOLD_KEYS = item("gold_keys");
    public static final TagKey<Item> BOSS_KEYS = item("boss_keys");
    public static final TagKey<Item> KEYS = item("keys");

    public static final TagKey<Item> LOCKS = item("locks");

    public static final TagKey<Item> SWITCH_BLOCKS_ITEM = item("switch_blocks");

    // BLOCKS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final TagKey<Block> BOMB_BREAKABLE = block("bomb_breakable");
    public static final TagKey<Block> BOMB_FLOWER_PLANTABLE = block("bomb_flower_plantable");
    public static final TagKey<Block> CHISELED_PLANKS_BLOCK = block("chiseled_planks");
    public static final TagKey<Block> LOCKABLE = block("lockable");

    public static final TagKey<Block> RED_SWITCH_BLOCKS = block("red_switch_blocks");
    public static final TagKey<Block> BLUE_SWITCH_BLOCKS = block("blue_switch_blocks");
    public static final TagKey<Block> SWITCH_BLOCKS = block("switch_blocks");

    // ENTITIES ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final TagKey<EntityType<?>> IMMUNE_TO_STUNNING = entity("immune_to_stunning");

    // FLUIDS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final TagKey<Fluid> ENCHANTED_SPRING_WATER = fluid("enchanted_spring_water");

    // BIOMES //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final TagKey<Biome> GENERATES_DEKU_TREES = biome("has_deku_trees");
    public static final TagKey<Biome> SPAWNS_MORE_BLUE_TEKTITES = biome("spawns_more_blue_tektites");
    public static final TagKey<Biome> SPAWNS_MORE_RED_TEKTITES = biome("spawns_more_red_tektites");
    public static final TagKey<Biome> SPAWNS_ARURODAS = biome("spawns_arurodas");

    // Registry ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static TagKey<Item> item(String name) {
        return getTag(RegistryKeys.ITEM, name);
    }

    public static TagKey<Block> block(String name) {
        return getTag(RegistryKeys.BLOCK, name);
    }

    public static TagKey<Fluid> fluid(String name) {
        return getTag(RegistryKeys.FLUID, name);
    }

    public static TagKey<Biome> biome(String name) {
        return getTag(RegistryKeys.BIOME, name);
    }

    public static TagKey<EntityType<?>> entity(String name) {
        return getTag(RegistryKeys.ENTITY_TYPE, name);
    }

    private static <T> TagKey<T> getTag(RegistryKey<? extends Registry<T>> registry, String name) {
        return TagKey.of(registry, LegendOfSteve.id(name));
    }
}
