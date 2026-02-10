package net.deadlydiamond.legend_of_steve.mixin.common;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LavaFluid.class)
public class LavaFluidMixin {
    @WrapWithCondition(method = "flow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean legend_of_steve$flow(WorldAccess instance, BlockPos pos, BlockState state, int i) {
        FluidState fluidState = instance.getFluidState(pos);
        if (fluidState.isIn(ZeldaTags.ENCHANTED_SPRING_WATER)) {
            instance.setBlockState(pos, ZeldaBlocks.FAIRY_MARBLE.base.getDefaultState(), i);
            return false;
        }
        return true;
    }

//    @ModifyReturnValue(method = "canBeReplacedWith", at = @At("RETURN"))
//    private boolean legend_of_steve$canBeReplacedWith(boolean original, @Local(argsOnly = true) FluidState state, @Local(argsOnly = true) BlockView world, @Local(argsOnly = true) BlockPos pos, @Local(argsOnly = true) Fluid fluid) {
//        return original || (state.getHeight(world, pos) >= 0.44444445F && fluid.isIn(ZeldaTags.ENCHANTED_SPRING_WATER));
//    }
}
