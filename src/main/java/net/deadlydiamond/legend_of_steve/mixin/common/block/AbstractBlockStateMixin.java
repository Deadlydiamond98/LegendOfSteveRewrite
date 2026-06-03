package net.deadlydiamond.legend_of_steve.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.common.items.locking.ContainerModifyingItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IBlockEntityLocking;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Shadow protected abstract BlockState asBlockState();

    // LOCKING BLOCKS //////////////////////////////////////////////////////////////////////////////////////////////////

    @Shadow public abstract boolean isAir();

    @Shadow public abstract Block getBlock();

    @WrapMethod(method = "calcBlockBreakingDelta")
    private float legend_of_steve$calcBlockBreakingDelta(PlayerEntity player, BlockView world, BlockPos pos, Operation<Float> original) {
        if (this.asBlockState().isIn(ZeldaTags.LOCKABLE) && world.getBlockEntity(pos) instanceof IBlockEntityLocking locking) {
            if (locking.legend_of_steve$getLockItem() != null && !locking.legend_of_steve$getLockItem().isEmpty()) {
                return -1;
            }
        }

        return original.call(player, world, pos);
    }

    @WrapMethod(method = "onUse")
    private ActionResult legend_of_steve$onUse(World world, PlayerEntity player, Hand hand, BlockHitResult hit, Operation<ActionResult> original) {
        if (this.asBlockState().isIn(ZeldaTags.LOCKABLE) && world.getBlockEntity(hit.getBlockPos()) instanceof IBlockEntityLocking locking) {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof ContainerModifyingItem item) {
                if (item.modifyContainer(this.asBlockState(), world, hit.getBlockPos(), player, hand, hit, locking)) {
                    return ActionResult.PASS;
                }
            }
        }

        return original.call(world, player, hand, hit);
    }
}
