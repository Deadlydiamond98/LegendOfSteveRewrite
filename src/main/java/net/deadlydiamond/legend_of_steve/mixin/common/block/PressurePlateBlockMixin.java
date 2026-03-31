package net.deadlydiamond.legend_of_steve.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.deadlydiamond.legend_of_steve.common.entities.PushableBlockEntity;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PressurePlateBlock.class)
public class PressurePlateBlockMixin {
    @Shadow @Final private PressurePlateBlock.ActivationRule type;

    @WrapOperation(method = "getRedstoneOutput(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/PressurePlateBlock;getEntityCount(Lnet/minecraft/world/World;Lnet/minecraft/util/math/Box;Ljava/lang/Class;)I"))
    private int legend_of_steve$getRedstoneOutput(World world, Box box, Class aClass, Operation<Integer> original) {
        int entities = original.call(world, box, aClass);
        if (this.type == PressurePlateBlock.ActivationRule.MOBS) {
            entities += original.call(world, box, PushableBlockEntity.class);
        }
        return entities;
    }
}
