package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.light.BrazierBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.light.TallBrazierBlock;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.ToIntFunction;


public class BrazierBlockset extends AbstractBlockset {

    public final Block regular;
    public final Block soul;

    public final Block tall;
    public final Block tall_soul;

    public BrazierBlockset(String modID, String id, Block base) {
        super(modID, id);
        this.regular = this.register(modID, this.id() + "_brazier", new BrazierBlock(FabricBlockSettings.copyOf(base)
                .luminance(lightRegular(15)), 1)
        );
        this.soul = this.register(modID, this.id() + "_soul_brazier", new BrazierBlock(FabricBlockSettings.copyOf(base)
                .luminance(lightRegular(10)), 2)
        );

        this.tall = this.register(modID, "tall_" + this.id() + "_brazier", new TallBrazierBlock(FabricBlockSettings.copyOf(base)
                .luminance(lightTall(15)), 1)
        );
        this.tall_soul = this.register(modID, "tall_" + this.id() + "_soul_brazier", new TallBrazierBlock(FabricBlockSettings.copyOf(base)
                .luminance(lightTall(10)), 2)
        );
    }

    public static ToIntFunction<BlockState> lightTall(int litLevel) {
        return state -> (state.get(Properties.LIT) && state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) ? litLevel : 0;
    }

    public static ToIntFunction<BlockState> lightRegular(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.SharedModel sharedModel) {
        super.generateModels(modelGen, sharedModel);
        ZeldaBlockModelDatagenUtil.registerBrazier(modelGen, this.regular, new Identifier("block/campfire_fire"));
        ZeldaBlockModelDatagenUtil.registerBrazierParented(modelGen, this.soul, this.regular, new Identifier("block/soul_campfire_fire"));

        ZeldaBlockModelDatagenUtil.registerTallBrazier(modelGen, this.tall, this.regular, this.regular, new Identifier("block/campfire_fire"));
        ZeldaBlockModelDatagenUtil.registerTallBrazier(modelGen, this.tall_soul, this.regular, this.soul, new Identifier("block/soul_campfire_fire"));
    }

    public void generateRecipes(Consumer<RecipeJsonProvider> exporter, ItemConvertible block, ItemConvertible slab, ItemConvertible metal) {
        generateRecipes(exporter, Ingredient.ofItems(block), slab, metal);
    }

    public void generateRecipes(Consumer<RecipeJsonProvider> exporter, Ingredient block, ItemConvertible slab, ItemConvertible metal) {
        this.createBrazierRecipe(exporter, this.regular, ItemTags.COALS, slab, metal);
        this.createBrazierRecipe(exporter, this.soul, ItemTags.SOUL_FIRE_BASE_BLOCKS, slab, metal);
        this.createTallBrazierRecipe(exporter, this.tall, ItemTags.COALS, block, slab, metal);
        this.createTallBrazierRecipe(exporter, this.tall_soul, ItemTags.SOUL_FIRE_BASE_BLOCKS, block, slab, metal);
    }

    private void createTallBrazierRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, TagKey<Item> coalItem, Ingredient block, ItemConvertible slab, ItemConvertible metal) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('i', metal)
                .input('c', coalItem)
                .input('#', slab)
                .input('B', block)
                .pattern("ici")
                .pattern(" B ")
                .pattern("#B#")
                .criterion("has_proper_brazier_item", RecipeProvider.conditionsFromTag(coalItem))
                .offerTo(exporter);
    }

    private void createBrazierRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, TagKey<Item> coalItem, ItemConvertible slab, ItemConvertible metal) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .input('i', metal)
                .input('c', coalItem)
                .input('#', slab)
                .pattern("ici")
                .pattern("###")
                .criterion("has_proper_brazier_item", RecipeProvider.conditionsFromTag(coalItem))
                .offerTo(exporter);
    }
}
