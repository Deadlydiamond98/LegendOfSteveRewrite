package net.deadlydiamond.legend_of_steve.mixin.client.model;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.init.client.ZeldaRenderLayers;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;

@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin {

    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Ljava/util/Map;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;"))
    private static Map<Identifier, Identifier> addCustomAtlases(Map<Identifier, Identifier> original) {
        original = new HashMap<>(original);

        original.put(ZeldaRenderLayers.IRIDESCENT_ATLAS_TEXTURE, LegendOfSteve.id("iridescent"));
        original.put(ZeldaRenderLayers.NORMAL_MAPS_ATLAS_TEXTURE, LegendOfSteve.id("normal_maps"));

        return original;
    }
}