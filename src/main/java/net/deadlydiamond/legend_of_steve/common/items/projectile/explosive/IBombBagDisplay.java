package net.deadlydiamond.legend_of_steve.common.items.projectile.explosive;

public interface IBombBagDisplay {
    default float bagGUIRotation() {
        return 45;
    }
    default double bagXOffset() {
        return 0;
    }
    default double bagYOffset() {
        return 0;
    }
}
