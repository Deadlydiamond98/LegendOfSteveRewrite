package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.SpringWaterBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.light.GlowingBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.BombFlowerBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.FruitingLeaves;
import net.deadlydiamond.legend_of_steve.common.blocksets.IridescentStairSlabWallBlockset;
import net.deadlydiamond.legend_of_steve.common.blocksets.IridescentStoneBlockset;
import net.deadlydiamond.legend_of_steve.common.blocksets.WoodBlockset;
import net.deadlydiamond98.koalalib.common.blocksets.BaseStairSlabBlockset;
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
    public static final AbstractBlock.Settings TEKTILES = FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).sounds(BlockSoundGroup.DEEPSLATE_TILES);

    public static final Block BOMB_FLOWER = register("bomb_flower", new BombFlowerBlock(BOMB_FLOWER_SETTINGS), false);
    public static final Block ENCHANTED_SPRING_WATER = register("enchanted_spring_water", new SpringWaterBlock(ZeldaFluids.ENCHANTED_SPRING_WATER, FabricBlockSettings.copyOf(Blocks.WATER)), false);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DECORATIVE BLOCKS ///////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // FAIRY LAMPS
    public static final Block PINK_FAIRY_LAMP = register("pink_fairy_lamp", new GlowingBlock(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));
    public static final Block RED_FAIRY_LAMP = register("red_fairy_lamp", new GlowingBlock(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));
    public static final Block ORANGE_FAIRY_LAMP = register("orange_fairy_lamp", new GlowingBlock(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));
    public static final Block YELLOW_FAIRY_LAMP = register("yellow_fairy_lamp", new GlowingBlock(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));
    public static final Block GREEN_FAIRY_LAMP = register("green_fairy_lamp", new GlowingBlock(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));
    public static final Block BLUE_FAIRY_LAMP = register("blue_fairy_lamp", new GlowingBlock(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));
    public static final Block PURPLE_FAIRY_LAMP = register("purple_fairy_lamp", new GlowingBlock(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));

    // FAIRY MARBLE
    public static final IridescentStoneBlockset FAIRY_MARBLE = new IridescentStoneBlockset(LegendOfSteve.MOD_ID, "fairy_marble", FabricBlockSettings.copyOf(Blocks.CALCITE));
    public static final IridescentStairSlabWallBlockset COBBLED_FAIRY_MARBLE = new IridescentStairSlabWallBlockset(LegendOfSteve.MOD_ID, "cobbled_fairy_marble", FabricBlockSettings.copyOf(Blocks.CALCITE));
    public static final IridescentStairSlabWallBlockset POLISHED_FAIRY_MARBLE = new IridescentStairSlabWallBlockset(LegendOfSteve.MOD_ID, "polished_fairy_marble", FabricBlockSettings.copyOf(Blocks.CALCITE));
    public static final IridescentStairSlabWallBlockset FAIRY_MARBLE_BRICKS = new IridescentStairSlabWallBlockset(LegendOfSteve.MOD_ID, "fairy_marble_bricks", FabricBlockSettings.copyOf(Blocks.CALCITE));
    public static final IridescentStairSlabWallBlockset MOSSY_FAIRY_MARBLE_BRICKS = new IridescentStairSlabWallBlockset(LegendOfSteve.MOD_ID, "mossy_fairy_marble_bricks", FabricBlockSettings.copyOf(Blocks.CALCITE));
    public static final IridescentStairSlabWallBlockset FAIRY_MARBLE_TILES = new IridescentStairSlabWallBlockset(LegendOfSteve.MOD_ID, "fairy_marble_tiles", FabricBlockSettings.copyOf(Blocks.CALCITE));

    public static final Block CRACKED_FAIRY_MARBLE_BRICKS = register("cracked_fairy_marble_bricks", new Block(FabricBlockSettings.copyOf(Blocks.CALCITE)));
    public static final Block CHISELED_FAIRY_MARBLE = register("chiseled_fairy_marble", new Block(FabricBlockSettings.copyOf(Blocks.CALCITE)));
    public static final Block FAIRY_MARBLE_PILLAR = register("fairy_marble_pillar", new PillarBlock(FabricBlockSettings.copyOf(Blocks.CALCITE)));
    public static final Block SMOOTH_FAIRY_MARBLE = register("smooth_fairy_marble", new Block(FabricBlockSettings.copyOf(Blocks.CALCITE)));

    // DEKU WOOD
    public static final WoodBlockset DEKU_WOOD = new WoodBlockset(LegendOfSteve.MOD_ID, "deku", FabricBlockSettings.copyOf(Blocks.CHERRY_PLANKS), BlockSetType.CHERRY);

    public static final Block DEKU_LEAVES = register("deku_leaves", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.CHERRY_LEAVES)));
    public static final Block FRUITING_DEKU_LEAVES = register("fruiting_deku_leaves", new FruitingLeaves(FabricBlockSettings.copyOf(Blocks.CHERRY_LEAVES), DEKU_LEAVES));

    // TEKTILES
    public static final BaseStairSlabBlockset RED_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "red_tektiles", TEKTILES);
    public static final BaseStairSlabBlockset SMALL_RED_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "small_red_tektiles", TEKTILES);
    public static final BaseStairSlabBlockset BLUE_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "blue_tektiles", TEKTILES);
    public static final BaseStairSlabBlockset SMALL_BLUE_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "small_blue_tektiles", TEKTILES);

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
