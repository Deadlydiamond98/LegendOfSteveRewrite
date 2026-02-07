package net.deadlydiamond.legend_of_steve.client.models.entity;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class BombEntitySlimeModel<T extends Entity> extends EntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(LegendOfSteve.id("slime_overlay"), "main");

    private final ModelPart root;

    public BombEntitySlimeModel(ModelPart root) {
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -10.5F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(-1.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
