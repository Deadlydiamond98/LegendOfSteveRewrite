package net.deadlydiamond.legend_of_steve.client.models.entity;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.animation.ArurodaEntityAnimations;
import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.ArurodaEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ArurodaEntityModel<T extends ArurodaEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(LegendOfSteve.id("aruroda"), "main");

    private final ModelPart root;
    private final ModelPart eye;

    public ArurodaEntityModel(ModelPart root) {
        this.root = root;
        this.eye = this.root.getChild("body").getChild("eye");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -5.5F, -9.0F, 12.0F, 10.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 22).cuboid(-5.5F, -4.5F, 3.0F, 11.0F, 9.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 14.0F, -1.0F));

        ModelPartData FrontRightLegUpper = body.addChild("FrontRightLegUpper", ModelPartBuilder.create().uv(36, 26).cuboid(-0.75F, -1.5F, -1.5F, 8.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, 2.0F, -3.5F, 0.0F, 0.5672F, -0.1745F));

        ModelPartData FrontRightLegLower = FrontRightLegUpper.addChild("FrontRightLegLower", ModelPartBuilder.create().uv(36, 22).cuboid(-1.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(36, 32).cuboid(-1.0F, -3.0F, 0.0F, 11.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(7.25F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3526F));

        ModelPartData cube_r1 = FrontRightLegLower.addChild("cube_r1", ModelPartBuilder.create().uv(36, 34).cuboid(-0.0109F, -1.5695F, -0.1465F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(9.3F, -1.0F, 1.0F, -0.4409F, -0.0493F, 0.2442F));

        ModelPartData cube_r2 = FrontRightLegLower.addChild("cube_r2", ModelPartBuilder.create().uv(36, 34).cuboid(-0.0109F, -1.5695F, 0.1464F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(9.3F, -1.0F, -1.0F, 0.4409F, 0.0493F, 0.2442F));

        ModelPartData FrontRightLegEnd = FrontRightLegLower.addChild("FrontRightLegEnd", ModelPartBuilder.create(), ModelTransform.pivot(10.0F, 0.0F, 0.0F));

        ModelPartData BackRightLegUpper = body.addChild("BackRightLegUpper", ModelPartBuilder.create().uv(36, 26).cuboid(-2.0F, -1.5F, -1.5F, 8.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.75F, 2.0F, 3.5F, 0.0F, -0.2618F, 0.0F));

        ModelPartData BackRightLegLower = BackRightLegUpper.addChild("BackRightLegLower", ModelPartBuilder.create().uv(36, 22).cuboid(-1.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(36, 32).cuboid(-1.0F, -3.0F, 0.0F, 11.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.9599F));

        ModelPartData cube_r3 = BackRightLegLower.addChild("cube_r3", ModelPartBuilder.create().uv(36, 34).cuboid(-0.0109F, -1.5695F, -0.1465F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(9.3F, -1.0F, 1.0F, -0.4409F, -0.0493F, 0.2442F));

        ModelPartData cube_r4 = BackRightLegLower.addChild("cube_r4", ModelPartBuilder.create().uv(36, 34).cuboid(-0.0109F, -1.5695F, 0.1464F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(9.3F, -1.0F, -1.0F, 0.4409F, 0.0493F, 0.2442F));

        ModelPartData BackRightLegEnd = BackRightLegLower.addChild("BackRightLegEnd", ModelPartBuilder.create(), ModelTransform.pivot(10.0F, 0.0F, 0.0F));

        ModelPartData BackLeftLegUpper = body.addChild("BackLeftLegUpper", ModelPartBuilder.create().uv(36, 26).mirrored().cuboid(-5.3F, -1.5F, -1.5F, 8.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-5.45F, 2.0F, 3.5F, 0.0F, 0.2618F, 0.0F));

        ModelPartData BackLeftLegLower = BackLeftLegUpper.addChild("BackLeftLegLower", ModelPartBuilder.create().uv(36, 22).mirrored().cuboid(-10.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(36, 32).mirrored().cuboid(-10.0F, -3.0F, 0.0F, 11.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-5.3F, 0.0F, 0.0F, 0.0F, 0.0F, -0.9599F));

        ModelPartData cube_r5 = BackLeftLegLower.addChild("cube_r5", ModelPartBuilder.create().uv(36, 34).mirrored().cuboid(-1.9891F, -1.5695F, -0.1465F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.3F, -1.0F, 1.0F, -0.4409F, 0.0493F, -0.2442F));

        ModelPartData cube_r6 = BackLeftLegLower.addChild("cube_r6", ModelPartBuilder.create().uv(36, 34).mirrored().cuboid(-1.9891F, -1.5695F, 0.1464F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.3F, -1.0F, -1.0F, 0.4409F, -0.0493F, -0.2442F));

        ModelPartData BackLeftLegEnd = BackLeftLegLower.addChild("BackLeftLegEnd", ModelPartBuilder.create(), ModelTransform.pivot(-10.0F, 0.0F, 0.0F));

        ModelPartData FrontLeftLegUpper = body.addChild("FrontLeftLegUpper", ModelPartBuilder.create().uv(36, 26).mirrored().cuboid(-7.25F, -1.5F, -1.5F, 8.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-6.0F, 2.0F, -3.5F, 0.0F, -0.5672F, 0.1745F));

        ModelPartData FrontLeftLegLower = FrontLeftLegUpper.addChild("FrontLeftLegLower", ModelPartBuilder.create().uv(36, 22).mirrored().cuboid(-10.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(36, 32).mirrored().cuboid(-10.0F, -3.0F, 0.0F, 11.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-7.25F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3526F));

        ModelPartData cube_r7 = FrontLeftLegLower.addChild("cube_r7", ModelPartBuilder.create().uv(36, 34).mirrored().cuboid(-1.9891F, -1.5695F, -0.1465F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.3F, -1.0F, 1.0F, -0.4409F, 0.0493F, -0.2442F));

        ModelPartData cube_r8 = FrontLeftLegLower.addChild("cube_r8", ModelPartBuilder.create().uv(36, 34).mirrored().cuboid(-1.9891F, -1.5695F, 0.1464F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.3F, -1.0F, -1.0F, 0.4409F, -0.0493F, -0.2442F));

        ModelPartData FrontLeftLegEnd = FrontLeftLegLower.addChild("FrontLeftLegEnd", ModelPartBuilder.create(), ModelTransform.pivot(-10.0F, 0.0F, 0.0F));

        ModelPartData eye = body.addChild("eye", ModelPartBuilder.create().uv(49, 19).cuboid(-1.0F, -1.5F, -0.05F, 2.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -9.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(2, 40).cuboid(-3.0F, -6.0312F, -0.784F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.5F, 10.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData tailSegmentA = tail.addChild("tailSegmentA", ModelPartBuilder.create().uv(2, 40).cuboid(-3.0F, -6.0312F, -0.784F, 6.0F, 6.0F, 8.0F, new Dilation(-0.5F)), ModelTransform.of(0.0F, 0.0F, 7.5F, 0.3927F, 0.0F, 0.0F));

        ModelPartData tailSegmentB = tailSegmentA.addChild("tailSegmentB", ModelPartBuilder.create().uv(32, 42).cuboid(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.2812F, 7.216F, 0.7418F, 0.0F, 0.0F));

        ModelPartData tailSegmentC = tailSegmentB.addChild("tailSegmentC", ModelPartBuilder.create().uv(32, 42).cuboid(-2.0F, -3.5F, -1.0F, 4.0F, 4.0F, 8.0F, new Dilation(-0.5F)), ModelTransform.of(0.0F, -0.75F, 7.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData tailSegmentD = tailSegmentC.addChild("tailSegmentD", ModelPartBuilder.create().uv(32, 42).cuboid(-2.0F, -3.25F, -1.0F, 4.0F, 4.0F, 8.0F, new Dilation(-0.7F)), ModelTransform.of(0.0F, -0.25F, 6.5F, 0.6981F, 0.0F, 0.0F));

        ModelPartData tailSegmentE = tailSegmentD.addChild("tailSegmentE", ModelPartBuilder.create().uv(0, 55).cuboid(-2.0F, -2.05F, -0.75F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.2F, 6.1F, 0.6545F, 0.0F, 0.0F));

        ModelPartData cube_r9 = tailSegmentE.addChild("cube_r9", ModelPartBuilder.create().uv(36, 32).cuboid(0.0F, -1.0F, -2.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.45F, 4.5F, -2.0508F, 0.0F, 0.0F));

        ModelPartData tailEnd = tailSegmentE.addChild("tailEnd", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -0.3F, 5.75F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(ArurodaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        Entity target = MinecraftClient.getInstance().getCameraEntity();
        if (target != null) {
            Vec3d vec3d = target.getCameraPosVec(0.0F);
            Vec3d vec3d2 = entity.getCameraPosVec(0.0F);
            double d = vec3d.y - vec3d2.y;
            if (d > 0.0) {
                this.eye.pivotY = -0.75F;
            } else {
                this.eye.pivotY = -0.25F;
            }

            Vec3d vec3d3 = entity.getRotationVec(0.0F);
            vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z);
            Vec3d vec3d4 = new Vec3d(vec3d2.x - vec3d.x, 0.0, vec3d2.z - vec3d.z).normalize().rotateY((float) (Math.PI / 2));
            double e = vec3d3.dotProduct(vec3d4);
            this.eye.pivotX = MathHelper.sqrt((float)Math.abs(e)) * 2.0F * (float)Math.signum(e);
        }

        this.updateAnimation(entity.idleAnimationState, ArurodaEntityAnimations.IDLE, ageInTicks);
        this.updateAnimation(entity.jumpAnimationState, ArurodaEntityAnimations.JUMPING, ageInTicks);
        this.updateAnimation(entity.landAnimationState, ArurodaEntityAnimations.LANDING, ageInTicks);
        this.updateAnimation(entity.attackAnimationState, ArurodaEntityAnimations.ATTACKING, ageInTicks);
        if (!entity.isTektiteJumping()) {
            this.animateMovement(ArurodaEntityAnimations.WALKING, limbSwing, limbSwingAmount, 2.0f, 2.5f);
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}