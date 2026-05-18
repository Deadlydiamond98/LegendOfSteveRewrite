package net.deadlydiamond.legend_of_steve.client.models.entity;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.animation.TektiteEntityAnimations;
import net.deadlydiamond.legend_of_steve.common.entities.living.TektiteEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TektiteEntityModel<T extends TektiteEntity> extends SinglePartEntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(LegendOfSteve.id("tektite"), "main");

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart FrontRightLegUpper;
	private final ModelPart FrontRightLegLower;
	private final ModelPart FrontRightLegEnd;
	private final ModelPart BackRightLegUpper;
	private final ModelPart BackRightLegLower;
	private final ModelPart BackRightLegEnd;
	private final ModelPart BackLeftLegUpper;
	private final ModelPart BackLeftLegLower;
	private final ModelPart BackLeftLegEnd;
	private final ModelPart FrontLeftLegUpper;
	private final ModelPart FrontLeftLegLower;
	private final ModelPart FrontLeftLegEnd;
	private final ModelPart eye;

	public TektiteEntityModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.FrontRightLegUpper = this.body.getChild("FrontRightLegUpper");
		this.FrontRightLegLower = this.FrontRightLegUpper.getChild("FrontRightLegLower");
		this.FrontRightLegEnd = this.FrontRightLegLower.getChild("FrontRightLegEnd");
		this.BackRightLegUpper = this.body.getChild("BackRightLegUpper");
		this.BackRightLegLower = this.BackRightLegUpper.getChild("BackRightLegLower");
		this.BackRightLegEnd = this.BackRightLegLower.getChild("BackRightLegEnd");
		this.BackLeftLegUpper = this.body.getChild("BackLeftLegUpper");
		this.BackLeftLegLower = this.BackLeftLegUpper.getChild("BackLeftLegLower");
		this.BackLeftLegEnd = this.BackLeftLegLower.getChild("BackLeftLegEnd");
		this.FrontLeftLegUpper = this.body.getChild("FrontLeftLegUpper");
		this.FrontLeftLegLower = this.FrontLeftLegUpper.getChild("FrontLeftLegLower");
		this.FrontLeftLegEnd = this.FrontLeftLegLower.getChild("FrontLeftLegEnd");
		this.eye = this.body.getChild("eye");
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
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(TektiteEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.idleAnimationState, TektiteEntityAnimations.IDLE, ageInTicks);
		this.updateAnimation(entity.jumpAnimationState, TektiteEntityAnimations.JUMPING, ageInTicks);
		this.updateAnimation(entity.landAnimationState, TektiteEntityAnimations.LANDING, ageInTicks);
		this.animateMovement(TektiteEntityAnimations.WALKING, limbSwing, limbSwingAmount, 2.0f, 2.5f);
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