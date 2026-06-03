package net.deadlydiamond.legend_of_steve.client.models.be.lock;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class ChestLockModel extends EntityModel<Entity> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(LegendOfSteve.id("chest_lock"), "main");

    private final ModelPart root;

    public ChestLockModel(ModelPart root) {
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 8).cuboid(-1.5F, -14.5F, -7.5F, 3.0F, 15.0F, 15.0F, new Dilation(0.0F))
                .uv(23, 1).cuboid(-3.5F, -12.5F, -9.0F, 7.0F, 7.0F, 3.0F, new Dilation(-0.9F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r1 = bb_main.addChild("cube_r1", ModelPartBuilder.create().uv(0, 8).cuboid(-2.0F, -7.5F, 0.5F, 3.0F, 15.0F, 15.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, -6.5F, -8.0F, 0.0F, 0.0F, 1.5708F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}