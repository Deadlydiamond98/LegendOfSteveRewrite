package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.util.wood.WoodVariantUtil;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class ZeldaLootTableDatagen extends FabricBlockLootTableProvider {

    public ZeldaLootTableDatagen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

        WoodVariantUtil.generateLootTables(this);

        generateLootTables(
                // DEKU WOOD
                ZeldaBlocks.DEKU_WOOD,
                // DUNGEONCITE
                ZeldaBlocks.BROWN_DUNGEONCITE,
                // TILES
                ZeldaBlocks.STONE_TILES,
                // FAIRY MARBLE
                ZeldaBlocks.COBBLED_FAIRY_MARBLE,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.FAIRY_MARBLE_TILES,
                ZeldaBlocks.PERLITE_BRICKS,
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
                ZeldaBlocks.RED_TEKTILE_BRICKS,
                ZeldaBlocks.BLUE_TEKTILES,
                ZeldaBlocks.BLUE_TEKTILE_BRICKS,
                // SWITCH BLOCKS
                ZeldaBlocks.RED_SWITCH_BLOCKS,
                ZeldaBlocks.BLUE_SWITCH_BLOCKS
        );

        addSimpleBlockDrops(
                ZeldaBlocks.DUNGEON_TABLE,
                ZeldaBlocks.CRATE,
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
                ZeldaBlocks.PERLITE,
                ZeldaBlocks.PERLITE_PILLAR,
                ZeldaBlocks.CHISELED_PERLITE,
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
                ZeldaBlocks.BLUE_QUESTION_BLOCK,
                ZeldaBlocks.INVISIBLE_QUESTION_BLOCK,

                // Other
                ZeldaBlocks.REDSTONE_LOCK_BLOCK,
                ZeldaBlocks.STONE_SWORD_PEDESTAL,
                ZeldaBlocks.DEEPSLATE_SWORD_PEDESTAL,
                ZeldaBlocks.BLACKSTONE_SWORD_PEDESTAL,
                ZeldaBlocks.QUARTZ_SWORD_PEDESTAL,
                ZeldaBlocks.FAIRY_MARBLE_SWORD_PEDESTAL,
                ZeldaBlocks.STRANGE_DIRT_SWORD_PEDESTAL,
                ZeldaBlocks.STRANGE_BLUE_DIRT_SWORD_PEDESTAL
        );

        addDrop(ZeldaBlocks.FAIRY_MARBLE.base, block -> this.drops(block, ZeldaBlocks.COBBLED_FAIRY_MARBLE.base));
        addDrop(ZeldaBlocks.FAIRY_MARBLE.slab);
        addDrop(ZeldaBlocks.FAIRY_MARBLE.stair);
        addDrop(ZeldaBlocks.FAIRY_MARBLE.wall);
        addDrop(ZeldaBlocks.FAIRY_MARBLE.button);
        addDrop(ZeldaBlocks.FAIRY_MARBLE.plate);

        addDropWithSilkTouch(ZeldaBlocks.LOOT_POT);
        ZeldaBlocks.DYED_LOOT_POTS.generateLootTables(this);

        addDrop(ZeldaBlocks.MASTER_DOOR, doorDrops(ZeldaBlocks.MASTER_DOOR));

        // PLANTS //////////////////////////////////////////////////////////////////////////////////////////////////////
        addDrop(ZeldaBlocks.SILENT_PRINCESS);
        addDrop(ZeldaBlocks.SILENT_PRINCESS_CROP, block -> this.drops(block, ZeldaItems.SILENT_PRINCESS_BULB));
    }

    private void generateLootTables(AbstractBlockset... blocksets) {
        for (AbstractBlockset blockset : blocksets) {
            blockset.generateLootTables(this);
        }
    }

    private void addSimpleBlockDrops(Block... blocks) {
        for (Block block : blocks) {
            if (block instanceof SlabBlock) {
                addDrop(block, slabDrops(block));
            } else {
                addDrop(block);
            }
        }
    }
}
