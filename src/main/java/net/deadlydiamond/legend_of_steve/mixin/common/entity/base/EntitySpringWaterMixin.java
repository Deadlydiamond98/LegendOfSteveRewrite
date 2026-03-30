package net.deadlydiamond.legend_of_steve.mixin.common.entity.base;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.deadlydiamond.legend_of_steve.common.items.FairyBottleItem;
import net.deadlydiamond.legend_of_steve.common.particles.MagicSparkleParticleEffect;
import net.deadlydiamond.legend_of_steve.common.recipes.SpringWaterRecipe;
import net.deadlydiamond.legend_of_steve.init.ZeldaAdvancements;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.networking.s2c.ItemTransmutationPoofS2CPacket;
import net.deadlydiamond98.koalalib.util.magic.MagicBarHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntitySpringWaterMixin {
    @Shadow protected boolean firstUpdate;
    @Shadow protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;
    @Shadow public abstract boolean updateMovementInFluid(TagKey<Fluid> tag, double speed);
    @Shadow public abstract World getWorld();
    @Shadow public abstract double getX();
    @Shadow public abstract double getY();
    @Shadow public abstract double getZ();
    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);
    @Shadow public abstract Vec3d getPos();
    @Shadow public abstract void discard();
    @Shadow public abstract double getRandomBodyY();
    @Shadow public abstract double getParticleZ(double widthScale);
    @Shadow public abstract double getParticleX(double widthScale);

    @Shadow private World world;
    @Unique private int legend_of_steve$springWaterTicks;

    // SPRING WATER INTERACTION ////////////////////////////////////////////////////////////////////////////////////////

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attemptTickInVoid()V", shift = At.Shift.BEFORE))
    private void legend_of_steve$baseTickSpringWater(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (legend_of_steve$isInSpringWater()) {
            legend_of_steve$tickSpringWaterInteraction(entity, this.legend_of_steve$springWaterTicks++);
        } else {
            this.legend_of_steve$springWaterTicks = 0;
        }
    }

    @WrapMethod(method = "updateWaterState")
    private boolean legend_of_steve$updateWaterState(Operation<Boolean> original) {
        boolean bl = original.call();
        boolean updateSpringWaterMovement = this.updateMovementInFluid(ZeldaTags.ENCHANTED_SPRING_WATER, 0);
        return bl || updateSpringWaterMovement;
    }

    @Unique
    public void legend_of_steve$tickSpringWaterInteraction(Entity entity, int submergedTime) {
        World world = getWorld();

        if (entity instanceof LivingEntity living && submergedTime >= 140) {
            if (!this.getWorld().isClient) {
                if (submergedTime % 5 == 0) {
                    living.heal(1);
                    MagicBarHelper.addMana(living, 5);
                    if (living instanceof PlayerEntity player) {
                        ZeldaAdvancements.RELAX_IN_SPRING_WATER.trigger(player);
                    }
                }
            } else {
                Vec3d pos = new Vec3d(
                        this.getParticleX(0.5),
                        this.getRandomBodyY(),
                        this.getParticleZ(0.5)
                );

                MagicSparkleParticleEffect.createFountainSparkles(getWorld(), pos, 1, 0.05, 0.01);
            }
        } else if (entity instanceof ItemEntity itemEntity) {
            if (!world.isClient() && submergedTime % 10 == 0) {

                if (itemEntity.getStack().isOf(ZeldaItems.FAIRY_BOTTLE)) {
                    if (submergedTime >= 140) {
                        convertItem(itemEntity, FairyBottleItem.getFairyColor(itemEntity.getStack()).getFairyLamp());
                    }
                } else {
                    Optional<SpringWaterRecipe> match = world.getServer().getRecipeManager().getFirstMatch(
                            SpringWaterRecipe.Type.INSTANCE, new SimpleInventory(itemEntity.getStack()), world
                    );

                    if (match.isPresent()) {
                        SpringWaterRecipe recipe = match.get();
                        if (submergedTime >= recipe.getTime()) {
                            convertItem(itemEntity, recipe.getOutput(itemEntity.getStack().getCount()));
                        }
                    } else if (submergedTime >= 140) {
                        ItemTransmutationPoofS2CPacket.send(world, itemEntity);
                        playSound(ZeldaSounds.SPRING_WATER_CONSUME, 1, 1);
                        this.discard();
                    }
                }
            }

            if (getWorld().isClient() && submergedTime % 2 == 0) {
                MagicSparkleParticleEffect.createFountainSparkles(
                        world, getPos().add(0, 0.25, 0).add(
                                (world.random.nextFloat() - 0.5) * 0.125,
                                (world.random.nextFloat() - 0.5) * 0.125,
                                (world.random.nextFloat() - 0.5) * 0.125
                        ), 1, 0.05, 0.01
                );
            }
        }
    }

    private void convertItem(ItemEntity itemEntity, ItemStack output) {
        if (itemEntity.getStack().getCount() == 1) {
            output.setNbt(itemEntity.getStack().getOrCreateNbt());
        }

        if (itemEntity.getOwner() instanceof PlayerEntity player) {
            ZeldaAdvancements.TRANSMUTE_ITEM.trigger(player);
        }

        ItemEntity result = new ItemEntity(world, getX(), getY(), getZ(), output);
        result.setNoGravity(true);
        result.setGlowing(true);
        result.setVelocity(
                (world.random.nextFloat() - 0.5) * 0.01,
                0.05,
                (world.random.nextFloat() - 0.5) * 0.01
        );
        world.spawnEntity(result);

        ItemTransmutationPoofS2CPacket.send(world, itemEntity);
        playSound(ZeldaSounds.SPRING_WATER_TRANSFORM, 1, 1);
        this.discard();
    }

    @Unique
    private boolean legend_of_steve$isInSpringWater() {
        return !this.firstUpdate && this.fluidHeight.getDouble(ZeldaTags.ENCHANTED_SPRING_WATER) > 0.0;
    }
}
