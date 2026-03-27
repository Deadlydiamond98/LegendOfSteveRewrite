package net.deadlydiamond.legend_of_steve.client.rendering.player.itemmodel;

import net.deadlydiamond.legend_of_steve.client.rendering.player.ZeldaPlayerRendering;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

public class ThrownBlockHeldItemRenderer extends CustomHeldItemRenderer {

    @Override
    public boolean isValid(LivingEntity entity, Arm arm, ItemStack itemStack) {
        if (!ZeldaPlayerRendering.BOMB_ITEM_RENDERER.isValid(entity, arm, itemStack)) {
            if (!(itemStack.getItem() instanceof BlockItem)) {
                return false;
            }

            boolean cooldown = false;
            if (entity instanceof PlayerEntity player) {
                cooldown = player.getItemCooldownManager().isCoolingDown(itemStack.getItem());
            }
            return itemStack.isIn(ZeldaTags.HELD_OVER_HEAD) && !entity.isInSwimmingPose() && !entity.isUsingItem() && !cooldown;
        }
        return false;
    }

    @Override
    public void render(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, BipedEntityModel model, HeldItemRenderer playerHeldItemRenderer) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (stack.getItem() instanceof BlockItem block) {
            matrices.push();

            model.rightArm.yaw = 0.0F;
            model.leftArm.yaw = 0.0F;

            float pitch = ((float) (-Math.PI / 2) + model.head.pitch) * 0.7f - ((float) Math.PI * 0.56f);
            pitch = Math.min(-2.54f, pitch);

            float yawValue = model.head.yaw * ((-1 * Math.abs(model.head.pitch)) + 1.5f);

            model.rightArm.pitch = pitch - Math.min(0.65f, Math.max(yawValue, 0));
            model.leftArm.pitch = pitch + Math.max(-0.65f, Math.min(yawValue, 0));

            double yPos = -0.0125;
            if (entity.isInSneakingPose()) {
                yPos = 0.125;
            }

            float armPitch = ((model.leftArm.pitch + model.rightArm.pitch) / 2.0f) - 0.1f;

            matrices.multiply(RotationAxis.POSITIVE_X.rotation(armPitch));

            float scale = 1.9f;
            matrices.scale(-scale, -scale, scale);
            matrices.translate(0.3125, yPos, -0.3125);

            HeadFeatureRenderer.translate(matrices, false);

            BlockRenderManager blockRenderer = client.getBlockRenderManager();
            BlockState state = block.getBlock().getDefaultState();
            BlockPos pos = entity.getBlockPos().up().up();

            blockRenderer.getModelRenderer().render(
                    entity.getWorld(),
                    blockRenderer.getModel(state),
                    state,
                    pos,
                    matrices,
                    vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(state)),
                    false,
                    Random.create(),
                    state.getRenderingSeed(pos),
                    OverlayTexture.DEFAULT_UV
            );

            matrices.pop();
        }
    }
}
