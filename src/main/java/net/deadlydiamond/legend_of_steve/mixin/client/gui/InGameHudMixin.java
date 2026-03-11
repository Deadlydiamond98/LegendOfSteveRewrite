package net.deadlydiamond.legend_of_steve.mixin.client.gui;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.deadlydiamond.legend_of_steve.common.items.bag.BombBagItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;

    @WrapWithCondition(method = "renderHotbarItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;II)V"))
    private boolean legend_of_steve$renderHotbarItem(DrawContext instance, TextRenderer textRenderer, ItemStack stack, int x, int y) {
        if (stack.getItem() instanceof BombBagItem) {
            ItemStack bombStack = BombBagItem.getFirstStack(stack);
            if (!bombStack.isEmpty()) {
                legend_of_steve$drawBombBagItem(instance, this.client.textRenderer, stack, x, y, bombStack.getCount());
                return false;
            }
        }
        return true;
    }

    // Draws Item Slot things, but without the Item Bar, & with a count present
    @Unique
    private void legend_of_steve$drawBombBagItem(DrawContext instance, TextRenderer textRenderer, ItemStack stack, int x, int y, int count) {
        if (!stack.isEmpty()) {
            instance.getMatrices().push();

            String string = String.valueOf(count);
            instance.getMatrices().translate(0.0F, 0.0F, 200.0F);
            instance.drawText(textRenderer, string, x + 19 - 2 - textRenderer.getWidth(string), y + 6 + 3, 16777215, true);

            ClientPlayerEntity clientPlayerEntity = this.client.player;
            float f = clientPlayerEntity == null ? 0.0F : clientPlayerEntity.getItemCooldownManager().getCooldownProgress(stack.getItem(), this.client.getTickDelta());
            if (f > 0.0F) {
                int k = y + MathHelper.floor(16.0F * (1.0F - f));
                int l = k + MathHelper.ceil(16.0F * f);
                instance.fill(RenderLayer.getGuiOverlay(), x, k, x + 16, l, Integer.MAX_VALUE);
            }

            instance.getMatrices().pop();
        }
    }
}
