package net.deadlydiamond.legend_of_steve.common.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.deadlydiamond98.koalalib.util.ColorHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import org.joml.Vector3f;

import java.util.Locale;

public abstract class AbstractColoredParticle implements ParticleEffect {
    protected Vector3f startColor;
    protected Vector3f endColor;

    public AbstractColoredParticle(int startColor, int endColor) {
        this(getColorFromHex(startColor), getColorFromHex(endColor));
    }

    public AbstractColoredParticle(Vector3f startColor, Vector3f endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVector3f(this.startColor);
        buf.writeVector3f(this.endColor);
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

    public int getStartColor() {
        return getColor(this.startColor);
    }

    public int getEndColor() {
        return getColor(this.endColor);
    }

    protected int getColor(Vector3f color) {
        return ColorHelper.ARGBToHex(255, (int) (color.x * 255), (int) (color.y * 255), (int) (color.z * 255));
    }

    private static Vector3f getColorFromHex(int hex) {
        int[] argb = ColorHelper.HexToARGB(hex);
        float r = argb[1] / 255.0f;
        float g = argb[2] / 255.0f;
        float b = argb[3] / 255.0f;
        return new Vector3f(r, g, b);
    }
}
