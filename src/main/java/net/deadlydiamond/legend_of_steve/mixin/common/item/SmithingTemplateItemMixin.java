package net.deadlydiamond.legend_of_steve.mixin.common.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(SmithingTemplateItem.class)
public class SmithingTemplateItemMixin {
    @ModifyReturnValue(method = "getNetheriteUpgradeEmptyBaseSlotTextures", at = @At("RETURN"))
    private static List<Identifier> aam$getNetheriteUpgradeEmptyBaseSlotTextures(List<Identifier> original) {
        List<Identifier> textures = new ArrayList<>(original);
        textures.add(LegendOfSteve.id("item/slot/quiver"));
        textures.add(LegendOfSteve.id("item/slot/bomb_bag"));
        return textures;
    }
}
