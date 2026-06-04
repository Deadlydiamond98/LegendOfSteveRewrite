package net.deadlydiamond.legend_of_steve.client.models.block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.switches.ISwitchBlock;
import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class SwitchBlockBakedModel implements BakedModel {
    private final BakedModel onModel;
    private final BakedModel offModel;

    public SwitchBlockBakedModel(BakedModel regularModel, BakedModel riverModel) {
        this.onModel = regularModel;
        this.offModel = riverModel;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
//        if (state.getBlock() instanceof ISwitchBlock) {
//            SwitchBlockManager.saveBlockPos(pos);
//        }

        if (blockView.getBlockEntityRenderData(pos) instanceof Boolean bl) {
            if (bl) {
                onModel.emitBlockQuads(blockView, state, pos, randomSupplier, context);
            } else {
                offModel.emitBlockQuads(blockView, state, pos, randomSupplier, context);
            }
        }
    }


    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        onModel.emitItemQuads(stack, randomSupplier, context);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return true;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return onModel.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return ModelHelper.MODEL_TRANSFORM_BLOCK;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }
}
