package net.deadlydiamond.legend_of_steve.mixin.client.rendering;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaShaders;
import net.deadlydiamond98.koalalib.client.PostProcessingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Unique private boolean legend_of_steve$renderGlowing = true;

    @Shadow protected abstract void renderLayer(RenderLayer renderLayer, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f positionMatrix);
    @Shadow @Final private MinecraftClient client;

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderLayer(Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack;DDDLorg/joml/Matrix4f;)V"))
    private void legend_of_steve$render(WorldRenderer instance, RenderLayer renderLayer, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f positionMatrix, Operation<Void> original) {
        if (renderLayer == RenderLayer.getCutout()) {
            renderLayer(ZeldaRenderLayers.IRIDESCENCE, matrices, cameraX, cameraY, cameraZ, positionMatrix);
        }

        original.call(instance, renderLayer, matrices, cameraX, cameraY, cameraZ, positionMatrix);
    }
    
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/chunk/ChunkBuilder$ChunkData;getBlockEntities()Ljava/util/List;", shift = At.Shift.BEFORE), order = 900)
    private void test(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci) {
        if (legend_of_steve$renderGlowing) {
            Vec3d cameraPos = camera.getPos();
            PostProcessingRegistry.renderEffectForNextTick(ZeldaShaders.BLOOM_GLOWING_SHADER_ID);
            renderLayer(ZeldaRenderLayers.BLOOM_GLOW, matrices, cameraPos.x, cameraPos.y, cameraPos.z, projectionMatrix);
            legend_of_steve$renderGlowing = false;
        }
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    private void testAgain(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci) {
        legend_of_steve$renderGlowing = true;
    }
}
