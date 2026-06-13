package net.deadlydiamond.legend_of_steve.client.models.block.connected;

import net.deadlydiamond.legend_of_steve.client.models.block.transforms.AxisQuadTransform;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ConnectedPillarBakedModel implements BakedModel {
    private final Sprite[] sprites;

    public ConnectedPillarBakedModel(Sprite[] sprites) {
        this.sprites = sprites;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        BlockState pillar = blockView.getBlockState(pos);
        Direction.Axis axis = getAxis(pillar);

        QuadEmitter emitter = context.getEmitter();
        context.pushTransform(new AxisQuadTransform(axis));

        int direction = axis == Direction.Axis.Z ? -1 : 1;

        for (Direction side : Direction.values()) {
            ConnectedTextureTypes.Pillar texture = getInitTexture(side);

            if (side.getHorizontal() != -1) {
                if (side.getAxis().isHorizontal()) {
                    boolean connectAbove = canConnect(blockView, pillar, pos.offset(axis, direction));
                    boolean connectBelow = canConnect(blockView, pillar, pos.offset(axis, -direction));

                    if (connectAbove && connectBelow) {
                        texture = ConnectedTextureTypes.Pillar.MIDDLE;
                    } else if (connectAbove) {
                        texture = ConnectedTextureTypes.Pillar.BOTTOM;
                    } else if (connectBelow) {
                        texture = ConnectedTextureTypes.Pillar.TOP;
                    }
                }
            }

            renderQuad(emitter, side, texture);
        }
        context.popTransform();
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        for (Direction side : Direction.values()) {
            renderQuad(context.getEmitter(), side, getInitTexture(side));
        }
    }

    private ConnectedTextureTypes.Pillar getInitTexture(Direction side) {
        return side.getHorizontal() != -1 ? ConnectedTextureTypes.Pillar.SINGLE : ConnectedTextureTypes.Pillar.END;
    }

    private void renderQuad(QuadEmitter emitter, Direction side, ConnectedTextureTypes.Pillar texture) {
        emitter.square(side, 0, 0, 1, 1, 0);
        emitter.spriteBake(sprites[texture.ordinal()], MutableQuadView.BAKE_LOCK_UV);
        emitter.color(-1, -1, -1, -1);
        emitter.emit();
    }

    // Connection & Orientation ////////////////////////////////////////////////////////////////////////////////////////

    private static boolean canConnect(BlockRenderView blockView, BlockState originState, BlockPos otherPos) {
        BlockState otherState = blockView.getBlockState(otherPos);

        if (!otherState.isOf(originState.getBlock())) {
            return false;
        }

        return getAxis(otherState) == getAxis(originState);
    }

    private static Direction.Axis getAxis(BlockState pillar) {
        if (pillar.contains(Properties.AXIS)) {
            return pillar.get(Properties.AXIS);
        }
        return Direction.Axis.Y;
    }

    // OTHER MODEL STUFF ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
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
        return this.sprites[0];
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
