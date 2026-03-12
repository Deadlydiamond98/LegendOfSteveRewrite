package net.deadlydiamond.legend_of_steve.client.particles.base;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public abstract class SpecialRenderParticle extends Particle {
    protected double yaw, prevYaw;
    protected double pitch, prevPitch;
    protected double prevVelocityX, prevVelocityY, prevVelocityZ;

    public SpecialRenderParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.prevVelocityX = velocityX;
        this.prevVelocityY = velocityY;
        this.prevVelocityZ = velocityZ;
    }

    @Override
    public void tick() {
        this.prevPitch = this.pitch;
        this.prevYaw = this.yaw;
        this.prevVelocityX = this.velocityX;
        this.prevVelocityY = this.velocityY;
        this.prevVelocityZ = this.velocityZ;
        super.tick();
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        VertexConsumerProvider.Immediate vertexConsumerProvider = client.getBufferBuilders().getEntityVertexConsumers();
        MatrixStack matrices = new MatrixStack();

        matrices.push();

        Vec3d cameraPos = camera.getPos();
        Vec3d translatePos = shouldOffsetWithPos() ? getPos(tickDelta).subtract(cameraPos) : cameraPos.negate();
        matrices.translate(translatePos.x, translatePos.y, translatePos.z);

        renderParticle(matrices, vertexConsumerProvider, tickDelta, camera);

        vertexConsumerProvider.draw();
        matrices.pop();
    }

    protected boolean shouldOffsetWithPos() {
        return true;
    }

    public abstract void renderParticle(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumerProvider, float tickDelta, Camera camera);

    protected void renderVertex(VertexConsumer vertexConsumer, Matrix4f m4f, Matrix3f m3f, float x, float y, float z, float u, float v, int light) {
        renderVertex(vertexConsumer, m4f, m3f, x, y, z, 255, 255, 255, 255, u, v, light);
    }

    protected void renderVertex(VertexConsumer vertexConsumer, Matrix4f m4f, Matrix3f m3f, float x, float y, float z, float r, float g, float b, float a, float u, float v, int light) {
        vertexConsumer.vertex(m4f, x, y, z).color(r, g, b, a).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(m3f, 0, 1, 0).next();
    }

    protected abstract VertexConsumer getVertexConsumer(VertexConsumerProvider.Immediate vertexConsumerProvider);

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.CUSTOM;
    }

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    public Vec3d getVelocity() {
        return new Vec3d(this.velocityX, this.velocityY, this.velocityZ);
    }

    public void setVelocity(Vec3d velocity) {
        setVelocity(velocity.x, velocity.y, velocity.z);
    }

    public Vec3d getPos() {
        return getPos(1);
    }

    public Vec3d getPos(float tickDelta) {
        return new Vec3d(
                MathHelper.lerp(tickDelta, this.prevPosX, this.x),
                MathHelper.lerp(tickDelta, this.prevPosY, this.y),
                MathHelper.lerp(tickDelta, this.prevPosZ, this.z)
        );
    }
}
