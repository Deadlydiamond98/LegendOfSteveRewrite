package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.container.MasterBarrelBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.container.single.LootPotBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.container.single.SwordPedestal;
import net.deadlydiamond.legend_of_steve.common.blocks.container.single.hittable.HittableContainerBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.ConnectedPillarBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.GirderBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.glowing.FairyLamp;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.BombFlowerBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.FruitingLeavesBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.LootGrassBlock;
import net.deadlydiamond.legend_of_steve.common.blocksets.*;
import net.deadlydiamond.legend_of_steve.common.blocksets.dungeoncite.DungeonciteBlockset;
import net.deadlydiamond.legend_of_steve.common.blocksets.iridescent.IridescentStairSlabWallBlockset;
import net.deadlydiamond.legend_of_steve.common.blocksets.iridescent.IridescentStoneBlockset;
import net.deadlydiamond.legend_of_steve.common.entities.living.fairy.FairyColor;
import net.deadlydiamond.legend_of_steve.common.items.block.CrateItem;
import net.deadlydiamond.legend_of_steve.common.items.block.LootPotItem;
import net.deadlydiamond.legend_of_steve.worldgen.sapling.DekuSaplingGenerator;
import net.deadlydiamond98.koalalib.common.blocksets.BaseStairSlabBlockset;
import net.deadlydiamond98.koalalib.common.blocksets.BaseStairSlabWallBlockset;
import net.deadlydiamond98.koalalib.common.blocksets.WoodBlockset;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static net.deadlydiamond.legend_of_steve.init.ZeldaBlockSettings.*;

public class ZeldaBlocks {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DECORATIVE BLOCKS ///////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // CHISELED PLANKS
    public static final Block CHISELED_OAK_PLANKS = register("chiseled_oak_planks", new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_OAK_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_oak_bricks", FabricBlockSettings.copyOf(Blocks.OAK_PLANKS), true);
    public static final Block CHISELED_BIRCH_PLANKS = register("chiseled_birch_planks", new Block(FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_BIRCH_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_birch_bricks", FabricBlockSettings.copyOf(Blocks.BIRCH_PLANKS), true);
    public static final Block CHISELED_SPRUCE_PLANKS = register("chiseled_spruce_planks", new Block(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_SPRUCE_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_spruce_bricks", FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS), true);
    public static final Block CHISELED_JUNGLE_PLANKS = register("chiseled_jungle_planks", new Block(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_JUNGLE_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_jungle_bricks", FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS), true);
    public static final Block CHISELED_ACACIA_PLANKS = register("chiseled_acacia_planks", new Block(FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_ACACIA_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_acacia_bricks", FabricBlockSettings.copyOf(Blocks.ACACIA_PLANKS), true);
    public static final Block CHISELED_DARK_OAK_PLANKS = register("chiseled_dark_oak_planks", new Block(FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_DARK_OAK_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_dark_oak_bricks", FabricBlockSettings.copyOf(Blocks.DARK_OAK_PLANKS), true);
    public static final Block CHISELED_CRIMSON_PLANKS = register("chiseled_crimson_planks", new Block(FabricBlockSettings.copyOf(Blocks.CRIMSON_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_CRIMSON_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_crimson_bricks", FabricBlockSettings.copyOf(Blocks.CRIMSON_PLANKS), false);
    public static final Block CHISELED_WARPED_PLANKS = register("chiseled_warped_planks", new Block(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_WARPED_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_warped_bricks", FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS), false);
    public static final Block CHISELED_MANGROVE_PLANKS = register("chiseled_mangrove_planks", new Block(FabricBlockSettings.copyOf(Blocks.MANGROVE_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_MANGROVE_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_mangrove_bricks", FabricBlockSettings.copyOf(Blocks.MANGROVE_PLANKS), true);
    public static final Block CHISELED_BAMBOO_PLANKS = register("chiseled_bamboo_planks", new Block(FabricBlockSettings.copyOf(Blocks.BAMBOO_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_BAMBOO_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_bamboo_bricks", FabricBlockSettings.copyOf(Blocks.BAMBOO_PLANKS), true);
    public static final Block CHISELED_CHERRY_PLANKS = register("chiseled_cherry_planks", new Block(FabricBlockSettings.copyOf(Blocks.CHERRY_PLANKS)));
    public static final ChiseledWoodBrickBlockset CHISELED_CHERRY_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_cherry_bricks", FabricBlockSettings.copyOf(Blocks.CHERRY_PLANKS), true);
    public static final Block CHISELED_DEKU_PLANKS = register("chiseled_deku_planks", new Block(FabricBlockSettings.copyOf(DEKU_WOOD_SETTINGS)));
    public static final ChiseledWoodBrickBlockset CHISELED_DEKU_BRICKS = new ChiseledWoodBrickBlockset(LegendOfSteve.MOD_ID, "chiseled_deku_bricks", DEKU_WOOD_SETTINGS, true);

    // DEKU WOOD
    public static final WoodBlockset DEKU_WOOD = new WoodBlockset(LegendOfSteve.MOD_ID, "deku", DEKU_WOOD_SETTINGS, BlockSetType.CHERRY);

    public static final Block DEKU_LEAVES = register("deku_leaves", new LeavesBlock(DEKU_LEAVES_SETTINGS));
    public static final Block FRUITING_DEKU_LEAVES = register("fruiting_deku_leaves", new FruitingLeavesBlock(DEKU_LEAVES_SETTINGS, DEKU_LEAVES));
    public static final Block DEKU_SAPLING = register("deku_sapling", new SaplingBlock(new DekuSaplingGenerator(), DEKU_SAPLING_SETTINGS));
    public static final Block POTTED_DEKU_SAPLING = register("potted_deku_sapling", Blocks.createFlowerPotBlock(DEKU_SAPLING), false);

    // FAIRY LAMPS
    public static final Block PINK_FAIRY_LAMP = register("pink_fairy_lamp", new FairyLamp(PINK_FAIRY_LIGHT_SETTINGS, FairyColor.PINK));
    public static final Block RED_FAIRY_LAMP = register("red_fairy_lamp", new FairyLamp(RED_FAIRY_LIGHT_SETTINGS, FairyColor.RED));
    public static final Block ORANGE_FAIRY_LAMP = register("orange_fairy_lamp", new FairyLamp(ORANGE_FAIRY_LIGHT_SETTINGS, FairyColor.ORANGE));
    public static final Block YELLOW_FAIRY_LAMP = register("yellow_fairy_lamp", new FairyLamp(YELLOW_FAIRY_LIGHT_SETTINGS, FairyColor.YELLOW));
    public static final Block GREEN_FAIRY_LAMP = register("green_fairy_lamp", new FairyLamp(GREEN_FAIRY_LIGHT_SETTINGS, FairyColor.GREEN));
    public static final Block BLUE_FAIRY_LAMP = register("blue_fairy_lamp", new FairyLamp(BLUE_FAIRY_LIGHT_SETTINGS, FairyColor.BLUE));
    public static final Block PURPLE_FAIRY_LAMP = register("purple_fairy_lamp", new FairyLamp(PURPLE_FAIRY_LIGHT_SETTINGS, FairyColor.PURPLE));

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

    // DUNGEONCITE
    public static final DungeonciteBlockset BROWN_DUNGEONCITE = new DungeonciteBlockset("brown", "story/mine_diamond", MapColor.SPRUCE_BROWN);

    // MASTER
    public static final Block MASTER_ORE = register("master_ore", new ExperienceDroppingBlock(MASTER_ORE_BLOCK_SETTINGS));
    public static final Block DEEPSLATE_MASTER_ORE = register("deepslate_master_ore", new ExperienceDroppingBlock(DEEPSLATE_MASTER_ORE_BLOCK_SETTINGS));

    public static final Block MASTER_SCRAP_BLOCK = register("master_scrap_block", new Block(MASTER_SCRAP_SETTINGS));
    public static final Block MASTER_BLOCK = registerNetherite("master_block", new Block(MASTER_BLOCK_SETTINGS));
    public static final BaseStairSlabBlockset MASTER_PLATE = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "master_plate", MASTER_PLATE_SETTINGS);
    public static final BaseStairSlabBlockset MASTER_BRICK = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "master_bricks", MASTER_SCRAP_SETTINGS);
    public static final BaseStairSlabBlockset MASTER_TILE = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "master_tile", MASTER_SCRAP_SETTINGS);
    public static final Block CUT_MASTER_PLATE = register("cut_master_plate", new Block(MASTER_PLATE_SETTINGS));
    public static final Block MASTER_PILLAR = register("master_pillar", new PillarBlock(MASTER_SCRAP_SETTINGS));

    public static final Block MASTER_BARS = register("master_bars", new PaneBlock(MASTER_BAR_SETTINGS));
    public static final Block MASTER_CHAIN = register("master_chain", new ChainBlock(MASTER_CHAIN_SETTINGS));
    public static final Block MASTER_DOOR = register("master_door", new DoorBlock(MASTER_DOOR_SETTINGS, MASTER_TYPE));
    public static final Block MASTER_TRAPDOOR = register("master_trapdoor", new TrapdoorBlock(MASTER_TRAPDOOR_SETTINGS, MASTER_TYPE));
    public static final Block MASTER_GIRDER = register("master_girder", new GirderBlock(MASTER_GIRDER_SETTINGS));

    public static final Block MASTER_BARREL = register("master_barrel", new MasterBarrelBlock(MASTER_PLATE_SETTINGS));

    // Strange Dirt
    public static final BaseStairSlabWallBlockset STRANGE_DIRT = new BaseStairSlabWallBlockset(LegendOfSteve.MOD_ID, "strange_dirt", STRANGE_DIRT_SETTINGS);
    public static final StrangeDirtBricksBlockset STRANGE_DIRT_BRICKS = new StrangeDirtBricksBlockset(LegendOfSteve.MOD_ID, "strange_dirt_bricks", STRANGE_DIRT_SETTINGS);
    public static final BaseStairSlabWallBlockset POLISHED_STRANGE_DIRT = new BaseStairSlabWallBlockset(LegendOfSteve.MOD_ID, "polished_strange_dirt", STRANGE_DIRT_SETTINGS);
    public static final ReinforcedBlockset REINFORCED_STRANGE_DIRT = new ReinforcedBlockset(LegendOfSteve.MOD_ID, "reinforced_strange_dirt", STRANGE_DIRT_SETTINGS);
    public static final Block STRANGE_DIRT_PILLAR = register("strange_dirt_pillar", new ConnectedPillarBlock(STRANGE_DIRT_SETTINGS));

    public static final BaseStairSlabWallBlockset STRANGE_BLUE_DIRT = new BaseStairSlabWallBlockset(LegendOfSteve.MOD_ID, "strange_blue_dirt", STRANGE_BLUE_DIRT_SETTINGS);
    public static final StrangeDirtBricksBlockset STRANGE_BLUE_DIRT_BRICKS = new StrangeDirtBricksBlockset(LegendOfSteve.MOD_ID, "strange_blue_dirt_bricks", STRANGE_BLUE_DIRT_SETTINGS);
    public static final BaseStairSlabWallBlockset POLISHED_BLUE_STRANGE_DIRT = new BaseStairSlabWallBlockset(LegendOfSteve.MOD_ID, "polished_strange_blue_dirt", STRANGE_BLUE_DIRT_SETTINGS);
    public static final ReinforcedBlockset REINFORCED_STRANGE_BLUE_DIRT = new ReinforcedBlockset(LegendOfSteve.MOD_ID, "reinforced_strange_blue_dirt", STRANGE_BLUE_DIRT_SETTINGS);
    public static final Block STRANGE_BLUE_DIRT_PILLAR = register("strange_blue_dirt_pillar", new ConnectedPillarBlock(STRANGE_BLUE_DIRT_SETTINGS));

    public static final Block QUESTION_BLOCK = register("question_block", new HittableContainerBlock(QUESTION_BLOCK_SETTINGS));

    // TEKTILES
    public static final BaseStairSlabBlockset RED_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "red_tektiles", RED_TEKTILES_SETTINGS);
    public static final BaseStairSlabBlockset SMALL_RED_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "small_red_tektiles", RED_TEKTILES_SETTINGS);
    public static final BaseStairSlabWallBlockset RED_TEKTILE_BRICKS = new BaseStairSlabWallBlockset(LegendOfSteve.MOD_ID, "red_tektile_bricks", RED_TEKTILES_SETTINGS);
    public static final BaseStairSlabBlockset BLUE_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "blue_tektiles", BLUE_TEKTILES_SETTINGS);
    public static final BaseStairSlabBlockset SMALL_BLUE_TEKTILES = new BaseStairSlabBlockset(LegendOfSteve.MOD_ID, "small_blue_tektiles", BLUE_TEKTILES_SETTINGS);
    public static final BaseStairSlabWallBlockset BLUE_TEKTILE_BRICKS = new BaseStairSlabWallBlockset(LegendOfSteve.MOD_ID, "blue_tektile_bricks", BLUE_TEKTILES_SETTINGS);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FUNCTIONAL BLOCKS ///////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // PLANTS
    public static final Block BOMB_FLOWER = register("bomb_flower", new BombFlowerBlock(BOMB_FLOWER_SETTINGS), false);
    public static final Block LOOT_GRASS = register("loot_grass", new LootGrassBlock(LOOT_GRASS_SETTINGS));
    // FLUIDS
    public static final Block ENCHANTED_SPRING_WATER = register("enchanted_spring_water", new FluidBlock(
            ZeldaFluids.ENCHANTED_SPRING_WATER, FabricBlockSettings.copyOf(Blocks.WATER).luminance(state -> 10)), false
    );
    // STORAGE
    public static final Block LOOT_POT = registerPot("loot_pot", new LootPotBlock(LOOT_POT_SETTINGS));
    public static final LootPotBlockset DYED_LOOT_POTS = new LootPotBlockset(LegendOfSteve.MOD_ID, "loot_pot", LOOT_POT);

    public static final Block CRATE = register("crate", new Block(CRATE_SETTINGS), false);
    public static final Item CRATE_ITEM = ZeldaItems.register("crate", new CrateItem(new FabricItemSettings(), CRATE.getDefaultState()));

    public static final Block STONE_SWORD_PEDESTAL = register("stone_sword_pedestal", new SwordPedestal(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block DEEPSLATE_SWORD_PEDESTAL = register("deepslate_sword_pedestal", new SwordPedestal(FabricBlockSettings.copyOf(Blocks.POLISHED_DEEPSLATE)));
    public static final Block BLACKSTONE_SWORD_PEDESTAL = register("blackstone_sword_pedestal", new SwordPedestal(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE)));
    public static final Block QUARTZ_SWORD_PEDESTAL = register("quartz_sword_pedestal", new SwordPedestal(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK)));
    public static final Block FAIRY_MARBLE_SWORD_PEDESTAL = register("fairy_marble_sword_pedestal", new SwordPedestal(FAIRY_MARBLE_SETTINGS));
    public static final Block STRANGE_DIRT_SWORD_PEDESTAL = register("strange_dirt_sword_pedestal", new SwordPedestal(STRANGE_DIRT_SETTINGS));
    public static final Block STRANGE_BLUE_DIRT_SWORD_PEDESTAL = register("strange_blue_dirt_sword_pedestal", new SwordPedestal(STRANGE_BLUE_DIRT_SETTINGS));

    // BRAZIERS
    public static final BrazierBlockset STONE_BRAZIER_BLOCKSET = new BrazierBlockset(LegendOfSteve.MOD_ID, "stone", Blocks.STONE);
    public static final BrazierBlockset DEEPSLATE_BRAZIER_BLOCKSET = new BrazierBlockset(LegendOfSteve.MOD_ID, "deepslate", Blocks.POLISHED_DEEPSLATE);
    public static final BrazierBlockset BLACKSTONE_BRAZIER_BLOCKSET = new BrazierBlockset(LegendOfSteve.MOD_ID, "blackstone", Blocks.POLISHED_BLACKSTONE);
    public static final BrazierBlockset QUARTZ_BRAZIER_BLOCKSET = new BrazierBlockset(LegendOfSteve.MOD_ID, "quartz", Blocks.QUARTZ_BLOCK);
    public static final BrazierBlockset FAIRY_MARBLE_BRAZIER_BLOCKSET = new BrazierBlockset(LegendOfSteve.MOD_ID, "fairy_marble", FAIRY_MARBLE.base);
    public static final BrazierBlockset STRANGE_DIRT_BRAZIER_BLOCKSET = new BrazierBlockset(LegendOfSteve.MOD_ID, "strange_dirt", STRANGE_DIRT.base);
    public static final BrazierBlockset STRANGE_BLUE_DIRT_BRAZIER_BLOCKSET = new BrazierBlockset(LegendOfSteve.MOD_ID, "strange_blue_dirt", STRANGE_BLUE_DIRT.base);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REGISTRATION ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Block register(String id, Block block) {
        return register(id, block, true);
    }

    public static Block register(String id, Block block, boolean withItem) {
        if (withItem) {
            ZeldaItems.register(id, new BlockItem(block, new FabricItemSettings()));
        }
        return Registry.register(Registries.BLOCK, LegendOfSteve.id(id), block);
    }

    public static Block registerNetherite(String id, Block block) {
        ZeldaItems.register(id, new BlockItem(block, new FabricItemSettings().fireproof()));
        return Registry.register(Registries.BLOCK, LegendOfSteve.id(id), block);
    }

    public static Block registerPot(String id, Block block) {
        ZeldaItems.register(id, new LootPotItem(block, new FabricItemSettings()));
        return register(id, block, false);
    }

    public static void register() {
        FlammableBlockRegistry.getDefaultInstance().add(LOOT_GRASS, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(DEKU_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(FRUITING_DEKU_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ZeldaTags.CHISELED_PLANKS_BLOCK, 5, 20);

        FuelRegistry.INSTANCE.add(ZeldaTags.CHISELED_PLANKS_ITEM, 300);
        FuelRegistry.INSTANCE.add(CRATE, 300);

        CompostingChanceRegistry.INSTANCE.add(DEKU_SAPLING, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(DEKU_LEAVES, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(FRUITING_DEKU_LEAVES, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(LOOT_GRASS, 0.3f);
    }
}
