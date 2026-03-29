package net.deadlydiamond.legend_of_steve.common.entities.living;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.particles.SparkParticleEffect;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public enum FairyColor {
    RED(0xfff3eb, 0xc63b67),
    ORANGE(0xf7ecca, 0xcf7d44),
    YELLOW(0xfdffde, 0xd2ac40),
    GREEN(0xeefad1, 0x60c84a),
    BLUE(0xdeffff, 0x49aaef),
    PURPLE(0xf0ebfd, 0xaf40c9),
    PINK(0xffe3e8, 0xc947a7);

    private final Identifier texture;
    private final int hexStart, hexEnd;

    FairyColor(int hexStart, int hexEnd) {
        this.texture = texture(name().toLowerCase());
        this.hexStart = hexStart;
        this.hexEnd = hexEnd;
    }

    public static FairyColor init(Random random) {
        return values()[random.nextInt(values().length)];
    }

    public Identifier getTexture() {
        return this.texture;
    }

    private Identifier texture(String color) {
        return LegendOfSteve.id("textures/entity/fairy/center/" + color + "_fairy.png");
    }

    public void createParticles(World world, Vec3d pos, int count) {
        SparkParticleEffect.createSparks(world, new SparkParticleEffect(this.hexStart, this.hexEnd), pos, count);
    }

    public void writeNbt(NbtCompound nbt) {
        nbt.putString("FairyColor", this.toString());
    }

    public static FairyColor readNbt(NbtCompound nbt) {
        return FairyColor.valueOf(nbt.getString("FairyColor"));
    }
}
