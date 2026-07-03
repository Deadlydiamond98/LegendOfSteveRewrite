package net.deadlydiamond.legend_of_steve.client.models.block;

import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.util.function.Supplier;

public class LockedBlockBakedModel extends ForwardingBakedModel {

    public LockedBlockBakedModel(BakedModel wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        if (blockView.getBlockEntityRenderData(pos) instanceof BlockState lockedBlock) {
            MinecraftClient.getInstance().getBlockRenderManager().getModel(lockedBlock).emitBlockQuads(
                    blockView, lockedBlock, pos, randomSupplier, context
            );
        }
    }
}
