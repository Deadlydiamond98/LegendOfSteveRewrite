package net.deadlydiamond.legend_of_steve.client.rendering.entity.projectile;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.SwordBeamEntity;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class SwordBeamEntityRenderer<T extends SwordBeamEntity> extends EntityRenderer<T>  {
    public static final Identifier TEXTURE = LegendOfSteve.id("textures/entity/sword_beam/sword_beam.png");

    public SwordBeamEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        Vec3d velocity = entity.getVelocity();
        float yawAngle = (float) (Math.atan2(velocity.z, velocity.x) * (180 / Math.PI));
        float pitchAngle = (float) (Math.atan2(velocity.y, Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z)) * (180 / Math.PI));
        matrices.translate(0, 0.25f, 0);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-yawAngle));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(pitchAngle + 45));

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ZeldaRenderLayers.getEntityUnlit(getTexture(entity)));
        MatrixStack.Entry matrixEntry = matrices.peek();
        Matrix4f modelMatrix = matrixEntry.getPositionMatrix();
        Matrix3f normalMatrix = matrixEntry.getNormalMatrix();

        float v = 0.25f * (entity.age % 4);

        //Render
        int emissiveLight = 15728880;
        vertexConsumer.vertex(modelMatrix, -0.25f, 0.25f, 0).color(255, 255, 255, 255).texture(0, v + 0.25f).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, 0.25f, 0.25f, 0).color(255, 255, 255, 255).texture(1, v + 0.25f).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, 0.25f, -0.25f, 0).color(255, 255, 255, 255).texture(1, v).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, 1, 0).next();
        vertexConsumer.vertex(modelMatrix, -0.25f, -0.25f, 0).color(255, 255, 255, 255).texture(0, v).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, 1, 0).next();

        vertexConsumer.vertex(modelMatrix, -0.25f, -0.25f, 0).color(255, 255, 255, 255).texture(0, v).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, -1, 0).next();
        vertexConsumer.vertex(modelMatrix, 0.25f, -0.25f, 0).color(255, 255, 255, 255).texture(1, v).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, -1, 0).next();
        vertexConsumer.vertex(modelMatrix, 0.25f, 0.25f, 0).color(255, 255, 255, 255).texture(1, v + 0.25f).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, -1, 0).next();
        vertexConsumer.vertex(modelMatrix, -0.25f, 0.25f, 0).color(255, 255, 255, 255).texture(0, v + 0.25f).overlay(OverlayTexture.DEFAULT_UV).light(emissiveLight).normal(normalMatrix, 0, -1, 0).next();

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
