package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.BlockSetType;

public class ZeldaBlockSetType {
    public static final BlockSetType FAIRY_MARBLE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).build(LegendOfSteve.id("fairy_marble"));
}
