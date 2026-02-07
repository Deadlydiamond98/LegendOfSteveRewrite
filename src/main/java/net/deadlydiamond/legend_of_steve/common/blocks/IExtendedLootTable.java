package net.deadlydiamond.legend_of_steve.common.blocks;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.block.Block.dropStack;

public interface IExtendedLootTable {
    default void dropCustomStacks(String lootTableID, BlockState state, World world, BlockPos pos) {
        if (world instanceof ServerWorld) {
            getCustomDroppedStacks(LegendOfSteve.id("blocks/" + lootTableID), state, (ServerWorld)world, pos, null).forEach(stack -> dropStack(world, pos, stack));
            state.onStacksDropped((ServerWorld)world, pos, ItemStack.EMPTY, true);
        }
    }

    default void dropCustomStacks(Identifier lootTableID, BlockState state, World world, BlockPos pos) {
        if (world instanceof ServerWorld) {
            getCustomDroppedStacks(lootTableID, state, (ServerWorld)world, pos, null).forEach(stack -> dropStack(world, pos, stack));
            state.onStacksDropped((ServerWorld)world, pos, ItemStack.EMPTY, true);
        }
    }

    default List<ItemStack> getCustomDroppedStacks(Identifier lootTableID, BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity) {
        LootTable lootTable = world.getServer().getLootManager().getLootTable(lootTableID);
        LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder(world)
                .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
                .add(LootContextParameters.TOOL, ItemStack.EMPTY)
                .addOptional(LootContextParameters.BLOCK_ENTITY, blockEntity)
                .add(LootContextParameters.BLOCK_STATE, state);
        LootContextParameterSet lootContextParameterSet = builder.build(LootContextTypes.BLOCK);

        return lootTable.generateLoot(lootContextParameterSet);
    }
}
