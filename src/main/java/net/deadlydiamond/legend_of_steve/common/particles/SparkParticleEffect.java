package net.deadlydiamond.legend_of_steve.common.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.deadlydiamond.legend_of_steve.init.ZeldaParticleTypes;
import net.deadlydiamond98.koalalib.util.ColorHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.Locale;

public class SparkParticleEffect extends AbstractColoredParticle {

    public static final SparkParticleEffect FIRE = new SparkParticleEffect(0xf9ebab, 0xcb2e00);
    public static final SparkParticleEffect SOUL = new SparkParticleEffect(0xd4feff, 0x1362ab);
    public static final SparkParticleEffect CURSED = new SparkParticleEffect(0xe6f7c7, 0x0a9d64);

    public static final Codec<SparkParticleEffect> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codecs.VECTOR_3F.fieldOf("startColor").forGetter(effect -> effect.startColor),
                    Codecs.VECTOR_3F.fieldOf("endColor").forGetter(effect -> effect.endColor)
            ).apply(instance, SparkParticleEffect::new)
    );

    public static final ParticleEffect.Factory<SparkParticleEffect> FACTORY = new ParticleEffect.Factory<>() {
        public SparkParticleEffect read(ParticleType<SparkParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            Vector3f startColor = readColor(stringReader);
            Vector3f endColor = readColor(stringReader);
            return new SparkParticleEffect(startColor, endColor);
        }

        public SparkParticleEffect read(ParticleType<SparkParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            Vector3f startColor = packetByteBuf.readVector3f();
            Vector3f endColor = packetByteBuf.readVector3f();
            return new SparkParticleEffect(startColor, endColor);
        }
    };

    public SparkParticleEffect(int startColor, int endColor) {
        super(startColor, endColor);
    }

    public SparkParticleEffect(Vector3f startColor, Vector3f endColor) {
        super(startColor, endColor);
    }


    public static void createSparks(World world, SparkParticleEffect sparkParticleEffect, Vec3d pos, int count) {
        for (int i = 0; i < count; i++) {
            world.addParticle(sparkParticleEffect, pos.x, pos.y, pos.z, 0, 0, 0);
        }
    }

    @Override
    public ParticleType<?> getType() {
        return ZeldaParticleTypes.SPARK;
    }
}
