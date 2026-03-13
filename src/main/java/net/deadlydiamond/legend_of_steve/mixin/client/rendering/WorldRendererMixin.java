package net.deadlydiamond.legend_of_steve.mixin.client.rendering;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.deadlydiamond.legend_of_steve.client.rendering.block.baked.BakedBlockEntityRenderer;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow protected abstract void renderLayer(RenderLayer renderLayer, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f positionMatrix);

    @Shadow private @Nullable ClientWorld world;

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderLayer(Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack;DDDLorg/joml/Matrix4f;)V"))
    private void legend_of_steve$render(WorldRenderer instance, RenderLayer renderLayer, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f positionMatrix, Operation<Void> original) {
        if (renderLayer == RenderLayer.getCutout()) {
            renderLayer(ZeldaRenderLayers.IRIDESCENCE, matrices, cameraX, cameraY, cameraZ, positionMatrix);
        }
        original.call(instance, renderLayer, matrices, cameraX, cameraY, cameraZ, positionMatrix);
    }

    @Inject(method = "setWorld", at = @At("RETURN"))
    public void legend_of_steve$setWorld(ClientWorld clientWorld, CallbackInfo ci) {
        BakedBlockEntityRenderer.Manager.setWorld(clientWorld);
    }

    @Inject(method = "scheduleBlockRerenderIfNeeded", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;scheduleBlockRenders(IIIIII)V"))
    private void legend_of_steve$scheduleBlockRerenderIfNeeded(BlockPos pos, BlockState old, BlockState updated, CallbackInfo ci) {
        BlockPos.iterateOutwards(pos, 1, 1, 1).forEach(BakedBlockEntityRenderer.Manager::markForRebuild);
    }
}
