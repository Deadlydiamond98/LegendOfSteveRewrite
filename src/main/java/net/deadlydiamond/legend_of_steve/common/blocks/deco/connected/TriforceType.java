package net.deadlydiamond.legend_of_steve.common.blocks.deco.connected;

import net.minecraft.util.StringIdentifiable;

public enum TriforceType implements StringIdentifiable {
    SINGLE("single"),

    DOUBLE_LEFT("double_left"),
    DOUBLE_RIGHT("double_right"),

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
        return this == BIG_TOP_LEFT || this == BIG_TOP_RIGHT;
    }

    public boolean isBottom() {
        return this == BIG_BOTTOM_LEFT || this == BIG_BOTTOM_RIGHT;
    }

    public boolean isLeft() {
        return this == DOUBLE_LEFT || this == BIG_TOP_LEFT || this == BIG_BOTTOM_LEFT;
    }

    public boolean isRight() {
        return this == DOUBLE_RIGHT || this == BIG_TOP_RIGHT || this == BIG_BOTTOM_RIGHT;
    }
}
