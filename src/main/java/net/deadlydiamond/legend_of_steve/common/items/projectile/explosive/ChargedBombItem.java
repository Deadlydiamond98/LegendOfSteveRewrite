package net.deadlydiamond.legend_of_steve.common.items.projectile.explosive;

import net.deadlydiamond.legend_of_steve.common.entities.bomb.BombEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ChargedBombItem extends BombItem implements ICharged {
    public ChargedBombItem(Settings settings, EntityType<?> type, int fuse, int power) {
        super(settings, type, fuse, power);
    }

    @Override
    protected void initBomb(BombEntity bomb, ItemStack stack, LivingEntity owner) {
        super.initBomb(bomb, stack, owner);
        bomb.setCharged(true);
    }
}
