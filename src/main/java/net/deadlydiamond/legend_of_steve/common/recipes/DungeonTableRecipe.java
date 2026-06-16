package net.deadlydiamond.legend_of_steve.common.recipes;

import com.google.gson.JsonObject;
import net.deadlydiamond.legend_of_steve.util.recipe.GridRecipeUtil;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Map;

public class DungeonTableRecipe implements Recipe<RecipeInputInventory> {
    private final Identifier id;
    private final int width;
    private final int height;
    private final DefaultedList<Ingredient> input;
    private final ItemStack output;

    public DungeonTableRecipe(Identifier id, int width, int height, DefaultedList<Ingredient> input, ItemStack output) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.input = input;
        this.output = output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        return GridRecipeUtil.matches(inventory, world, this.width, this.height, this.input);
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        return this.getOutput(registryManager).copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.input;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    // TYPE & SERIALIZER ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<DungeonTableRecipe> {
        public static final DungeonTableRecipe.Type INSTANCE = new DungeonTableRecipe.Type();
    }

    public static class Serializer implements RecipeSerializer<DungeonTableRecipe> {
        public static final DungeonTableRecipe.Serializer INSTANCE = new DungeonTableRecipe.Serializer();

        @Override
        public DungeonTableRecipe read(Identifier id, JsonObject json) {

            String[] strings = GridRecipeUtil.removePadding(GridRecipeUtil.getPattern(JsonHelper.getArray(json, "pattern")));
            Map<String, Ingredient> map = GridRecipeUtil.readSymbols(JsonHelper.getObject(json, "key"));
            int width = strings[0].length();
            int height = strings.length;

            DefaultedList<Ingredient> input = GridRecipeUtil.createMatrixPattern(strings, map, width, height);
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));

            return new DungeonTableRecipe(id, width, height, input, output);
        }

        @Override
        public DungeonTableRecipe read(Identifier id, PacketByteBuf buf) {
            int width = buf.readVarInt();
            int height = buf.readVarInt();

            DefaultedList<Ingredient> input = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
            input.replaceAll(ignored -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();

            return new DungeonTableRecipe(id, width, height, input, output);
        }

        @Override
        public void write(PacketByteBuf buf, DungeonTableRecipe recipe) {
            buf.writeVarInt(recipe.width);
            buf.writeVarInt(recipe.height);

            for (Ingredient ingredient : recipe.input) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.output);
        }
    }
}
