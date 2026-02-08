package net.deadlydiamond.legend_of_steve.events.common;

import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class ZeldaLootTableEvents {
    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, identifier, builder, lootTableSource) -> {
            Identifier lootTable = LootTables.SNIFFER_DIGGING_GAMEPLAY;
            if (identifier.equals(lootTable)) {
                builder.modifyPools(builder1 -> {
                    builder1.with(ItemEntry.builder(ZeldaItems.BOMB_FLOWER_SEEDS));
                });
            }
        });
    }
}
