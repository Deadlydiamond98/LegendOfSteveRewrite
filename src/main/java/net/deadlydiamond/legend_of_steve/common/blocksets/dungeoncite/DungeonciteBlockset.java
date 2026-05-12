package net.deadlydiamond.legend_of_steve.common.blocksets.dungeoncite;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.TileBlock;
import net.deadlydiamond.legend_of_steve.common.items.block.PushableBlockItem;
import net.deadlydiamond.legend_of_steve.datagen.recipe.ZeldaRecipeDatagen;
import net.deadlydiamond.legend_of_steve.datagen.recipe.spring_water.ZeldaSpringWaterConversionDatagen;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocks.advancement.*;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.init.KoalaLibBlockProperties;
import net.deadlydiamond98.koalalib.init.KoalaLibTags;
import net.deadlydiamond98.koalalib.util.datagen.BlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.util.datagen.RecipeDatagenUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DungeonciteBlockset extends AbstractBlockset {

    // TODO:
    //  - Re-Add Pedestal Block
    //  - Add Wall Varients
    //  - Maybe add Button & Plate Variants?
    //  - Make Triforce Tiles One Block?

    public final Block base;
    public final Block baseSlab;
    public final Block baseStair;

    public final Block mossyBase;
    public final Block mossyBaseSlab;
    public final Block mossyBaseStair;

    public final Block crackedBase;

    public final Block brick;
    public final Block brickSlab;
    public final Block brickStair;

    public final Block mossyBrick;
    public final Block mossyBrickSlab;
    public final Block mossyBrickStair;

    public final Block crackedBrick;

    public final Block chiseledBrick;
    public final Block chiseledBrickSlab;
    public final Block chiseledBrickStair;

    public final Block mossyChiseledBrick;
    public final Block mossyChiseledBrickSlab;
    public final Block mossyChiseledBrickStair;

    public final Block crackedChiseledBrick;

    public final Block smallBrick;
    public final Block smallBrickSlab;
    public final Block smallBrickStair;

    public final Block mossySmallBrick;
    public final Block mossySmallBrickSlab;
    public final Block mossySmallBrickStair;

    public final Block crackedSmallBrick;

    public final Block tile;
    public final Block mossyTile;
    public final Block crackedTile;

    public final Block tileTTL;
    public final Block tileTTR;
    public final Block tileTBL;
    public final Block tileTBR;

    public final Block reinforced;
    public final Item pushable;

//    public final Block pedestal;
    public final Block pillar;

    public final Block chiseled;

    public DungeonciteBlockset(String color, String advancementID, MapColor mapColor) {
        super(LegendOfSteve.MOD_ID, color + "_dungeoncite");

        FabricBlockSettings settings = FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(ZeldaSounds.DUNGEONCITE).mapColor(mapColor);
        FabricBlockSettings settingsBrick = FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(ZeldaSounds.DUNGEONCITE_BRICKS).mapColor(mapColor);
        FabricBlockSettings settingsTile = FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(ZeldaSounds.DUNGEONCITE_TILE).mapColor(mapColor);

        this.base = register(id(), new AdvancementNeededBlock(settings, advancementID));
        this.baseSlab = register(id() + "_slab", new AdvancementNeededSlab(settings, advancementID));
        this.baseStair = register(id() + "_stairs", new AdvancementNeededStairs(this.base.getDefaultState(), settings, advancementID));

        this.mossyBase = register("mossy_" + id(), new AdvancementNeededBlock(settings, advancementID));
        this.mossyBaseSlab = register("mossy_" + id() + "_slab", new AdvancementNeededSlab(settings, advancementID));
        this.mossyBaseStair = register("mossy_" + id() + "_stairs", new AdvancementNeededStairs(this.base.getDefaultState(), settings, advancementID));

        this.crackedBase = register("cracked_" + id(), new AdvancementNeededBlock(settings, advancementID));

        // Bricks

        this.brick = register(id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.brickSlab = register(id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.brickStair = register(id() + "_brick_stairs", new AdvancementNeededStairs(this.brick.getDefaultState(), settingsBrick, advancementID));

        this.mossyBrick = register("mossy_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.mossyBrickSlab = register("mossy_" + id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.mossyBrickStair = register("mossy_" + id() + "_brick_stairs", new AdvancementNeededStairs(this.mossyBrick.getDefaultState(), settingsBrick, advancementID));

        this.crackedBrick = register("cracked_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));

        // Chiseled Bricks

        this.chiseledBrick = register("chiseled_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.chiseledBrickSlab = register("chiseled_" + id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.chiseledBrickStair = register("chiseled_" + id() + "_brick_stairs", new AdvancementNeededStairs(this.chiseledBrick.getDefaultState(), settingsBrick, advancementID));

        this.mossyChiseledBrick = register("mossy_chiseled_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.mossyChiseledBrickSlab = register("mossy_chiseled_" + id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.mossyChiseledBrickStair = register("mossy_chiseled_" + id() + "_brick_stairs", new AdvancementNeededStairs(this.mossyChiseledBrick.getDefaultState(), settingsBrick, advancementID));

        this.crackedChiseledBrick = register("cracked_chiseled_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));

        // Small Bricks

        this.smallBrick = register("small_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.smallBrickSlab = register("small_" + id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.smallBrickStair = register("small_" + id() + "_brick_stairs", new AdvancementNeededStairs(this.smallBrick.getDefaultState(), settingsBrick, advancementID));

        this.mossySmallBrick = register("small_mossy_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.mossySmallBrickSlab = register("small_mossy_" + id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.mossySmallBrickStair = register("small_mossy_" + id() + "_brick_stairs", new AdvancementNeededStairs(this.smallBrick.getDefaultState(), settingsBrick, advancementID));

        this.crackedSmallBrick = register("small_cracked_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));

        // CHISELED

        this.chiseled = register("chiseled_" + id(), new AdvancementNeededBlock(settings, advancementID));

        // Tiles

        this.tile = register(id() + "_tile", new DungeonciteTile(settingsTile, advancementID));
        this.mossyTile = register("mossy_" + id() + "_tile", new DungeonciteTile(settingsTile, advancementID));
        this.crackedTile = register("cracked_" + id() + "_tile", new DungeonciteTile(settingsTile, advancementID));

        // There's probably a better way of doing this, but it works for now
        this.tileTTL = register(id() + "_tile_ttl", new DungeonciteTriforce(settingsTile, advancementID, id() + "_tile_triforce", "_ttl"));
        this.tileTTR = register(id() + "_tile_ttr", new DungeonciteTriforce(settingsTile, advancementID, id() + "_tile_triforce", "_ttr"));
        this.tileTBL = register(id() + "_tile_tbl", new DungeonciteTriforce(settingsTile, advancementID, id() + "_tile_triforce", "_tbl"));
        this.tileTBR = register(id() + "_tile_tbr", new DungeonciteTriforce(settingsTile, advancementID, id() + "_tile_triforce", "_tbr"));

        // Pillar
//        this.pedestal = register(id() + "_pedestal", new AdvancementNeededFacingBlock(settings, advancementID));
        this.pillar = register(id() + "_pillar", new AdvancementNeededPillarBlock(settings, advancementID));

        // Reinforced Dungeoncite

        this.reinforced = register("reinforced_" + id(), new AdvancementNeededBlock(settings, advancementID));
        this.pushable = ZeldaItems.register("pushable_" + id(), new PushableBlockItem(new FabricItemSettings(), this.reinforced.getDefaultState()));
    }

    @Override
    protected void addAdditionalToCreative(ItemGroup.Entries entry) {
        super.addAdditionalToCreative(entry);
        entry.add(this.pushable);
    }

    // Recipes /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        super.generateRecipes(exporter);

        // Base
        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.baseSlab, this.base);
        RecipeDatagenUtil.createStairRecipe(exporter, this.baseStair, this.base);

        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBase, this.base);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBaseSlab, this.baseSlab);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBaseStair, this.baseStair);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBaseSlab, this.mossyBase);
        RecipeDatagenUtil.createStairRecipe(exporter, this.mossyBaseStair, this.mossyBase);

        RecipeProvider.offerCrackingRecipe(exporter, this.crackedBase, this.base);

        // Brick
        RecipeProvider.offerPolishedStoneRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.brick, this.base);
        RecipeProvider.offerPolishedStoneRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrick, this.mossyBase);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.brickSlab, this.brick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.brickStair, this.brick);

        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrick, this.brick);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrickSlab, this.brickSlab);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrickStair, this.brickStair);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrickSlab, this.mossyBrick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.mossyBrickStair, this.mossyBrick);

        RecipeProvider.offerCrackingRecipe(exporter, this.crackedBrick, this.brick);

        // Chiseled Brick
        RecipeProvider.offerChiseledBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.chiseledBrick, this.brickSlab);
        RecipeProvider.offerChiseledBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrick, this.mossyBrickSlab);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.chiseledBrickSlab, this.chiseledBrick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.chiseledBrickStair, this.chiseledBrick);

        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrick, this.chiseledBrick);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrickSlab, this.chiseledBrickSlab);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrickStair, this.chiseledBrickStair);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrickSlab, this.mossyChiseledBrick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.mossyChiseledBrickStair, this.mossyChiseledBrick);

        RecipeProvider.offerCrackingRecipe(exporter, this.crackedChiseledBrick, this.chiseledBrick);

        // Small Brick
        RecipeProvider.offerPolishedStoneRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.smallBrick, this.brick);
        RecipeProvider.offerPolishedStoneRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrick, this.mossyBrick);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.smallBrickSlab, this.smallBrick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.smallBrickStair, this.smallBrick);

        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrick, this.smallBrick);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrickSlab, this.smallBrickSlab);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrickStair, this.smallBrickStair);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrickSlab, this.mossySmallBrick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.mossySmallBrickStair, this.mossySmallBrick);

        RecipeProvider.offerCrackingRecipe(exporter, this.crackedSmallBrick, this.smallBrick);

        // Tiles
        offerTileRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.tile, this.base, this.baseSlab);
        offerTileRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyTile, this.mossyBase, this.mossyBaseSlab);

        ZeldaSpringWaterConversionDatagen.offerConversion(exporter, this.tile, this.tileTTL);
        ZeldaSpringWaterConversionDatagen.offerConversion(exporter, this.tileTTL, this.tileTTR);
        ZeldaSpringWaterConversionDatagen.offerConversion(exporter, this.tileTTR, this.tileTBL);
        ZeldaSpringWaterConversionDatagen.offerConversion(exporter, this.tileTBL, this.tileTBR);
        ZeldaSpringWaterConversionDatagen.offerConversion(exporter, this.tileTBR, this.tile);

        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyTile, this.tile);
        RecipeProvider.offerCrackingRecipe(exporter, this.crackedTile, this.tile);

        // MISC
        RecipeProvider.offerChiseledBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.chiseled, this.baseSlab);
        RecipeProvider.offerChiseledBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.pillar, this.base);

        ZeldaRecipeDatagen.offerReinforcedRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.reinforced, this.base);
        RecipeProvider.offerSingleOutputShapelessRecipe(exporter, this.reinforced, this.pushable, "reinforced_block");
        RecipeProvider.offerSingleOutputShapelessRecipe(exporter, this.pushable, this.reinforced, "push_block");

        // Stonecutting
        slab(exporter, this.base, this.baseSlab);
        cut(exporter, this.base, this.baseStair);
        cut(exporter, this.base, this.brick);
        slab(exporter, this.base, this.brickSlab);
        cut(exporter, this.base, this.brickStair);
        cut(exporter, this.base, this.chiseledBrick);
        slab(exporter, this.base, this.chiseledBrickSlab);
        cut(exporter, this.base, this.chiseledBrickStair);
        cut(exporter, this.base, this.smallBrick);
        slab(exporter, this.base, this.smallBrickSlab);
        cut(exporter, this.base, this.smallBrickStair);
        cut(exporter, this.base, this.chiseled);
        cut(exporter, this.base, this.pillar);
        cut(exporter, this.base, this.reinforced);
        cut(exporter, this.base, this.pushable);
        cut(exporter, this.base, this.tile);
        cut(exporter, this.base, this.tileTBL);
        cut(exporter, this.base, this.tileTBR);
        cut(exporter, this.base, this.tileTTL);
        cut(exporter, this.base, this.tileTTR);

        slab(exporter, this.brick, this.brickSlab);
        cut(exporter, this.brick, this.brickStair);
        cut(exporter, this.brick, this.chiseledBrick);
        slab(exporter, this.brick, this.chiseledBrickSlab);
        cut(exporter, this.brick, this.chiseledBrickStair);
        cut(exporter, this.brick, this.smallBrick);
        slab(exporter, this.brick, this.smallBrickSlab);
        cut(exporter, this.brick, this.smallBrickStair);

        slab(exporter, this.chiseledBrick, this.chiseledBrickSlab);
        cut(exporter, this.chiseledBrick, this.chiseledBrickStair);

        slab(exporter, this.smallBrick, this.smallBrickSlab);
        cut(exporter, this.smallBrick, this.smallBrickStair);

        cut(exporter, this.tile, this.tileTBL);
        cut(exporter, this.tile, this.tileTBR);
        cut(exporter, this.tile, this.tileTTL);
        cut(exporter, this.tile, this.tileTTR);

        cut(exporter, this.pushable, this.reinforced);
        cut(exporter, this.reinforced, this.pushable);

        // Stonecutting Mossy
        slab(exporter, this.mossyBase, this.mossyBaseSlab);
        cut(exporter, this.mossyBase, this.mossyBaseStair);
        cut(exporter, this.mossyBase, this.mossyBrick);
        slab(exporter, this.mossyBase, this.mossyBrickSlab);
        cut(exporter, this.mossyBase, this.mossyBrickStair);
        cut(exporter, this.mossyBase, this.mossyChiseledBrick);
        slab(exporter, this.mossyBase, this.mossyChiseledBrickSlab);
        cut(exporter, this.mossyBase, this.mossyChiseledBrickStair);
        cut(exporter, this.mossyBase, this.mossySmallBrick);
        slab(exporter, this.mossyBase, this.mossySmallBrickSlab);
        cut(exporter, this.mossyBase, this.mossySmallBrickStair);
        cut(exporter, this.mossyBase, this.mossyTile);

        slab(exporter, this.mossyBrick, this.mossyBrickSlab);
        cut(exporter, this.mossyBrick, this.mossyBrickStair);
        cut(exporter, this.mossyBrick, this.mossyChiseledBrick);
        slab(exporter, this.mossyBrick, this.mossyChiseledBrickSlab);
        cut(exporter, this.mossyBrick, this.mossyChiseledBrickStair);
        cut(exporter, this.mossyBrick, this.mossySmallBrick);
        slab(exporter, this.mossyBrick, this.mossySmallBrickSlab);
        cut(exporter, this.mossyBrick, this.mossySmallBrickStair);

        slab(exporter, this.mossyChiseledBrick, this.mossyChiseledBrickSlab);
        cut(exporter, this.mossyChiseledBrick, this.mossyChiseledBrickStair);

        slab(exporter, this.mossySmallBrick, this.mossySmallBrickSlab);
        cut(exporter, this.mossySmallBrick, this.mossySmallBrickStair);
    }

    private static void cut(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, ItemConvertible output) {
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, output, input);
    }

    private static void slab(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, ItemConvertible output) {
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, output, input, 2);
    }

    public static void offerTileRecipe(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, ItemConvertible base, ItemConvertible slab) {
        ShapedRecipeJsonBuilder.create(category, output, 4)
                .input('#', base)
                .input('s', slab)
                .pattern("ss")
                .pattern("##")
                .criterion(RecipeProvider.hasItem(base), RecipeProvider.conditionsFromItem(base))
                .offerTo(exporter);
    }


    // Tags ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void generateBlockTags(BiConsumer<TagKey<Block>, Block> tagConsumer, TagKey<Block>... mineableTags) {
        super.generateBlockTags(tagConsumer, mineableTags);
        // SLABS
        tagConsumer.accept(BlockTags.SLABS, this.baseSlab);
        tagConsumer.accept(BlockTags.SLABS, this.mossyBaseSlab);
        tagConsumer.accept(BlockTags.SLABS, this.brickSlab);
        tagConsumer.accept(BlockTags.SLABS, this.mossyBrickSlab);
        tagConsumer.accept(BlockTags.SLABS, this.chiseledBrickSlab);
        tagConsumer.accept(BlockTags.SLABS, this.mossyChiseledBrickSlab);
        tagConsumer.accept(BlockTags.SLABS, this.smallBrickSlab);
        tagConsumer.accept(BlockTags.SLABS, this.mossySmallBrickSlab);

        // STAIRS
        tagConsumer.accept(BlockTags.STAIRS, this.baseStair);
        tagConsumer.accept(BlockTags.STAIRS, this.mossyBaseStair);
        tagConsumer.accept(BlockTags.STAIRS, this.brickStair);
        tagConsumer.accept(BlockTags.STAIRS, this.mossyBrickStair);
        tagConsumer.accept(BlockTags.STAIRS, this.chiseledBrickStair);
        tagConsumer.accept(BlockTags.STAIRS, this.mossyChiseledBrickStair);
        tagConsumer.accept(BlockTags.STAIRS, this.smallBrickStair);
        tagConsumer.accept(BlockTags.STAIRS, this.mossySmallBrickStair);

        // Cracked
        tagConsumer.accept(KoalaLibTags.CRACKED_BRICKS, this.crackedBase);
        tagConsumer.accept(KoalaLibTags.CRACKED_BRICKS, this.crackedBrick);
        tagConsumer.accept(KoalaLibTags.CRACKED_BRICKS, this.crackedChiseledBrick);
        tagConsumer.accept(KoalaLibTags.CRACKED_BRICKS, this.crackedSmallBrick);
        tagConsumer.accept(KoalaLibTags.CRACKED_BRICKS, this.crackedTile);
    }

    @Override
    public void generateItemTags(BiConsumer<TagKey<Item>, ItemConvertible> tagConsumer) {
        super.generateItemTags(tagConsumer);
        // SLABS
        tagConsumer.accept(ItemTags.SLABS, this.baseSlab);
        tagConsumer.accept(ItemTags.SLABS, this.mossyBaseSlab);
        tagConsumer.accept(ItemTags.SLABS, this.brickSlab);
        tagConsumer.accept(ItemTags.SLABS, this.mossyBrickSlab);
        tagConsumer.accept(ItemTags.SLABS, this.chiseledBrickSlab);
        tagConsumer.accept(ItemTags.SLABS, this.mossyChiseledBrickSlab);
        tagConsumer.accept(ItemTags.SLABS, this.smallBrickSlab);
        tagConsumer.accept(ItemTags.SLABS, this.mossySmallBrickSlab);

        // STAIRS
        tagConsumer.accept(ItemTags.STAIRS, this.baseStair);
        tagConsumer.accept(ItemTags.STAIRS, this.mossyBaseStair);
        tagConsumer.accept(ItemTags.STAIRS, this.brickStair);
        tagConsumer.accept(ItemTags.STAIRS, this.mossyBrickStair);
        tagConsumer.accept(ItemTags.STAIRS, this.chiseledBrickStair);
        tagConsumer.accept(ItemTags.STAIRS, this.mossyChiseledBrickStair);
        tagConsumer.accept(ItemTags.STAIRS, this.smallBrickStair);
        tagConsumer.accept(ItemTags.STAIRS, this.mossySmallBrickStair);
    }

    // MODELS //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable SharedModel sharedModel) {
        // Base
        registerRegularMossyCrackedGroup(modelGen,
                this.base, this.baseSlab, this.baseStair,
                this.mossyBase, this.mossyBaseSlab, this.mossyBaseStair,
                this.crackedBase
        );
        // Brick
        registerRegularMossyCrackedGroup(modelGen,
                this.brick, this.brickSlab, this.brickStair,
                this.mossyBrick, this.mossyBrickSlab, this.mossyBrickStair,
                this.crackedBrick
        );
        // Chiseled Brick
        registerRegularMossyCrackedGroup(modelGen,
                this.chiseledBrick, this.chiseledBrickSlab, this.chiseledBrickStair,
                this.mossyChiseledBrick, this.mossyChiseledBrickSlab, this.mossyChiseledBrickStair,
                this.crackedChiseledBrick
        );
        // Small Brick
        registerRegularMossyCrackedGroup(modelGen,
                this.smallBrick, this.smallBrickSlab, this.smallBrickStair,
                this.mossySmallBrick, this.mossySmallBrickSlab, this.mossySmallBrickStair,
                this.crackedSmallBrick
        );
        // Chiseled
        modelGen.registerSimpleCubeAll(this.chiseled);
        // Tile
        ZeldaBlockModelDatagenUtil.registerTile(modelGen, this.tile, this.tileTTL, this.tileTTR, this.tileTBL, this.tileTBR);
        ZeldaBlockModelDatagenUtil.registerTile(modelGen, this.mossyTile);
        ZeldaBlockModelDatagenUtil.registerTile(modelGen, this.crackedTile);
        // Reinforced
        modelGen.registerSimpleCubeAll(this.reinforced);
        modelGen.registerParentedItemModel(this.pushable, ModelIds.getBlockModelId(this.reinforced));
        // Pillar
        BlockModelDatagenUtil.registerPillar(modelGen, this.pillar);
//        ModelDatagenUtil.registerPedestal(modelGen, this.pedestal, this.tile, this.pillar);
    }

    public static void registerRegularMossyCrackedGroup(BlockStateModelGenerator modelGen, Block base, Block slab, Block stair, Block mossBase, Block mossSlab, Block mossStair, Block crackedBase) {
        registerBlockSlabStair(modelGen, base, slab, stair);
        registerBlockSlabStair(modelGen, mossBase, mossSlab, mossStair);
        modelGen.registerSimpleCubeAll(crackedBase);
    }

    public static void registerBlockSlabStair(BlockStateModelGenerator blockStateModelGenerator, Block base, Block slab, Block stair) {
        blockStateModelGenerator.registerSimpleCubeAll(base);
        BlockModelDatagenUtil.registerSlab(blockStateModelGenerator, slab, base);
        BlockModelDatagenUtil.registerStairs(blockStateModelGenerator, stair, base);
    }

    // OTHER ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Block register(String id, Block block) {
        return register(LegendOfSteve.MOD_ID, id, block);
    }

    public static class DungeonciteTile extends TileBlock implements IAdvancementNeeded {
        public static final BooleanProperty PLAYERMADE = KoalaLibBlockProperties.PLAYER_MADE_PROPERY;
        private final String advancementID;

        public DungeonciteTile(Settings settings, String advancementID) {
            super(settings);
            this.advancementID = advancementID;
            setDefaultState(this.getDefaultState().with(PLAYERMADE, false));
        }

        public BlockState getPlacementState(ItemPlacementContext ctx) {
            return super.getPlacementState(ctx).with(PLAYERMADE, this.isPlayerPlaced(ctx));
        }

        @Override
        public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
            return !this.hasAdvancment(player, this.advancementID) && !(Boolean)state.get(PLAYERMADE) ? -1 : super.calcBlockBreakingDelta(state, player, world, pos);
        }

        @Override
        protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
            super.appendProperties(builder);
            builder.add(PLAYERMADE);
        }
    }

    public static class DungeonciteTriforce extends DungeonciteTile {

        private final String baseKey;
        private final String piece;

        public DungeonciteTriforce(Settings settings, String advancementID, String baseKey, String piece) {
            super(settings, advancementID);
            this.baseKey = baseKey;
            this.piece = piece;
        }

        @Override
        public String getTranslationKey() {
            return "block.legend_of_steve." + this.baseKey;
        }

        @Override
        public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
            super.appendTooltip(stack, world, tooltip, options);
            tooltip.add(Text.translatable("block.legend_of_steve.dungeoncite_tile" + this.piece).formatted(Formatting.GRAY));
        }
    }
}
