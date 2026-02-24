package net.deadlydiamond.legend_of_steve.init.client;

import net.deadlydiamond.legend_of_steve.common.items.bag.QuiverItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

public class ZeldaModelPredicates {
    public static void register() {
        registerModelPredicates();
        registerTintables();
    }

    private static void registerModelPredicates() {
        // QUIVERS
        registerPredicateMulti(QuiverItem.QUIVERS, "filled", (stack, world, entity, seed) -> {
            if (stack.isItemBarVisible()) {
                List<ItemStack> stacks = QuiverItem.getItemStacks(stack);
                int i = 0;
                for (ItemStack itemStack : stacks) {
                    i += itemStack.getCount();
                    if (i >= 3) {
                        return 0.75f;
                    }
                }
                return i == 2 ? 0.5f : 0.25f;
            }
            return 0;
        });
    }

    private static void registerTintables() {
        registerTintedLeaves(ZeldaBlocks.DEKU_LEAVES, ZeldaBlocks.FRUITING_DEKU_LEAVES);
    }

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    private static void registerPredicateMulti(List<Item> items, String id, ClampedModelPredicateProvider provider) {
        items.forEach(item -> ModelPredicateProviderRegistry.register(item, new Identifier(id), provider));
    }

    private static void registerTintedLeaves(Block... items) {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? FoliageColors.getDefaultColor() : -1, items);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (tintIndex == 0) {
                if (world != null && pos != null) {
                    return BiomeColors.getFoliageColor(world, pos);
                }
                return FoliageColors.getDefaultColor();
            }
            return -1;
        }, items);
    }
}
