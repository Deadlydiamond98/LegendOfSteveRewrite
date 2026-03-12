package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ZeldaParticles {
    public static final DefaultParticleType SPARK_PARTICLE = register("spark", false);

    private static DefaultParticleType register(String name, boolean alwaysSpawn) {
        return Registry.register(Registries.PARTICLE_TYPE, LegendOfSteve.id(name), FabricParticleTypes.simple(alwaysSpawn));
    }

    public static void register() {}
}
