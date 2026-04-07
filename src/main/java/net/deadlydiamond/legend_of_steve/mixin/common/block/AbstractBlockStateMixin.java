package net.deadlydiamond.legend_of_steve.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.common.world.states.LockManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
//    @WrapMethod(method = "onUse")
//    private ActionResult legend_of_steve$onUse(World world, PlayerEntity player, Hand hand, BlockHitResult hit, Operation<ActionResult> original) {
//        BlockPos pos = hit.getBlockPos();
//        BlockState state = world.getBlockState(pos);
//
//        if (player.getStackInHand(hand).isOf(Items.DIAMOND)) {
//            LockManager.lockBlock(world, pos, state, player.getStackInHand(hand).getItem());
//            return ActionResult.SUCCESS;
//        } else if (player.getStackInHand(hand).isOf(Items.IRON_INGOT)) {
//            LockManager.unlockBlock(world, pos);
//            return ActionResult.SUCCESS;
//        }
//        return LockManager.isUnlocked(world, pos) ? original.call(world, player, hand, hit) : ActionResult.SUCCESS;
//    }
}
