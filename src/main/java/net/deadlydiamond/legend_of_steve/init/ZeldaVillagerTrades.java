package net.deadlydiamond.legend_of_steve.init;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;

public class ZeldaVillagerTrades {
    public static void register() {
        registerWanderingTrader(2, 5, new ItemStack(ZeldaItems.BOMB_FLOWER, 4), 4, 1);
    }

    public static void registerWanderingTrader(int level, int cost, ItemStack offer, int maxUses, int exp) {
        TradeOfferHelper.registerWanderingTraderOffers(level, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, cost), offer, maxUses, exp, 0.1f
            ));
        });
    }
}
