package net.deadlydiamond.legend_of_steve.compat.jei;

import mezz.jei.api.recipe.RecipeType;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.recipes.DungeonTableRecipe;
import net.deadlydiamond.legend_of_steve.common.recipes.SpringWaterRecipe;

public class ZeldaJEIRecipeTypes {
    public static final RecipeType<SpringWaterRecipe> SPRING_WATER = register("spring_water_conversion", SpringWaterRecipe.class);
    public static final RecipeType<DungeonTableRecipe> DUNGEON_TABLE = register("dungeon_table", DungeonTableRecipe.class);

    public static <T> RecipeType<T> register(String name, Class<? extends T> recipeClass) {
        return RecipeType.create(LegendOfSteve.MOD_ID, name, recipeClass);
    }
}
