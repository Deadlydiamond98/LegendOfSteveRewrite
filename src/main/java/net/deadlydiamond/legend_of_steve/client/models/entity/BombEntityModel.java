package net.deadlydiamond.legend_of_steve.client.models.entity;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.BombEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

public class BombEntityModel<T extends BombEntity> extends EntityModel<T> {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(LegendOfSteve.id("bomb"), "main");

    private final ModelPart top;
    private final ModelPart base;
    private final ModelPart fuse;

    public BombEntityModel(ModelPart root) {
        this.top = root.getChild("top");
        this.base = root.getChild("base");
        this.fuse = root.getChild("fuse");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData top = modelPartData.addChild("top", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, -9.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData fuse = modelPartData.addChild("fuse", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData fuse_r1 = fuse.addChild("fuse_r1", ModelPartBuilder.create().uv(17, 15).cuboid(0.0F, -3.5F, -0.5F, 0.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        top.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public void renderFuse(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        fuse.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public void renderOverlay(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}