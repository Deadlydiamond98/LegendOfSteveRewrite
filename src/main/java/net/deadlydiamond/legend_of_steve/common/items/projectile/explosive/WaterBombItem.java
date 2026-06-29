package net.deadlydiamond.legend_of_steve.common.items.projectile.explosive;

import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;

public class WaterBombItem extends BombItem {
    public WaterBombItem(Settings settings) {
        super(settings, ZeldaEntityTypes.WATER_BOMB, ZeldaTags.BOMB_BREAKABLE, 60, 3.5f);
    }

    @Override
    public double bagXOffset() {
        return 1;
    }

    @Override
    public double bagYOffset() {
        return 3;
    }

    @Override
    public float bagGUIRotation() {
        return 0;
    }
}
