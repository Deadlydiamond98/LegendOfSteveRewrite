package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond98.koalalib.common.advancement.CustomAdvancement;
import net.minecraft.advancement.criterion.Criteria;

public class ZeldaAdvancements {
    public static final CustomAdvancement CUT_LOOT_GRASS = register("cut_loot_grass");
    public static final CustomAdvancement RELAX_IN_SPRING_WATER = register("relax_in_spring_water");
    public static final CustomAdvancement TRANSMUTE_ITEM = register("transmute_item");
    public static final CustomAdvancement STUN_ENTITY_WITH_NUT = register("stun_entity_with_nut");
    public static final CustomAdvancement MINOR_CONCUSSION = register("hit_block");
    public static final CustomAdvancement WATER_WALKING = register("water_walking");
    public static final CustomAdvancement TRIGGER_CRYSTAL_SWITCH = register("trigger_crystal_switch");
    public static final CustomAdvancement KAIZO_TRAP = register("kaizo_trap");
    public static final CustomAdvancement LOCKE_AND_KEY = register("locke_and_key");

    public static CustomAdvancement register(String name) {
        return Criteria.register(new CustomAdvancement(LegendOfSteve.id(name)));
    }

    public static void register() {}
}
