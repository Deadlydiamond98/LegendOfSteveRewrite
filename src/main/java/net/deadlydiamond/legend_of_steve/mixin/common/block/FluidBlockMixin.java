package net.deadlydiamond.legend_of_steve.mixin.common.block;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin {
    @Shadow @Final protected FlowableFluid fluid;

    @Shadow @Final public static ImmutableList<Direction> FLOW_DIRECTIONS;

    @Shadow protected abstract void playExtinguishSound(WorldAccess world, BlockPos pos);

    @WrapMethod(method = "receiveNeighborFluids")
    private boolean legend_of_steve$receiveNeighborFluids(World world, BlockPos pos, BlockState state, Operation<Boolean> original) {
        if (this.fluid.isIn(FluidTags.LAVA)) {
            for (Direction direction : FLOW_DIRECTIONS) {
                BlockPos blockPos = pos.offset(direction.getOpposite());
                if (world.getFluidState(blockPos).isIn(ZeldaTags.ENCHANTED_SPRING_WATER)) {
                    Block block = world.getFluidState(pos).isStill() ? Blocks.CRYING_OBSIDIAN : ZeldaBlocks.COBBLED_FAIRY_MARBLE.base;
                    world.setBlockState(pos, block.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    return false;
                }
            }
        }
        return original.call(world, pos, state);
    }
}
