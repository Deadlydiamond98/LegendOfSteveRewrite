package net.deadlydiamond.legend_of_steve.common.blocks.functional;

import net.deadlydiamond.legend_of_steve.common.bes.grouping.BoundGroupBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public interface IBindabledGroup {
    default void addBlockGroupTooltip(ItemStack stack, List<Text> tooltips) {
        tooltips.add(ScreenTexts.SPACE);
        tooltips.add(Text.translatable("tooltip.legend_of_steve.block_group").formatted(Formatting.GRAY));
        tooltips.add(ScreenTexts.space().append(Text.literal(getBlockGroup(stack))).formatted(Formatting.GREEN));
    }

    default void applyGroupOnPlaced(World world, BlockPos pos, BlockState state, ItemStack itemStack) {
        String group = getBlockGroup(itemStack);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof BoundGroupBlockEntity boundBlockEntity) {
            boundBlockEntity.setGroupID(group);
        }
    }

    default void applyGroupOnPickStack(ItemStack stack, BlockView world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof BoundGroupBlockEntity boundBlockEntity) {
            putBlockGroup(stack, boundBlockEntity.getGroupID());
        }
    }

    default void dropStackWithBlockGroup(World world, BlockPos pos, PlayerEntity player, ItemConvertible itemConvertible) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof BoundGroupBlockEntity boundBlockEntity && !player.isCreative()) {
            ItemStack stack = new ItemStack(itemConvertible);
            putBlockGroup(stack, boundBlockEntity.getGroupID());
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
    }

    default String getBlockGroup(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        return nbt.contains("blockGroupID") ? nbt.getString("blockGroupID") : "Global";
    }

    default void putBlockGroup(ItemStack stack, String blockGroup) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putString("blockGroupID", blockGroup);
        stack.setNbt(nbt);
    }
}
