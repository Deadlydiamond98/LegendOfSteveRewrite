package net.deadlydiamond.legend_of_steve.common.blocksets.dungeoncite;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.dungeoncite.ChiseledDungeoncitePillar;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.dungeoncite.DungeonciteTileBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.dungeoncite.DungeonciteWallBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.connected.temp.TriforceTileBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.DungeoncitePressurePlate;
import net.deadlydiamond.legend_of_steve.common.items.block.PushableBlockItem;
import net.deadlydiamond.legend_of_steve.datagen.recipe.ZeldaRecipeDatagen;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.util.ZeldaModels;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocks.advancement.*;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.init.KoalaLibTags;
import net.deadlydiamond98.koalalib.util.datagen.BlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.util.datagen.RecipeDatagenUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.client.*;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DungeonciteBlockset extends AbstractBlockset {

    // TODO:
    //  - Maybe add Button & Plate Variants?

    public final Block base;
    public final Block baseSlab;
    public final Block baseStair;
    public final Block baseWall;

    public final Block mossyBase;
    public final Block mossyBaseSlab;
    public final Block mossyBaseStair;
    public final Block mossyBaseWall;

    public final Block crackedBase;

    public final Block brick;
    public final Block brickSlab;
    public final Block brickStair;
    public final Block brickWall;

    public final Block mossyBrick;
    public final Block mossyBrickSlab;
    public final Block mossyBrickStair;
    public final Block mossyBrickWall;

    public final Block crackedBrick;

    public final Block chiseledBrick;
    public final Block chiseledBrickSlab;
    public final Block chiseledBrickStair;
    public final Block chiseledBrickWall;

    public final Block mossyChiseledBrick;
    public final Block mossyChiseledBrickSlab;
    public final Block mossyChiseledBrickStair;
    public final Block mossyChiseledBrickWall;

    public final Block crackedChiseledBrick;

    public final Block smallBrick;
    public final Block smallBrickSlab;
    public final Block smallBrickStair;
    public final Block smallBrickWall;

    public final Block mossySmallBrick;
    public final Block mossySmallBrickSlab;
    public final Block mossySmallBrickStair;
    public final Block mossySmallBrickWall;

    public final Block crackedSmallBrick;

    public final Block tile;
    public final Block mossyTile;
    public final Block crackedTile;
    public final Block triforce;

    public final Block reinforced;
    public final Item pushable;

    public final Block chiseledPillar;
    public final Block pillar;

    public final Block chiseled;

    public final BlockSetType blockSetType;
    public final Block pressurePlate;

    public DungeonciteBlockset(String color, String advancementID, MapColor mapColor) {
        super(LegendOfSteve.MOD_ID, color + "_dungeoncite");

        FabricBlockSettings settings = FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(ZeldaSounds.DUNGEONCITE).mapColor(mapColor);
        FabricBlockSettings settingsBrick = FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(ZeldaSounds.DUNGEONCITE_BRICKS).mapColor(mapColor);
        FabricBlockSettings settingsTile = FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(ZeldaSounds.DUNGEONCITE_TILE).mapColor(mapColor);

        this.blockSetType = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).soundGroup(ZeldaSounds.DUNGEONCITE_TILE)
                .build(new Identifier(LegendOfSteve.MOD_ID, id()));

        this.base = register(id(), new AdvancementNeededBlock(settings, advancementID));
        this.baseSlab = register(id() + "_slab", new AdvancementNeededSlab(settings, advancementID));
        this.baseStair = register(id() + "_stairs", new AdvancementNeededStairs(this.base.getDefaultState(), settings, advancementID));
        this.baseWall = register(id() + "_wall", new DungeonciteWallBlock(settings, advancementID));

        this.mossyBase = register("mossy_" + id(), new AdvancementNeededBlock(settings, advancementID));
        this.mossyBaseSlab = register("mossy_" + id() + "_slab", new AdvancementNeededSlab(settings, advancementID));
        this.mossyBaseStair = register("mossy_" + id() + "_stairs", new AdvancementNeededStairs(this.base.getDefaultState(), settings, advancementID));
        this.mossyBaseWall = register("mossy_" + id() + "_wall", new DungeonciteWallBlock(settings, advancementID));

        this.crackedBase = register("cracked_" + id(), new AdvancementNeededBlock(settings, advancementID));

        // Bricks

        this.brick = register(id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.brickSlab = register(id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.brickStair = register(id() + "_brick_stairs", new AdvancementNeededStairs(this.brick.getDefaultState(), settingsBrick, advancementID));
        this.brickWall = register(id() + "_brick_wall", new DungeonciteWallBlock(settingsBrick, advancementID));

        this.mossyBrick = register("mossy_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.mossyBrickSlab = register("mossy_" + id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.mossyBrickStair = register("mossy_" + id() + "_brick_stairs", new AdvancementNeededStairs(this.mossyBrick.getDefaultState(), settingsBrick, advancementID));
        this.mossyBrickWall = register("mossy_" + id() + "_brick_wall", new DungeonciteWallBlock(settingsBrick, advancementID));

        this.crackedBrick = register("cracked_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));

        // Chiseled Bricks

        this.chiseledBrick = register("chiseled_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.chiseledBrickSlab = register("chiseled_" + id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.chiseledBrickStair = register("chiseled_" + id() + "_brick_stairs", new AdvancementNeededStairs(this.chiseledBrick.getDefaultState(), settingsBrick, advancementID));
        this.chiseledBrickWall = register("chiseled_" + id() + "_brick_wall", new DungeonciteWallBlock(settingsBrick, advancementID));

        this.mossyChiseledBrick = register("mossy_chiseled_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.mossyChiseledBrickSlab = register("mossy_chiseled_" + id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.mossyChiseledBrickStair = register("mossy_chiseled_" + id() + "_brick_stairs", new AdvancementNeededStairs(this.mossyChiseledBrick.getDefaultState(), settingsBrick, advancementID));
        this.mossyChiseledBrickWall = register("mossy_chiseled_" + id() + "_brick_wall", new DungeonciteWallBlock(settingsBrick, advancementID));

        this.crackedChiseledBrick = register("cracked_chiseled_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));

        // Small Bricks

        this.smallBrick = register("small_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.smallBrickSlab = register("small_" + id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.smallBrickStair = register("small_" + id() + "_brick_stairs", new AdvancementNeededStairs(this.smallBrick.getDefaultState(), settingsBrick, advancementID));
        this.smallBrickWall = register("small_" + id() + "_brick_wall", new DungeonciteWallBlock(settingsBrick, advancementID));

        this.mossySmallBrick = register("small_mossy_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));
        this.mossySmallBrickSlab = register("small_mossy_" + id() + "_brick_slab", new AdvancementNeededSlab(settingsBrick, advancementID));
        this.mossySmallBrickStair = register("small_mossy_" + id() + "_brick_stairs", new AdvancementNeededStairs(this.smallBrick.getDefaultState(), settingsBrick, advancementID));
        this.mossySmallBrickWall = register("small_mossy_" + id() + "_brick_wall", new DungeonciteWallBlock(settingsBrick, advancementID));

        this.crackedSmallBrick = register("small_cracked_" + id() + "_bricks", new AdvancementNeededBlock(settingsBrick, advancementID));

        // CHISELED

        this.chiseled = register("chiseled_" + id(), new AdvancementNeededBlock(settings, advancementID));

        // Tiles

        this.tile = register(id() + "_tile", new DungeonciteTileBlock(settingsTile, advancementID));
        this.mossyTile = register("mossy_" + id() + "_tile", new DungeonciteTileBlock(settingsTile, advancementID));
        this.crackedTile = register("cracked_" + id() + "_tile", new DungeonciteTileBlock(settingsTile, advancementID));

        // There's probably a better way of doing this, but it works for now
        this.triforce = register(id() + "_triforce_tile", new TriforceTileBlock(settingsTile, advancementID));

        // REDSTONE
        this.pressurePlate =  register(id() + "_pressure_plate", new DungeoncitePressurePlate(FabricBlockSettings.copyOf(this.tile)
                .noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY), this.blockSetType, advancementID
        ));

        // Pillar
        this.pillar = register(id() + "_pillar", new AdvancementNeededPillarBlock(settings, advancementID));
        this.chiseledPillar = register("chiseled_" + id() + "_pillar", new ChiseledDungeoncitePillar(settingsTile, advancementID));

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
        RecipeProvider.offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.baseWall, this.base);

        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBase, this.base);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBaseSlab, this.baseSlab);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBaseStair, this.baseStair);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBaseWall, this.baseWall);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBaseSlab, this.mossyBase);
        RecipeDatagenUtil.createStairRecipe(exporter, this.mossyBaseStair, this.mossyBase);
        RecipeProvider.offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBaseWall, this.mossyBase);

        RecipeProvider.offerCrackingRecipe(exporter, this.crackedBase, this.base);

        // Brick
        RecipeProvider.offerPolishedStoneRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.brick, this.base);
        RecipeProvider.offerPolishedStoneRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrick, this.mossyBase);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.brickSlab, this.brick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.brickStair, this.brick);
        RecipeProvider.offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.brickWall, this.brick);

        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrick, this.brick);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrickSlab, this.brickSlab);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrickStair, this.brickStair);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrickWall, this.brickWall);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrickSlab, this.mossyBrick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.mossyBrickStair, this.mossyBrick);
        RecipeProvider.offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyBrickWall, this.mossyBrick);

        RecipeProvider.offerCrackingRecipe(exporter, this.crackedBrick, this.brick);

        // Chiseled Brick
        RecipeProvider.offerChiseledBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.chiseledBrick, this.brickSlab);
        RecipeProvider.offerChiseledBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrick, this.mossyBrickSlab);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.chiseledBrickSlab, this.chiseledBrick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.chiseledBrickStair, this.chiseledBrick);
        RecipeProvider.offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.chiseledBrickWall, this.chiseledBrick);

        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrick, this.chiseledBrick);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrickSlab, this.chiseledBrickSlab);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrickStair, this.chiseledBrickStair);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrickWall, this.chiseledBrickWall);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrickSlab, this.mossyChiseledBrick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.mossyChiseledBrickStair, this.mossyChiseledBrick);
        RecipeProvider.offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyChiseledBrickWall, this.mossyChiseledBrick);

        RecipeProvider.offerCrackingRecipe(exporter, this.crackedChiseledBrick, this.chiseledBrick);

        // Small Brick
        RecipeProvider.offerPolishedStoneRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.smallBrick, this.brick);
        RecipeProvider.offerPolishedStoneRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrick, this.mossyBrick);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.smallBrickSlab, this.smallBrick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.smallBrickStair, this.smallBrick);
        RecipeProvider.offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.smallBrickWall, this.smallBrick);

        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrick, this.smallBrick);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrickSlab, this.smallBrickSlab);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrickStair, this.smallBrickStair);
        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrickWall, this.smallBrickWall);

        RecipeProvider.offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrickSlab, this.mossySmallBrick);
        RecipeDatagenUtil.createStairRecipe(exporter, this.mossySmallBrickStair, this.mossySmallBrick);
        RecipeProvider.offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossySmallBrickWall, this.mossySmallBrick);

        RecipeProvider.offerCrackingRecipe(exporter, this.crackedSmallBrick, this.smallBrick);

        // Tiles
        offerTileRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.tile, this.base, this.baseSlab);
        offerTileRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyTile, this.mossyBase, this.mossyBaseSlab);
        offerTriforceTileRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.triforce, this.tile);

        RecipeDatagenUtil.createMossyRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.mossyTile, this.tile);
        RecipeProvider.offerCrackingRecipe(exporter, this.crackedTile, this.tile);

        // MISC
        RecipeProvider.offerChiseledBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.chiseled, this.baseSlab);
        RecipeProvider.offerChiseledBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.pillar, this.base);
        RecipeProvider.offerChiseledBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.chiseledPillar, this.pillar);
        RecipeProvider.offerPressurePlateRecipe(exporter, this.pressurePlate, this.tile);

        ZeldaRecipeDatagen.offerReinforcedRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.reinforced, this.base);
        RecipeProvider.offerSingleOutputShapelessRecipe(exporter, this.reinforced, this.pushable, "reinforced_block");
        RecipeProvider.offerSingleOutputShapelessRecipe(exporter, this.pushable, this.reinforced, "push_block");

        // Stonecutting
        slab(exporter, this.base, this.baseSlab);
        cut(exporter, this.base, this.baseStair);
        cut(exporter, this.base, this.baseWall);
        cut(exporter, this.base, this.brick);
        slab(exporter, this.base, this.brickSlab);
        cut(exporter, this.base, this.brickStair);
        cut(exporter, this.base, this.brickWall);
        cut(exporter, this.base, this.chiseledBrick);
        slab(exporter, this.base, this.chiseledBrickSlab);
        cut(exporter, this.base, this.chiseledBrickStair);
        cut(exporter, this.base, this.chiseledBrickWall);
        cut(exporter, this.base, this.smallBrick);
        slab(exporter, this.base, this.smallBrickSlab);
        cut(exporter, this.base, this.smallBrickStair);
        cut(exporter, this.base, this.smallBrickWall);
        cut(exporter, this.base, this.chiseled);
        cut(exporter, this.base, this.pillar);
        cut(exporter, this.base, this.chiseledPillar);
        cut(exporter, this.base, this.reinforced);
        cut(exporter, this.base, this.pushable);
        cut(exporter, this.base, this.tile);
        cut(exporter, this.base, this.triforce);
        cut(exporter, this.base, this.pressurePlate);

        slab(exporter, this.brick, this.brickSlab);
        cut(exporter, this.brick, this.brickStair);
        cut(exporter, this.brick, this.brickWall);
        cut(exporter, this.brick, this.chiseledBrick);
        slab(exporter, this.brick, this.chiseledBrickSlab);
        cut(exporter, this.brick, this.chiseledBrickStair);
        cut(exporter, this.brick, this.chiseledBrickWall);
        cut(exporter, this.brick, this.smallBrick);
        slab(exporter, this.brick, this.smallBrickSlab);
        cut(exporter, this.brick, this.smallBrickStair);
        cut(exporter, this.brick, this.smallBrickWall);

        slab(exporter, this.chiseledBrick, this.chiseledBrickSlab);
        cut(exporter, this.chiseledBrick, this.chiseledBrickStair);
        cut(exporter, this.chiseledBrick, this.chiseledBrickWall);

        slab(exporter, this.smallBrick, this.smallBrickSlab);
        cut(exporter, this.smallBrick, this.smallBrickStair);
        cut(exporter, this.smallBrick, this.smallBrickWall);

        cut(exporter, this.tile, this.triforce);
        cut(exporter, this.tile, this.pressurePlate);

        cut(exporter, this.pushable, this.reinforced);
        cut(exporter, this.reinforced, this.pushable);

        // Stonecutting Mossy
        slab(exporter, this.mossyBase, this.mossyBaseSlab);
        cut(exporter, this.mossyBase, this.mossyBaseStair);
        cut(exporter, this.mossyBase, this.mossyBaseWall);
        cut(exporter, this.mossyBase, this.mossyBrick);
        slab(exporter, this.mossyBase, this.mossyBrickSlab);
        cut(exporter, this.mossyBase, this.mossyBrickStair);
        cut(exporter, this.mossyBase, this.mossyBrickWall);
        cut(exporter, this.mossyBase, this.mossyChiseledBrick);
        slab(exporter, this.mossyBase, this.mossyChiseledBrickSlab);
        cut(exporter, this.mossyBase, this.mossyChiseledBrickStair);
        cut(exporter, this.mossyBase, this.mossyChiseledBrickWall);
        cut(exporter, this.mossyBase, this.mossySmallBrick);
        slab(exporter, this.mossyBase, this.mossySmallBrickSlab);
        cut(exporter, this.mossyBase, this.mossySmallBrickStair);
        cut(exporter, this.mossyBase, this.mossySmallBrickWall);
        cut(exporter, this.mossyBase, this.mossyTile);

        slab(exporter, this.mossyBrick, this.mossyBrickSlab);
        cut(exporter, this.mossyBrick, this.mossyBrickStair);
        cut(exporter, this.mossyBrick, this.mossyBrickWall);
        cut(exporter, this.mossyBrick, this.mossyChiseledBrick);
        slab(exporter, this.mossyBrick, this.mossyChiseledBrickSlab);
        cut(exporter, this.mossyBrick, this.mossyChiseledBrickStair);
        cut(exporter, this.mossyBrick, this.mossyChiseledBrickWall);
        cut(exporter, this.mossyBrick, this.mossySmallBrick);
        slab(exporter, this.mossyBrick, this.mossySmallBrickSlab);
        cut(exporter, this.mossyBrick, this.mossySmallBrickStair);
        cut(exporter, this.mossyBrick, this.mossySmallBrickWall);

        slab(exporter, this.mossyChiseledBrick, this.mossyChiseledBrickSlab);
        cut(exporter, this.mossyChiseledBrick, this.mossyChiseledBrickStair);
        cut(exporter, this.mossyChiseledBrick, this.mossyChiseledBrickWall);

        slab(exporter, this.mossySmallBrick, this.mossySmallBrickSlab);
        cut(exporter, this.mossySmallBrick, this.mossySmallBrickStair);
        cut(exporter, this.mossySmallBrick, this.mossySmallBrickWall);
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

    public static void offerTriforceTileRecipe(Consumer<RecipeJsonProvider> exporter, RecipeCategory category, ItemConvertible output, ItemConvertible tile) {
        ShapedRecipeJsonBuilder.create(category, output, 4)
                .input('#', tile)
                .pattern(" # ")
                .pattern("###")
                .criterion(RecipeProvider.hasItem(tile), RecipeProvider.conditionsFromItem(tile))
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

        // WALL
        tagConsumer.accept(BlockTags.WALLS, this.baseWall);
        tagConsumer.accept(BlockTags.WALLS, this.mossyBaseWall);
        tagConsumer.accept(BlockTags.WALLS, this.brickWall);
        tagConsumer.accept(BlockTags.WALLS, this.mossyBrickWall);
        tagConsumer.accept(BlockTags.WALLS, this.chiseledBrickWall);
        tagConsumer.accept(BlockTags.WALLS, this.mossyChiseledBrickWall);
        tagConsumer.accept(BlockTags.WALLS, this.smallBrickWall);
        tagConsumer.accept(BlockTags.WALLS, this.mossySmallBrickWall);

        // OTHER
        tagConsumer.accept(BlockTags.STONE_PRESSURE_PLATES, this.pressurePlate);
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

        // WALLS
        tagConsumer.accept(ItemTags.WALLS, this.baseWall);
        tagConsumer.accept(ItemTags.WALLS, this.mossyBaseWall);
        tagConsumer.accept(ItemTags.WALLS, this.brickWall);
        tagConsumer.accept(ItemTags.WALLS, this.mossyBrickWall);
        tagConsumer.accept(ItemTags.WALLS, this.chiseledBrickWall);
        tagConsumer.accept(ItemTags.WALLS, this.mossyChiseledBrickWall);
        tagConsumer.accept(ItemTags.WALLS, this.smallBrickWall);
        tagConsumer.accept(ItemTags.WALLS, this.mossySmallBrickWall);
    }

    // MODELS //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable SharedModel sharedModel) {
        // Base
        registerRegularMossyCrackedGroup(modelGen,
                this.base, this.baseSlab, this.baseStair, this.baseWall,
                this.mossyBase, this.mossyBaseSlab, this.mossyBaseStair, this.mossyBaseWall,
                this.crackedBase
        );
        // Brick
        registerRegularMossyCrackedGroup(modelGen,
                this.brick, this.brickSlab, this.brickStair, this.brickWall,
                this.mossyBrick, this.mossyBrickSlab, this.mossyBrickStair, this.mossyBrickWall,
                this.crackedBrick
        );
        // Chiseled Brick
        registerRegularMossyCrackedGroup(modelGen,
                this.chiseledBrick, this.chiseledBrickSlab, this.chiseledBrickStair, this.chiseledBrickWall,
                this.mossyChiseledBrick, this.mossyChiseledBrickSlab, this.mossyChiseledBrickStair, this.mossyChiseledBrickWall,
                this.crackedChiseledBrick
        );
        // Small Brick
        registerRegularMossyCrackedGroup(modelGen,
                this.smallBrick, this.smallBrickSlab, this.smallBrickStair, this.smallBrickWall,
                this.mossySmallBrick, this.mossySmallBrickSlab, this.mossySmallBrickStair, this.mossySmallBrickWall,
                this.crackedSmallBrick
        );
        // Chiseled
        modelGen.registerSimpleCubeAll(this.chiseled);
        // Tile
        ZeldaBlockModelDatagenUtil.registerTile(modelGen, this.tile);
        ZeldaBlockModelDatagenUtil.registerTile(modelGen, this.mossyTile);
        ZeldaBlockModelDatagenUtil.registerTile(modelGen, this.crackedTile);
        ZeldaBlockModelDatagenUtil.registerTriforceTile(modelGen, this.tile, this.triforce);
        // Reinforced
        modelGen.registerSimpleCubeAll(this.reinforced);
        modelGen.registerParentedItemModel(this.pushable, ModelIds.getBlockModelId(this.reinforced));
        // Pillar
        BlockModelDatagenUtil.registerPillar(modelGen, this.pillar);
        ZeldaBlockModelDatagenUtil.registerConnectedPillar(modelGen, this.chiseledPillar);

        dungeoncitePlate(modelGen, this.pressurePlate, this.tile);
    }

    public static void dungeoncitePlate(BlockStateModelGenerator modelGen, Block plate, Block parent) {
        TexturedModel texturedModel = TexturedModel.CUBE_BOTTOM_TOP.get(parent);
        TextureMap textureMap = texturedModel.getTextures().put(TextureKey.TOP, TextureMap.getId(parent));
        Identifier reg = ZeldaModels.DUNGEONCITE_PRESSURE_PLATE.upload(plate, textureMap, modelGen.modelCollector);
        Identifier pressed = ZeldaModels.DUNGEONCITE_PRESSURE_PLATE_DOWN.upload(plate, textureMap, modelGen.modelCollector);
        modelGen.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(plate)
                        .coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates())
                        .coordinate(BlockStateModelGenerator.createBooleanModelMap(Properties.POWERED, pressed, reg))
        );
    }

    public static void registerRegularMossyCrackedGroup(BlockStateModelGenerator modelGen, Block base, Block slab, Block stair, Block wall,
                                                        Block mossBase, Block mossSlab, Block mossStair, Block mossWall, Block crackedBase) {
        registerBlockSlabStair(modelGen, base, slab, stair, wall);
        registerBlockSlabStair(modelGen, mossBase, mossSlab, mossStair, mossWall);
        modelGen.registerSimpleCubeAll(crackedBase);
    }

    public static void registerBlockSlabStair(BlockStateModelGenerator blockStateModelGenerator, Block base, Block slab, Block stair, Block wall) {
        blockStateModelGenerator.registerSimpleCubeAll(base);
        BlockModelDatagenUtil.registerSlab(blockStateModelGenerator, slab, base);
        BlockModelDatagenUtil.registerStairs(blockStateModelGenerator, stair, base);
        BlockModelDatagenUtil.registerWall(blockStateModelGenerator, wall, base);
    }

    private Block register(String id, Block block) {
        return register(LegendOfSteve.MOD_ID, id, block);
    }
}
