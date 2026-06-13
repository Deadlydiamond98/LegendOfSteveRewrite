package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.common.blocks.deco.connected.TriforceTileBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.oriented.TileBlock;
import net.deadlydiamond.legend_of_steve.common.blocksets.dungeoncite.DungeonciteBlockset;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.init.KoalaLibTags;
import net.deadlydiamond98.koalalib.util.datagen.RecipeDatagenUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TileBlockset extends AbstractBlockset {

    public final Block tile;
    public final Block mossyTile;
    public final Block crackedTile;
    public final Block triforce;

    public TileBlockset(String modID, String id, AbstractBlock.Settings settings) {
        super(modID, id);

        this.tile = register(modID, id() + "_tile", new TileBlock(settings));
        this.mossyTile = register(modID,  "mossy_" + id() + "_tile", new TileBlock(settings));
        this.crackedTile = register(modID, "cracked_" + id() + "_tile", new TileBlock(settings));
        this.triforce = register(modID, id() + "_triforce_tile", new TriforceTileBlock(settings));
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.SharedModel sharedModel) {
        ZeldaBlockModelDatagenUtil.registerTile(modelGen, this.tile);
        ZeldaBlockModelDatagenUtil.registerTile(modelGen, this.mossyTile);
        ZeldaBlockModelDatagenUtil.registerTile(modelGen, this.crackedTile);
        ZeldaBlockModelDatagenUtil.registerTriforceTile(modelGen, this.tile, this.triforce);
    }

    @Override
    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        DungeonciteBlockset.cut(exporter, this.tile, this.triforce);
        DungeonciteBlockset.offerTriforceTileRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.triforce, this.tile);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyTile, this.tile);
        RecipeProvider.offerCrackingRecipe(exporter, this.crackedTile, this.tile);
    }

    @Override
    public void generateBlockTags(BiConsumer<TagKey<Block>, Block> tagConsumer, TagKey<Block>... mineableTags) {
        super.generateBlockTags(tagConsumer, mineableTags);
        tagConsumer.accept(KoalaLibTags.CRACKED_BRICKS, this.crackedTile);
    }
}
