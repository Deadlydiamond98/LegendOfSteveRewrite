package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ZeldaCreativeTabs {
    // TODO: CHANGE THE CREATIVE TAB ICONS ONCE APPROPRIATE ITEMS ARE ADDED

    public static final ItemGroup ITEMS = registerTab("items", ZeldaItems.BOMB, (displayContext, entries) -> {
        add(entries,
                ZeldaItems.BOMB_FLOWER_SEEDS,
                ZeldaItems.BOMB_FLOWER,
                ZeldaItems.BOMB,
                ZeldaItems.CHARGED_BOMB,
                ZeldaItems.SUPER_BOMB,

                ZeldaItems.QUIVER,
                ZeldaItems.GILDED_QUIVER,
                ZeldaItems.NETHERITE_QUIVER,

                ZeldaItems.BLUE_TEKTITE_CHITIN,
                ZeldaItems.BLUE_TEKTITE_SHELL,
                ZeldaItems.RED_TEKTITE_CHITIN,
                ZeldaItems.RED_TEKTITE_SHELL,

                ZeldaItems.RAW_MASTER_ORE,
                ZeldaItems.MASTER_SCRAP,
                ZeldaItems.MASTER_INGOT
        );
    });

    public static final ItemGroup BLOCKS = registerTab("blocks", ZeldaBlocks.DEKU_LEAVES, (displayContext, entries) -> {
        // DEKU WOOD
        ZeldaBlocks.DEKU_WOOD.addToCreative(entries);
        entries.add(ZeldaBlocks.DEKU_SAPLING);
        entries.add(ZeldaBlocks.DEKU_LEAVES);
        entries.add(ZeldaBlocks.FRUITING_DEKU_LEAVES);

        // FAIRY LAMPS
        entries.add(ZeldaBlocks.RED_FAIRY_LAMP);
        entries.add(ZeldaBlocks.ORANGE_FAIRY_LAMP);
        entries.add(ZeldaBlocks.YELLOW_FAIRY_LAMP);
        entries.add(ZeldaBlocks.GREEN_FAIRY_LAMP);
        entries.add(ZeldaBlocks.BLUE_FAIRY_LAMP);
        entries.add(ZeldaBlocks.PURPLE_FAIRY_LAMP);
        entries.add(ZeldaBlocks.PINK_FAIRY_LAMP);

        // FAIRY MARBLE
        ZeldaBlocks.COBBLED_FAIRY_MARBLE.addToCreative(entries);
        ZeldaBlocks.FAIRY_MARBLE.addToCreative(entries);
        ZeldaBlocks.POLISHED_FAIRY_MARBLE.addToCreative(entries);
        ZeldaBlocks.FAIRY_MARBLE_BRICKS.addToCreative(entries);
        ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.addToCreative(entries);
        entries.add(ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS);
        entries.add(ZeldaBlocks.CHISELED_FAIRY_MARBLE);
        entries.add(ZeldaBlocks.FAIRY_MARBLE_PILLAR);
        entries.add(ZeldaBlocks.SMOOTH_FAIRY_MARBLE);
        ZeldaBlocks.FAIRY_MARBLE_TILES.addToCreative(entries);

        // MASTER
        entries.add(ZeldaBlocks.MASTER_ORE);
        entries.add(ZeldaBlocks.DEEPSLATE_MASTER_ORE);
        entries.add(ZeldaBlocks.MASTER_SCRAP_BLOCK);
        entries.add(ZeldaBlocks.MASTER_BLOCK);
        ZeldaBlocks.MASTER_PLATE.addToCreative(entries);
        entries.add(ZeldaBlocks.CUT_MASTER_PLATE);
        ZeldaBlocks.MASTER_BRICK.addToCreative(entries);
        ZeldaBlocks.MASTER_TILE.addToCreative(entries);
        entries.add(ZeldaBlocks.MASTER_PILLAR);
        entries.add(ZeldaBlocks.MASTER_BARS);
        entries.add(ZeldaBlocks.MASTER_CHAIN);
        entries.add(ZeldaBlocks.MASTER_DOOR);
        entries.add(ZeldaBlocks.MASTER_TRAPDOOR);
        entries.add(ZeldaBlocks.MASTER_GIRDER);
        entries.add(ZeldaBlocks.MASTER_BARREL);

        // TEKTILES
        ZeldaBlocks.BLUE_TEKTILES.addToCreative(entries);
        ZeldaBlocks.SMALL_BLUE_TEKTILES.addToCreative(entries);
        ZeldaBlocks.RED_TEKTILES.addToCreative(entries);
        ZeldaBlocks.SMALL_RED_TEKTILES.addToCreative(entries);
    });

    // HELPER //////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void add(ItemGroup.Entries entries, ItemConvertible... items) {
        for (ItemConvertible item : items) {
            entries.add(item);
        }
    }

    public static ItemGroup registerTab(String id, ItemConvertible icon, ItemGroup.EntryCollector entryCollector) {
        return Registry.register(Registries.ITEM_GROUP, LegendOfSteve.id(id), FabricItemGroup.builder()
                .displayName(Text.translatable("itemgroup." + LegendOfSteve.MOD_ID + "." + id))
                .icon(() -> icon.asItem().getDefaultStack())
                .entries(entryCollector).build());
    }

    public static void register() {}
}
