package net.deadlydiamond.legend_of_steve.mixin.common.handler;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.deadlydiamond.legend_of_steve.common.items.IModifiedCraftingResult;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.CraftingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {

    @WrapOperation(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/CraftingRecipe;craft(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;"))
    private static ItemStack legend_of_steve$updateResult(CraftingRecipe instance, Inventory inventory, DynamicRegistryManager dynamicRegistryManager, Operation<ItemStack> original) {
        ItemStack stack = original.call(instance, inventory, dynamicRegistryManager);
        if (stack.getItem() instanceof IModifiedCraftingResult modifiedCraftingResult) {
            modifiedCraftingResult.modifyCraftingResult(stack, inventory);
        }
        return stack;
    }
}
