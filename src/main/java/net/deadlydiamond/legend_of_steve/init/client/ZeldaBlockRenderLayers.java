package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaFluids;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

import java.util.ArrayList;
import java.util.List;

public class ZeldaBlockRenderLayers {
    public static final List<Block> IRIDESCENT_BLOCKS = new ArrayList<>();

    public static void register() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ZeldaBlocks.BOMB_FLOWER,
                ZeldaBlocks.DEKU_LEAVES,
                ZeldaBlocks.FRUITING_DEKU_LEAVES,
                ZeldaBlocks.DEKU_SAPLING,
                ZeldaBlocks.POTTED_DEKU_SAPLING,
                ZeldaBlocks.MASTER_DOOR,
                ZeldaBlocks.MASTER_TRAPDOOR,
                ZeldaBlocks.MASTER_BARS,
                ZeldaBlocks.MASTER_CHAIN,
                ZeldaBlocks.MASTER_GIRDER
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                ZeldaBlocks.DEKU_WOOD.door,
                ZeldaBlocks.DEKU_WOOD.trapdoor
        );

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ZeldaFluids.ENCHANTED_SPRING_WATER,
                ZeldaFluids.FLOWING_ENCHANTED_SPRING_WATER
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(ZeldaRenderLayers.BLOOM_GLOW,
                ZeldaBlocks.PINK_FAIRY_LAMP,
                ZeldaBlocks.RED_FAIRY_LAMP,
                ZeldaBlocks.ORANGE_FAIRY_LAMP,
                ZeldaBlocks.YELLOW_FAIRY_LAMP,
                ZeldaBlocks.GREEN_FAIRY_LAMP,
                ZeldaBlocks.BLUE_FAIRY_LAMP,
                ZeldaBlocks.PURPLE_FAIRY_LAMP
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(ZeldaRenderLayers.IRIDESCENCE,
                ZeldaBlocks.FAIRY_MARBLE.base,
                ZeldaBlocks.FAIRY_MARBLE.slab,
                ZeldaBlocks.FAIRY_MARBLE.stair,
                ZeldaBlocks.FAIRY_MARBLE.wall,
                ZeldaBlocks.FAIRY_MARBLE.button,
                ZeldaBlocks.FAIRY_MARBLE.plate,
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
                ZeldaBlocks.FAIRY_MARBLE_TILES.wall,
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

    private static void registerIridescentSet(AbstractBlockset... blocksets) {
        for (AbstractBlockset blockset : blocksets) {
            registerIridescent(blockset.getAll());
        }
    }

    private static void registerIridescent(Block... blocks) {
        BlockRenderLayerMap.INSTANCE.putBlocks(ZeldaRenderLayers.IRIDESCENCE, blocks);
    }
}
