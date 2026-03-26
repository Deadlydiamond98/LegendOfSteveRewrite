package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ZeldaRecipeDatagen extends FabricRecipeProvider {

    private static final List<Item> DYES = List.of(
            Items.WHITE_DYE,
            Items.LIGHT_GRAY_DYE,
            Items.GRAY_DYE,
            Items.BLACK_DYE,
            Items.BROWN_DYE,
            Items.RED_DYE,
            Items.ORANGE_DYE,
            Items.YELLOW_DYE,
            Items.LIME_DYE,
            Items.GREEN_DYE,
            Items.CYAN_DYE,
            Items.LIGHT_BLUE_DYE,
            Items.BLUE_DYE,
            Items.PURPLE_DYE,
            Items.MAGENTA_DYE,
            Items.PINK_DYE
    );

    public ZeldaRecipeDatagen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        chiseledPlanks(consumer);
        fairyMarble(consumer);
        masterOre(consumer);
        tektiles(consumer);
        lootpots(consumer);

        ZeldaBlocks.DEKU_WOOD.generateRecipes(consumer);
    }

    private void chiseledPlanks(Consumer<RecipeJsonProvider> consumer) {
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_OAK_PLANKS, Blocks.OAK_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_BIRCH_PLANKS, Blocks.BIRCH_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_SPRUCE_PLANKS, Blocks.SPRUCE_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_JUNGLE_PLANKS, Blocks.JUNGLE_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_ACACIA_PLANKS, Blocks.ACACIA_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_DARK_OAK_PLANKS, Blocks.DARK_OAK_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_CRIMSON_PLANKS, Blocks.CRIMSON_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_WARPED_PLANKS, Blocks.WARPED_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_MANGROVE_PLANKS, Blocks.MANGROVE_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_BAMBOO_PLANKS, Blocks.BAMBOO_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_CHERRY_PLANKS, Blocks.CHERRY_SLAB);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_DEKU_PLANKS, ZeldaBlocks.DEKU_WOOD.slab);
    }

    private void fairyMarble(Consumer<RecipeJsonProvider> consumer) {
        ZeldaBlocks.COBBLED_FAIRY_MARBLE.generateRecipesStone(consumer);
        offerStoneSmelt(consumer, ZeldaBlocks.COBBLED_FAIRY_MARBLE.base, ZeldaBlocks.FAIRY_MARBLE.base);
        offerStoneSmelt(consumer, ZeldaBlocks.FAIRY_MARBLE.base, ZeldaBlocks.SMOOTH_FAIRY_MARBLE);

        ZeldaBlocks.FAIRY_MARBLE.generateRecipesStone(consumer);

        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.POLISHED_FAIRY_MARBLE.base, ZeldaBlocks.FAIRY_MARBLE.base);
        ZeldaBlocks.POLISHED_FAIRY_MARBLE.generateRecipesStone(consumer, ZeldaBlocks.FAIRY_MARBLE.base);

        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.FAIRY_MARBLE_BRICKS.base, ZeldaBlocks.POLISHED_FAIRY_MARBLE.base);
        ZeldaBlocks.FAIRY_MARBLE_BRICKS.generateRecipesStone(consumer, ZeldaBlocks.FAIRY_MARBLE.base, ZeldaBlocks.POLISHED_FAIRY_MARBLE.base);

        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.FAIRY_MARBLE_TILES.base, ZeldaBlocks.FAIRY_MARBLE_BRICKS.base);
        ZeldaBlocks.FAIRY_MARBLE_TILES.generateRecipesStone(consumer, ZeldaBlocks.FAIRY_MARBLE.base, ZeldaBlocks.POLISHED_FAIRY_MARBLE.base, ZeldaBlocks.FAIRY_MARBLE_BRICKS.base);

        offerMossyBrickRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.base, ZeldaBlocks.FAIRY_MARBLE_BRICKS.base);
        ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.generateRecipesStone(consumer);

        offerCrackingRecipe(consumer, ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS, ZeldaBlocks.FAIRY_MARBLE_BRICKS.base);

        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_FAIRY_MARBLE, ZeldaBlocks.FAIRY_MARBLE_BRICKS.slab);
        offerStoneCuttingRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CHISELED_FAIRY_MARBLE, ZeldaBlocks.FAIRY_MARBLE.base, ZeldaBlocks.POLISHED_FAIRY_MARBLE.base, ZeldaBlocks.FAIRY_MARBLE_BRICKS.base);

        offerPillarRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.FAIRY_MARBLE_PILLAR, ZeldaBlocks.FAIRY_MARBLE_BRICKS.base);
        offerStoneCuttingRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.FAIRY_MARBLE_PILLAR, ZeldaBlocks.FAIRY_MARBLE.base, ZeldaBlocks.POLISHED_FAIRY_MARBLE.base, ZeldaBlocks.FAIRY_MARBLE_BRICKS.base);
    }

    private void masterOre(Consumer<RecipeJsonProvider> consumer) {
        offerReversibleCompactingRecipes(consumer, RecipeCategory.DECORATIONS, ZeldaItems.MASTER_INGOT, RecipeCategory.DECORATIONS, ZeldaBlocks.MASTER_BLOCK);
        offerReversibleCompactingRecipes(consumer, RecipeCategory.DECORATIONS, ZeldaItems.MASTER_SCRAP, RecipeCategory.DECORATIONS, ZeldaBlocks.MASTER_SCRAP_BLOCK);

        ZeldaBlocks.MASTER_PLATE.generateRecipesStone(consumer);

        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.MASTER_BRICK.base, ZeldaBlocks.MASTER_PLATE.base);
        ZeldaBlocks.MASTER_BRICK.generateRecipesStone(consumer, ZeldaBlocks.MASTER_PLATE.base);

        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.MASTER_TILE.base, ZeldaBlocks.MASTER_BRICK.base);
        ZeldaBlocks.MASTER_TILE.generateRecipesStone(consumer, ZeldaBlocks.MASTER_PLATE.base, ZeldaBlocks.MASTER_BRICK.base);

        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CUT_MASTER_PLATE, ZeldaBlocks.MASTER_PLATE.slab);
        offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.CUT_MASTER_PLATE, ZeldaBlocks.MASTER_PLATE.base);

        offerPillarRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.MASTER_PILLAR, ZeldaBlocks.MASTER_PLATE.base);
        offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.MASTER_PILLAR, ZeldaBlocks.MASTER_PLATE.base);
    }

    private void tektiles(Consumer<RecipeJsonProvider> consumer) {
        ZeldaBlocks.BLUE_TEKTILES.generateRecipesStone(consumer);
        ZeldaBlocks.SMALL_BLUE_TEKTILES.generateRecipesStone(consumer, ZeldaBlocks.BLUE_TEKTILES.base);
        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.SMALL_BLUE_TEKTILES.base, ZeldaBlocks.BLUE_TEKTILES.base);

        ZeldaBlocks.RED_TEKTILES.generateRecipesStone(consumer);
        ZeldaBlocks.SMALL_RED_TEKTILES.generateRecipesStone(consumer, ZeldaBlocks.RED_TEKTILES.base);
        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ZeldaBlocks.SMALL_RED_TEKTILES.base, ZeldaBlocks.RED_TEKTILES.base);
    }

    private void lootpots(Consumer<RecipeJsonProvider> consumer) {
        List<Item> pots = new ArrayList<>();
        for (Block block : ZeldaBlocks.DYED_LOOT_POTS.getAll()) {
            pots.add(block.asItem());
        }

        offerDyeableRecipes(consumer, DYES, pots, "loot_pot", ZeldaBlocks.LOOT_POT.asItem());
        offerDyeable8xRecipes(consumer, DYES, pots, "loot_pot", ZeldaBlocks.LOOT_POT.asItem());
        offerLootPotRecipes(consumer, DYES, pots, "loot_pot");
    }

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    private static void offerStoneSmelt(Consumer<RecipeJsonProvider> consumer, Block input, Block output) {
        offerSmelting(
                consumer, List.of(input),
                RecipeCategory.BUILDING_BLOCKS, output,
                0.1f, 200, RecipeCategory.BUILDING_BLOCKS.getName()
        );
    }

    public static void offerPillarRecipe(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(category, output, 2)
                .input('#', input)
                .pattern("#")
                .pattern("#")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter);
    }

    public static void offerMossyBrickRecipe(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, ItemConvertible input) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .input(Blocks.VINE)
                .group(category.getName())
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input) + "_vine");

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input(input)
                .input(Blocks.MOSS_BLOCK)
                .group(category.getName())
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, convertBetween(output, input) + "_moss");
    }

    public static void offerStoneCuttingRecipes(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, ItemConvertible... inputs) {
        for (ItemConvertible input : inputs) {
            offerStonecuttingRecipe(exporter, category, output, input);
        }
    }

    // Dyed ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void offerDyeableRecipes(Consumer<RecipeJsonProvider> exporter, List<Item> dyes, List<Item> dyeables, String group, Item... additionalInput) {
        List<Item> dyableInputs = new ArrayList<>(List.copyOf(dyeables));
        dyableInputs.addAll(Arrays.asList(additionalInput));

        for (int i = 0; i < dyes.size(); i++) {
            Item item = dyes.get(i);
            Item item2 = dyeables.get(i);
            ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, item2)
                    .input(item)
                    .input(Ingredient.ofStacks(dyableInputs.stream().filter(dyeable -> !dyeable.equals(item2)).map(ItemStack::new)))
                    .group(group)
                    .criterion("has_needed_dye", conditionsFromItem(item))
                    .offerTo(exporter, "dye_" + getItemPath(item2));
        }
    }

    public static void offerDyeable8xRecipes(Consumer<RecipeJsonProvider> exporter, List<Item> dyes, List<Item> dyeables, String group, Item... additionalInput) {
        List<Item> dyableInputs = new ArrayList<>(List.copyOf(dyeables));
        dyableInputs.addAll(Arrays.asList(additionalInput));

        for (int i = 0; i < dyes.size(); i++) {
            Item item = dyes.get(i);
            Item item2 = dyeables.get(i);
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, item2, 8)
                    .input('#', Ingredient.ofStacks(dyableInputs.stream().filter(dyeable -> !dyeable.equals(item2)).map(ItemStack::new)))
                    .input('X', item)
                    .pattern("###")
                    .pattern("#X#")
                    .pattern("###")
                    .group(group)
                    .criterion("has_needed_dye", conditionsFromItem(item))
                    .offerTo(exporter, "multi_dye_" + getItemPath(item2));
        }
    }

    public static void offerLootPotRecipes(Consumer<RecipeJsonProvider> exporter, List<Item> dyes, List<Item> dyeables, String group) {
        for (int i = 0; i < dyes.size(); i++) {
            Item dye = dyes.get(i);
            Item output = dyeables.get(i);
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 2)
                    .input('E', ZeldaItems.EMERALD_SHARD)
                    .input('X', Items.BRICK)
                    .input('#', dye)
                    .pattern("E")
                    .pattern("X")
                    .pattern("#")
                    .group(group)
                    .criterion("has_needed_dye", conditionsFromItem(dye))
                    .offerTo(exporter, "base_dye_" + getItemPath(output));
        }
    }
}
