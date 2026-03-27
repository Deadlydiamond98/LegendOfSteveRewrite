package net.deadlydiamond.legend_of_steve.common.items.projectile;

import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.deadlydiamond98.koalalib.common.items.vanillamodified.projectile.CustomProjectileItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DekuNutProjectile extends CustomProjectileItem {
    public DekuNutProjectile(Settings settings) {
        super(settings, ZeldaEntityTypes.DEKU_NUT);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        user.getItemCooldownManager().set(this, 80);
        return super.use(world, user, hand);
    }
}
