package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaFluids;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class ZeldaBlockRenderLayers {

    public static void register() {

        // CUTOUT //////////////////////////////////////////////////////////////////////////////////////////////////////

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ZeldaBlocks.BOMB_FLOWER,
                ZeldaBlocks.LOOT_GRASS,
                ZeldaBlocks.DEKU_LEAVES,
                ZeldaBlocks.FRUITING_DEKU_LEAVES,
                ZeldaBlocks.DEKU_SAPLING,
                ZeldaBlocks.POTTED_DEKU_SAPLING,
                ZeldaBlocks.MASTER_DOOR,
                ZeldaBlocks.MASTER_TRAPDOOR,
                ZeldaBlocks.MASTER_BARS,
                ZeldaBlocks.MASTER_CHAIN,
                ZeldaBlocks.MASTER_GIRDER,
                ZeldaBlocks.CRYSTAL_SWITCH,
                ZeldaBlocks.DUNGEON_TABLE,
                ZeldaBlocks.SILENT_PRINCESS,
                ZeldaBlocks.POTTED_SILENT_PRINCESS,
                ZeldaBlocks.SILENT_PRINCESS_CROP
        );

        registerBlockset(RenderLayer.getCutout(),
                ZeldaBlocks.STONE_BRAZIER_BLOCKSET,
                ZeldaBlocks.DEEPSLATE_BRAZIER_BLOCKSET,
                ZeldaBlocks.BLACKSTONE_BRAZIER_BLOCKSET,
                ZeldaBlocks.QUARTZ_BRAZIER_BLOCKSET,
                ZeldaBlocks.STRANGE_DIRT_BRAZIER_BLOCKSET,
                ZeldaBlocks.STRANGE_BLUE_DIRT_BRAZIER_BLOCKSET
        );

        // SWITCH BLOCKS ///////////////////////////////////////////////////////////////////////////////////////////////

        registerBlockset(ZeldaRenderLayers.getSwitchBlock(),
                ZeldaBlocks.RED_SWITCH_BLOCKS,
                ZeldaBlocks.BLUE_SWITCH_BLOCKS
        );

        // TRANSLUCENT /////////////////////////////////////////////////////////////////////////////////////////////////

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                ZeldaBlocks.DEKU_WOOD.door,
                ZeldaBlocks.DEKU_WOOD.trapdoor,
                ZeldaBlocks.INVISIBLE_QUESTION_BLOCK
        );

        // BLOOM ///////////////////////////////////////////////////////////////////////////////////////////////////////

        BlockRenderLayerMap.INSTANCE.putBlocks(ZeldaRenderLayers.getBloomGlow(),
                ZeldaBlocks.PINK_FAIRY_LAMP,
                ZeldaBlocks.RED_FAIRY_LAMP,
                ZeldaBlocks.ORANGE_FAIRY_LAMP,
                ZeldaBlocks.YELLOW_FAIRY_LAMP,
                ZeldaBlocks.GREEN_FAIRY_LAMP,
                ZeldaBlocks.BLUE_FAIRY_LAMP,
                ZeldaBlocks.PURPLE_FAIRY_LAMP
        );

        // IRIDESCENT //////////////////////////////////////////////////////////////////////////////////////////////////

        registerBlockset(ZeldaRenderLayers.getIridescence(),
                ZeldaBlocks.FAIRY_MARBLE,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.FAIRY_MARBLE_TILES,
                ZeldaBlocks.FAIRY_MARBLE_BRAZIER_BLOCKSET,
                ZeldaBlocks.PERLITE_BRICKS
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(ZeldaRenderLayers.getIridescence(),
                ZeldaBlocks.PERLITE,
                ZeldaBlocks.PERLITE_PILLAR,
                ZeldaBlocks.CHISELED_PERLITE,
                ZeldaBlocks.SMOOTH_FAIRY_MARBLE,
                ZeldaBlocks.CHISELED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_PILLAR,
                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.FAIRY_MARBLE_SWORD_PEDESTAL
        );

        // FLUIDS //////////////////////////////////////////////////////////////////////////////////////////////////////

        BlockRenderLayerMap.INSTANCE.putFluids(ZeldaRenderLayers.getBloomGlow(),
                ZeldaFluids.ENCHANTED_SPRING_WATER,
                ZeldaFluids.FLOWING_ENCHANTED_SPRING_WATER
        );

        FluidRenderHandlerRegistry.INSTANCE.register(ZeldaFluids.ENCHANTED_SPRING_WATER, ZeldaFluids.FLOWING_ENCHANTED_SPRING_WATER,
                new SimpleFluidRenderHandler(
                        new Identifier("block/water_still"),
                        new Identifier("block/water_flow"),
                        0x5db7ef
                )
        );
    }

    public static void registerBlockset(RenderLayer renderLayer, AbstractBlockset... blocksets) {
        for (AbstractBlockset blockset : blocksets) {
            BlockRenderLayerMap.INSTANCE.putBlocks(renderLayer, blockset.getAll());
        }
    }
}
