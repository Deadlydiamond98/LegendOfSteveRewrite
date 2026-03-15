package net.deadlydiamond.legend_of_steve.mixin.client.rendering;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.TestGlowingBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(ChunkBuilder.BuiltChunk.RebuildTask.class)
public class ChunkBuilderMixin {

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/BlockRenderManager;renderBlock(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLnet/minecraft/util/math/random/Random;)V"))
    private void temp(BlockRenderManager instance, BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrices, VertexConsumer vertexConsumer, boolean cull, Random random, Operation<Void> original, @Local BlockBufferBuilderStorage storage, @Local Set<RenderLayer> set) {
        if (state.isOf(ZeldaBlocks.BLOOM_ALTERNATIVE_TEST)) {
            BlockState secondState = state.with(TestGlowingBlock.GLOWING, true);

            RenderLayer secondLayer = RenderLayer.getSolid();
            BufferBuilder secondLayerBuffer = storage.get(secondLayer);

            if (set.add(secondLayer)) {
                secondLayerBuffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
            }

            original.call(instance, secondState, pos, world, matrices, secondLayerBuffer, cull, random);
        }
        original.call(instance, state, pos, world, matrices, vertexConsumer, cull, random);
    }
}
