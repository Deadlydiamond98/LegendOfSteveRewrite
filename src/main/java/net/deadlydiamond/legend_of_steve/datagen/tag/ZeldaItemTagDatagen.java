package net.deadlydiamond.legend_of_steve.datagen.tag;

import net.deadlydiamond.legend_of_steve.common.blocksets.LockBlockset;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.wood.WoodVariantUtil;
import net.deadlydiamond98.koalalib.common.blocksets.AbstractBlockset;
import net.deadlydiamond98.koalalib.init.KoalaLibTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ZeldaItemTagDatagen extends FabricTagProvider.ItemTagProvider {

    public ZeldaItemTagDatagen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        // BLOCKSETS ///////////////////////////////////////////////////////////////////////////////////////////////////

        createItemTags(
                // DEKU WOOD
                ZeldaBlocks.DEKU_WOOD,
                // DUNGEONCITE
                ZeldaBlocks.BROWN_DUNGEONCITE,
                // CHISELED WOOD BRICKS
//                ZeldaBlocks.CHISELED_OAK_BRICKS,
//                ZeldaBlocks.CHISELED_BIRCH_BRICKS,
//                ZeldaBlocks.CHISELED_SPRUCE_BRICKS,
//                ZeldaBlocks.CHISELED_JUNGLE_BRICKS,
//                ZeldaBlocks.CHISELED_ACACIA_BRICKS,
//                ZeldaBlocks.CHISELED_DARK_OAK_BRICKS,
//                ZeldaBlocks.CHISELED_CRIMSON_BRICKS,
//                ZeldaBlocks.CHISELED_WARPED_BRICKS,
//                ZeldaBlocks.CHISELED_MANGROVE_BRICKS,
//                ZeldaBlocks.CHISELED_BAMBOO_BRICKS,
//                ZeldaBlocks.CHISELED_CHERRY_BRICKS,
//                ZeldaBlocks.CHISELED_DEKU_BRICKS,
                // TILES
                ZeldaBlocks.STONE_TILES,
                // FAIRY MARBLE
                ZeldaBlocks.FAIRY_MARBLE,
                ZeldaBlocks.COBBLED_FAIRY_MARBLE,
                ZeldaBlocks.POLISHED_FAIRY_MARBLE,
                ZeldaBlocks.FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS,
                ZeldaBlocks.FAIRY_MARBLE_TILES,
                // PERLITE
                ZeldaBlocks.PERLITE_BRICKS,
                // MASTER
                ZeldaBlocks.MASTER_PLATE,
                ZeldaBlocks.MASTER_BRICK,
                ZeldaBlocks.MASTER_TILE,
                // STRANGE DIRT
                ZeldaBlocks.STRANGE_DIRT,
                ZeldaBlocks.POLISHED_STRANGE_DIRT,
                ZeldaBlocks.STRANGE_DIRT_BRICKS,
                ZeldaBlocks.STRANGE_BLUE_DIRT,
                ZeldaBlocks.POLISHED_BLUE_STRANGE_DIRT,
                ZeldaBlocks.STRANGE_BLUE_DIRT_BRICKS,
                // TEKTILES
                ZeldaBlocks.RED_TEKTILES,
                ZeldaBlocks.SMALL_RED_TEKTILES,
                ZeldaBlocks.RED_TEKTILE_BRICKS,
                ZeldaBlocks.BLUE_TEKTILES,
                ZeldaBlocks.SMALL_BLUE_TEKTILES,
                ZeldaBlocks.BLUE_TEKTILE_BRICKS,
                // SWITCH BLOCKS
                ZeldaBlocks.RED_SWITCH_BLOCKS,
                ZeldaBlocks.BLUE_SWITCH_BLOCKS
        );

        // WOOD ////////////////////////////////////////////////////////////////////////////////////////////////////////

        WoodVariantUtil.generateWoodItemTags((tagKey, itemConvertible) -> getOrCreateTagBuilder(tagKey).add(itemConvertible.asItem()));
        ZeldaBlocks.CHISELED_PLANKS.forEach((woodVariant, block) -> {
            getOrCreateTagBuilder(ZeldaTags.CHISELED_PLANKS_ITEM).add(block.asItem());
            if (!woodVariant.isFlammable()) {
                getOrCreateTagBuilder(ItemTags.NON_FLAMMABLE_WOOD).add(block.asItem());
            }
        });

        getOrCreateTagBuilder(ItemTags.SAPLINGS).add(
                ZeldaBlocks.DEKU_SAPLING.asItem()
        );

        getOrCreateTagBuilder(ItemTags.LEAVES).add(
                ZeldaBlocks.DEKU_LEAVES.asItem(),
                ZeldaBlocks.FRUITING_DEKU_LEAVES.asItem()
        );

        // OTHER ///////////////////////////////////////////////////////////////////////////////////////////////////////

        getOrCreateTagBuilder(ZeldaTags.IRIDESCENT_ITEM).add(
                ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS.asItem(),
                ZeldaBlocks.CHISELED_FAIRY_MARBLE.asItem(),
                ZeldaBlocks.FAIRY_MARBLE_PILLAR.asItem(),
                ZeldaBlocks.SMOOTH_FAIRY_MARBLE.asItem(),
                ZeldaBlocks.FAIRY_MARBLE_SWORD_PEDESTAL.asItem()
        );

        addBlocksetsToTag(ZeldaTags.IRIDESCENT_ITEM,
                ZeldaBlocks.FAIRY_MARBLE_BRAZIER_BLOCKSET
        );

        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED).add(
                ZeldaItems.GILDED_BOMB_BAG,
                ZeldaItems.GILDED_QUIVER
        );

        getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS).add(
                ZeldaItems.MASTER_INGOT
        );

        getOrCreateTagBuilder(KoalaLibTags.PIGLIN_GOLD_ARMOR).add(
                ZeldaItems.GILDED_QUIVER
        );

        getOrCreateTagBuilder(ZeldaTags.BOMB_BAGS).add(
                ZeldaItems.BOMB_BAG,
                ZeldaItems.GILDED_BOMB_BAG,
                ZeldaItems.NETHERITE_BOMB_BAG
        );

        getOrCreateTagBuilder(ZeldaTags.QUIVERS).add(
                ZeldaItems.QUIVER,
                ZeldaItems.GILDED_QUIVER,
                ZeldaItems.NETHERITE_QUIVER
        );

        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(
                ZeldaItems.MUSIC_DISC_LEGEND,
                ZeldaItems.MUSIC_DISC_ODD_SANCTUARY
        );

        getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS).add(
                ZeldaBlocks.SILENT_PRINCESS.asItem()
        );

        // LOOT POTS
        addBlocksetsToTag(ZeldaTags.LOOT_POTS, ZeldaBlocks.DYED_LOOT_POTS);
        getOrCreateTagBuilder(ZeldaTags.LOOT_POTS).add(ZeldaBlocks.LOOT_POT.asItem());

        // STRANGE DIRT
        getOrCreateTagBuilder(ZeldaTags.STRANGE_DIRT).add(
                ZeldaBlocks.STRANGE_DIRT.base.asItem(),
                ZeldaBlocks.POLISHED_STRANGE_DIRT.base.asItem(),
                ZeldaBlocks.STRANGE_DIRT_BRICKS.base.asItem(),
                ZeldaBlocks.REINFORCED_STRANGE_DIRT.base.asItem(),
                ZeldaBlocks.STRANGE_DIRT_PILLAR.asItem()
        );

        getOrCreateTagBuilder(ZeldaTags.STRANGE_BLUE_DIRT).add(
                ZeldaBlocks.STRANGE_BLUE_DIRT.base.asItem(),
                ZeldaBlocks.POLISHED_BLUE_STRANGE_DIRT.base.asItem(),
                ZeldaBlocks.STRANGE_BLUE_DIRT_BRICKS.base.asItem(),
                ZeldaBlocks.REINFORCED_STRANGE_BLUE_DIRT.base.asItem(),
                ZeldaBlocks.STRANGE_BLUE_DIRT_PILLAR.asItem()
        );

        // SWITCH BLOCKS

        getOrCreateTagBuilder(ZeldaTags.SWITCH_BLOCKS_ITEM).add(
                ZeldaBlocks.CRYSTAL_SWITCH.asItem()
        );

        // LOCKS & KEYS
        createItemTags(ZeldaBlocks.LOCKS.toArray(LockBlockset[]::new));
        getOrCreateTagBuilder(ZeldaTags.KEYS)
                .addTag(ZeldaTags.COPPER_KEYS)
                .addTag(ZeldaTags.IRON_KEYS)
                .addTag(ZeldaTags.GOLD_KEYS)
                .addTag(ZeldaTags.BOSS_KEYS);

        getOrCreateTagBuilder(ZeldaTags.KEYS).add(
                ZeldaItems.CREATIVE_KEY,
                Items.TRIPWIRE_HOOK
        );
    }

    private void addBlocksetsToTag(TagKey<Item> tag, AbstractBlockset... blocksets) {
        for (AbstractBlockset blockset : blocksets) {
            for (Block block : blockset.getAll()) {
                getOrCreateTagBuilder(tag).add(block.asItem());
            }
        }
    }

    private void createItemTags(AbstractBlockset... blocksets) {
        for (AbstractBlockset blockset : blocksets) {
            blockset.generateItemTags((itemTagKey, itemConvertible) -> getOrCreateTagBuilder(itemTagKey).add(itemConvertible.asItem()));
        }
    }
}
