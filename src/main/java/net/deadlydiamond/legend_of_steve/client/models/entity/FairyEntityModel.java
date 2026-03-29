package net.deadlydiamond.legend_of_steve.client.models.entity;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.living.fairy.FairyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

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
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.right_wing.yaw = MathHelper.cos((animationProgress + limbDistance) * 74 * 0.005f) * 0.25f * (float) Math.PI;
        this.left_wing.yaw = -this.right_wing.yaw;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
