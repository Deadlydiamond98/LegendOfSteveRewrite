package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.util.wood.WoodVariant;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.minecraft.block.Block;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ZeldaNoteBlockSounds {
    public static final Map<Block, SoundEvent> NOTE_BLOCK_SOUNDS = new HashMap<>();

    public static void register() {
        // BRASS ENSEMBLE //////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.BRASS_ENSEMBLE,
                ZeldaBlocks.MASTER_BLOCK
        );
        // LTTP TRUMPET ////////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.LTTP_TRUMPET,
                ZeldaBlocks.MASTER_PLATE,
                ZeldaBlocks.MASTER_BRICK,
                ZeldaBlocks.MASTER_TILE
        );
        registerNoteBlockSound(ZeldaSounds.LTTP_TRUMPET,
                ZeldaBlocks.MASTER_SCRAP_BLOCK,
                ZeldaBlocks.CUT_MASTER_PLATE,
                ZeldaBlocks.MASTER_PILLAR,
                ZeldaBlocks.MASTER_BARS,
                ZeldaBlocks.MASTER_CHAIN,
                ZeldaBlocks.MASTER_DOOR,
                ZeldaBlocks.MASTER_TRAPDOOR,
                ZeldaBlocks.MASTER_GIRDER,
                ZeldaBlocks.MASTER_BARREL,
                ZeldaBlocks.RED_DUNGEON_CHEST,
                ZeldaBlocks.TRAPPED_RED_DUNGEON_CHEST,
                ZeldaBlocks.BLUE_DUNGEON_CHEST,
                ZeldaBlocks.TRAPPED_BLUE_DUNGEON_CHEST
        );
        // MALON ///////////////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.MALON,
                ZeldaBlocks.RED_FAIRY_LAMP,
                ZeldaBlocks.ORANGE_FAIRY_LAMP,
                ZeldaBlocks.YELLOW_FAIRY_LAMP,
                ZeldaBlocks.GREEN_FAIRY_LAMP,
                ZeldaBlocks.BLUE_FAIRY_LAMP,
                ZeldaBlocks.PURPLE_FAIRY_LAMP,
                ZeldaBlocks.PINK_FAIRY_LAMP
        );
        // PIZZICATO STRING ////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.PIZZICATO_STRING,
                ZeldaBlocks.FAIRY_MARBLE,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.FAIRY_MARBLE_TILES
        );
        registerNoteBlockSound(ZeldaSounds.PIZZICATO_STRING,
                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.CHISELED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_PILLAR,
                ZeldaBlocks.SMOOTH_FAIRY_MARBLE
        );
        // OCARINA /////////////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.OCARINA,
                ZeldaBlocks.DEKU_WOOD
        );
        // HAND PAN ////////////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.HAND_PAN,
                ZeldaBlocks.CHISELED_PLANKS,
                ZeldaBlocks.BEVELED_PLANKS
        );

        // SMW PIANO ///////////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.SMW_PIANO,
                ZeldaBlocks.STRANGE_DIRT,
                ZeldaBlocks.POLISHED_STRANGE_DIRT,
                ZeldaBlocks.STRANGE_DIRT_BRICKS,
                ZeldaBlocks.REINFORCED_STRANGE_DIRT
        );
        registerNoteBlockSound(ZeldaSounds.SMW_PIANO,
                ZeldaBlocks.STRANGE_DIRT_PILLAR,
                ZeldaBlocks.FLIP_BLOCK
        );
        // SMW SLAP BASS ///////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.SMW_SLAP_BASS,
                ZeldaBlocks.STRANGE_BLUE_DIRT,
                ZeldaBlocks.POLISHED_BLUE_STRANGE_DIRT,
                ZeldaBlocks.STRANGE_BLUE_DIRT_BRICKS,
                ZeldaBlocks.REINFORCED_STRANGE_BLUE_DIRT
        );
        registerNoteBlockSound(ZeldaSounds.SMW_SLAP_BASS,
                ZeldaBlocks.STRANGE_BLUE_DIRT_PILLAR
        );
        // PAAH ////////////////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.PAAH,
                ZeldaBlocks.QUESTION_BLOCK,
                ZeldaBlocks.BLUE_QUESTION_BLOCK,
                ZeldaBlocks.INVISIBLE_QUESTION_BLOCK
        );
        // NYLON GUITAR ////////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.NYLON_GUITAR,
                ZeldaBlocks.BROWN_DUNGEONCITE
        );
        // TMNT4 ORCHESTRA HIT /////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.TMNT4_ORCHESTRA_HIT,
                ZeldaBlocks.BLUE_TEKTILES,
                ZeldaBlocks.SMALL_BLUE_TEKTILES,
                ZeldaBlocks.BLUE_TEKTILE_BRICKS,
                ZeldaBlocks.RED_TEKTILES,
                ZeldaBlocks.SMALL_RED_TEKTILES,
                ZeldaBlocks.RED_TEKTILE_BRICKS
        );
        // VIBRAPHONE //////////////////////////////////////////////////////////////////////////////////////////////////
        registerNoteBlockSound(ZeldaSounds.VIBRAPHONE,
                ZeldaBlocks.PERLITE_BRICKS
        );

        registerNoteBlockSound(ZeldaSounds.VIBRAPHONE,
                ZeldaBlocks.PERLITE,
                ZeldaBlocks.PERLITE_PILLAR,
                ZeldaBlocks.CHISELED_PERLITE
        );
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SafeVarargs
    public static void registerNoteBlockSound(SoundEvent soundEvent, Map<WoodVariant, ?>... maps) {
        for (Map<WoodVariant, ?> map : maps) {
            map.forEach((woodVariant, t) -> {
                if (t instanceof Block block) {
                    registerNoteBlockSound(soundEvent, block);
                } else if (t instanceof AbstractBlockset blockset) {
                    registerNoteBlockSound(soundEvent, blockset);
                }
            });
        }
    }

    public static void registerNoteBlockSound(SoundEvent soundEvent, AbstractBlockset... blocksets) {
        for (AbstractBlockset blockset : blocksets) {
            for (Block block : blockset.getAll()) {
                registerNoteBlockSound(soundEvent, block);
            }
        }
    }

    public static void registerNoteBlockSound(SoundEvent soundEvent, Block... blocks) {
        for (Block block : blocks) {
            NOTE_BLOCK_SOUNDS.put(block, soundEvent);
        }
    }

    public static SoundEvent getCustomSound(World world, BlockPos pos) {
        return NOTE_BLOCK_SOUNDS.get(world.getBlockState(pos.down()).getBlock());
    }
}
