package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.util.datagen.IridescentBlockModelDatagenUtil;
import net.deadlydiamond.legend_of_steve.util.datagen.ZeldaItemModelDatagenUtil;
import net.deadlydiamond98.koalalib.util.datagen.ItemModelDatagenUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class ZeldaModelDatagen extends FabricModelProvider {

    public ZeldaModelDatagen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {

        // FAIRY MARBLE ////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.FAIRY_MARBLE.generateModels(generator);
        ZeldaBlocks.COBBLED_FAIRY_MARBLE.generateModels(generator);
        ZeldaBlocks.POLISHED_FAIRY_MARBLE.generateModels(generator);
        ZeldaBlocks.FAIRY_MARBLE_BRICKS.generateModels(generator);
        ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.generateModels(generator);
        ZeldaBlocks.FAIRY_MARBLE_TILES.generateModels(generator);

        IridescentBlockModelDatagenUtil.registerIridescentPillar(generator, ZeldaBlocks.FAIRY_MARBLE_PILLAR);
        IridescentBlockModelDatagenUtil.registerIridescentBlock(generator, ZeldaBlocks.SMOOTH_FAIRY_MARBLE);
        IridescentBlockModelDatagenUtil.registerIridescentBlock(generator, ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS);

        // TEKTILES ////////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.RED_TEKTILES.generateModels(generator);
        ZeldaBlocks.SMALL_RED_TEKTILES.generateModels(generator);
        ZeldaBlocks.BLUE_TEKTILES.generateModels(generator);
        ZeldaBlocks.SMALL_BLUE_TEKTILES.generateModels(generator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        ItemModelDatagenUtil.registerGenerated(
                itemModelGenerator,
                ZeldaItems.BOMB_FLOWER_SEEDS,
                ZeldaItems.BOMB,
                ZeldaItems.SUPER_BOMB,
                ZeldaItems.BOMB_FLOWER,
                ZeldaItems.BLUE_TEKTITE_CHITIN,
                ZeldaItems.BLUE_TEKTITE_SHELL,
                ZeldaItems.RED_TEKTITE_CHITIN,
                ZeldaItems.RED_TEKTITE_SHELL
        );

        ZeldaItemModelDatagenUtil.registerQuiver(itemModelGenerator, ZeldaItems.QUIVER);
        ZeldaItemModelDatagenUtil.registerQuiver(itemModelGenerator, ZeldaItems.GILDED_QUIVER);
        ZeldaItemModelDatagenUtil.registerQuiver(itemModelGenerator, ZeldaItems.NETHERITE_QUIVER);
    }
}
