package net.deadlydiamond.legend_of_steve.compat.jei.categories;

import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.recipes.SpringWaterRecipe;
import net.deadlydiamond.legend_of_steve.compat.jei.LegendOfSteveJEI;
import net.deadlydiamond.legend_of_steve.compat.jei.ZeldaJEIRecipeTypes;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SpringWaterRecipeCategory implements IRecipeCategory<SpringWaterRecipe> {
    private static final Identifier TEXTURE = LegendOfSteve.id("textures/gui/jei/spring_water/spring_water_transmutation.png");
    private static final Identifier WATER = LegendOfSteve.id("textures/gui/jei/spring_water/spring_water.png");

    private static final int WIDTH = 113;
    private static final int HEIGHT = 64;

    private final ITickTimer waterAnimationTimer;
    private final IGuiHelper guiHelper;
    private final IDrawable icon;

    public SpringWaterRecipeCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ZeldaItems.SPRING_WATER_BUCKET));
        this.waterAnimationTimer = guiHelper.createTickTimer(32, 32, false);
        this.guiHelper = guiHelper;
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, SpringWaterRecipe recipe, IFocusGroup focuses) {
        int transmuteTime = recipe.getTime();

        IDrawableAnimated arrow = this.guiHelper.drawableBuilder(TEXTURE, 113, 0, 24, 18)
                        .buildAnimated(transmuteTime, IDrawableAnimated.StartDirection.LEFT, false);

        builder.addDrawable(arrow, 21, 23);

        builder.addText(Text.literal(transmuteTime / 20 + "s"), getWidth() - 20, 10)
                .setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
                .setColor(0xFF808080);
    }

    @Override
    public void draw(SpringWaterRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
        drawWater(guiGraphics, 49, 0, 4, 4);
        guiGraphics.drawTexture(TEXTURE, 0, 0, 0, 0, WIDTH, HEIGHT);
    }

    private void drawWater(DrawContext guiGraphics, int startX, int startY, int xCount, int yCount) {
        float v = this.waterAnimationTimer.getValue() * 16;
        for (int x = 0; x < xCount; x++) {
            for (int y = 0; y < yCount; y++) {
                guiGraphics.drawTexture(
                        WATER,
                        startX + (16 * x), startY + (16 * y),
                        0, v,
                        16, 16,
                        16, 512
                );
            }
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpringWaterRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(1, 24).addIngredients(recipe.getIngredients().get(0));
        builder.addOutputSlot(73, 24).addItemStack(LegendOfSteveJEI.getOutput(recipe));
    }

    @Override
    public Text getTitle() {
        return LegendOfSteveJEI.getTitle("enchanted_spring_water");
    }

    @Override
    public RecipeType<SpringWaterRecipe> getRecipeType() {
        return ZeldaJEIRecipeTypes.SPRING_WATER;
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
