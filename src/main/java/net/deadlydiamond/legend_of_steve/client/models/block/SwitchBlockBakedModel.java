package net.deadlydiamond.legend_of_steve.client.models.block;

import net.deadlydiamond.legend_of_steve.client.switches.SwitchBlockAtlas;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.SpriteFinder;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.util.function.Supplier;

public class SwitchBlockBakedModel extends ForwardingBakedModel {

    public SwitchBlockBakedModel(BakedModel baseModel) {
        this.wrapped = baseModel;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        SpriteFinder finder = SpriteFinder.get(MinecraftClient.getInstance().getBakedModelManager().getAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));

        String groupID;
        if (blockView.getBlockEntityRenderData(pos) instanceof String beGroupID) {
            groupID = beGroupID;
        } else {
            groupID = "Global";
        }

        context.pushTransform(quad -> {
            Sprite sprite = finder.find(quad);

            if (SwitchBlockAtlas.INSTANCE != null) {
                Sprite switchSprite = SwitchBlockAtlas.INSTANCE.getSprite(groupID, sprite.getContents().getId());
                if (switchSprite != null) {
                    quad.spriteBake(switchSprite, MutableQuadView.BAKE_LOCK_UV);
                }
            }

            return true;
        });
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        context.popTransform();
    }
}
