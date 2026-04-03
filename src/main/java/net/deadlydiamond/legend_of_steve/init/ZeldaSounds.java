package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ZeldaSounds {

    // Bomb Related ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final BlockSoundGroup BOMB_FLOWER = registerGroup("bomb_flower");
    public static final SoundEvent BOMB_HARVESTED = register("block.bomb_flower.pick");

    public static final SoundEvent SECRET_ROOM_JINGLE = register("entity.bomb.jingle");
    public static final SoundEvent BOMB_PICKED_UP = register("entity.bomb.pick_up");
    public static final SoundEvent BOMB_EXTINGUISH = register("entity.bomb.extinguish");
    public static final SoundEvent BOMB_PRIMED = register("entity.bomb.primed");
    public static final SoundEvent BOMB_THROWN = register("entity.bomb.thrown");

    // Blocks //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final SoundEvent LOOT_POT_WITHDRAW = register("block.loot_pot.withdraw");
    public static final SoundEvent LOOT_POT_DEPOSIT = register("block.loot_pot.deposit");
    public static final SoundEvent LOOT_POT_THROWN = register("block.loot_pot.thrown");
    public static final SoundEvent LOOT_POT_GRAB = register("block.loot_pot.grab");
    // Magic
    public static final BlockSoundGroup FAIRY_LAMP = registerGroup("fairy_lamp", 1, 1.2f);
    public static final BlockSoundGroup STARSTONE = registerGroup("starstone", 1, 1);
    public static final SoundEvent SPRING_WATER_TRANSFORM = register("block.spring_water.transform");
    public static final SoundEvent SPRING_WATER_CONSUME = register("block.spring_water.consume");
    public static final SoundEvent SPRING_WATER_SPARKLE = register("block.spring_water.sparkle");
    // Master Ore
    public static final BlockSoundGroup MASTER_BLOCK = registerGroup("master_block", 1, 1.5f);
    public static final BlockSoundGroup MASTER_PLATE = registerGroup("master_plate", 1, 1.5f);
    public static final BlockSoundGroup MASTER_BARS = registerGroup("master_bars", 1, 1.5f);
    public static final BlockSoundGroup MASTER_CHAIN = registerGroup("master_chain", 0.75f, 1.5f);
    public static final SoundEvent MASTER_BARREL_OPEN = register("block.master_barrel.open");
    public static final SoundEvent MASTER_BARREL_CLOSE = register("block.master_barrel.close");
    // STRANGE DIRT
    public static final BlockSoundGroup STRANGE_DIRT = registerGroup("strange_dirt", 1, 1.25f);
    public static final BlockSoundGroup STRANGE_BLUE_DIRT = registerGroup("strange_blue_dirt", 1, 1);
    public static final BlockSoundGroup QUESTION_BLOCK = registerGroup("question_block", 1, 1);
    public static final SoundEvent QUESTION_BLOCK_HIT = register("block.question_block.bounce");
    public static final SoundEvent QUESTION_BLOCK_EMPTY_CONTENTS = register("block.question_block.empty_content");
    public static final SoundEvent QUESTION_BLOCK_DEPOSIT = register("block.question_block.deposit");
    // OTHER
    public static final BlockSoundGroup TEKTILES = registerGroup("tektiles", 1, 1.25f);
    public static final SoundEvent CRATE_OPEN = register("block.crate.open");
    public static final SoundEvent CRATE_CLOSE = register("block.crate.close");
    // Note Block
    public static final SoundEvent MALON = register("block.note_block.malon");
    public static final SoundEvent PIZZICATO_STRING = register("block.note_block.pizzicato_string");
    public static final SoundEvent OCARINA = register("block.note_block.ocarina");
    public static final SoundEvent SMW_PIANO = register("block.note_block.smw_piano");
    public static final SoundEvent BRASS_ENSEMBLE = register("block.note_block.brass_ensemble");
    public static final SoundEvent LTTP_TRUMPET = register("block.note_block.lttp_trumpet");
    public static final SoundEvent SMW_SLAP_BASS = register("block.note_block.smw_slap_bass");
    public static final SoundEvent HAND_PAN = register("block.note_block.hand_pan");

    // Items ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Emerald Shards
    public static final SoundEvent EMERALD_SHARD_PICKED_UP = register("item.emerald_shard.pick_up");
    public static final SoundEvent EMERALD_SHARD_CONDENSE = register("item.emerald_shard.condense");
    public static final SoundEvent EMERALD_SHARD_CONDENSE_FURTHER = register("item.emerald_shard.condense_further");
    // Deku Nut
    public static final SoundEvent DEKU_NUT_SNAP = register("item.deku_nut.snap");
    public static final SoundEvent DEKU_NUT_STUN = register("item.deku_nut.freeze");
    // Bottle
    public static final SoundEvent EMPTY_BOTTLE = register("item.bottle.empty");

    // Entities ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Fairy
    public static final SoundEvent FAIRY_DEATH = register("entity.fairy.death");
    public static final SoundEvent FAIRY_HURT = register("entity.fairy.hurt");
    public static final SoundEvent FAIRY_AMBIENT = register("entity.fairy.ambient");
    public static final SoundEvent FAIRY_HEAL = register("entity.fairy.heal");

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    public static SoundEvent register(String name) {
        Identifier id = LegendOfSteve.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static BlockSoundGroup registerGroup(String name) {
        return registerGroup(name, 1, 1);
    }

    public static BlockSoundGroup registerGroup(String name, float volume, float pitch) {
        return new BlockSoundGroup(
                volume, pitch,
                register("block." + name + ".break"),
                register("block." + name + ".step"),
                register("block." + name + ".place"),
                register("block." + name + ".hit"),
                register("block." + name + ".fall")
        );
    }

    public static void register() {}
}
