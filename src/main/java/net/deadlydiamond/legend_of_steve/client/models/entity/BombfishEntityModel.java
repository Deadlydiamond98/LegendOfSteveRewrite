package net.deadlydiamond.legend_of_steve.client.models.entity;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.WaterBombEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class BombfishEntityModel<T extends Entity> extends EntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(LegendOfSteve.id("bombfish"), "main");

	private final ModelPart body;
	private final ModelPart bodyFront;
	private final ModelPart bodyMiddle;
	private final ModelPart bodyBack;
	private final ModelPart leftFin;
	private final ModelPart rightFin;
	private final ModelPart head;
	private final ModelPart bomb;
	private final ModelPart upperJaw;
	private final ModelPart upperTeeth;
	private final ModelPart lowerJaw;
	private final ModelPart lowerTeeth;

	public BombfishEntityModel(ModelPart root) {
		this.body = root.getChild("body");
		this.bodyFront = this.body.getChild("bodyFront");
		this.bodyMiddle = this.bodyFront.getChild("bodyMiddle");
		this.bodyBack = this.bodyMiddle.getChild("bodyBack");
		this.leftFin = this.bodyMiddle.getChild("leftFin");
		this.rightFin = this.bodyMiddle.getChild("rightFin");
		this.head = this.bodyFront.getChild("head");
		this.bomb = this.head.getChild("bomb");
		this.upperJaw = this.head.getChild("upperJaw");
		this.upperTeeth = this.upperJaw.getChild("upperTeeth");
		this.lowerJaw = this.head.getChild("lowerJaw");
		this.lowerTeeth = this.lowerJaw.getChild("lowerTeeth");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 18.75F, 0.0F));

		ModelPartData bodyFront = body.addChild("bodyFront", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.75F, 6.25F));

		ModelPartData bodyMiddle = bodyFront.addChild("bodyMiddle", ModelPartBuilder.create().uv(0, 26).cuboid(-3.5F, -3.05F, 0.5F, 7.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, -5.5F));

		ModelPartData bodyBack = bodyMiddle.addChild("bodyBack", ModelPartBuilder.create().uv(0, 38).cuboid(-2.0F, -2.05F, 0.0F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 5.5F));

		ModelPartData cube_r1 = bodyBack.addChild("cube_r1", ModelPartBuilder.create().uv(20, 43).cuboid(0.0F, -2.0F, -2.0F, 0.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 3.9F, 0.1745F, 0.0F, 0.0F));

		ModelPartData tailFin_r1 = bodyBack.addChild("tailFin_r1", ModelPartBuilder.create().uv(14, 33).cuboid(0.0F, -4.0F, 0.0F, 0.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 6.0F, 0.1047F, 0.0F, 0.0F));

		ModelPartData leftFin = bodyMiddle.addChild("leftFin", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, -1.5F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.5F, -1.25F, 4.0F, 0.5996F, 0.1245F, -0.1796F));

		ModelPartData rightFin = bodyMiddle.addChild("rightFin", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(0.0F, 0.0F, -1.5F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.5F, -1.25F, 4.0F, 0.5996F, -0.1245F, 0.1796F));

		ModelPartData head = bodyFront.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 7.0F, -9.5F));

		ModelPartData bomb = head.addChild("bomb", ModelPartBuilder.create().uv(0, 49).cuboid(-4.0F, -6.7F, -3.5F, 8.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData upperJaw = head.addChild("upperJaw", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -5.0F, -9.0F, 9.0F, 5.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 4.5F, -0.2618F, 0.0F, 0.0F));

		ModelPartData upperTeeth = upperJaw.addChild("upperTeeth", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData upperTeeth_r1 = upperTeeth.addChild("upperTeeth_r1", ModelPartBuilder.create().uv(28, 6).cuboid(-4.0F, -3.0F, -4.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.0625F)), ModelTransform.of(0.0F, 1.9F, -4.0F, 0.2618F, 0.0F, 0.0F));

		ModelPartData lowerJaw = head.addChild("lowerJaw", ModelPartBuilder.create().uv(0, 14).cuboid(-4.5F, 0.0F, -9.0F, 9.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 4.5F, 0.3927F, 0.0F, 0.0F));

		ModelPartData lowerTeeth = lowerJaw.addChild("lowerTeeth", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData lowerTeeth_r1 = lowerTeeth.addChild("lowerTeeth_r1", ModelPartBuilder.create().uv(28, 18).cuboid(-4.0F, -3.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0625F)), ModelTransform.of(0.0F, 2.4F, -5.1F, -0.2182F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = 1.0F;
		float g = 1.0F;
		boolean bl = entity.isTouchingWater();

		if (!bl) {
			f = 1.3F;
			g = 1.7F;
		}

		double vX = entity.getVelocity().horizontalLengthSquared();
		float rate = bl ? Math.max(Math.min(0.125f, (float) (vX * 25)), 0.03125f) : 0.125f;

		this.bodyMiddle.yaw = -f * rate * MathHelper.sin(g * 0.6f * ageInTicks);
		this.bodyBack.yaw = -f * rate * MathHelper.sin(g * 0.6f * ageInTicks);

		this.body.pitch = headPitch * 0.5f * (float) (Math.PI / 180.0);
		this.body.yaw = netHeadYaw * (float) (Math.PI / 180.0);

		if (vX > 1.0E-7) {
			this.body.pitch = this.body.pitch + (-0.05F - 0.05F * MathHelper.cos(ageInTicks * 0.3F));
		}
	}

	public void setDeadAngles(WaterBombEntity entity, float tickDelta) {
		this.bodyMiddle.yaw = entity.getTailAngle(tickDelta);
		this.bodyBack.yaw = entity.getTailAngle(tickDelta);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		this.body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	public void renderChargedLayer(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		this.upperTeeth.visible = false;
		this.lowerTeeth.visible = false;
		render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		this.upperTeeth.visible = true;
		this.lowerTeeth.visible = true;
	}
}