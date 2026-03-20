package net.deadlydiamond.legend_of_steve.worldgen.structure.processor;

import com.mojang.serialization.Codec;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.worldgen.ZeldaStructures;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;


public class FairyFountainStructureProcessor extends StructureProcessor {
    public static final Codec<FairyFountainStructureProcessor> CODEC = Codec.unit(() -> FairyFountainStructureProcessor.INSTANCE);
    public static final FairyFountainStructureProcessor INSTANCE = new FairyFountainStructureProcessor();

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo currentBlockInfo, StructurePlacementData data) {
        if (currentBlockInfo.state().isOf(ZeldaBlocks.ENCHANTED_SPRING_WATER)) {
            if (isRegularWater(world, pos)) {
                return null;
            }

            for (Direction value : Direction.values()) {
                if (isRegularWater(world, pos.offset(value))) {
                    return null;
                }
            }
        }
        return super.process(world, pos, pivot, originalBlockInfo, currentBlockInfo, data);
    }

    private boolean isRegularWater(WorldView world, BlockPos pos) {
        return world.getFluidState(pos).isIn(FluidTags.WATER) && !world.getFluidState(pos).isIn(ZeldaTags.ENCHANTED_SPRING_WATER);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ZeldaStructures.FAIRY_FOUNTAIN_PROCESSOR;
    }
}
