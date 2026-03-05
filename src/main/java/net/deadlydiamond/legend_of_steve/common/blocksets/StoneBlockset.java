package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.common.blocksets.BaseStairSlabWallBlockset;
import net.deadlydiamond98.koalalib.util.datagen.RecipeDatagenUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class StoneBlockset extends BaseStairSlabWallBlockset {

    public final BlockSetType blockSetType;

    public final Block button;
    public final Block plate;

    public StoneBlockset(String modID, String id, AbstractBlock.Settings settings) {
        this(modID, id, settings, BlockSetType.STONE);
    }

    public StoneBlockset(String modID, String id, AbstractBlock.Settings settings, BlockSetType blockSetTypeReference) {
        this(modID, id, settings, blockSetTypeReference, true);
    }

    public StoneBlockset(String modID, String id, AbstractBlock.Settings settings, BlockSetType blockSetTypeReference, boolean stripEndS) {
        super(modID, id, settings, stripEndS);

        this.blockSetType = BlockSetTypeBuilder.copyOf(blockSetTypeReference).build(new Identifier(modID, id));

        this.button = this.register(modID, this.id() + "_button", new ButtonBlock(
                FabricBlockSettings.copyOf(this.base).noCollision().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY),
                this.blockSetType, 20, false
        ));

        this.plate = this.register(modID, this.id() + "_pressure_plate", new PressurePlateBlock(
                PressurePlateBlock.ActivationRule.MOBS,
                FabricBlockSettings.copyOf(this.base).noCollision().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY),
                this.blockSetType
        ));
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, boolean uniqueSlab) {
        super.generateModels(modelGen, uniqueSlab);
        ZeldaBlockModelDatagenUtil.registerButton(modelGen, this.button, this.base);
        ZeldaBlockModelDatagenUtil.registerPressurePlate(modelGen, this.plate, this.base);
    }

    @Override
    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        super.generateRecipes(exporter);
        RecipeProvider.offerPressurePlateRecipe(exporter, this.plate, this.base);
        RecipeProvider.offerSingleOutputShapelessRecipe(exporter, this.button, this.base, RecipeCategory.REDSTONE.getName());
    }

    @Override
    protected void stoneCutterRecipes(Consumer<RecipeJsonProvider> exporter, Block block) {
        super.stoneCutterRecipes(exporter, block);
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, this.button, block);
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.REDSTONE, this.plate, block);
    }
}
