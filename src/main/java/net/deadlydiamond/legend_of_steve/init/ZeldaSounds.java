package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ZeldaSounds {

    // Music ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final SoundEvent MUSIC_DISC_LEGEND = register("music_disc.legend");

    // Blocks //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Bomb Flower
    public static final BlockSoundGroup BOMB_FLOWER = registerGroup("bomb_flower");
    public static final SoundEvent BOMB_HARVESTED = register("block.bomb_flower.pick");
    // Loot Pot
    public static final SoundEvent LOOT_POT_WITHDRAW = register("block.loot_pot.withdraw");
    public static final SoundEvent LOOT_POT_DEPOSIT = register("block.loot_pot.deposit");
    public static final SoundEvent LOOT_POT_THROWN = register("block.loot_pot.thrown");
    public static final SoundEvent LOOT_POT_GRAB = register("block.loot_pot.grab");
    // Sword Pedestal
    public static final SoundEvent SWORD_PEDESTAL_WITHDRAW = register("block.sword_pedestal.withdraw");
    public static final SoundEvent SWORD_PEDESTAL_DEPOSIT = register("block.sword_pedestal.deposit");
    public static final SoundEvent SWORD_PEDESTAL_WITHDRAW_WOODEN = register("block.sword_pedestal.withdraw_wooden");
    public static final SoundEvent SWORD_PEDESTAL_DEPOSIT_WOODEN = register("block.sword_pedestal.deposit_wooden");
    // Spring Water
    public static final SoundEvent SPRING_WATER_HEAL = register("block.spring_water.heal");
    public static final SoundEvent SPRING_WATER_TRANSFORM = register("block.spring_water.transform");
    public static final SoundEvent SPRING_WATER_CONSUME = register("block.spring_water.consume");
    public static final SoundEvent SPRING_WATER_SPARKLE = register("block.spring_water.sparkle");
    // Dungeoncite
    public static final BlockSoundGroup DUNGEONCITE = registerGroup("dungeoncite", 1, 0.75f);
    public static final BlockSoundGroup DUNGEONCITE_BRICKS = registerGroup("dungeoncite_bricks", 1, 0.75f);
    public static final BlockSoundGroup DUNGEONCITE_TILE = registerGroup("dungeoncite_tile", 1, 0.75f);
    // Master Ore
    public static final BlockSoundGroup MASTER_BLOCK = registerGroup("master_block", 1, 1.5f);
    public static final BlockSoundGroup MASTER_PLATE = registerGroup("master_plate", 1, 1.5f);
    public static final BlockSoundGroup MASTER_BARS = registerGroup("master_bars", 1, 1.5f);
    public static final BlockSoundGroup MASTER_CHAIN = registerGroup("master_chain", 0.75f, 1.5f);
    public static final SoundEvent MASTER_BARREL_OPEN = register("block.master_barrel.open");
    public static final SoundEvent MASTER_BARREL_CLOSE = register("block.master_barrel.close");
    // Strange Dirt
    public static final BlockSoundGroup STRANGE_DIRT = registerGroup("strange_dirt", 1, 1.25f);
    public static final BlockSoundGroup STRANGE_BLUE_DIRT = registerGroup("strange_blue_dirt", 1, 1);
    public static final BlockSoundGroup QUESTION_BLOCK = registerGroup("question_block", 1, 1);
    // Question Block
    public static final SoundEvent QUESTION_BLOCK_HIT = register("block.question_block.bounce");
    public static final SoundEvent QUESTION_BLOCK_EMPTY_CONTENTS = register("block.question_block.empty_content");
    public static final SoundEvent QUESTION_BLOCK_DEPOSIT = register("block.question_block.deposit");
    // CRATE
    public static final SoundEvent CRATE_OPEN = register("block.crate.open");
    public static final SoundEvent CRATE_CLOSE = register("block.crate.close");
    // FLUID WALK SOUNDS
    public static final SoundEvent WATER_STEP = register("block.water.pondstriding.step");
    public static final SoundEvent LAVA_STEP = register("block.lava.hotstriding.step");
    // OTHER
    public static final BlockSoundGroup FAIRY_LAMP = registerGroup("fairy_lamp", 1, 1.2f);
    public static final BlockSoundGroup STARSTONE = registerGroup("starstone", 1, 1);
    public static final BlockSoundGroup TEKTILES = registerGroup("tektiles", 1, 1);
    // Note Block
    public static final SoundEvent MALON = register("block.note_block.malon");
    public static final SoundEvent PIZZICATO_STRING = register("block.note_block.pizzicato_string");
    public static final SoundEvent OCARINA = register("block.note_block.ocarina");
    public static final SoundEvent SMW_PIANO = register("block.note_block.smw_piano");
    public static final SoundEvent BRASS_ENSEMBLE = register("block.note_block.brass_ensemble");
    public static final SoundEvent LTTP_TRUMPET = register("block.note_block.lttp_trumpet");
    public static final SoundEvent SMW_SLAP_BASS = register("block.note_block.smw_slap_bass");
    public static final SoundEvent HAND_PAN = register("block.note_block.hand_pan");
    public static final SoundEvent PAAH = register("block.note_block.paah");
    public static final SoundEvent NYLON_GUITAR = register("block.note_block.nylon_guitar");
    public static final SoundEvent TMNT4_ORCHESTRA_HIT = register("block.note_block.tmnt4_orchestra_hit");

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
    // Lock
    public static final SoundEvent LOCK = register("item.lock.lock");
    public static final SoundEvent UNLOCK = register("item.lock.unlock");
    // Magic Sword
    public static final SoundEvent SWORD_SHOOT = register("item.magic_sword.shoot");
    public static final SoundEvent SWORD_RECHARGE = register("item.magic_sword.recharge");

    // Entities ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Aruroda
    public static final SoundEvent ARURODA_AMBIENT = register("entity.aruroda.ambient");
    public static final SoundEvent ARURODA_DEATH = register("entity.aruroda.death");
    public static final SoundEvent ARURODA_HURT = register("entity.aruroda.hurt");
    public static final SoundEvent ARURODA_STEP = register("entity.aruroda.step");
    public static final SoundEvent ARURODA_HOP = register("entity.aruroda.hop");
    // Bomb
    public static final SoundEvent SECRET_ROOM_JINGLE = register("entity.bomb.jingle");
    public static final SoundEvent BOMB_PICKED_UP = register("entity.bomb.pick_up");
    public static final SoundEvent BOMB_EXTINGUISH = register("entity.bomb.extinguish");
    public static final SoundEvent BOMB_PRIMED = register("entity.bomb.primed");
    public static final SoundEvent BOMB_THROWN = register("entity.bomb.thrown");
    // Fairy
    public static final SoundEvent FAIRY_DEATH = register("entity.fairy.death");
    public static final SoundEvent FAIRY_HURT = register("entity.fairy.hurt");
    public static final SoundEvent FAIRY_AMBIENT = register("entity.fairy.ambient");
    public static final SoundEvent FAIRY_HEAL = register("entity.fairy.heal");
    // Tektite
    public static final SoundEvent TEKTITE_AMBIENT = register("entity.tektite.ambient");
    public static final SoundEvent TEKTITE_DEATH = register("entity.tektite.death");
    public static final SoundEvent TEKTITE_HURT = register("entity.tektite.hurt");
    public static final SoundEvent TEKTITE_STEP = register("entity.tektite.step");
    public static final SoundEvent TEKTITE_HOP = register("entity.tektite.hop");

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
