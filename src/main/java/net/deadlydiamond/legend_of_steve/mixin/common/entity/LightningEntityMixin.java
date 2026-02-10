package net.deadlydiamond.legend_of_steve.mixin.common.entity;

import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.BombFlowerBlock;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.BombEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public class LightningEntityMixin {
    @Inject(method = "powerLightningRod", at = @At("TAIL"))
    private void legend_of_steve$powerLightningRod(CallbackInfo ci, @Local BlockPos blockPos) {
        LightningEntity entity = (LightningEntity) (Object) this;
        World world = entity.getWorld();

        BlockPos.iterateOutwards(blockPos.up(), 1, 1, 1).forEach(pos -> {
            BlockState state = world.getBlockState(pos);
            float chance = pos == blockPos.up() ? 0 : world.random.nextFloat();
            if (state.isOf(ZeldaBlocks.BOMB_FLOWER) && !state.hasRandomTicks() && chance <= 0.5) {
                world.setBlockState(pos, state.with(BombFlowerBlock.CHARGED, true));
            }
        });

        world.getEntitiesByClass(BombEntity.class, entity.getBoundingBox().expand(1), bombEntity -> true).forEach(bombEntity -> {
            bombEntity.setCharged(true);
            bombEntity.setPrimed(true);
        });
    }
}
