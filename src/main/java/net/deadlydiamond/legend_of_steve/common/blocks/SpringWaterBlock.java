package net.deadlydiamond.legend_of_steve.common.blocks;

import net.deadlydiamond.legend_of_steve.common.blocks.light.IGlowingBlock;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;

public class SpringWaterBlock extends FluidBlock {
    public SpringWaterBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

//    @Override
//    public boolean stopZFighting() {
//        return false;
//    }
}
