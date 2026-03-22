package net.deadlydiamond.legend_of_steve.mixin.common.fluid;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LavaFluid.class)
public class LavaFluidMixin {
    @WrapMethod(method = "flow")
    private void legend_of_steve$flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState, Operation<Void> original) {
        LavaFluid lavaFluid = (LavaFluid) (Object) this;

        if (direction == Direction.DOWN) {
            FluidState fluidState2 = world.getFluidState(pos);
            if (lavaFluid.isIn(FluidTags.LAVA) && fluidState2.isIn(ZeldaTags.ENCHANTED_SPRING_WATER)) {
                if (state.getBlock() instanceof FluidBlock) {
                    world.setBlockState(pos, ZeldaBlocks.FAIRY_MARBLE.base.getDefaultState(), Block.NOTIFY_ALL);
                }

                world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
                return;
            }
        }
        original.call(world, pos, state, direction, fluidState);
    }
}
