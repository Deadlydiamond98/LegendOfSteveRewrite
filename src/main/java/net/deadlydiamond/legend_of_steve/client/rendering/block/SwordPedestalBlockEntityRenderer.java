package net.deadlydiamond.legend_of_steve.client.rendering.block;

import net.deadlydiamond.legend_of_steve.common.bes.container.single.SwordPedestalBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class SwordPedestalBlockEntityRenderer implements BlockEntityRenderer<SwordPedestalBlockEntity> {
    private static final List<SwordPedestalRendererEntry> SWORD_PEDESTALS = new ArrayList<>();

    public SwordPedestalBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(SwordPedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.isEmpty()) {
            SWORD_PEDESTALS.add(new SwordPedestalRendererEntry(entity, light, overlay));
        }
    }

    // It's rendered here rather than the regular renderer to prevent the block breaking overlay on the item
    // (There is probably a much better way to do this, but it works)
    public static void renderSword(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        matrices.push();

        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        SWORD_PEDESTALS.forEach(entry -> {
            matrices.push();

            BlockPos pos = entry.entity.getPos();
            matrices.translate(pos.getX(), pos.getY(), pos.getZ());


            matrices.translate(0.5, 0.5, 0.5);

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(
                    entry.entity.getCachedState().get(Properties.HORIZONTAL_FACING).asRotation()
            ));

            matrices.translate(0, 0.25, 0);

            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(135));

            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    entry.entity.getStack(0),
                    ModelTransformationMode.FIXED,
                    entry.light,
                    entry.overlay,
                    matrices,
                    vertexConsumers,
                    entry.entity.getWorld(),
                    0
            );

            matrices.pop();
        });

        matrices.pop();

        SWORD_PEDESTALS.clear();
    }


    private record SwordPedestalRendererEntry(SwordPedestalBlockEntity entity, int light, int overlay) {}
}
