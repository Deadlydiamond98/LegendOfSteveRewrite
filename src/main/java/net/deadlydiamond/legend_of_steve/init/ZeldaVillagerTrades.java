package net.deadlydiamond.legend_of_steve.init;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ZeldaVillagerTrades {
    public static void register() {
        // FLETCHING
        registerFletchingTrade(3, 10, new ItemStack(ZeldaItems.QUIVER), 3, 10, 0.05f);
        // WANDERING TRADES
        registerWanderingTrade(2, 5, new ItemStack(ZeldaItems.BOMB_FLOWER, 4), 4, 1);
        registerWanderingTrade(1, 5, new ItemStack(ZeldaBlocks.DEKU_SAPLING), 8, 1);
    }

    public static void registerFletchingTrade(int level, int cost, ItemStack offer, int maxUses, int exp, float priceMultiplier) {
        registerTrade(VillagerProfession.FLETCHER, level, cost, offer, maxUses, exp, priceMultiplier);
    }

    public static void registerTrade(VillagerProfession profession, int level, int cost, ItemStack offer, int maxUses, int exp, float priceMultiplier) {
        TradeOfferHelper.registerVillagerOffers(profession, level, factories -> factories.add((entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, cost), offer, maxUses, exp, priceMultiplier
        )));
    }

    public static void registerWanderingTrade(int level, int cost, ItemStack offer, int maxUses, int exp) {
        TradeOfferHelper.registerWanderingTraderOffers(level, factories -> factories.add((entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, cost), offer, maxUses, exp, 0.1f
        )));
    }
}
