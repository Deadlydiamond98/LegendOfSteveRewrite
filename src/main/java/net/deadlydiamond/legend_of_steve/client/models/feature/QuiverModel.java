package net.deadlydiamond.legend_of_steve.client.models.feature;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class QuiverModel<T extends LivingEntity> extends EntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(LegendOfSteve.id("quiver"), "main");

    private final ModelPart quiver;
    private final ModelPart strap;

    public QuiverModel(ModelPart root) {
        this.quiver = root.getChild("quiver");
        this.strap = root.getChild("strap");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData quiver = modelPartData.addChild("quiver", ModelPartBuilder.create(), ModelTransform.of(0.75F, 6.25F, 8.0F, 0.0F, 0.0F, 0.1309F));

        ModelPartData cube_r1 = quiver.addChild("cube_r1", ModelPartBuilder.create().uv(0, 16).cuboid(-3.5F, -8.0F, -6.5F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-3.0F, -7.0F, -6.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

        ModelPartData strap = modelPartData.addChild("strap", ModelPartBuilder.create().uv(16, 0).cuboid(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        quiver.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        strap.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}