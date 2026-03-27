package net.deadlydiamond.legend_of_steve.datagen.recipe.spring_water;

import com.google.gson.JsonObject;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.recipes.SpringWaterRecipe;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SpringWaterRecipeJsonBuilder {
    private final Item input;
    private final ItemStack output;
    private final int time;

    private SpringWaterRecipeJsonBuilder(Item input, ItemStack output, int time) {
        this.input = input;
        this.output = output;
        this.time = time;
    }

    public static void offerSpringWaterConversion(Consumer<RecipeJsonProvider> exporter, ItemStack output, Item input, int time, String type) {
        String fileName = "spring_water_" + Registries.ITEM.getId(input).getPath() + "_to_" + Registries.ITEM.getId(output.getItem()).getPath();

        SpringWaterRecipeJsonBuilder.create(input, output, time).offerTo(exporter, LegendOfSteve.id("spring_water/" + type + "/" + fileName));
    }

    public static SpringWaterRecipeJsonBuilder create(Item input, ItemStack output, int time) {
        return new SpringWaterRecipeJsonBuilder(input, output, time);
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier id) {
        exporter.accept(new RecipeJsonProvider() {

            @Override
            public void serialize(JsonObject json) {
                json.addProperty("type", LegendOfSteve.id("spring_water_conversion").toString());
                addItemStackProperty(json, input.getDefaultStack(), "input", false);
                addItemStackProperty(json, output, "result", true);
                if (time != 140) {
                    json.addProperty("submerged_time", time);
                }
            }

            private void addItemStackProperty(JsonObject json, ItemStack stack, String field, boolean withCount) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("item", Registries.ITEM.getId(stack.getItem()).toString());
                if (stack.getCount() > 1 && withCount) {
                    jsonObject.addProperty("count", stack.getCount());
                }

                json.add(field, jsonObject);
            }

            @Override
            public Identifier getRecipeId() {
                return id;
            }

            @Override
            public RecipeSerializer<?> getSerializer() {
                return SpringWaterRecipe.Serializer.INSTANCE;
            }

            @Override
            @Nullable
            public JsonObject toAdvancementJson() {
                return null;
            }

            @Override
            @Nullable
            public Identifier getAdvancementId() {
                return null;
            }
        });
    }
}
