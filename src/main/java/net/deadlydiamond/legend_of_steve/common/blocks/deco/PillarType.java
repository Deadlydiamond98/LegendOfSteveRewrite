package net.deadlydiamond.legend_of_steve.common.blocks.deco;

import net.minecraft.util.StringIdentifiable;

public enum PillarType implements StringIdentifiable {
    SINGLE("single"),
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom");

    private final String type;

    PillarType(String type) {
        this.type = type;
    }

    @Override
    public String asString() {
        return this.type;
    }
}
