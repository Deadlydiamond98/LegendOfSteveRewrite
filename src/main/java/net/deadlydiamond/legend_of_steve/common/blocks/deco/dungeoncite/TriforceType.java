package net.deadlydiamond.legend_of_steve.common.blocks.deco.dungeoncite;

import net.minecraft.util.StringIdentifiable;

public enum TriforceType implements StringIdentifiable {
    SINGLE("single"),

    DOUBLE_TOP("double_top"),
    DOUBLE_BOTTOM("double_bottom"),

    BIG_TOP_LEFT("big_top_left"),
    BIG_TOP_RIGHT("big_top_right"),
    BIG_BOTTOM_LEFT("big_bottom_left"),
    BIG_BOTTOM_RIGHT("big_bottom_right");

    private final String type;

    TriforceType(String type) {
        this.type = type;
    }

    @Override
    public String asString() {
        return this.type;
    }

    public boolean isTop() {
        return this == DOUBLE_TOP || this == BIG_TOP_LEFT || this == BIG_TOP_RIGHT;
    }

    public boolean isBottom() {
        return this == DOUBLE_BOTTOM || this == BIG_BOTTOM_LEFT || this == BIG_BOTTOM_RIGHT;
    }

    public boolean isLeft() {
        return this == BIG_TOP_LEFT || this == BIG_BOTTOM_LEFT;
    }

    public boolean isRight() {
        return this == BIG_TOP_RIGHT || this == BIG_BOTTOM_RIGHT;
    }
}
