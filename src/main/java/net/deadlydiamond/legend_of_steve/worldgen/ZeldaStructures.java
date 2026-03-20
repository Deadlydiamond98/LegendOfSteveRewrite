package net.deadlydiamond.legend_of_steve.worldgen;

import com.mojang.serialization.Codec;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.worldgen.structure.processor.FairyFountainStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;

public class ZeldaStructures {

    public static final StructureProcessorType<FairyFountainStructureProcessor> FAIRY_FOUNTAIN_PROCESSOR = registerProcessor(
            "remove_overlapping_water", FairyFountainStructureProcessor.CODEC
    );


    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    private static <P extends StructureProcessor> StructureProcessorType<P> registerProcessor(String name, Codec<P> codec) {
        return StructureProcessorType.register(LegendOfSteve.id(name).toString(), codec);
    }

    public static void register() {}
}
