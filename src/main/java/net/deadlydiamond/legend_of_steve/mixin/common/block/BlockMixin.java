package net.deadlydiamond.legend_of_steve.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.switches.ISwitchBlock;
import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public class BlockMixin {
    @WrapMethod(method = "shouldDrawSide")
    private static boolean legend_of_steve$shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, Operation<Boolean> original) {
        boolean bl = original.call(state, world, pos, side, otherPos);
        if (bl && state.getBlock() instanceof ISwitchBlock) {
            SwitchBlockManager.saveBlockPos(pos);
        }
        return bl;
    }
}
