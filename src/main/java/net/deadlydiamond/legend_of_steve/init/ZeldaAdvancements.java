package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond98.koalalib.common.advancement.CustomAdvancement;
import net.minecraft.advancement.criterion.Criteria;

public class ZeldaAdvancements {
    public static final CustomAdvancement RELAX_IN_SPRING_WATER = register("relax_in_spring_water");

    public static CustomAdvancement register(String name) {
        return Criteria.register(new CustomAdvancement(LegendOfSteve.id(name)));
    }

    public static void register() {}
}
