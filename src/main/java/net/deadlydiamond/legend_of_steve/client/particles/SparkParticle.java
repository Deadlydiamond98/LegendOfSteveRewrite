package net.deadlydiamond.legend_of_steve.client.particles;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.particles.base.AbstractTrailParticle;
import net.deadlydiamond.legend_of_steve.common.particles.SparkParticleEffect;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.deadlydiamond98.koalalib.util.ColorHelper;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SparkParticle extends AbstractTrailParticle {
    private static final Identifier TRAIL_TEXTURE = LegendOfSteve.id("textures/trail/rectangle.png");
    private final int startHex, endHex;

    public SparkParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int startingColor, int endingColor) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.startHex = startingColor;
        this.endHex = endingColor;

        this.gravityStrength = 0.6f;
        setVelocity(getVelocity().multiply(1.2));
        this.maxAge = 50;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.onGround && getVelocity().lengthSquared() > 0.05) {
            this.velocityY *= this.random.nextBoolean() ? -0.25 : -0.05;
        }
    }

    @Override
    public void renderParticle(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumerProvider, float tickDelta, Camera camera) {
        if (this.age > 0) {
            setTrailHex(ColorHelper.blendHexColors(this.startHex, this.endHex, (this.age + tickDelta) / this.maxAge));
            super.renderParticle(matrices, vertexConsumerProvider, tickDelta, camera);
        }
    }

    @Override
    public Identifier getTrailTexture() {
        return TRAIL_TEXTURE;
    }

    @Override
    public float getTrailThickness() {
        return 0.05f;
    }

    @Override
    public int getTrailLength() {
        return 1;
    }

    @Override
    protected VertexConsumer getVertexConsumer(VertexConsumerProvider.Immediate vertexConsumerProvider) {
        return vertexConsumerProvider.getBuffer(ZeldaRenderLayers.getEntityUnlit(getTrailTexture()));
    }

    protected void setTrailHex(int hex) {
        int[] argb = ColorHelper.HexToARGB(hex);
        this.trailR = argb[1] / 255.0f;
        this.trailG = argb[2] / 255.0f;
        this.trailB = argb[3] / 255.0f;
    }

    // FACTORIES ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class Factory implements ParticleFactory<SparkParticleEffect> {

        @Nullable @Override
        public Particle createParticle(SparkParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SparkParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters.getStartColor(), parameters.getEndColor());
        }
    }

    public static class RegularFactory implements ParticleFactory<DefaultParticleType> {
        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SparkParticle(world, x, y, z, velocityX, velocityY, velocityZ, 0xf9ebab, 0xcb2e00);
        }
    }

    public static class SoulFactory implements ParticleFactory<DefaultParticleType> {
        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SparkParticle(world, x, y, z, velocityX, velocityY, velocityZ, 0xd4feff, 0x1362ab);
        }
    }

    public static class CursedFactory implements ParticleFactory<DefaultParticleType> {
        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SparkParticle(world, x, y, z, velocityX, velocityY, velocityZ, 0xe6f7c7, 0x0a9d64);
        }
    }
}
