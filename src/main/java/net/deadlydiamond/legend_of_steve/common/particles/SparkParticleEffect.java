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
import org.joml.Vector3f;

import java.util.Locale;

public class SparkParticleEffect extends AbstractZeldaParticleEffect {
    protected final Vector3f startColor;
    protected final Vector3f endColor;

    public static final Codec<SparkParticleEffect> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codecs.VECTOR_3F.fieldOf("startColor").forGetter(effect -> effect.startColor),
                    Codecs.VECTOR_3F.fieldOf("endColor").forGetter(effect -> effect.endColor)
            ).apply(instance, SparkParticleEffect::new)
    );

    public static final ParticleEffect.Factory<SparkParticleEffect> FACTORY = new ParticleEffect.Factory<SparkParticleEffect>() {
        public SparkParticleEffect read(ParticleType<SparkParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
            Vector3f startColor = readColor(stringReader);
            Vector3f endColor = readColor(stringReader);
            return new SparkParticleEffect(startColor, endColor);
        }

        public SparkParticleEffect read(ParticleType<SparkParticleEffect> particleType, PacketByteBuf packetByteBuf) {
            Vector3f startColor = readColor(packetByteBuf);
            Vector3f endColor = readColor(packetByteBuf);
            return new SparkParticleEffect(startColor, endColor);
        }
    };

    public SparkParticleEffect(Vector3f color, Vector3f endColor) {
        this.startColor = color;
        this.endColor = endColor;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.startColor.x());
        buf.writeFloat(this.startColor.y());
        buf.writeFloat(this.startColor.z());

        buf.writeFloat(this.endColor.x());
        buf.writeFloat(this.endColor.y());
        buf.writeFloat(this.endColor.z());
    }

    public static Vector3f readColor(PacketByteBuf buf) {
        return new Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public static Vector3f readColor(StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        float f = reader.readFloat();
        reader.expect(' ');
        float g = reader.readFloat();
        reader.expect(' ');
        float h = reader.readFloat();
        return new Vector3f(f, g, h);
    }

    public int getStartColor() {
        return getColor(this.startColor);
    }

    public int getEndColor() {
        return getColor(this.endColor);
    }

    private int getColor(Vector3f color) {
        return ColorHelper.ARGBToHex(255, (int) (color.x * 255), (int) (color.y * 255), (int) (color.z * 255));
    }

    @Override
    public ParticleType<?> getType() {
        return ZeldaParticleTypes.SPARK;
    }

    @Override
    public String asString() {
        return String.format(
                Locale.ROOT,
                "%s %.2f %.2f %.2f %.2f %.2f %.2f",
                Registries.PARTICLE_TYPE.getId(this.getType()),
                this.startColor.x(),
                this.startColor.y(),
                this.startColor.z(),
                this.endColor.x(),
                this.endColor.y(),
                this.endColor.z()
        );
    }
}
