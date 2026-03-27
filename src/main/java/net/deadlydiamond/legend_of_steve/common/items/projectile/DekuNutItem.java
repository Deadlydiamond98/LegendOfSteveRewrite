package net.deadlydiamond.legend_of_steve.common.items.projectile;

import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.deadlydiamond98.koalalib.common.items.vanillamodified.projectile.CustomProjectileItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DekuNutItem extends CustomProjectileItem {
    public DekuNutItem(Settings settings) {
        super(settings, ZeldaEntityTypes.DEKU_NUT);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.getItemCooldownManager().set(this, 120);
        return super.use(world, user, hand);
    }

    @Override
    public void initProjectile(Entity entity, ItemStack stack, LivingEntity owner, @Nullable Hand hand) {
        super.initProjectile(entity, stack, owner, hand);
        if (entity instanceof ProjectileEntity projectile) {
            projectile.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0, 1.25f, 1);
        }
    }
}
