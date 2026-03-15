package net.deadlydiamond.legend_of_steve.mixin.client.rendering;


import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(RenderLayer.class)
public class RenderLayerMixin {
    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;", remap = false))
    private static ImmutableList<RenderLayer> addLayers(ImmutableList<RenderLayer> original) {
        List<RenderLayer> list = new ArrayList<>(original);

        list.add(ZeldaRenderLayers.IRIDESCENCE);
        list.add(ZeldaRenderLayers.BLOOM_GLOW);

        return ImmutableList.copyOf(list);
    }
}
