package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;

public class ZeldaLootTableDatagen extends FabricBlockLootTableProvider {

    public ZeldaLootTableDatagen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

        generateLootTables(
                // DEKU WOOD
                ZeldaBlocks.DEKU_WOOD,
                // FAIRY MARBLE
                ZeldaBlocks.FAIRY_MARBLE,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.FAIRY_MARBLE_TILES,
                // MASTER
                ZeldaBlocks.MASTER_PLATE,
                ZeldaBlocks.MASTER_BRICK,
                ZeldaBlocks.MASTER_TILE,
                // STRANGE DIRT
                ZeldaBlocks.STRANGE_DIRT,
                ZeldaBlocks.STRANGE_DIRT_BRICKS,
                ZeldaBlocks.POLISHED_STRANGE_DIRT,
                ZeldaBlocks.REINFORCED_STRANGE_DIRT,
                ZeldaBlocks.STRANGE_BLUE_DIRT,
                ZeldaBlocks.STRANGE_BLUE_DIRT_BRICKS,
                ZeldaBlocks.POLISHED_BLUE_STRANGE_DIRT,
                ZeldaBlocks.REINFORCED_STRANGE_BLUE_DIRT,
                // TEKTILES
                ZeldaBlocks.RED_TEKTILES,
                ZeldaBlocks.SMALL_RED_TEKTILES,
                ZeldaBlocks.BLUE_TEKTILES,
                ZeldaBlocks.SMALL_BLUE_TEKTILES
        );

        addSimpleBlockDrops(
                ZeldaBlocks.CRATE,
                // CHISELED PLANKS
                ZeldaBlocks.CHISELED_OAK_PLANKS,
                ZeldaBlocks.CHISELED_BIRCH_PLANKS,
                ZeldaBlocks.CHISELED_SPRUCE_PLANKS,
                ZeldaBlocks.CHISELED_JUNGLE_PLANKS,
                ZeldaBlocks.CHISELED_ACACIA_PLANKS,
                ZeldaBlocks.CHISELED_DARK_OAK_PLANKS,
                ZeldaBlocks.CHISELED_CRIMSON_PLANKS,
                ZeldaBlocks.CHISELED_WARPED_PLANKS,
                ZeldaBlocks.CHISELED_MANGROVE_PLANKS,
                ZeldaBlocks.CHISELED_BAMBOO_PLANKS,
                ZeldaBlocks.CHISELED_CHERRY_PLANKS,
                ZeldaBlocks.CHISELED_DEKU_PLANKS,
                // FAIRY LIGHTS
                ZeldaBlocks.PINK_FAIRY_LAMP,
                ZeldaBlocks.RED_FAIRY_LAMP,
                ZeldaBlocks.ORANGE_FAIRY_LAMP,
                ZeldaBlocks.YELLOW_FAIRY_LAMP,
                ZeldaBlocks.GREEN_FAIRY_LAMP,
                ZeldaBlocks.BLUE_FAIRY_LAMP,
                ZeldaBlocks.PURPLE_FAIRY_LAMP,
                // FAIRY MARBLE
                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.CHISELED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_PILLAR,
                ZeldaBlocks.SMOOTH_FAIRY_MARBLE,
                // MASTER BLOCK
                ZeldaBlocks.MASTER_SCRAP_BLOCK,
                ZeldaBlocks.MASTER_BLOCK,

                ZeldaBlocks.CUT_MASTER_PLATE,
                ZeldaBlocks.MASTER_PILLAR,
                ZeldaBlocks.MASTER_BARS,
                ZeldaBlocks.MASTER_CHAIN,
                ZeldaBlocks.MASTER_TRAPDOOR,
                ZeldaBlocks.MASTER_GIRDER,
                ZeldaBlocks.MASTER_BARREL,
                // STRANGE DIRT
                ZeldaBlocks.STRANGE_DIRT_PILLAR,
                ZeldaBlocks.STRANGE_BLUE_DIRT_PILLAR,
                ZeldaBlocks.QUESTION_BLOCK,
                // Other
                ZeldaBlocks.STONE_SWORD_PEDESTAL,
                ZeldaBlocks.DEEPSLATE_SWORD_PEDESTAL,
                ZeldaBlocks.BLACKSTONE_SWORD_PEDESTAL,
                ZeldaBlocks.QUARTZ_SWORD_PEDESTAL,
                ZeldaBlocks.FAIRY_MARBLE_SWORD_PEDESTAL,
                ZeldaBlocks.STRANGE_DIRT_SWORD_PEDESTAL,
                ZeldaBlocks.STRANGE_BLUE_DIRT_SWORD_PEDESTAL
        );

        addDropWithSilkTouch(ZeldaBlocks.LOOT_POT);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.white);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.light_gray);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.gray);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.black);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.brown);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.red);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.orange);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.yellow);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.lime);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.green);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.cyan);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.light_blue);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.blue);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.purple);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.magenta);
        addDropWithSilkTouch(ZeldaBlocks.DYED_LOOT_POTS.pink);

        addDrop(ZeldaBlocks.MASTER_DOOR, doorDrops(ZeldaBlocks.MASTER_DOOR));
    }

    private void generateLootTables(AbstractBlockset... blocksets) {
        for (AbstractBlockset blockset : blocksets) {
            blockset.generateLootTables(this);
        }
    }

    private void addSimpleBlockDrops(Block... blocks) {
        for (Block block : blocks) {
            addDrop(block);
        }
    }
}
