package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.recipes.SpringWaterRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ZeldaRecipes {

    public static void register() {
        register("spring_water_conversion", SpringWaterRecipe.Serializer.INSTANCE, SpringWaterRecipe.Type.INSTANCE);
    }

    private static void register(String id, RecipeSerializer<?> serializer, RecipeType<?> type) {
        Registry.register(Registries.RECIPE_SERIALIZER, LegendOfSteve.id(id), serializer);
        Registry.register(Registries.RECIPE_TYPE, LegendOfSteve.id(id), type);
    }
}
