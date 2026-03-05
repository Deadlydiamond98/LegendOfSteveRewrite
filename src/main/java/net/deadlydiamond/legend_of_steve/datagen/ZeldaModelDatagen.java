package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.util.datagen.model.IridescentBlockModelDatagenUtil;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaItemModelDatagenUtil;
import net.deadlydiamond98.koalalib.util.datagen.BlockModelDatagenUtil;
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

        // FAIRY LAMP //////////////////////////////////////////////////////////////////////////////////////////////////
        generator.registerSimpleCubeAll(ZeldaBlocks.PINK_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.RED_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.ORANGE_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.YELLOW_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.GREEN_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.BLUE_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.PURPLE_FAIRY_LAMP);

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

        // MASTER //////////////////////////////////////////////////////////////////////////////////////////////////////
        generator.registerSimpleCubeAll(ZeldaBlocks.MASTER_BLOCK);
        generator.registerSimpleCubeAll(ZeldaBlocks.MASTER_SCRAP_BLOCK);
        generator.registerSimpleCubeAll(ZeldaBlocks.MASTER_ORE);
        generator.registerSimpleCubeAll(ZeldaBlocks.DEEPSLATE_MASTER_ORE);
        ZeldaBlocks.MASTER_PLATE.generateModels(generator, true);
        ZeldaBlocks.MASTER_BRICK.generateModels(generator);
        ZeldaBlocks.MASTER_TILE.generateModels(generator);
        generator.registerSimpleCubeAll(ZeldaBlocks.CUT_MASTER_PLATE);
        BlockModelDatagenUtil.registerPillar(generator, ZeldaBlocks.MASTER_PILLAR);
        generator.registerDoor(ZeldaBlocks.MASTER_DOOR);
        generator.registerOrientableTrapdoor(ZeldaBlocks.MASTER_TRAPDOOR);

        // TEKTILES ////////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.RED_TEKTILES.generateModels(generator);
        ZeldaBlocks.SMALL_RED_TEKTILES.generateModels(generator);
        ZeldaBlocks.BLUE_TEKTILES.generateModels(generator);
        ZeldaBlocks.SMALL_BLUE_TEKTILES.generateModels(generator);

        // WOOD ////////////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.DEKU_WOOD.generateModels(generator);
        generator.registerFlowerPotPlant(ZeldaBlocks.DEKU_SAPLING, ZeldaBlocks.POTTED_DEKU_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
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
                ZeldaItems.RED_TEKTITE_SHELL,
                ZeldaItems.RAW_MASTER_ORE,
                ZeldaItems.MASTER_SCRAP,
                ZeldaItems.MASTER_INGOT
        );

        ZeldaItemModelDatagenUtil.registerQuiver(itemModelGenerator, ZeldaItems.QUIVER);
        ZeldaItemModelDatagenUtil.registerQuiver(itemModelGenerator, ZeldaItems.GILDED_QUIVER);
        ZeldaItemModelDatagenUtil.registerQuiver(itemModelGenerator, ZeldaItems.NETHERITE_QUIVER);
    }
}
