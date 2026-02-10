package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.BombFlowerBlock;
import net.deadlydiamond98.koalalib.common.blocksets.BaseStairSlabWallBlockset;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class ZeldaBlocks {
    // BLOCK SETTINGS //////////////////////////////////////////////////////////////////////////////////////////////////
    public static final FabricBlockSettings BOMB_FLOWER_SETTINGS = FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK).sounds(BlockSoundGroup.SPORE_BLOSSOM).nonOpaque().noCollision().breakInstantly();

    public static final Block BOMB_FLOWER = register("bomb_flower", new BombFlowerBlock(BOMB_FLOWER_SETTINGS), false);
    public static final Block ENCHANTED_SPRING_WATER = register("enchanted_spring_water", new FluidBlock(ZeldaFluids.ENCHANTED_SPRING_WATER, FabricBlockSettings.copyOf(Blocks.WATER)), false);

    // DECORATIVE BLOCKS ///////////////////////////////////////////////////////////////////////////////////////////////
    public static final BaseStairSlabWallBlockset FAIRY_MARBLE = new BaseStairSlabWallBlockset(LegendOfSteve.MOD_ID, "fairy_marble", FabricBlockSettings.copyOf(Blocks.CALCITE));
    public static final Block FAIRY_MARBLE_BUTTON = register("fairy_marble_button", new ButtonBlock(FabricBlockSettings.copyOf(Blocks.STONE_BUTTON), ZeldaBlockSetType.FAIRY_MARBLE, 20, false));
    public static final Block FAIRY_MARBLE_PRESSURE_PLATE = register("fairy_marble_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copyOf(Blocks.STONE_PRESSURE_PLATE), ZeldaBlockSetType.FAIRY_MARBLE));
    public static final BaseStairSlabWallBlockset COBBLED_FAIRY_MARBLE = new BaseStairSlabWallBlockset(LegendOfSteve.MOD_ID, "cobbled_fairy_marble", FabricBlockSettings.copyOf(Blocks.CALCITE));

    // REGISTRATION ////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Block register(String id, Block block) {
        return register(id, block, true);
    }

    public static Block register(String id, Block block, boolean withItem) {
        if (withItem) {
            ZeldaItems.register(id, new BlockItem(block, new FabricItemSettings()));
        }
        return Registry.register(Registries.BLOCK, LegendOfSteve.id(id), block);
    }

    public static void register() {}
}
