package net.deadlydiamond.legend_of_steve.init;

import com.mojang.serialization.Codec;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.particles.MagicSparkleParticleEffect;
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
    public static final DefaultParticleType DEKU_NUT_FLASH = register("deku_nut_flash", true);
    public static final DefaultParticleType CRYSTAL_SWITCH_ON_PARTICLE = register("crystal_switch_on", false);
    public static final DefaultParticleType CRYSTAL_SWITCH_OFF_PARTICLE = register("crystal_switch_off", false);

    // COMPLEX PARTICLES ///////////////////////////////////////////////////////////////////////////////////////////////
    public static final ParticleType<SparkParticleEffect> SPARK = register(
            "spark", false, SparkParticleEffect.FACTORY, particle -> SparkParticleEffect.CODEC
    );
    public static final ParticleType<MagicSparkleParticleEffect> MAGIC_SPARKLE = register(
            "magic_sparkle", false, MagicSparkleParticleEffect.FACTORY, particle -> MagicSparkleParticleEffect.CODEC
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
