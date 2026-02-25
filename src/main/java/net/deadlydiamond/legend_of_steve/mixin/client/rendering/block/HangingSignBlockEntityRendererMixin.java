package net.deadlydiamond.legend_of_steve.mixin.client.rendering.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.common.blocks.sign.ICustomSign;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HangingSignBlockEntityRenderer.class)
public class HangingSignBlockEntityRendererMixin extends SignBlockEntityRendererMixin {

    @WrapMethod(method = "getTextureId")
    private SpriteIdentifier legend_of_steve$getTextureId(WoodType signType, Operation<SpriteIdentifier> original) {
        if (this.legend_of_steve$signBlockEntity != null && this.legend_of_steve$signBlockEntity.getCachedState().getBlock() instanceof ICustomSign sign) {
            return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, sign.getTexture());
        }
        return original.call(signType);
    }
}
