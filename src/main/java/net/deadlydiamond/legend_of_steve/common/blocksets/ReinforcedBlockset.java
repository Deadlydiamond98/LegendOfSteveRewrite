package net.deadlydiamond.legend_of_steve.common.blocksets;

import net.deadlydiamond.legend_of_steve.common.items.block.PushableBlockItem;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ReinforcedBlockset extends AbstractBlockset {

    public final Block base;
    public final Item pushable;

    public ReinforcedBlockset(String modID, String id, AbstractBlock.Settings settings) {
        super(modID, id);
        this.base = this.register(modID, this.id(), new Block(settings));
        this.pushable = this.registerPushable(modID, "pushable_" + this.id(), this.base);
    }

    @Override
    public void generateModels(BlockStateModelGenerator modelGen, @Nullable AbstractBlockset.SharedModel sharedModel) {
        super.generateModels(modelGen, sharedModel);
        modelGen.registerSimpleCubeAll(this.base);
        modelGen.registerParentedItemModel(this.pushable, ModelIds.getBlockModelId(this.base));
    }

    @Override
    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        super.generateRecipes(exporter);
        RecipeProvider.offerSingleOutputShapelessRecipe(exporter, this.base, this.pushable, "reinforced_block");
        RecipeProvider.offerSingleOutputShapelessRecipe(exporter, this.pushable, this.base, "push_block");
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.base, this.pushable);
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
        RecipeProvider.offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, this.pushable, block);
    }

    @Override
    protected void addAdditionalToCreative(ItemGroup.Entries entry) {
        super.addAdditionalToCreative(entry);
        entry.add(this.pushable);
    }

    private Item registerPushable(String modID, String name, Block parent) {
        return Registry.register(Registries.ITEM, new Identifier(modID, name),
                new PushableBlockItem(new FabricItemSettings(), parent.getDefaultState())
        );
    }
}
