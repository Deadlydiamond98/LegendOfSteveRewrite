package net.deadlydiamond.legend_of_steve.common.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.deadlydiamond.legend_of_steve.init.ZeldaParticleTypes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class MagicSparkleParticleEffect extends AbstractColoredParticle {

    public static final Codec<MagicSparkleParticleEffect> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codecs.VECTOR_3F.fieldOf("startColor").forGetter(effect -> effect.startColor),
                    Codecs.VECTOR_3F.fieldOf("endColor").forGetter(effect -> effect.endColor)
            ).apply(instance, MagicSparkleParticleEffect::new)
    );

    public static final Factory<MagicSparkleParticleEffect> FACTORY = new Factory<>() {
        public MagicSparkleParticleEffect read(ParticleType<MagicSparkleParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            Vector3f startColor = readColor(stringReader);
            Vector3f endColor = readColor(stringReader);
            return new MagicSparkleParticleEffect(startColor, endColor);
        }

        public MagicSparkleParticleEffect read(ParticleType<MagicSparkleParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            Vector3f startColor = packetByteBuf.readVector3f();
            Vector3f endColor = packetByteBuf.readVector3f();
            return new MagicSparkleParticleEffect(startColor, endColor);
        }
    };

    public MagicSparkleParticleEffect(int startColor, int endColor) {
        super(startColor, endColor);
    }

    public MagicSparkleParticleEffect(Vector3f startColor, Vector3f endColor) {
        super(startColor, endColor);
    }


    public static void createSparkles(World world, MagicSparkleParticleEffect magicSparkle, int count, Vec3d pos, Vec3d velocity) {
        for (int i = 0; i < count; i++) {
            world.addParticle(magicSparkle, pos.x, pos.y, pos.z, velocity.x, velocity.y, velocity.z);
        }
    }

    public static void createFountainSparkles(World world, Vec3d pos, int count, double dy, double dxz) {
        MagicSparkleParticleEffect.createSparkles(world, new MagicSparkleParticleEffect(0xFFFFFF, 0xFFFFFF),
                count, pos, new Vec3d(
                        (world.random.nextFloat() - 0.5) * dxz,
                        (Math.max(world.random.nextFloat(), 0.1)) * dy,
                        (world.random.nextFloat() - 0.5) * dxz
                )
        );
    }

    @Override
    public ParticleType<?> getType() {
        return ZeldaParticleTypes.MAGIC_SPARKLE;
    }
}
