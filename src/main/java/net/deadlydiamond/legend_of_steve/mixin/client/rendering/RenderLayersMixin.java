package net.deadlydiamond.legend_of_steve.mixin.client.rendering;


import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.TestGlowingBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderLayers.class)
public class RenderLayersMixin {
    @Definition(id = "renderLayer", local = @Local(type = RenderLayer.class))
    @Definition(id = "getTranslucent", method = "Lnet/minecraft/client/render/RenderLayer;getTranslucent()Lnet/minecraft/client/render/RenderLayer;")
    @Expression("renderLayer == getTranslucent()")
    @Inject(method = "getEntityBlockLayer", at = @At("MIXINEXTRAS:EXPRESSION"), cancellable = true)
    private static void addIridescentLayer(BlockState state, boolean direct, CallbackInfoReturnable<RenderLayer> cir, @Local RenderLayer blockLayer) {
        if (blockLayer == ZeldaRenderLayers.IRIDESCENCE) {
            cir.setReturnValue(ZeldaRenderLayers.ENTITY_IRIDESCENCE_TEXTURED);
        }
    }

    @ModifyReturnValue(method = "getBlockLayer", at = @At("RETURN"))
    private static RenderLayer test(RenderLayer original, @Local BlockState state) {
        if (state.isOf(ZeldaBlocks.BLOOM_ALTERNATIVE_TEST) && state.get(TestGlowingBlock.GLOWING)) {
            return RenderLayer.getSolid();
        }
        return original;
    }
}