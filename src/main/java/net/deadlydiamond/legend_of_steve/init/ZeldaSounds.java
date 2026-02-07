package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ZeldaSounds {

    public static final SoundEvent SECRET_ROOM_JINGLE = register("bomb.jingle");

    public static SoundEvent register(String name) {
        Identifier id = LegendOfSteve.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {}
}
