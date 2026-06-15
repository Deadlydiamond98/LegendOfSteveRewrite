package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.fluids.EnchantedSpringWater;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ZeldaFluids {

    public static final FlowableFluid ENCHANTED_SPRING_WATER = register("enchanted_spring_water", new EnchantedSpringWater.Still());
    public static final FlowableFluid FLOWING_ENCHANTED_SPRING_WATER = register("flowing_enchanted_spring_water", new EnchantedSpringWater.Flowing());

    public static FlowableFluid register(String id, FlowableFluid fluid) {
        return Registry.register(Registries.FLUID, LegendOfSteve.id(id), fluid);
    }

    public static void register() {}
}
