package net.deadlydiamond.legend_of_steve.client.rendering.player.itemmodel;

import net.deadlydiamond.legend_of_steve.client.models.entity.BombOverlayModel;
import net.deadlydiamond.legend_of_steve.client.models.entity.BombEntityModel;
import net.deadlydiamond.legend_of_steve.client.rendering.IBombRenderer;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.BombEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;

public class BombHeldItemRenderer extends CustomHeldItemRenderer implements IBombRenderer {

    @Override
    public boolean isValid(LivingEntity entity, Arm arm, ItemStack itemStack) {
        return isBomb(itemStack) && !entity.isInSwimmingPose() && !entity.isUsingItem() && !hasTwoBombs(entity);
    }

    @Override
    public void render(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel model, HeldItemRenderer playerHeldItemRenderer) {
        if (entity instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                BombEntityModel<BombEntity> bombModel = new BombEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(BombEntityModel.LAYER_LOCATION));
                BombOverlayModel<BombEntity> bombOverlayModel = new BombOverlayModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(BombEntityModel.LAYER_LOCATION));

                matrices.push();

                model.rightArm.yaw = 0.0F;
                model.leftArm.yaw = 0.0F;

                float pitch = ((float) (-Math.PI / 2) + model.head.pitch) * 0.7f - ((float) Math.PI * 0.56f);
                pitch = Math.min(-2.54f, pitch);

                float yawValue = model.head.yaw * ((-1 * Math.abs(model.head.pitch)) + 1.5f);

                model.rightArm.pitch = pitch - Math.min(0.65f, Math.max(yawValue, 0));
                model.leftArm.pitch = pitch + Math.max(-0.65f, Math.min(yawValue, 0));

                double yPos = 1.4;
                if (entity.isInSneakingPose()) {
                    yPos = 1.3f;
                }

                float armPitch = ((model.leftArm.pitch + model.rightArm.pitch) / 2.0f) - 0.1f;

                matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) (armPitch + Math.PI)));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(90));

                float scale = 1.9f;
                matrices.scale(-scale, -scale, scale);
                double armSide = arm == Arm.RIGHT ? 1 : -1;
                matrices.translate(0.09375 * armSide, yPos, 0);

                HeadFeatureRenderer.translate(matrices, false);
                boolean bl = arm == Arm.LEFT;
                matrices.translate((bl ? -2.5F : 2.5F) / 16.0F, -0.0625F, 0.0F);
                VertexConsumer vCon = vertexConsumers.getBuffer(bombModel.getLayer(getBombTexture(stack.getItem())));
                bombModel.render(matrices, vCon, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
                bombModel.renderFuse(matrices, vCon, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);

                if (stack.isIn(ZeldaTags.CHARGED)) {
                    matrices.push();
                    matrices.translate(0, -0.25 - 0.0625, 0);
                    matrices.scale(1.25f, 1.25f, 1.25f);
                    VertexConsumer chargedOverlay = getChargedLayer(entity, vertexConsumers, MinecraftClient.getInstance().getTickDelta());
                    bombModel.renderOverlay(matrices, chargedOverlay, 15728640, OverlayTexture.DEFAULT_UV, 0.5f, 0.5f, 0.5f, 1);
                    matrices.pop();
                }

                matrices.pop();
            }
        }
    }
}
