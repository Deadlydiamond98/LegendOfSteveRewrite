package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.common.ZeldaDispenserBehavior;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.ThrownPotEntity;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.BombEntity;
import net.deadlydiamond.legend_of_steve.common.items.projectile.explosive.BombItem;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class ZeldaDispenserBehaviors {
    public static DispenserBehavior bomb() {
        return new ZeldaDispenserBehavior() {
            @Override
            protected float getForce() {
                return 0.6f;
            }

            @Override
            protected float getVariation() {
                return 14;
            }

            @Override
            protected ProjectileEntity createProjectile(World world, Position position, Direction direction, ItemStack stack) {
                return Util.make(new BombEntity(ZeldaEntityTypes.BOMB, world), entity -> {
                    if (stack.getItem() instanceof BombItem bombItem) {
                        bombItem.initBomb(entity, stack, null);
                    }
                    entity.setPosition(position.getX(), position.getY(), position.getZ());
                    entity.setYaw(direction.asRotation());
                });
            }
        };
    }

    public static DispenserBehavior lootPot() {
        return new ZeldaDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, Direction direction, ItemStack stack) {
                return Util.make(new ThrownPotEntity(ZeldaEntityTypes.THROWN_POT, world), entity -> {
                    entity.setItem(stack.copyWithCount(1));
                    entity.setPosition(position.getX(), position.getY(), position.getZ());
                    entity.setYaw(direction.asRotation());
                });
            }
        };
    }
}
