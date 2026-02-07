package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.plant.BombFlowerBlock;
import net.deadlydiamond.legend_of_steve.common.items.projectile.BombItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class ZeldaBlocks {
    // BLOCK SETTINGS //////////////////////////////////////////////////////////////////////////////////////////////////
    public static final FabricBlockSettings BOMB_FLOWER_SETTINGS = FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK).sounds(BlockSoundGroup.SPORE_BLOSSOM).nonOpaque().noCollision().breakInstantly();

    // PLANTS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Block BOMB_FLOWER = register("bomb_flower", new BombFlowerBlock(BOMB_FLOWER_SETTINGS), false);

    // REGISTRATION ////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Block register(String id, Block block) {
        return register(id, block, true);
    }

    public static Block register(String id, Block block, boolean withItem) {
        if (withItem) {
            ZeldaItems.register(id, new BlockItem(block, new FabricItemSettings()));
        }
        return Registry.register(Registries.BLOCK, LegendOfSteve.id(id), block);
    }

    public static void register() {}
}
