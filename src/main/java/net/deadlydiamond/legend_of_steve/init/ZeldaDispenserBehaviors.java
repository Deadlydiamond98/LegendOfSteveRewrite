package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.common.ZeldaDispenserBehavior;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock.ILockedBlock;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.ThrownPotEntity;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.BombEntity;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.WaterBombEntity;
import net.deadlydiamond.legend_of_steve.common.items.locking.LockItem;
import net.deadlydiamond.legend_of_steve.common.items.projectile.explosive.BombItem;
import net.deadlydiamond.legend_of_steve.util.LockManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
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

    public static DispenserBehavior waterBomb() {
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
                return Util.make(new WaterBombEntity(ZeldaEntityTypes.WATER_BOMB, world), entity -> {
                    if (stack.getItem() instanceof BombItem bombItem) {
                        bombItem.initBomb(entity, stack, null);
                    }
                    entity.setPosition(position.getX(), position.getY(), position.getZ());
                    entity.setYaw(direction.asRotation());
                });
            }
        };
    }

    public static DispenserBehavior key(DispenserBehavior fallback) {
        return new ItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                World world = pointer.getWorld();
                Direction facing = world.getBlockState(pointer.getPos()).get(Properties.FACING);
                BlockPos pos = pointer.getPos().offset(facing);
                BlockState state = world.getBlockState(pos);

                if (state.getBlock() instanceof ILockedBlock lock) {
                    if (lock.removeLock(world, pos, stack)) {
                        if (!stack.isOf(ZeldaItems.CREATIVE_KEY)) {
                            stack.decrement(1);
                        }
                        return stack;
                    }
                }
                return fallback.dispense(pointer, stack);
            }
        };
    }

    public static DispenserBehavior lock() {
        return new ItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                if (stack.getItem() instanceof LockItem lock) {
                    Direction facing = pointer.getBlockState().get(DispenserBlock.FACING);
                    World world = pointer.getWorld();
                    BlockPos pos = pointer.getPos().offset(facing);

                    if (LockManager.tryLockBlock(world, pos, facing, lock.getLockResult(world.getBlockState(pos)))) {
                        stack.decrement(1);
                        return stack;
                    }
                }
                return super.dispenseSilently(pointer, stack);
            }
        };
    }
}
