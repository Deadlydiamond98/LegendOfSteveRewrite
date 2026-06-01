package net.deadlydiamond.legend_of_steve.mixin.client.rendering.world;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.deadlydiamond.legend_of_steve.common.blocks.IModifiedOutlineRender;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaShaders;
import net.deadlydiamond98.koalalib.client.PostProcessingRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Unique private boolean legend_of_steve$renderGlowLayer = true;
    @Shadow protected abstract void renderLayer(RenderLayer renderLayer, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f positionMatrix);

    // Renders Iridescence & Bloom Blocks

    @Shadow public abstract void playSong(@Nullable SoundEvent song, BlockPos songPosition);

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderLayer(Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack;DDDLorg/joml/Matrix4f;)V"))
    private void legend_of_steve$renderRegularLayers(WorldRenderer instance, RenderLayer renderLayer, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f positionMatrix, Operation<Void> original) {
        if (renderLayer == RenderLayer.getCutout()) {
            renderLayer(ZeldaRenderLayers.IRIDESCENCE, matrices, cameraX, cameraY, cameraZ, positionMatrix);
        }
        original.call(instance, renderLayer, matrices, cameraX, cameraY, cameraZ, positionMatrix);
    }
    
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OutlineVertexConsumerProvider;draw()V", shift = At.Shift.BEFORE), order = 900)
    private void legend_of_steve$renderPostLayers(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci) {
        Vec3d cameraPos = camera.getPos();
        renderLayer(ZeldaRenderLayers.BLOOM_GLOW, matrices, cameraPos.x, cameraPos.y, cameraPos.z, projectionMatrix);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Prevents Bloom Glowing Shader from attempting to render if it's unnecessary
    // This optimization is probably not needed... but eh

    @Inject(method = "renderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/gl/VertexBuffer;"))
    private void legend_of_steve$renderLayer(RenderLayer renderLayer, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f positionMatrix, CallbackInfo ci) {
        if (renderLayer == ZeldaRenderLayers.BLOOM_GLOW && this.legend_of_steve$renderGlowLayer) {
            PostProcessingRegistry.renderEffectForNextTick(ZeldaShaders.BLOOM_GLOWING_SHADER_ID);
            this.legend_of_steve$renderGlowLayer = false;
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void legend_of_steve$renderFinish(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci) {
        this.legend_of_steve$renderGlowLayer = true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Custom Block Outline

    @WrapOperation(method = "drawBlockOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getOutlineShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;"))
    private VoxelShape legend_of_steve$drawBlockOutline(BlockState instance, BlockView blockView, BlockPos pos, ShapeContext shapeContext, Operation<VoxelShape> original) {
        return instance.getBlock() instanceof IModifiedOutlineRender modified ?
                modified.getRenderedOutlineShape(instance, blockView, pos, shapeContext) :
                original.call(instance, blockView, pos, shapeContext);
    }

    @WrapMethod(method = "drawBlockOutline")
    private void legend_of_steve$drawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos pos, BlockState state, Operation<Void> original) {
        if (state.getBlock() instanceof IModifiedOutlineRender modified) {
            pos = pos.add(modified.getOffset(pos, state));
        }

        original.call(matrices, vertexConsumer, entity, cameraX, cameraY, cameraZ, pos, state);
    }
}
