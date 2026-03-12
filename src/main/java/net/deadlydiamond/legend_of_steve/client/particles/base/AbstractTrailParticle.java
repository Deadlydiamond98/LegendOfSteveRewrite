package net.deadlydiamond.legend_of_steve.client.particles.base;


import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Arrays;

/*
 * This class is part of The Legend of Steve, but it uses many parts of code from Alex's Caves by Alexthe668.
 * The original project is licensed under the GNU Lesser General Public License.
 * For more details, visit: https://github.com/AlexModGuy/AlexsCaves/tree/main
 */

public abstract class AbstractTrailParticle extends SpecialRenderParticle {
    protected Vec3d[] trailPositions = new Vec3d[64];
    private int trailPointer = -1;

    protected float trailR = 1;
    protected float trailG = 1;
    protected float trailB = 1;
    protected float trailA = 1;

    public AbstractTrailParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
    }

    @Override
    public void tick() {
        tickTrail();
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;

        setVelocity(getVelocity().multiply(0.99));

        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            applyGravity();
        }
    }

    protected void applyGravity() {
        this.velocityY = this.velocityY - 0.04 * this.gravityStrength;
    }

    protected void tickTrail() {
        Vec3d currentPosition = new Vec3d(this.x, this.y, this.z);
        if (trailPointer == -1) {
            Arrays.fill(trailPositions, currentPosition);
        }
        if (++this.trailPointer == this.trailPositions.length) {
            this.trailPointer = 0;
        }
        this.trailPositions[this.trailPointer] = currentPosition;
    }

    @Override
    public void renderParticle(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumerProvider, float tickDelta, Camera camera) {
        if (this.trailPointer > -1) {
            matrices.push();

            int samples = 0;
            Vec3d drawFrom = getPos(tickDelta);
            float zRot = getTrailRot(camera);

            Vec3d topAngleVec = new Vec3d(0, getTrailThickness() / 2F, 0).rotateZ(zRot);
            Vec3d bottomAngleVec = new Vec3d(0, getTrailThickness() / -2F, 0).rotateZ(zRot);

            int light = getBrightness(tickDelta);
            VertexConsumer vertexConsumer = getVertexConsumer(vertexConsumerProvider);

            while (samples < getTrailLength()) {
                float u1 = samples / (float) getTrailLength();
                float u2 = u1 + 1 / (float) getTrailLength();

                Vec3d draw1 = drawFrom;
                Vec3d draw2 = getTrailPosition(samples * sampleStep(), tickDelta);

                MatrixStack.Entry entry = matrices.peek();
                Matrix4f m4f = entry.getPositionMatrix();
                Matrix3f m3f = entry.getNormalMatrix();

                renderVertex(vertexConsumer, m4f, m3f, (float) draw1.x + (float) bottomAngleVec.x, (float) draw1.y + (float) bottomAngleVec.y, (float) draw1.z + (float) bottomAngleVec.z, u1, 1, light);
                renderVertex(vertexConsumer, m4f, m3f, (float) draw2.x + (float) bottomAngleVec.x, (float) draw2.y + (float) bottomAngleVec.y, (float) draw2.z + (float) bottomAngleVec.z, u2, 1, light);
                renderVertex(vertexConsumer, m4f, m3f, (float) draw2.x + (float) topAngleVec.x, (float) draw2.y + (float) topAngleVec.y, (float) draw2.z + (float) topAngleVec.z, u2, 0, light);
                renderVertex(vertexConsumer, m4f, m3f, (float) draw1.x + (float) topAngleVec.x, (float) draw1.y + (float) topAngleVec.y, (float) draw1.z + (float) topAngleVec.z, u1, 0, light);

                if (renderBackface()) {
                    renderVertex(vertexConsumer, m4f, m3f, (float) draw1.x + (float) topAngleVec.x, (float) draw1.y + (float) topAngleVec.y, (float) draw1.z + (float) topAngleVec.z, u1, 0, light);
                    renderVertex(vertexConsumer, m4f, m3f, (float) draw2.x + (float) topAngleVec.x, (float) draw2.y + (float) topAngleVec.y, (float) draw2.z + (float) topAngleVec.z, u2, 0, light);
                    renderVertex(vertexConsumer, m4f, m3f, (float) draw2.x + (float) bottomAngleVec.x, (float) draw2.y + (float) bottomAngleVec.y, (float) draw2.z + (float) bottomAngleVec.z, u2, 1, light);
                    renderVertex(vertexConsumer, m4f, m3f, (float) draw1.x + (float) bottomAngleVec.x, (float) draw1.y + (float) bottomAngleVec.y, (float) draw1.z + (float) bottomAngleVec.z, u1, 1, light);
                }

                samples++;
                drawFrom = draw2;
            }
            matrices.pop();
        }
    }

    @Override
    protected void renderVertex(VertexConsumer vertexConsumer, Matrix4f m4f, Matrix3f m3f, float x, float y, float z, float u, float v, int light) {
        renderVertex(vertexConsumer, m4f, m3f, x, y, z, this.trailR, this.trailG, this.trailB, this.trailA, u, v, light);
    }

    @Override
    protected VertexConsumer getVertexConsumer(VertexConsumerProvider.Immediate vertexConsumerProvider) {
        return vertexConsumerProvider.getBuffer(RenderLayer.getItemEntityTranslucentCull(getTrailTexture()));
    }

    protected boolean renderBackface() {
        return true;
    }

    public float getTrailRot(Camera camera) {
        return -0.017453292F * camera.getPitch();
    }

    protected abstract Identifier getTrailTexture();
    protected abstract float getTrailThickness();
    protected abstract int getTrailLength();

    protected int sampleStep() {
        return 1;
    }

    protected Vec3d getTrailPosition(int pointer, float partialTick) {
        if (this.dead) {
            partialTick = 1;
        }
        int i = this.trailPointer - pointer & 63;
        int j = this.trailPointer - pointer - 1 & 63;
        Vec3d d0 = this.trailPositions[j];
        Vec3d d1 = this.trailPositions[i].subtract(d0);
        return d0.add(d1.multiply(partialTick));
    }

    @Override
    protected boolean shouldOffsetWithPos() {
        return false;
    }
}
