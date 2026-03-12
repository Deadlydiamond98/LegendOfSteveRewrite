package net.deadlydiamond.legend_of_steve.client.particles.base;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public abstract class ArrowLikeParticle extends SpecialRenderParticle {
    private float particleLength;
    private float scale = 1;
    private float prevScale = 1;

    public ArrowLikeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.prevPitch == 0 && this.prevYaw == 0) {
            this.prevYaw = this.yaw;
            this.prevPitch = this.pitch;
        }

        this.yaw = (float)(MathHelper.atan2(this.velocityX, this.velocityZ) * 180.0F / (float)Math.PI);
        this.pitch = (float)(MathHelper.atan2(this.velocityY, getVelocity().horizontalLength()) * 180.0F / (float)Math.PI);
        this.pitch = updateRotation(this.prevPitch, this.pitch);
        this.yaw = updateRotation(this.prevYaw, this.yaw);
        this.prevScale = this.scale;

        if (this.age >= this.maxAge - 8) {
            this.scale = Math.max(0, this.scale - 0.2f);
        }
    }

    protected double updateRotation(double prevRot, double newRot) {
        while (newRot - prevRot < -180.0F) {
            prevRot -= 360.0F;
        }
        while (newRot - prevRot >= 180.0F) {
            prevRot += 360.0F;
        }
        return MathHelper.lerp(0.2F, prevRot, newRot);
    }

    @Override
    public void renderParticle(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumerProvider, float tickDelta, Camera camera) {
        if (this.age <= 0) {
            return;
        }

        matrices.multiply(
                RotationAxis.POSITIVE_Y.rotationDegrees((float) (MathHelper.lerp(tickDelta, this.prevYaw, this.yaw) - 90.0F))
        );
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) MathHelper.lerp(tickDelta, this.prevPitch, this.pitch)));


        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45.0F));
        matrices.scale(0.05625F, 0.05625F, 0.05625F);

        float scale = MathHelper.lerp(tickDelta, this.prevScale, this.scale);
        matrices.scale(scale, scale, scale);

        matrices.translate(-4, 0, 0);
        VertexConsumer vertexConsumer = getVertexConsumer(vertexConsumerProvider);
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();

        float x2 = 8;
        float y1 = -0.25f;
        float y2 = 0.25f;

        Vec3d pos = new Vec3d(this.x, this.y, this.z);
        Vec3d prevPos = new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ);
        if (pos.distanceTo(prevPos) < 0.05f) {
            this.particleLength = MathHelper.lerp(tickDelta, this.particleLength, this.particleLength + 1);
        } else {
            this.particleLength = MathHelper.lerp(tickDelta, this.particleLength, this.particleLength - 0.25f);
        }

        this.particleLength = Math.min(x2, Math.max(5, this.particleLength));

        int light = LightmapTextureManager.MAX_LIGHT_COORDINATE;
        for (int u = 0; u < 4; u++) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
            renderVertex(vertexConsumer, matrix4f, matrix3f, this.particleLength, y1, 0, 0, 0, light);
            renderVertex(vertexConsumer, matrix4f, matrix3f, x2, y1, 0, 0.5f, 0, light);
            renderVertex(vertexConsumer, matrix4f, matrix3f, x2, y2, 0, 0.5f, 0.15625f, light);
            renderVertex(vertexConsumer, matrix4f, matrix3f, this.particleLength, y2, 0, 0, 0.15625f, light);
        }
    }
}
