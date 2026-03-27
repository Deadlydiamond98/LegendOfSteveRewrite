package net.deadlydiamond.legend_of_steve.util;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class CustomNoteBlockSounds {

    public static List<Block> BRASS_ENSEMBLE = List.of(
            ZeldaBlocks.MASTER_BLOCK
    );

    public static List<Block> LTTP_TRUMPET = List.of(
            ZeldaBlocks.MASTER_SCRAP_BLOCK,
            ZeldaBlocks.MASTER_PLATE.base,
            ZeldaBlocks.MASTER_PLATE.slab,
            ZeldaBlocks.MASTER_PLATE.stair,
            ZeldaBlocks.MASTER_BRICK.base,
            ZeldaBlocks.MASTER_BRICK.slab,
            ZeldaBlocks.MASTER_BRICK.stair,
            ZeldaBlocks.MASTER_TILE.base,
            ZeldaBlocks.MASTER_TILE.slab,
            ZeldaBlocks.MASTER_TILE.stair,
            ZeldaBlocks.CUT_MASTER_PLATE,
            ZeldaBlocks.MASTER_PILLAR,
            ZeldaBlocks.MASTER_BARS,
            ZeldaBlocks.MASTER_CHAIN,
            ZeldaBlocks.MASTER_DOOR,
            ZeldaBlocks.MASTER_TRAPDOOR,
            ZeldaBlocks.MASTER_GIRDER,
            ZeldaBlocks.MASTER_BARREL
    );

    public static List<Block> MALON = List.of(
            ZeldaBlocks.RED_FAIRY_LAMP,
            ZeldaBlocks.ORANGE_FAIRY_LAMP,
            ZeldaBlocks.YELLOW_FAIRY_LAMP,
            ZeldaBlocks.GREEN_FAIRY_LAMP,
            ZeldaBlocks.BLUE_FAIRY_LAMP,
            ZeldaBlocks.PURPLE_FAIRY_LAMP,
            ZeldaBlocks.PINK_FAIRY_LAMP
    );

    public static List<Block> PIZZICATO_STRING = List.of(
            ZeldaBlocks.FAIRY_MARBLE.base,
            ZeldaBlocks.FAIRY_MARBLE.slab,
            ZeldaBlocks.FAIRY_MARBLE.stair,
            ZeldaBlocks.FAIRY_MARBLE.wall,
            ZeldaBlocks.FAIRY_MARBLE.plate,
            ZeldaBlocks.COBBLED_FAIRY_MARBLE.base,
            ZeldaBlocks.COBBLED_FAIRY_MARBLE.slab,
            ZeldaBlocks.COBBLED_FAIRY_MARBLE.stair,
            ZeldaBlocks.COBBLED_FAIRY_MARBLE.wall,
            ZeldaBlocks.FAIRY_MARBLE_BRICKS.base,
            ZeldaBlocks.FAIRY_MARBLE_BRICKS.slab,
            ZeldaBlocks.FAIRY_MARBLE_BRICKS.stair,
            ZeldaBlocks.FAIRY_MARBLE_BRICKS.wall,
            ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.base,
            ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.slab,
            ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.stair,
            ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.wall,
            ZeldaBlocks.FAIRY_MARBLE_TILES.base,
            ZeldaBlocks.FAIRY_MARBLE_TILES.slab,
            ZeldaBlocks.FAIRY_MARBLE_TILES.stair,
            ZeldaBlocks.FAIRY_MARBLE_TILES.wall,
            ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS,
            ZeldaBlocks.CHISELED_FAIRY_MARBLE,
            ZeldaBlocks.FAIRY_MARBLE_PILLAR,
            ZeldaBlocks.SMOOTH_FAIRY_MARBLE
    );

    public static List<Block> OCARINA = Arrays.asList(ZeldaBlocks.DEKU_WOOD.getAll());

    public static List<Block> SMW_PIANO = List.of(
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
            ZeldaBlocks.CHISELED_DEKU_PLANKS
    );

    public static SoundEvent getCustomSound(World world, BlockPos pos) {
        Block block = world.getBlockState(pos.down()).getBlock();
        if (BRASS_ENSEMBLE.contains(block)) {
            return ZeldaSounds.BRASS_ENSEMBLE;
        }
        if (LTTP_TRUMPET.contains(block)) {
            return ZeldaSounds.LTTP_TRUMPET;
        }
        if (MALON.contains(block)) {
            return ZeldaSounds.MALON;
        }
        if (PIZZICATO_STRING.contains(block)) {
            return ZeldaSounds.PIZZICATO_STRING;
        }
        if (OCARINA.contains(block)) {
            return ZeldaSounds.OCARINA;
        }
        if (SMW_PIANO.contains(block)) {
            return ZeldaSounds.SMW_PIANO;
        }
        return null;
    }
}
