package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.common.blocks.deco.PlatformBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.util.datagen.BlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.util.datagen.RecipeDatagenUtil;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
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

public class ChiseledWoodBlockset extends AbstractBlockset {
    private final boolean flammable;

    public final Block base;
    public final Block platform;
    public final Block beveled;
    public final Block beveled_slab;
    public final Block beveled_stair;

    public ChiseledWoodBlockset(String modID, String id, AbstractBlock.Settings settings, boolean flammable) {
        super(modID, id);
        this.flammable = flammable;

        this.base = this.register(modID, "chiseled_" + this.id() + "_planks", new Block(settings));
        this.platform = this.register(modID, "chiseled_" + this.id() + "_platform", new PlatformBlock(settings));
        this.beveled = this.register(modID, "chiseled_" + this.id() + "_bricks", new Block(settings));
        this.beveled_stair = this.register(modID, "chiseled_" + this.id() + "_brick_stairs", new StairsBlock(this.base.getDefaultState(), settings));
        this.beveled_slab = this.register(modID, "chiseled_" + this.id() + "_brick_slab", new SlabBlock(settings));

        if (flammable) {
            FlammableBlockRegistry.getDefaultInstance().add(this.beveled_stair, 5, 20);
            FlammableBlockRegistry.getDefaultInstance().add(this.beveled_slab, 5, 20);
        }
    }

    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.@Nullable SharedModel sharedModel) {
        this.generateModels(modelGen, false);
    }

    public void generateModels(BlockStateModelGenerator modelGen, boolean uniqueSlab) {
        modelGen.registerSimpleCubeAll(this.base);
        if (uniqueSlab) {
            BlockModelDatagenUtil.registerSlabUnique(modelGen, this.beveled_slab, this.base);
        } else {
            BlockModelDatagenUtil.registerSlab(modelGen, this.beveled_slab, this.base);
        }

        BlockModelDatagenUtil.registerStairs(modelGen, this.beveled_stair, this.base);
    }

    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        super.generateRecipes(exporter);
        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.beveled_slab, this.base);
        RecipeDatagenUtil.createStairRecipe(exporter, this.beveled_stair, this.base);
    }

    public void generateBlockTags(BiConsumer<TagKey<Block>, Block> tagConsumer, TagKey<Block>... mineableTags) {
        super.generateBlockTags(tagConsumer, mineableTags);
        tagConsumer.accept(BlockTags.SLABS, this.beveled_slab);
        tagConsumer.accept(BlockTags.STAIRS, this.beveled_stair);

        tagConsumer.accept(ZeldaTags.CHISELED_PLANKS_BLOCK, this.base);
        tagConsumer.accept(ZeldaTags.CHISELED_PLANKS_BLOCK, this.beveled);
        tagConsumer.accept(ZeldaTags.CHISELED_PLANKS_BLOCK, this.platform);
        tagConsumer.accept(BlockTags.WOODEN_SLABS, this.beveled_slab);
        tagConsumer.accept(BlockTags.WOODEN_STAIRS, this.beveled_stair);
    }

    public void generateItemTags(BiConsumer<TagKey<Item>, ItemConvertible> tagConsumer) {
        super.generateItemTags(tagConsumer);
        tagConsumer.accept(ItemTags.SLABS, this.beveled_slab);
        tagConsumer.accept(ItemTags.STAIRS, this.beveled_stair);

        if (!this.flammable) {
            tagConsumer.accept(ItemTags.NON_FLAMMABLE_WOOD, this.beveled_slab.asItem());
            tagConsumer.accept(ItemTags.NON_FLAMMABLE_WOOD, this.beveled_stair.asItem());
        }

        tagConsumer.accept(ZeldaTags.CHISELED_PLANKS_ITEM, this.base);
        tagConsumer.accept(ZeldaTags.CHISELED_PLANKS_ITEM, this.beveled);
        tagConsumer.accept(ZeldaTags.CHISELED_PLANKS_ITEM, this.platform);
        tagConsumer.accept(ItemTags.WOODEN_SLABS, this.beveled_slab.asItem());
        tagConsumer.accept(ItemTags.WOODEN_STAIRS, this.beveled_stair.asItem());
    }
}
