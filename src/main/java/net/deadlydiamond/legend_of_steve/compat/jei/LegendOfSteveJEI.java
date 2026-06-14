package net.deadlydiamond.legend_of_steve.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.recipes.SpringWaterRecipe;
import net.deadlydiamond.legend_of_steve.compat.jei.categories.SpringWaterRecipeCategory;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Function;

@JeiPlugin
public class LegendOfSteveJEI implements IModPlugin {

    @Override
    public Identifier getPluginUid() {
        return LegendOfSteve.id("jei_compat");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ZeldaItems.SPRING_WATER_BUCKET, ZeldaJEIRecipeTypes.SPRING_WATER);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        category(registration, SpringWaterRecipeCategory::new);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ZeldaJEIRecipeTypes.SPRING_WATER, getRecipes(SpringWaterRecipe.Type.INSTANCE));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void category(IRecipeCategoryRegistration registration, Function<IGuiHelper, IRecipeCategory<?>> category) {
        registration.addRecipeCategories(category.apply(registration.getJeiHelpers().getGuiHelper()));
    }

    private <C extends Inventory, T extends Recipe<C>> List<T> getRecipes(net.minecraft.recipe.RecipeType<T> type) {
        return MinecraftClient.getInstance().world.getRecipeManager().listAllOfType(type);
    }

    public static Text getTitle(String name) {
        return LegendOfSteve.lang("jei", name);
    }
}
