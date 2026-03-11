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

    // Magicy Blocks ///////////////////////////////////////////////////////////////////////////////////////////////////
    public static final BlockSoundGroup FAIRY_LAMP = registerGroup("fairy_lamp", 1, 1.2f);
    public static final BlockSoundGroup STARSTONE = registerGroup("starstone", 1, 1);

    // Master Ore //////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final BlockSoundGroup MASTER_BLOCK = registerGroup("master_block", 1, 1.5f);
    public static final BlockSoundGroup MASTER_PLATE = registerGroup("master_plate", 1, 1.5f);
    public static final BlockSoundGroup MASTER_BARS = registerGroup("master_bars", 1, 1.5f);
    public static final BlockSoundGroup MASTER_CHAIN = registerGroup("master_chain", 1, 1.5f);
    public static final SoundEvent MASTER_BARREL_OPEN = register("block.master_barrel.open");
    public static final SoundEvent MASTER_BARREL_CLOSE = register("block.master_barrel.close");

    // Tektiles ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final BlockSoundGroup TEKTILES = registerGroup("tektiles", 1, 1.25f);

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
