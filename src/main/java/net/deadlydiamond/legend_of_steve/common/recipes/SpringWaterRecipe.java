package net.deadlydiamond.legend_of_steve.common.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SpringWaterRecipe implements Recipe<Inventory> {
    private final Identifier id;
    private final Item input;
    private final ItemStack output;
    private final int time;

    public SpringWaterRecipe(Identifier id, Item input, ItemStack output, int time) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.time = time;
    }

    public Identifier getId() {
        return this.id;
    }

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(Ingredient.ofItems(this.input));
        return defaultedList;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return Ingredient.ofItems(this.input).test(inventory.getStack(0));
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

    public ItemStack getOutput(int count) {
        return new ItemStack(this.output.getItem(), this.output.getCount() * count);
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
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

            Item input = ShapedRecipe.getItem(JsonHelper.getObject(jsonObject, "input"));
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            int time = JsonHelper.getInt(jsonObject, "submerged_time", 140);

            return new SpringWaterRecipe(identifier, input, output, time);
        }

        @Override
        public SpringWaterRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {

            Identifier input = packetByteBuf.readIdentifier();
            ItemStack output = packetByteBuf.readItemStack();
            int time = packetByteBuf.readInt();

            return new SpringWaterRecipe(identifier, Registries.ITEM.get(input), output, time);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, SpringWaterRecipe recipe) {
            packetByteBuf.writeIdentifier(Registries.ITEM.getId(recipe.input));
            packetByteBuf.writeItemStack(recipe.output);
            packetByteBuf.writeInt(recipe.time);
        }
    }
}
