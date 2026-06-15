package net.deadlydiamond.legend_of_steve.compat.jei.categories;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.recipes.DungeonTableRecipe;
import net.deadlydiamond.legend_of_steve.compat.jei.LegendOfSteveJEI;
import net.deadlydiamond.legend_of_steve.compat.jei.ZeldaJEIRecipeTypes;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class DungeonTableRecipeCategory implements IRecipeCategory<DungeonTableRecipe> {
    private static final Identifier TEXTURE = LegendOfSteve.id("textures/gui/jei/dungeon_table.png");
    private static final int WIDTH = 164;
    private static final int HEIGHT = 66;

    private final IDrawable icon;

    public DungeonTableRecipeCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ZeldaBlocks.DUNGEON_TABLE));
    }

    @Override
    public void draw(DungeonTableRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
        guiGraphics.drawTexture(TEXTURE, 0, 0, 0, 0, WIDTH, HEIGHT);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DungeonTableRecipe recipe, IFocusGroup focuses) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                builder.addInputSlot((x * 19) + 23, (y * 19) + 6).addIngredients(recipe.getIngredients().get(x + y * 3));
            }
        }

        builder.addOutputSlot(118, 25).addItemStack(recipe.getOutput());
    }

    @Override
    public Text getTitle() {
        return LegendOfSteveJEI.getTitle("dungeon_table");
    }

    @Override
    public RecipeType<DungeonTableRecipe> getRecipeType() {
        return ZeldaJEIRecipeTypes.DUNGEON_TABLE;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
