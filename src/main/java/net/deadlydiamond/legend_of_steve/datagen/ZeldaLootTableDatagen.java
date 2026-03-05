package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;

public class ZeldaLootTableDatagen extends FabricBlockLootTableProvider {

    public ZeldaLootTableDatagen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addSimpleBlockDrops(
                // DEKU WOOD
                ZeldaBlocks.DEKU_WOOD.log,
                ZeldaBlocks.DEKU_WOOD.wood,
                ZeldaBlocks.DEKU_WOOD.strippedLog,
                ZeldaBlocks.DEKU_WOOD.strippedWood,
                ZeldaBlocks.DEKU_WOOD.plank,
                ZeldaBlocks.DEKU_WOOD.stair,
                ZeldaBlocks.DEKU_WOOD.fence,
                ZeldaBlocks.DEKU_WOOD.gate,
                ZeldaBlocks.DEKU_WOOD.button,
                ZeldaBlocks.DEKU_WOOD.plate,
                ZeldaBlocks.DEKU_WOOD.door,
                ZeldaBlocks.DEKU_WOOD.trapdoor,
                ZeldaBlocks.DEKU_WOOD.sign,
                ZeldaBlocks.DEKU_WOOD.wallSign,
                ZeldaBlocks.DEKU_WOOD.hangingSign,
                ZeldaBlocks.DEKU_WOOD.wallHangingSign,
                // FAIRY LIGHTS
                ZeldaBlocks.PINK_FAIRY_LAMP,
                ZeldaBlocks.RED_FAIRY_LAMP,
                ZeldaBlocks.ORANGE_FAIRY_LAMP,
                ZeldaBlocks.YELLOW_FAIRY_LAMP,
                ZeldaBlocks.GREEN_FAIRY_LAMP,
                ZeldaBlocks.BLUE_FAIRY_LAMP,
                ZeldaBlocks.PURPLE_FAIRY_LAMP,
                // FAIRY MARBLE
                ZeldaBlocks.FAIRY_MARBLE.base,
                ZeldaBlocks.FAIRY_MARBLE.stair,
                ZeldaBlocks.FAIRY_MARBLE.wall,
                ZeldaBlocks.FAIRY_MARBLE.button,
                ZeldaBlocks.FAIRY_MARBLE.plate,

                ZeldaBlocks.COBBLED_FAIRY_MARBLE.base,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.stair,

                ZeldaBlocks.POLISHED_FAIRY_MARBLE.base,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.stair,

                ZeldaBlocks.FAIRY_MARBLE_BRICKS.base,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.stair,

                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.base,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.stair,

                ZeldaBlocks.FAIRY_MARBLE_TILES.base,
                ZeldaBlocks.FAIRY_MARBLE_TILES.stair,

                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.CHISELED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_PILLAR,
                ZeldaBlocks.SMOOTH_FAIRY_MARBLE,
                // MASTER BLOCK
                ZeldaBlocks.MASTER_SCRAP_BLOCK,
                ZeldaBlocks.MASTER_BLOCK,

                ZeldaBlocks.MASTER_PLATE.base,
                ZeldaBlocks.MASTER_PLATE.stair,

                ZeldaBlocks.MASTER_BRICK.base,
                ZeldaBlocks.MASTER_BRICK.stair,

                ZeldaBlocks.MASTER_TILE.base,
                ZeldaBlocks.MASTER_TILE.stair,

                ZeldaBlocks.CUT_MASTER_PLATE,
                ZeldaBlocks.MASTER_PILLAR,
                ZeldaBlocks.MASTER_BARS,
                ZeldaBlocks.MASTER_CHAIN,
                ZeldaBlocks.MASTER_DOOR,
                ZeldaBlocks.MASTER_TRAPDOOR,
                ZeldaBlocks.MASTER_GIRDER,
                ZeldaBlocks.MASTER_BARREL,
                // TEKTILES
                ZeldaBlocks.RED_TEKTILES.base,
                ZeldaBlocks.RED_TEKTILES.stair,

                ZeldaBlocks.SMALL_RED_TEKTILES.base,
                ZeldaBlocks.SMALL_RED_TEKTILES.stair,

                ZeldaBlocks.BLUE_TEKTILES.base,
                ZeldaBlocks.BLUE_TEKTILES.stair,

                ZeldaBlocks.SMALL_BLUE_TEKTILES.base,
                ZeldaBlocks.SMALL_BLUE_TEKTILES.stair
        );

        addSlabBlockDrops(
                ZeldaBlocks.DEKU_WOOD.slab,

                ZeldaBlocks.FAIRY_MARBLE.slab,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE.slab,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE.slab,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS.slab,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.slab,
                ZeldaBlocks.FAIRY_MARBLE_TILES.slab,

                ZeldaBlocks.MASTER_PLATE.slab,
                ZeldaBlocks.MASTER_BRICK.slab,
                ZeldaBlocks.MASTER_TILE.slab,

                ZeldaBlocks.RED_TEKTILES.slab,
                ZeldaBlocks.SMALL_RED_TEKTILES.slab,
                ZeldaBlocks.BLUE_TEKTILES.slab,
                ZeldaBlocks.SMALL_BLUE_TEKTILES.slab
        );
    }

    private void addSimpleBlockDrops(Block... blocks) {
        for (Block block : blocks) {
            addDrop(block);
        }
    }

    private void addSlabBlockDrops(Block... blocks) {
        for (Block block : blocks) {
            addDrop(block, slabDrops(block));
        }
    }
}
