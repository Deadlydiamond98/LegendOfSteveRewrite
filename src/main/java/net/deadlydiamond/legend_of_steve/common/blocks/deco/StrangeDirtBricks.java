package net.deadlydiamond.legend_of_steve.common.blocks.deco;

import net.deadlydiamond.legend_of_steve.common.blocks.IJumpIntoAction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StrangeDirtBricks extends Block implements IJumpIntoAction {
    public StrangeDirtBricks(Settings settings) {
        super(settings);
    }

    @Override
    public void jumpIntoBlock(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof PlayerEntity player && player.getAbilities().flying) {
            return;
        }

        if (entity instanceof LivingEntity living) {
            if (!world.isClient() && living.hasStatusEffect(StatusEffects.STRENGTH)) {
                world.breakBlock(pos, false);
            }
        }
    }
}
