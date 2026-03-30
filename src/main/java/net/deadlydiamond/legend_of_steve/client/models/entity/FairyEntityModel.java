package net.deadlydiamond.legend_of_steve.client.models.entity;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.living.fairy.FairyEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class FairyEntityModel<T extends FairyEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(LegendOfSteve.id("fairy"), "main");

    private final ModelPart root;
    private final ModelPart left_wing;
    private final ModelPart right_wing;

    public FairyEntityModel(ModelPart root) {
        this.root = root;
        this.left_wing = this.root.getChild("left_wing");
        this.right_wing = this.root.getChild("right_wing");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData left_wing = modelPartData.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 14.0F, 3.0F));
        ModelPartData cube_r1 = left_wing.addChild("cube_r1", ModelPartBuilder.create().uv(0, -5).cuboid(0.0F, -5.0F, -1.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -1.0F, 1.0F, 0.2444F, -0.7703F, -0.1719F));
        ModelPartData cube_r2 = left_wing.addChild("cube_r2", ModelPartBuilder.create().uv(2, 5).cuboid(0.0F, -1.0F, -1.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 1.0F, 0.5051F, -0.5086F, -0.263F));

        ModelPartData right_wing = modelPartData.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 14.0F, 3.0F));
        ModelPartData cube_r3 = right_wing.addChild("cube_r3", ModelPartBuilder.create().uv(0, -5).mirrored().cuboid(0.0F, -5.0F, -1.0F, 0.0F, 6.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.0F, -1.0F, 1.0F, 0.2444F, 0.7703F, 0.1719F));
        ModelPartData cube_r4 = right_wing.addChild("cube_r4", ModelPartBuilder.create().uv(2, 5).mirrored().cuboid(0.0F, -1.0F, -1.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.0F, 1.0F, 1.0F, 0.5051F, 0.5086F, 0.263F));

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.right_wing.yaw = MathHelper.cos((animationProgress + limbDistance) * 74 * 0.005f) * 0.25f * (float) Math.PI;
        this.left_wing.yaw = -this.right_wing.yaw;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CENTER PART STUFFS //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void renderCenterPart(MatrixStack matrices, VertexConsumer vertexConsumer, float scale, int light, int alpha) {
        matrices.push();
        matrices.translate(0, 0.3125f, 0);
        renderBillboardingFace(matrices, vertexConsumer, -scale, scale, -scale, scale, -0.01f, 0.0625f, 0.9375f, 0.0625f, 0.9375f, 255, 255, 255, alpha, light);
        matrices.pop();
    }

    private void renderBillboardingFace(MatrixStack matrices, VertexConsumer vertexConsumer, float x1, float x2, float y1, float y2, float z, float u1, float u2, float v1, float v2, int r, int g, int b, int a, int light) {
        MinecraftClient client = MinecraftClient.getInstance();
        matrices.push();
        matrices.multiply(client.getEntityRenderDispatcher().getRotation());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.PI));
        renderFace(matrices, vertexConsumer, x1, x2, y1, y2, z, u1, u2, v1, v2, r, g, b, a, light);
        matrices.pop();
    }

    private void renderFace(MatrixStack matrices, VertexConsumer vertexConsumer, float x1, float x2, float y1, float y2, float z, float u1, float u2, float v1, float v2, int r, int g, int b, int a, int light) {
        MatrixStack.Entry pose = matrices.peek();
        Matrix4f m4f = pose.getPositionMatrix();
        Matrix3f m3f = pose.getNormalMatrix();

        vertex(vertexConsumer, m4f, m3f, x2, y1, u1, v2, z, r, g, b, a, light);
        vertex(vertexConsumer, m4f, m3f, x1, y1, u2, v2, z, r, g, b, a, light);
        vertex(vertexConsumer, m4f, m3f, x1, y2, u2, v1, z, r, g, b, a, light);
        vertex(vertexConsumer, m4f, m3f, x2, y2, u1, v1, z, r, g, b, a, light);
    }

    private void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, float x, float y, float u, float v, float z, int r, int g, int b, int a, int light) {
        vertexConsumer.vertex(matrix4f, x, y, z).color(r, g, b, a).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0, 1, 0).next();
    }
}
