package net.deadlydiamond.legend_of_steve.mixin.client.rendering.entity.stun;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaStunned;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererStunMixin<T extends LivingEntity> {

    @ModifyArgs(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    private void legend_of_steve$renderColor(Args args, @Local(argsOnly = true, ordinal = 0) T entity) {
        if (legend_of_steve$isStunned(entity)) {
            args.set(4, 0.2784313725490196f); // red
            args.set(5, 0.4117647058823529f); // green
        }
    }

    @WrapMethod(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void legend_of_steve$render(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, Operation<Void> original) {
        // Ignore error, this works
        original.call(livingEntity, f, legend_of_steve$isStunned(livingEntity) ? 0 : g, matrixStack, vertexConsumerProvider, i);
    }

    @WrapMethod(method = "getAnimationProgress")
    private float legend_of_steve$getAnimationProgress(T entity, float tickDelta, Operation<Float> original) {
        // Ignore error, this works
        return legend_of_steve$isStunned(entity) ? 0 : original.call(entity, tickDelta);
    }

    @WrapMethod(method = "isShaking")
    private boolean legend_of_steve$isShaking(T entity, Operation<Boolean> original) {
        // Ignore error, this works
        return original.call(entity) && !legend_of_steve$isStunned(entity);
    }

    @Unique
    private boolean legend_of_steve$isStunned(T entity) {
        return entity instanceof IZeldaStunned stunned && stunned.legend_of_steve$isStunned();
    }
}
