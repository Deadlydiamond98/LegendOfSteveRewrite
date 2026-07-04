package net.deadlydiamond.legend_of_steve.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.init.ZeldaDispenserBehaviors;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin {
    @WrapMethod(method = "getBehaviorForItem")
    private DispenserBehavior legend_of_steve$getBehaviorForItem(ItemStack stack, Operation<DispenserBehavior> original) {
        DispenserBehavior behavior = original.call(stack);
        if (stack.isIn(ZeldaTags.KEYS)) {
            return ZeldaDispenserBehaviors.key(behavior);
        }
        return behavior;
    }
}
