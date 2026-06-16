package net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable;

import net.deadlydiamond.legend_of_steve.common.bes.grouping.BoundGroupBlockEntity;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.List;

public class BoundBlockUtil {
    public static final String DEFAULT = "Global";

    public static void addTooltip(ItemStack stack, List<Text> tooltip) {
        tooltip.add(ScreenTexts.SPACE);
        tooltip.add(Text.translatable("tooltip.legend_of_steve.block_group").formatted(Formatting.GRAY));
        tooltip.add(ScreenTexts.space().append(Text.literal(getBlockGroup(stack))).formatted(Formatting.GREEN));
    }

    public static String getBlockGroup(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains("BlockEntityTag")) {
            NbtCompound entityTag = nbt.getCompound("BlockEntityTag");
            if (entityTag.contains("blockGroupID")) {
                return entityTag.getString("blockGroupID");
            }
        }
        return DEFAULT;
    }

    public static void putBlockGroup(ItemStack stack, String blockGroup) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound entityTag = new NbtCompound();
        entityTag.putString("blockGroupID", blockGroup);
        nbt.put("BlockEntityTag", entityTag);
        stack.setNbt(nbt);
    }

    // APPLYING TO ITEM ////////////////////////////////////////////////////////////////////////////////////////////////

    public static ItemStack getCreativeEntry(ItemStack stack) {
        putBlockGroup(stack, DEFAULT);
        return stack;
    }

    public static ItemStack applyGroupOnPickStack(ItemStack stack, BlockView world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof BoundGroupBlockEntity boundBlockEntity) {
            putBlockGroup(stack, boundBlockEntity.getGroupID());
        }

        return stack;
    }

    // LOOT TABLE //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static LootTable.Builder lootTable(FabricBlockLootTableProvider lootTableProvider, Block drop) {
        if (drop instanceof SlabBlock) {
            return LootTable.builder().pool(
                    lootTableProvider.addSurvivesExplosionCondition(drop, LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                            .with(ItemEntry.builder(drop).apply(saveGroupLootFunction()).apply(slabLootFunction(drop))))
            );
        }

        return LootTable.builder().pool(
                lootTableProvider.addSurvivesExplosionCondition(drop, LootPool.builder().rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(drop).apply(saveGroupLootFunction())))
        );
    }

    private static LootFunction.Builder saveGroupLootFunction() {
        return CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                .withOperation("blockGroupID", "BlockEntityTag.blockGroupID");
    }

    private static LootFunction.Builder slabLootFunction(Block drop) {
        return SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))
                .conditionally(BlockStatePropertyLootCondition.builder(drop)
                        .properties(StatePredicate.Builder.create()
                                .exactMatch(SlabBlock.TYPE, SlabType.DOUBLE)));
    }
}
