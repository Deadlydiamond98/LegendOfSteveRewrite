package net.deadlydiamond.legend_of_steve.common.items.projectile.explosive;

import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.BombEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class WaterBombItem extends BombItem {
    private final boolean charged;

    public WaterBombItem(Settings settings, boolean charged) {
        super(settings, ZeldaEntityTypes.WATER_BOMB, ZeldaTags.BOMB_BREAKABLE, 60, 3.5f);
        this.charged = charged;
    }

    @Override
    public boolean isCharged() {
        return this.charged;
    }

    @Override
    public void initBomb(BombEntity bomb, ItemStack stack, LivingEntity owner) {
        super.initBomb(bomb, stack, owner);
        bomb.setCharged(this.charged);
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
