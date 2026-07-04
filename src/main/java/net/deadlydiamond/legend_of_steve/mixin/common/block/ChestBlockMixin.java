package net.deadlydiamond.legend_of_steve.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.LockedBlock;
import net.deadlydiamond.legend_of_steve.common.items.locking.LockItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.util.ChestLockUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {
    @WrapMethod(method = "onUse")
    private ActionResult legend_of_steve$onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, Operation<ActionResult> original) {
        if (state.isOf(Blocks.CHEST) || state.isOf(Blocks.TRAPPED_CHEST)) {
            ItemStack lock = ChestLockUtil.getLockItemForBlock(world.getBlockEntity(pos), state, world, pos);
            if (!lock.isEmpty()) {
                ItemStack key = player.getStackInHand(hand);
                if (legend_of_steve$isValidKey(lock, key) || key.isOf(ZeldaItems.CREATIVE_KEY)) {
                    if (!world.isClient) {
                        world.playSound(null, pos, ZeldaSounds.UNLOCK, SoundCategory.BLOCKS);
                        if (!player.isCreative()) {
                            player.getStackInHand(hand).decrement(1);
                        }
                        ChestLockUtil.setLockItemForBlock(world.getBlockEntity(pos), state, world, pos, ItemStack.EMPTY);
                        return ActionResult.SUCCESS;
                    }
                }
                return ActionResult.PASS;
            }
        }
        return original.call(state, world, pos, player, hand, hit);
    }

    @Unique
    private boolean legend_of_steve$isValidKey(ItemStack lock, ItemStack key) {
        return lock.getItem() instanceof LockItem lockItem && lockItem.getLockBlock() instanceof LockedBlock lockedBlock && key.isIn(lockedBlock.getKeyTag());
    }
}
