package net.deadlydiamond.legend_of_steve.mixin.common.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.common.items.IExtraCanMine;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public class ItemMixin {
    @ModifyReturnValue(method = "canMine", at = @At("RETURN"))
    private boolean legend_of_steve$canMine(boolean original, @Local(argsOnly = true) BlockState state, @Local(argsOnly = true) World world, @Local(argsOnly = true) BlockPos pos, @Local(argsOnly = true) PlayerEntity miner) {
        boolean bl = true;
        if (state.getBlock() instanceof IExtraCanMine miningInteraction) {
            return miningInteraction.canMineBlock(state, world, pos, miner);
        }

        return original && bl;
    }
}
