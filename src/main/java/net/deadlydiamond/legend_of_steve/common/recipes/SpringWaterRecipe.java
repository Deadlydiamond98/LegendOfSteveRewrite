package net.deadlydiamond.legend_of_steve.common.recipes;

import com.google.gson.JsonObject;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SpringWaterRecipe implements Recipe<Inventory> {
    private final Identifier id;
    private final ItemStack input;
    private final ItemStack output;
    protected final String group;

    public SpringWaterRecipe(Identifier id, String group, ItemStack input, ItemStack output) {
        this.id = id;
        this.group = group;
        this.input = input;
        this.output = output;
    }

    public Identifier getId() {
        return this.id;
    }

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(Ingredient.ofItems(this.input.getItem()));
        return defaultedList;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return Ingredient.ofItems(this.input.getItem()).test(inventory.getStack(0));
    }

    public ItemStack getInput() {
        return this.input;
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SpringWaterRecipe> {
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<SpringWaterRecipe> {
        public static final Serializer INSTANCE = new Serializer();


        @Override
        public SpringWaterRecipe read(Identifier identifier, JsonObject jsonObject) {

            String string = JsonHelper.getString(jsonObject, "group", "");
            ItemStack input = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "input"));
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));

            return new SpringWaterRecipe(identifier, string, input, output);
        }

        @Override
        public SpringWaterRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {

            String string = packetByteBuf.readString();

            ItemStack input = packetByteBuf.readItemStack();
            ItemStack output = packetByteBuf.readItemStack();

            return new SpringWaterRecipe(identifier, string, input, output);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, SpringWaterRecipe recipe) {
            packetByteBuf.writeString(recipe.group);
            packetByteBuf.writeItemStack(recipe.input);
            packetByteBuf.writeItemStack(recipe.output);
        }
    }
}
