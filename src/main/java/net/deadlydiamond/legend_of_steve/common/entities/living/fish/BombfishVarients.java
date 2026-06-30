package net.deadlydiamond.legend_of_steve.common.entities.living.fish;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.util.Identifier;
import net.minecraft.world.WorldAccess;

import java.util.List;

public class BombfishVarients {
    public static final List<Identifier> TEXTURES = List.of(
            addTexture("teal_red"), addTexture("teal_orange"),
            addTexture("cyan_red"), addTexture("cyan_orange"),
            addTexture("blue_red"), addTexture("blue_orange"),
            addTexture("green_red"), addTexture("green_orange")
    );

    public static int getRandom(WorldAccess world) {
        return world.getRandom().nextInt(TEXTURES.size());
    }

    private static Identifier addTexture(String type) {
        return LegendOfSteve.id("textures/entity/bombfish/" + type + "_bombfish.png");
    }
}
