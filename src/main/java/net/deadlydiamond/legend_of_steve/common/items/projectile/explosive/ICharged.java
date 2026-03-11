package net.deadlydiamond.legend_of_steve.common.items.projectile.explosive;

/**
 * Gives Items a Charged Creeper-like overlay
 */
public interface ICharged {
    default boolean hasChargedLayer() {
        return true;
    }
}
