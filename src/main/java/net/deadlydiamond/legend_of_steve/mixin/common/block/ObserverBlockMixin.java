package net.deadlydiamond.legend_of_steve.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.ObserverBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ObserverBlock.class)
public class ObserverBlockMixin {
//    @WrapMethod(method = "getStateForNeighborUpdate")
//    private BlockState legend_of_steve$getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, Operation<BlockState> original) {
//        return neighborState.isOf(ZeldaBlocks.CRYSTAL_SWITCH) ? state : original.call(state, direction, neighborState, world, pos, neighborPos);
//    }
}
