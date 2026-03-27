package net.deadlydiamond.legend_of_steve.client.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class DekuNutFlashParticle extends SpriteBillboardParticle {
    public DekuNutFlashParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, g, h, i);
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;

        this.maxAge = 20;
        this.scale = 4;

        this.setSprite(spriteProvider);
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.scale -= 0.4f * tickDelta;
        this.scale = Math.max(0, this.scale);
        super.buildGeometry(vertexConsumer, camera, tickDelta);
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

        @Nullable @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new DekuNutFlashParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}
