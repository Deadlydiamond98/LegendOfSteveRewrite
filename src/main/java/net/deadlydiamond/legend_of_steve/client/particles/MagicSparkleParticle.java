package net.deadlydiamond.legend_of_steve.client.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class MagicSparkleParticle extends SpriteBillboardParticle {
    private final int alphaOffset;
    private final float angleIncrement;

    public MagicSparkleParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;

        this.scale = 0.05f + (this.random.nextFloat() * 0.1f);

        this.setSprite(spriteProvider.getSprite(clientWorld.random));
        this.maxAge = this.random.nextBetween(50, 100);

        this.angle = this.random.nextBetween(0, 10);
        this.angleIncrement = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);

        this.alphaOffset = this.random.nextBetween(0, 10);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.dead) {
            this.prevAngle = this.angle;
            this.angle += this.angleIncrement / 20;
        }
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        super.buildGeometry(vertexConsumer, camera, tickDelta);
        this.alpha = (0.125f * (float) Math.sin(0.25f * (this.age + this.alphaOffset + tickDelta)) + 0.875f);
    }

    @Override
    protected int getBrightness(float tint) {
        return 240;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new MagicSparkleParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}
