package net.deadlydiamond.legend_of_steve.mixin.common.fluid;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WaterFluid.class)
public class WaterFluidMixin {

    @WrapOperation(method = "canBeReplacedWith", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/Fluid;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean legend_of_steve$canBeReplacedWith(Fluid instance, TagKey<Fluid> tag, Operation<Boolean> original) {
        return original.call(instance, tag) && (!instance.isIn(ZeldaTags.ENCHANTED_SPRING_WATER));
    }
}
