package net.deadlydiamond.legend_of_steve.common.blocks.functional;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BindableGroupBlock extends BlockWithEntity {
    public BindableGroupBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        tooltip.add(ScreenTexts.SPACE);
        tooltip.add(Text.translatable("tooltip.legend_of_steve.block_group").formatted(Formatting.GRAY));
        tooltip.add(ScreenTexts.space().append(Text.literal(getBlockGroup(stack))).formatted(Formatting.GREEN));
    }

    protected String getBlockGroup(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        return nbt.contains("blockGroupID") ? nbt.getString("blockGroupID") : "global";
    }

    protected void putBlockGroup(ItemStack stack, String blockGroup) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putString("blockGroupID", blockGroup);
        stack.setNbt(nbt);
    }
}
