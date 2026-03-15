package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.MasterBarrelBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.SpringWaterBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.GirderBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.TestGlowingBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.BombFlowerBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.FruitingLeaves;
import net.deadlydiamond.legend_of_steve.common.blocksets.IridescentStairSlabWallBlockset;
import net.deadlydiamond.legend_of_steve.common.blocksets.IridescentStoneBlockset;
import net.deadlydiamond.legend_of_steve.worldgen.sapling.DekuSaplingGenerator;
import net.deadlydiamond98.koalalib.common.blocksets.BaseStairSlabBlockset;
import net.deadlydiamond98.koalalib.common.blocksets.WoodBlockset;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static net.deadlydiamond.legend_of_steve.init.ZeldaBlockSettings.*;

public class ZeldaBlocks {
    // BLOCK SETTINGS //////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Block BOMB_FLOWER = register("bomb_flower", new BombFlowerBlock(BOMB_FLOWER_SETTINGS), false);
    public static final Block ENCHANTED_SPRING_WATER = register("enchanted_spring_water", new SpringWaterBlock(ZeldaFluids.ENCHANTED_SPRING_WATER, FabricBlockSettings.copyOf(Blocks.WATER)), false);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DECORATIVE BLOCKS ///////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // CHISELED PLANKS
    public static final Block CHISELED_OAK_PLANKS = register("chiseled_oak_planks", new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block CHISELED_BIRCH_PLANKS = register("chiseled_birch_planks", new Block(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS)));
    public static final Block CHISELED_SPRUCE_PLANKS = register("chiseled_spruce_planks", new Block(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS)));
    public static final Block CHISELED_JUNGLE_PLANKS = register("chiseled_jungle_planks", new Block(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS)));
    public static final Block CHISELED_ACACIA_PLANKS = register("chiseled_acacia_planks", new Block(FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS)));
    public static final Block CHISELED_DARK_OAK_PLANKS = register("chiseled_dark_oak_planks", new Block(FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS)));
    public static final Block CHISELED_CRIMSON_PLANKS = register("chiseled_crimson_planks", new Block(FabricBlockSettings.copyOf(Blocks.CRIMSON_PLANKS)));
    public static final Block CHISELED_WARPED_PLANKS = register("chiseled_warped_planks", new Block(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS)));
    public static final Block CHISELED_MANGROVE_PLANKS = register("chiseled_mangrove_planks", new Block(FabricBlockSettings.copyOf(Blocks.MANGROVE_PLANKS)));
    public static final Block CHISELED_BAMBOO_PLANKS = register("chiseled_bamboo_planks", new Block(FabricBlockSettings.copyOf(Blocks.BAMBOO_PLANKS)));
    public static final Block CHISELED_CHERRY_PLANKS = register("chiseled_cherry_planks", new Block(FabricBlockSettings.copyOf(Blocks.CHERRY_PLANKS)));
    public static final Block CHISELED_DEKU_PLANKS = register("chiseled_deku_planks", new Block(FabricBlockSettings.copyOf(DEKU_WOOD_SETTINGS)));

    // DEKU WOOD
    public static final WoodBlockset DEKU_WOOD = new WoodBlockset(LegendOfSteve.MOD_ID, "deku", DEKU_WOOD_SETTINGS, BlockSetType.CHERRY);

    public static final Block DEKU_LEAVES = register("deku_leaves", new LeavesBlock(DEKU_LEAVES_SETTINGS));
    public static final Block FRUITING_DEKU_LEAVES = register("fruiting_deku_leaves", new FruitingLeaves(DEKU_LEAVES_SETTINGS, DEKU_LEAVES));
    public static final Block DEKU_SAPLING = register("deku_sapling", new SaplingBlock(new DekuSaplingGenerator(), DEKU_SAPLING_SETTINGS));
    public static final Block POTTED_DEKU_SAPLING = register("potted_deku_sapling", Blocks.createFlowerPotBlock(DEKU_SAPLING), false);

    // FAIRY LAMPS
    // TODO: ADD RECIPE FOR THESE ONCE FAIRIES ARE ADDED
    public static final Block PINK_FAIRY_LAMP = register("pink_fairy_lamp", new TestGlowingBlock(PINK_FAIRY_LIGHT_SETTINGS));
    public static final Block RED_FAIRY_LAMP = register("red_fairy_lamp", new TestGlowingBlock(RED_FAIRY_LIGHT_SETTINGS));
    public static final Block ORANGE_FAIRY_LAMP = register("orange_fairy_lamp", new TestGlowingBlock(ORANGE_FAIRY_LIGHT_SETTINGS));
    public static final Block YELLOW_FAIRY_LAMP = register("yellow_fairy_lamp", new TestGlowingBlock(YELLOW_FAIRY_LIGHT_SETTINGS));
    public static final Block GREEN_FAIRY_LAMP = register("green_fairy_lamp", new TestGlowingBlock(GREEN_FAIRY_LIGHT_SETTINGS));
    public static final Block BLUE_FAIRY_LAMP = register("blue_fairy_lamp", new TestGlowingBlock(BLUE_FAIRY_LIGHT_SETTINGS));
    public static final Block PURPLE_FAIRY_LAMP = register("purple_fairy_lamp", new TestGlowingBlock(PURPLE_FAIRY_LIGHT_SETTINGS));

    public static final Block BLOOM_ALTERNATIVE_TEST = register("bloom_alternative_test", new TestGlowingBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));

    // FAIRY MARBLE
    public static final IridescentStoneBlockset FAIRY_MARBLE = new IridescentStoneBlockset(LegendOfSteve.MOD_ID, "fairy_marble", FAIRY_MARBLE_SETTINGS);
    public static final IridescentStairSlabWallBlockset COBBLED_FAIRY_MARBLE = new IridescentStairSlabWallBlockset(LegendOfSteve.MOD_ID, "cobbled_fairy_marble", FAIRY_MARBLE_SETTINGS);
    public static final IridescentStairSlabWallBlockset POLISHED_FAIRY_MARBLE = new IridescentStairSlabWallBlockset(LegendOfSteve.MOD_ID, "polished_fairy_marble", FAIRY_MARBLE_SETTINGS);
    public static final IridescentStairSlabWallBlockset FAIRY_MARBLE_BRICKS = new IridescentStairSlabWallBlockset(LegendOfSteve.MOD_ID, "fairy_marble_bricks", FAIRY_MARBLE_SETTINGS);
    public static final IridescentStairSlabWallBlockset MOSSY_FAIRY_MARBLE_BRICKS = new IridescentStairSlabWallBlockset(LegendOfSteve.MOD_ID, "mossy_fairy_marble_bricks", FAIRY_MARBLE_SETTINGS);
    public static final IridescentStairSlabWallBlockset FAIRY_MARBLE_TILES = new IridescentStairSlabWallBlockset(LegendOfSteve.MOD_ID, "fairy_marble_tiles", FAIRY_MARBLE_SETTINGS);

    public static final Block CRACKED_FAIRY_MARBLE_BRICKS = register("cracked_fairy_marble_bricks", new Block(FAIRY_MARBLE_SETTINGS));
    public static final Block CHISELED_FAIRY_MARBLE = register("chiseled_fairy_marble", new Block(FAIRY_MARBLE_SETTINGS));
    public static final Block FAIRY_MARBLE_PILLAR = register("fairy_marble_pillar", new PillarBlock(FAIRY_MARBLE_SETTINGS));
    public static final Block SMOOTH_FAIRY_MARBLE = register("smooth_fairy_marble", new Block(FAIRY_MARBLE_SETTINGS));

    // MASTER
    public static final Block MASTER_ORE = register("master_ore", new ExperienceDroppingBlock(MASTER_ORE_BLOCK_SETTINGS));
    public static final Block DEEPSLATE_MASTER_ORE = register("deepslate_master_ore", new ExperienceDroppingBlock(DEEPSLATE_MASTER_ORE_BLOCK_SETTINGS));

    public static final Block MASTER_SCRAP_BLOCK = register("master_scrap_block", new Block(MASTER_SCRAP_SETTINGS));
    public static final Block MASTER_BLOCK = register("master_block", new Block(MASTER_BLOCK_SETTINGS));
    public static final BaseStairSlabBlockset MASTER_PLATE = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "master_plate", MASTER_PLATE_SETTINGS);
    public static final BaseStairSlabBlockset MASTER_BRICK = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "master_bricks", MASTER_PLATE_SETTINGS);
    public static final BaseStairSlabBlockset MASTER_TILE = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "master_tile", MASTER_PLATE_SETTINGS);
    public static final Block CUT_MASTER_PLATE = register("cut_master_plate", new Block(MASTER_PLATE_SETTINGS));
    public static final Block MASTER_PILLAR = register("master_pillar", new PillarBlock(MASTER_PLATE_SETTINGS));

    public static final Block MASTER_BARS = register("master_bars", new PaneBlock(MASTER_BAR_SETTINGS));
    public static final Block MASTER_CHAIN = register("master_chain", new ChainBlock(MASTER_CHAIN_SETTINGS));
    public static final Block MASTER_DOOR = register("master_door", new DoorBlock(MASTER_DOOR_SETTINGS, MASTER_TYPE));
    public static final Block MASTER_TRAPDOOR = register("master_trapdoor", new TrapdoorBlock(MASTER_TRAPDOOR_SETTINGS, MASTER_TYPE));
    public static final Block MASTER_GIRDER = register("master_girder", new GirderBlock(MASTER_GIRDER_SETTINGS));

    public static final Block MASTER_BARREL = register("master_barrel", new MasterBarrelBlock(MASTER_PLATE_SETTINGS));

    // TEKTILES
    public static final BaseStairSlabBlockset RED_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "red_tektiles", RED_TEKTILES_SETTINGS);
    public static final BaseStairSlabBlockset SMALL_RED_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "small_red_tektiles", RED_TEKTILES_SETTINGS);
    public static final BaseStairSlabBlockset BLUE_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "blue_tektiles", BLUE_TEKTILES_SETTINGS);
    public static final BaseStairSlabBlockset SMALL_BLUE_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "small_blue_tektiles", BLUE_TEKTILES_SETTINGS);

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

    public static void register() {
        FlammableBlockRegistry.getDefaultInstance().add(DEKU_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(FRUITING_DEKU_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ZeldaTags.CHISELED_PLANKS_BLOCK, 5, 20);

        FuelRegistry.INSTANCE.add(ZeldaTags.CHISELED_PLANKS_ITEM, 300);

        CompostingChanceRegistry.INSTANCE.add(DEKU_SAPLING, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(DEKU_LEAVES, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(FRUITING_DEKU_LEAVES, 0.3f);
    }
}
