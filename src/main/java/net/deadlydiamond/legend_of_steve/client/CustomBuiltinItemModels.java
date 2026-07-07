package net.deadlydiamond.legend_of_steve.client;

import net.deadlydiamond.legend_of_steve.common.bes.container.chest.DungeonChestBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.container.chest.TrappedDungeonChestBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CustomBuiltinItemModels {
    public static Map<Block, BlockEntity> BUILTIN_ITEM_MODELS = new HashMap<>();

    static {
        addBuiltinModel(ZeldaBlocks.RED_DUNGEON_CHEST, DungeonChestBlockEntity::new);
        addBuiltinModel(ZeldaBlocks.BLUE_DUNGEON_CHEST, DungeonChestBlockEntity::new);
        addBuiltinModel(ZeldaBlocks.TRAPPED_RED_DUNGEON_CHEST, TrappedDungeonChestBlockEntity::new);
        addBuiltinModel(ZeldaBlocks.TRAPPED_BLUE_DUNGEON_CHEST, TrappedDungeonChestBlockEntity::new);
    }

    public static void addBuiltinModel(Block block, BiFunction<BlockPos, BlockState, BlockEntity> blockEntityFunction) {
        BUILTIN_ITEM_MODELS.put(block, blockEntityFunction.apply(BlockPos.ORIGIN, block.getDefaultState()));
    }
}
