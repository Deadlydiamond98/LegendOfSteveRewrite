package net.deadlydiamond.legend_of_steve.datagen.recipe.spring_water;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class ZeldaSpringWaterConversionDatagen {

    public static void generate(Consumer<RecipeJsonProvider> consumer) {
        vanillaItem(consumer);
        vanillaBlock(consumer);
        swapRecipe(consumer);
        dye(consumer);
        fairyMarble(consumer);
        unique(consumer);
    }

    private static void vanillaItem(Consumer<RecipeJsonProvider> consumer) {
        // Purification
        offerConversion(consumer, Items.POISONOUS_POTATO, Items.POTATO);
        offerConversion(consumer, Items.ECHO_SHARD, Items.AMETHYST_SHARD);
        offerConversion(consumer, Items.QUARTZ, Blocks.CALCITE);
        offerConversion(consumer, Items.NETHER_BRICK, Items.BRICK);
        offerConversion(consumer, Items.ENDER_EYE, Items.ENDER_PEARL);
        offerConversion(consumer, Items.MUSIC_DISC_11, Items.MUSIC_DISC_STAL);
        offerConversion(consumer, Items.ROTTEN_FLESH, Items.LEATHER);
        offerConversion(consumer, Items.FERMENTED_SPIDER_EYE, Items.SPIDER_EYE);

        // Other
        offerConversion(consumer, Items.RABBIT_FOOT, Items.RABBIT_HIDE);
        offerConversion(consumer, Items.GLASS_BOTTLE, Blocks.GLASS);
    }

    private static void vanillaBlock(Consumer<RecipeJsonProvider> consumer) {
        // Purification
        offerConversion(consumer, Blocks.NETHERRACK, Blocks.COBBLESTONE);
        offerConversion(consumer, Blocks.WARPED_WART_BLOCK, Blocks.NETHER_WART_BLOCK);
        offerConversion(consumer, Blocks.CRYING_OBSIDIAN, Blocks.OBSIDIAN);
        offerConversion(consumer, Blocks.DEEPSLATE, Blocks.TUFF);
        offerConversion(consumer, Blocks.TUFF, Blocks.STONE);
        offerConversion(consumer, Blocks.DEAD_BUSH, Blocks.OAK_SAPLING);

        // Nether Brick
        offerConversion(consumer, Blocks.NETHER_BRICKS, Blocks.BRICKS);
        offerConversion(consumer, Blocks.NETHER_BRICK_SLAB, Blocks.BRICK_SLAB);
        offerConversion(consumer, Blocks.NETHER_BRICK_STAIRS, Blocks.BRICK_STAIRS);
        offerConversion(consumer, Blocks.NETHER_BRICK_WALL, Blocks.BRICK_WALL);

        offerConversion(consumer, Blocks.RED_NETHER_BRICKS, Blocks.NETHER_BRICKS);
        offerConversion(consumer, Blocks.RED_NETHER_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB);
        offerConversion(consumer, Blocks.RED_NETHER_BRICK_STAIRS, Blocks.NETHER_BRICK_STAIRS);
        offerConversion(consumer, Blocks.RED_NETHER_BRICK_WALL, Blocks.NETHER_BRICK_WALL);

        // Remove Soul
        offerConversion(consumer, Blocks.SOUL_SAND, Blocks.SAND);
        offerConversion(consumer, Blocks.SOUL_SOIL, Blocks.DIRT);
        offerConversion(consumer, Blocks.SOUL_CAMPFIRE, Blocks.CAMPFIRE);
        offerConversion(consumer, Blocks.SOUL_LANTERN, Blocks.LANTERN);
        offerConversion(consumer, Blocks.SOUL_TORCH, Blocks.TORCH);
        // Sculk
        offerConversion(consumer, Blocks.SCULK, Blocks.MOSS_BLOCK);
        offerConversion(consumer, Blocks.SCULK_CATALYST, Blocks.SCULK);
        offerConversion(consumer, Blocks.SCULK_SHRIEKER, Blocks.SCULK);
        offerConversion(consumer, Blocks.SCULK_VEIN, Blocks.GLOW_LICHEN);
        // Corals
        offerConversion(consumer, Blocks.DEAD_BRAIN_CORAL, Blocks.BRAIN_CORAL);
        offerConversion(consumer, Blocks.DEAD_BRAIN_CORAL_BLOCK, Blocks.BRAIN_CORAL_BLOCK);
        offerConversion(consumer, Blocks.DEAD_BRAIN_CORAL_FAN, Blocks.BRAIN_CORAL_FAN);

        offerConversion(consumer, Blocks.DEAD_BUBBLE_CORAL, Blocks.BUBBLE_CORAL);
        offerConversion(consumer, Blocks.DEAD_BUBBLE_CORAL_BLOCK, Blocks.BUBBLE_CORAL_BLOCK);
        offerConversion(consumer, Blocks.DEAD_BUBBLE_CORAL_FAN, Blocks.BUBBLE_CORAL_FAN);

        offerConversion(consumer, Blocks.DEAD_FIRE_CORAL, Blocks.FIRE_CORAL);
        offerConversion(consumer, Blocks.DEAD_FIRE_CORAL_BLOCK, Blocks.FIRE_CORAL_BLOCK);
        offerConversion(consumer, Blocks.DEAD_FIRE_CORAL_FAN, Blocks.FIRE_CORAL_FAN);

        offerConversion(consumer, Blocks.DEAD_HORN_CORAL, Blocks.HORN_CORAL);
        offerConversion(consumer, Blocks.DEAD_HORN_CORAL_BLOCK, Blocks.HORN_CORAL_BLOCK);
        offerConversion(consumer, Blocks.DEAD_HORN_CORAL_FAN, Blocks.HORN_CORAL_FAN);

        offerConversion(consumer, Blocks.DEAD_TUBE_CORAL, Blocks.TUBE_CORAL);
        offerConversion(consumer, Blocks.DEAD_TUBE_CORAL_BLOCK, Blocks.TUBE_CORAL_BLOCK);
        offerConversion(consumer, Blocks.DEAD_TUBE_CORAL_FAN, Blocks.TUBE_CORAL_FAN);
        // HEAD
        offerConversion(consumer, Blocks.ZOMBIE_HEAD, Blocks.PLAYER_HEAD);
        offerConversion(consumer, Blocks.SKELETON_SKULL, Blocks.PLAYER_HEAD);

        // Other
        offerConversion(consumer, Blocks.DRIPSTONE_BLOCK, Blocks.POINTED_DRIPSTONE);
        offerConversion(consumer, Blocks.GRASS, Blocks.TALL_GRASS);
        offerConversion(consumer, Blocks.FERN, Blocks.LARGE_FERN);
        offerConversion(consumer, Blocks.BEE_NEST, Blocks.BEEHIVE);
        offerConversion(consumer, Blocks.GOLD_BLOCK, Blocks.BELL);
    }

    private static void swapRecipe(Consumer<RecipeJsonProvider> consumer) {
        // Saplings
        offerConversion(consumer, Blocks.OAK_SAPLING, Blocks.BIRCH_SAPLING);
        offerConversion(consumer, Blocks.BIRCH_SAPLING, Blocks.SPRUCE_SAPLING);
        offerConversion(consumer, Blocks.SPRUCE_SAPLING, Blocks.JUNGLE_SAPLING);
        offerConversion(consumer, Blocks.JUNGLE_SAPLING, Blocks.ACACIA_SAPLING);
        offerConversion(consumer, Blocks.ACACIA_SAPLING, Blocks.DARK_OAK_SAPLING);
        offerConversion(consumer, Blocks.DARK_OAK_SAPLING, Blocks.MANGROVE_PROPAGULE);
        offerConversion(consumer, Blocks.MANGROVE_PROPAGULE, Blocks.CHERRY_SAPLING);
        offerConversion(consumer, Blocks.CHERRY_SAPLING, Blocks.OAK_SAPLING);

        offerConversion(consumer, Blocks.AZALEA, Blocks.FLOWERING_AZALEA);
        offerConversion(consumer, Blocks.FLOWERING_AZALEA, Blocks.AZALEA);

        // Nether Fungus
        offerConversion(consumer, Blocks.WARPED_FUNGUS, Blocks.CRIMSON_FUNGUS);
        offerConversion(consumer, Blocks.CRIMSON_FUNGUS, Blocks.WARPED_FUNGUS);

        // Mushroom
        offerConversion(consumer, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM);
        offerConversion(consumer, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM);

        offerConversion(consumer, Blocks.RED_MUSHROOM_BLOCK, Blocks.BROWN_MUSHROOM_BLOCK);
        offerConversion(consumer, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK);

        // Prismarine
        offerConversion(consumer, Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS);
        offerConversion(consumer, Items.PRISMARINE_CRYSTALS, Items.PRISMARINE_SHARD);

        // Gourd Seeds
        offerConversion(consumer, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS);
        offerConversion(consumer, Items.PUMPKIN_SEEDS, Items.MELON_SEEDS);
    }

    private static void dye(Consumer<RecipeJsonProvider> consumer) {
        // WOOL
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.GRAY_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.BLACK_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.BROWN_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.RED_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.ORANGE_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.YELLOW_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.LIME_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.GREEN_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.CYAN_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.BLUE_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.PURPLE_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.MAGENTA_WOOL, Blocks.WHITE_WOOL);
        offerDyeConversion(consumer, Blocks.PINK_WOOL, Blocks.WHITE_WOOL);
        // CARPET
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.GRAY_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.BLACK_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.BROWN_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.RED_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.ORANGE_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.YELLOW_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.LIME_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.GREEN_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.CYAN_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.BLUE_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.PURPLE_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.MAGENTA_CARPET, Blocks.WHITE_CARPET);
        offerDyeConversion(consumer, Blocks.PINK_CARPET, Blocks.WHITE_CARPET);
        // TERRACOTTA
        offerDyeConversion(consumer, Blocks.WHITE_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.GRAY_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.BLACK_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.BROWN_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.RED_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.ORANGE_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.YELLOW_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.LIME_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.GREEN_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.CYAN_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.BLUE_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.PURPLE_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.MAGENTA_TERRACOTTA, Blocks.TERRACOTTA);
        offerDyeConversion(consumer, Blocks.PINK_TERRACOTTA, Blocks.TERRACOTTA);
        // CONCRETE
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.GRAY_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.BLACK_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.BROWN_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.RED_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.ORANGE_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.YELLOW_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.LIME_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.GREEN_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.CYAN_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.BLUE_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.PURPLE_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.MAGENTA_CONCRETE, Blocks.WHITE_CONCRETE);
        offerDyeConversion(consumer, Blocks.PINK_CONCRETE, Blocks.WHITE_CONCRETE);
        // CONCRETE POWDER
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.GRAY_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.BLACK_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.BROWN_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.RED_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.ORANGE_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.YELLOW_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.LIME_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.GREEN_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.CYAN_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.BLUE_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.PURPLE_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        offerDyeConversion(consumer, Blocks.PINK_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER);
        // GLAZED TERRACOTTA
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.RED_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.LIME_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        offerDyeConversion(consumer, Blocks.PINK_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA);
        // STAINED GLASS
        offerDyeConversion(consumer, Blocks.WHITE_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.GRAY_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.BLACK_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.BROWN_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.RED_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.ORANGE_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.YELLOW_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.LIME_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.GREEN_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.CYAN_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.BLUE_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.PURPLE_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.MAGENTA_STAINED_GLASS, Blocks.GLASS);
        offerDyeConversion(consumer, Blocks.PINK_STAINED_GLASS, Blocks.GLASS);
        // STAINED GLASS PANE
        offerDyeConversion(consumer, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.BLACK_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.RED_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.YELLOW_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.LIME_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.CYAN_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        offerDyeConversion(consumer, Blocks.PINK_STAINED_GLASS_PANE, Blocks.GLASS_PANE);
        // BED
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.GRAY_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.BLACK_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.BROWN_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.RED_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.ORANGE_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.YELLOW_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.LIME_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.GREEN_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.CYAN_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.BLUE_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.PURPLE_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.MAGENTA_BED, Blocks.WHITE_BED);
        offerDyeConversion(consumer, Blocks.PINK_BED, Blocks.WHITE_BED);
        // CANDLE
        offerDyeConversion(consumer, Blocks.WHITE_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.GRAY_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.BLACK_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.BROWN_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.RED_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.ORANGE_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.YELLOW_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.LIME_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.GREEN_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.CYAN_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.BLUE_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.PURPLE_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.MAGENTA_CANDLE, Blocks.CANDLE);
        offerDyeConversion(consumer, Blocks.PINK_CANDLE, Blocks.CANDLE);
        // BANNER
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.GRAY_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.BLACK_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.BROWN_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.RED_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.ORANGE_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.YELLOW_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.LIME_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.GREEN_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.CYAN_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.BLUE_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.PURPLE_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.MAGENTA_BANNER, Blocks.WHITE_BANNER);
        offerDyeConversion(consumer, Blocks.PINK_BANNER, Blocks.WHITE_BANNER);
        // SHULKER BOX
        offerDyeConversion(consumer, Blocks.WHITE_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.GRAY_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.BLACK_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.BROWN_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.RED_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.ORANGE_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.YELLOW_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.LIME_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.GREEN_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.CYAN_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.BLUE_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.PURPLE_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.MAGENTA_SHULKER_BOX, Blocks.SHULKER_BOX);
        offerDyeConversion(consumer, Blocks.PINK_SHULKER_BOX, Blocks.SHULKER_BOX);
        // LOOT POT
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.white, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.light_gray, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.gray, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.black, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.brown, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.red, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.orange, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.yellow, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.lime, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.green, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.cyan, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.light_blue, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.blue, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.purple, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.magenta, ZeldaBlocks.LOOT_POT);
        offerDyeConversion(consumer, ZeldaBlocks.DYED_LOOT_POTS.pink, ZeldaBlocks.LOOT_POT);
    }

    private static void fairyMarble(Consumer<RecipeJsonProvider> consumer) {
        // STONE ///////////////////////////////////////////////////////////////////////////////////////////////////////
        // base
        offerConversion(consumer, Blocks.STONE, ZeldaBlocks.FAIRY_MARBLE.base);
        offerConversion(consumer, Blocks.STONE_SLAB, ZeldaBlocks.FAIRY_MARBLE.slab);
        offerConversion(consumer, Blocks.STONE_STAIRS, ZeldaBlocks.FAIRY_MARBLE.stair);
        offerConversion(consumer, Blocks.STONE_BUTTON, ZeldaBlocks.FAIRY_MARBLE.button);
        offerConversion(consumer, Blocks.STONE_PRESSURE_PLATE, ZeldaBlocks.FAIRY_MARBLE.plate);
        // cobble
        offerConversion(consumer, Blocks.COBBLESTONE, ZeldaBlocks.COBBLED_FAIRY_MARBLE.base);
        offerConversion(consumer, Blocks.COBBLESTONE_SLAB, ZeldaBlocks.COBBLED_FAIRY_MARBLE.slab);
        offerConversion(consumer, Blocks.COBBLESTONE_STAIRS, ZeldaBlocks.COBBLED_FAIRY_MARBLE.stair);
        offerConversion(consumer, Blocks.COBBLESTONE_WALL, ZeldaBlocks.COBBLED_FAIRY_MARBLE.wall);
        // brick
        offerConversion(consumer, Blocks.STONE_BRICKS, ZeldaBlocks.FAIRY_MARBLE_BRICKS.base);
        offerConversion(consumer, Blocks.STONE_BRICK_SLAB, ZeldaBlocks.FAIRY_MARBLE_BRICKS.slab);
        offerConversion(consumer, Blocks.STONE_BRICK_STAIRS, ZeldaBlocks.FAIRY_MARBLE_BRICKS.stair);
        offerConversion(consumer, Blocks.STONE_BRICK_WALL, ZeldaBlocks.FAIRY_MARBLE_BRICKS.wall);
        // mossy brick
        offerConversion(consumer, Blocks.MOSSY_STONE_BRICKS, ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.base);
        offerConversion(consumer, Blocks.MOSSY_STONE_BRICK_SLAB, ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.slab);
        offerConversion(consumer, Blocks.MOSSY_STONE_BRICK_STAIRS, ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.stair);
        offerConversion(consumer, Blocks.MOSSY_STONE_BRICK_WALL, ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.wall);
        // brazier
        offerConversion(consumer, ZeldaBlocks.STONE_BRAZIER_BLOCKSET.regular, ZeldaBlocks.FAIRY_MARBLE_BRAZIER_BLOCKSET.regular);
        offerConversion(consumer, ZeldaBlocks.STONE_BRAZIER_BLOCKSET.soul, ZeldaBlocks.FAIRY_MARBLE_BRAZIER_BLOCKSET.regular);
        offerConversion(consumer, ZeldaBlocks.STONE_BRAZIER_BLOCKSET.tall, ZeldaBlocks.FAIRY_MARBLE_BRAZIER_BLOCKSET.tall);
        offerConversion(consumer, ZeldaBlocks.STONE_BRAZIER_BLOCKSET.tall_soul, ZeldaBlocks.FAIRY_MARBLE_BRAZIER_BLOCKSET.tall);
        // other
        offerConversion(consumer, Blocks.CRACKED_STONE_BRICKS, ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS);
        offerConversion(consumer, Blocks.CHISELED_STONE_BRICKS, ZeldaBlocks.CHISELED_FAIRY_MARBLE);
        offerConversion(consumer, Blocks.SMOOTH_STONE, ZeldaBlocks.SMOOTH_FAIRY_MARBLE);
        offerConversion(consumer, ZeldaBlocks.STONE_SWORD_PEDESTAL, ZeldaBlocks.FAIRY_MARBLE_SWORD_PEDESTAL);
    }

    private static void unique(Consumer<RecipeJsonProvider> consumer) {


        // Dungeoncite /////////////////////////////////////////////////////////////////////////////////////////////////

        // These Recipes might be removed in the future!!!
        offerConversion(consumer, Blocks.PACKED_MUD, ZeldaBlocks.BROWN_DUNGEONCITE.base);

        offerConversion(consumer, Blocks.MUD_BRICKS, ZeldaBlocks.BROWN_DUNGEONCITE.brick);
        offerConversion(consumer, Blocks.MUD_BRICK_SLAB, ZeldaBlocks.BROWN_DUNGEONCITE.brickSlab);
        offerConversion(consumer, Blocks.MUD_BRICK_STAIRS, ZeldaBlocks.BROWN_DUNGEONCITE.brickStair);

        // Strange Dirt ////////////////////////////////////////////////////////////////////////////////////////////////
        offerConversion(consumer, Blocks.DIRT, ZeldaBlocks.STRANGE_DIRT.base);
        offerConversion(consumer, Blocks.GRASS_BLOCK, ZeldaBlocks.STRANGE_DIRT.base);
        offerConversion(consumer, Blocks.MYCELIUM, ZeldaBlocks.STRANGE_DIRT.base);
        offerConversion(consumer, Blocks.PODZOL, ZeldaBlocks.STRANGE_DIRT.base);
        offerConversion(consumer, Blocks.COARSE_DIRT, ZeldaBlocks.STRANGE_DIRT.base);
        offerConversion(consumer, Blocks.ROOTED_DIRT, ZeldaBlocks.STRANGE_DIRT.base);

        offerConversion(consumer, ZeldaBlocks.STRANGE_DIRT.base, Blocks.DIRT);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper Methods //////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void offerConversion(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, ItemConvertible output) {
        offerConversion(exporter, input, new ItemStack(output), "conversion");
    }

    public static void offerDyeConversion(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, ItemConvertible output) {
        offerConversion(exporter, input, new ItemStack(output), "undye");
    }

    public static void offerConversion(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, ItemStack output, String type) {
        SpringWaterRecipeJsonBuilder.offerSpringWaterConversion(exporter, output, input.asItem(), 140, type);
    }
}
