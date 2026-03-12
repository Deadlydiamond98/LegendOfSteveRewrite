package net.deadlydiamond.legend_of_steve.init;

import com.mojang.serialization.Codec;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.particles.SparkParticleEffect;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

public class ZeldaParticleTypes {
    // REGULAR PARTICLES ///////////////////////////////////////////////////////////////////////////////////////////////

    // COMPLEX PARTICLES ///////////////////////////////////////////////////////////////////////////////////////////////
    public static final ParticleType<SparkParticleEffect> SPARK = register(
            "spark", false, SparkParticleEffect.FACTORY, particle -> SparkParticleEffect.CODEC
    );

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    private static DefaultParticleType register(String name, boolean alwaysSpawn) {
        return Registry.register(Registries.PARTICLE_TYPE, LegendOfSteve.id(name), FabricParticleTypes.simple(alwaysSpawn));
    }

    private static <T extends ParticleEffect> ParticleType<T> register(String name, boolean alwaysSpawn, ParticleEffect.Factory<T> factory, Function<ParticleType<T>, Codec<T>> codecGetter) {
        return Registry.register(Registries.PARTICLE_TYPE, LegendOfSteve.id(name), new ParticleType<T>(alwaysSpawn, factory) {
            @Override
            public Codec<T> getCodec() {
                return codecGetter.apply(this);
            }
        });
    }

    public static void register() {}
}
