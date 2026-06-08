package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.QuestionBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.StrangeBrickBlock;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.util.datagen.BlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.util.datagen.RecipeDatagenUtil;
import net.minecraft.block.*;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class StrangeDirtBricksBlockset extends AbstractBlockset {
    private final boolean stripEndS;

    public final Block base;
    public final Block container;
    public final Block slab;
    public final Block stair;
    public final Block wall;

    public StrangeDirtBricksBlockset(String modID, String id, AbstractBlock.Settings settings) {
        this(modID, id, settings, true);
    }

    public StrangeDirtBricksBlockset(String modID, String id, AbstractBlock.Settings settings, boolean stripEndS) {
        super(modID, id);
        this.stripEndS = stripEndS;
        this.base = this.register(modID, this.id(), new StrangeBrickBlock(settings));
        this.container = this.register(modID, this.id() + "_container", new QuestionBlock(settings));
        this.stair = this.register(modID, this.id(this.stripEndS()) + "_stairs", new StairsBlock(this.base.getDefaultState(), settings));
        this.slab = this.register(modID, this.id(this.stripEndS()) + "_slab", new SlabBlock(settings));
        this.wall = this.register(modID, this.id(this.stripEndS()) + "_wall", new WallBlock(settings));
    }

    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.@Nullable SharedModel sharedModel) {
        this.generateModels(modelGen, false);
    }

    public void generateModels(BlockStateModelGenerator modelGen, boolean uniqueSlab) {
        modelGen.registerSimpleCubeAll(this.base);
//        ZeldaBlockModelDatagenUtil.registerHittableBlock(modelGen, this.container, this.base);
        if (uniqueSlab) {
            BlockModelDatagenUtil.registerSlabUnique(modelGen, this.slab, this.base);
        } else {
            BlockModelDatagenUtil.registerSlab(modelGen, this.slab, this.base);
        }

        BlockModelDatagenUtil.registerStairs(modelGen, this.stair, this.base);
        BlockModelDatagenUtil.registerWall(modelGen, this.wall, this.base);
    }

    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        super.generateRecipes(exporter);
        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.slab, this.base);
        RecipeDatagenUtil.createStairRecipe(exporter, this.stair, this.base);
        RecipeProvider.offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.wall, this.base);
    }

    public void generateRecipesStone(Consumer<RecipeJsonProvider> exporter, Block... additionalInputs) {
        this.generateRecipes(exporter);
        this.stoneCutterRecipes(exporter, this.base);
        Block[] var3 = additionalInputs;
        int var4 = additionalInputs.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Block block = var3[var5];
            this.stoneCutterRecipes(exporter, block);
        }
    }

    protected void stoneCutterRecipes(Consumer<RecipeJsonProvider> exporter, Block block) {
        if (block != this.base) {
            RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.base, block);
        }

        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.stair, block);
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.slab, block, 2);
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.wall, block);
    }

    public void generateBlockTags(BiConsumer<TagKey<Block>, Block> tagConsumer, TagKey<Block>... mineableTags) {
        super.generateBlockTags(tagConsumer, mineableTags);
        tagConsumer.accept(BlockTags.SLABS, this.slab);
        tagConsumer.accept(BlockTags.STAIRS, this.stair);
        tagConsumer.accept(BlockTags.WALLS, this.wall);
    }

    public void generateItemTags(BiConsumer<TagKey<Item>, ItemConvertible> tagConsumer) {
        super.generateItemTags(tagConsumer);
        tagConsumer.accept(ItemTags.SLABS, this.slab);
        tagConsumer.accept(ItemTags.STAIRS, this.stair);
        tagConsumer.accept(ItemTags.WALLS, this.wall);
    }

    protected String stripEndS() {
        return this.stripEndS ? "s$" : "";
    }
}
