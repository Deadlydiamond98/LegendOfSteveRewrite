package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.client.particles.MagicSparkleParticle;
import net.deadlydiamond.legend_of_steve.client.particles.SparkParticle;
import net.deadlydiamond.legend_of_steve.init.ZeldaParticleTypes;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class ZeldaParticleFactories {

    public static void register() {
        register(ZeldaParticleTypes.SPARK, new SparkParticle.Factory());
        register(ZeldaParticleTypes.MAGIC_SPARKLE, MagicSparkleParticle.Factory::new);
    }

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    private static <T extends ParticleEffect> void register(ParticleType<T> particle, ParticleFactory<T> factory) {
        ParticleFactoryRegistry.getInstance().register(particle, factory);
    }

    private static <T extends ParticleEffect> void register(ParticleType<T> particle, ParticleFactoryRegistry.PendingParticleFactory<T> factory) {
        ParticleFactoryRegistry.getInstance().register(particle, factory);
    }
}
