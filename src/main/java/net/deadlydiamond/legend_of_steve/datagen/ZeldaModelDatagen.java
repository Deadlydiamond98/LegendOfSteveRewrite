package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
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
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        ZeldaBlocks.RED_TEKTILES.generateModels(blockStateModelGenerator);
        ZeldaBlocks.SMALL_RED_TEKTILES.generateModels(blockStateModelGenerator);
        ZeldaBlocks.BLUE_TEKTILES.generateModels(blockStateModelGenerator);
        ZeldaBlocks.SMALL_BLUE_TEKTILES.generateModels(blockStateModelGenerator);
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
    }
}
