package net.deadlydiamond.legend_of_steve.client.models.block.transforms;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class QuadVec {
    private Vec3d vec3d;

    public QuadVec(float x, float y, float z) {
        this.vec3d = new Vec3d(x, y, z);
    }

    public Vec3d getVec3d() {
        return this.vec3d;
    }

    // Rotation ////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Centered
    public void rotateCentered(Direction.Axis axis, float degrees) {
        rotateRadianCentered(axis, (float) Math.toRadians(degrees));
    }

    public void rotateRadianCentered(Direction.Axis axis, float radians) {
        translate(-0.5, -0.5, -0.5);
        rotateRadian(axis, radians);
        translate(0.5, 0.5, 0.5);
    }

    // Uncentered
    public void rotate(Direction.Axis axis, float degrees) {
        rotateRadian(axis, (float) Math.toRadians(degrees));
    }

    public void rotateRadian(Direction.Axis axis, float radians) {
        switch (axis) {
            case X -> this.vec3d = this.vec3d.rotateX(radians);
            case Y -> this.vec3d = this.vec3d.rotateY(radians);
            case Z -> this.vec3d = this.vec3d.rotateZ(radians);
        }
    }

    // Other Operations ////////////////////////////////////////////////////////////////////////////////////////////////

    public void translate(double x, double y, double z) {
        this.vec3d = this.vec3d.add(x, y, z);
    }

    public void scale(double x, double y, double z) {
        this.vec3d = this.vec3d.multiply(x, y, z);
    }

    public void invert() {
        scale(-1, -1, -1);
    }
}
