package net.deadlydiamond.legend_of_steve.mixin.common;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.common.blocks.sign.ICustomSign;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @WrapMethod(method = "supports")
    private boolean legend_of_steve$supports(BlockState state, Operation<Boolean> original) {
        if ((BlockEntityType.SIGN.equals(this) || BlockEntityType.HANGING_SIGN.equals(this)) && state.getBlock() instanceof ICustomSign) {
            return true;
        }
        return original.call(state);
    }
}