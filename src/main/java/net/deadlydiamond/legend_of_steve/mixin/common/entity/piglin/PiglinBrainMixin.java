package net.deadlydiamond.legend_of_steve.mixin.common.entity.piglin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @ModifyReturnValue(method = "wearsGoldArmor", at = @At("RETURN"))
    private static boolean legend_of_steve$wearsGoldArmor(boolean original, @Local(argsOnly = true) LivingEntity entity) {
        for (ItemStack itemStack : entity.getArmorItems()) {
            // TODO: I could probably make this a tag :/
            if (itemStack.isOf(ZeldaItems.GILDED_QUIVER)) {
                return true;
            }
        }
        return original;
    }
}
