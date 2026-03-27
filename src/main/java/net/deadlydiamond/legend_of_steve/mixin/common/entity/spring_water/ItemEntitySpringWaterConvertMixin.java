package net.deadlydiamond.legend_of_steve.mixin.common.entity.spring_water;

import net.deadlydiamond.legend_of_steve.common.recipes.SpringWaterRecipe;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(ItemEntity.class)
public abstract class ItemEntitySpringWaterConvertMixin extends EntitySpringWaterMixin {
    @Shadow public abstract ItemStack getStack();

    @Override
    public void legend_of_steve$tickSpringWaterInteraction(Entity entity, int submergedTime) {
        super.legend_of_steve$tickSpringWaterInteraction(entity, submergedTime);
        if (submergedTime >= 140) {
            if (!getWorld().isClient()) {
                Optional<SpringWaterRecipe> match = getWorld().getServer().getRecipeManager().getFirstMatch(
                        SpringWaterRecipe.Type.INSTANCE, new SimpleInventory(getStack()), getWorld()
                );

                if (match.isPresent()) {
                    SpringWaterRecipe recipe = match.get();
                    getStack().decrement(recipe.getInput().getCount());

                    ItemEntity result = new ItemEntity(getWorld(), getX(), getY(), getZ(), recipe.getOutput());
                    result.setNoGravity(true);
                    result.setGlowing(true);
                    result.setVelocity(0, 0.05f, 0);
                    getWorld().spawnEntity(result);

                    playSound(ZeldaSounds.SPRING_WATER_TRANSFORM, 1, 1);
                    this.legend_of_steve$springWaterTicks = 0;
                }
            }
        } else if (getWorld().isClient()) {
//            SparkParticleEffect.createSparks(getWorld(), SparkParticleEffect.SOUL, getPos().add(0, 0.25, 0), 2);
        }
    }
}
