package net.deadlydiamond.legend_of_steve.mixin.common.be;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.common.blocks.container.chest.DungeonChestBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin {
    @WrapMethod(method = "playSound")
    private static void legend_of_steve$playSound(World world, BlockPos pos, BlockState state, SoundEvent soundEvent, Operation<Void> original) {
        if (state.getBlock() instanceof DungeonChestBlock dungeonChestBlock) {
            if (soundEvent == SoundEvents.BLOCK_CHEST_OPEN) {
                soundEvent = dungeonChestBlock.getOpenSound();
            } else if (soundEvent == SoundEvents.BLOCK_CHEST_CLOSE) {
                soundEvent = dungeonChestBlock.getCloseSound();
            }
        }
        original.call(world, pos, state, soundEvent);
    }
}
