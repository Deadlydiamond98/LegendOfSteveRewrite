package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaFluids;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ZeldaBlockRenderLayers {
    public static void register() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ZeldaBlocks.BOMB_FLOWER,
                ZeldaBlocks.DEKU_LEAVES
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                ZeldaBlocks.DEKU_WOOD.door,
                ZeldaBlocks.DEKU_WOOD.trapdoor
        );

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ZeldaFluids.ENCHANTED_SPRING_WATER,
                ZeldaFluids.FLOWING_ENCHANTED_SPRING_WATER
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(ZeldaRenderLayers.IRIDESCENCE,
                ZeldaBlocks.FAIRY_MARBLE.base,
                ZeldaBlocks.FAIRY_MARBLE.slab,
                ZeldaBlocks.FAIRY_MARBLE.stair,
                ZeldaBlocks.FAIRY_MARBLE.wall,
                ZeldaBlocks.FAIRY_MARBLE_BUTTON,
                ZeldaBlocks.FAIRY_MARBLE_PRESSURE_PLATE,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.base,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.slab,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.stair,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.wall,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.base,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.slab,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.stair,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.wall,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.base,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.slab,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.stair,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.wall,
                ZeldaBlocks.FAIRY_MARBLE_TILES.base,
                ZeldaBlocks.FAIRY_MARBLE_TILES.slab,
                ZeldaBlocks.FAIRY_MARBLE_TILES.stair,
                ZeldaBlocks.SMOOTH_FAIRY_MARBLE,
                ZeldaBlocks.CHISELED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_PILLAR,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.base,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.slab,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.stair,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.wall,
                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS
        );
    }
}
