package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.items.projectile.BombItem;
import net.deadlydiamond.legend_of_steve.common.items.projectile.ChargedBombItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ZeldaItems {

    // BOMB ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item BOMB_FLOWER_SEEDS = register("bomb_flower_seeds", new BlockItem(ZeldaBlocks.BOMB_FLOWER, new FabricItemSettings()));

    public static final Item BOMB = register("bomb", new BombItem(new FabricItemSettings().maxCount(16), ZeldaEntityTypes.BOMB,  50, 3));
    public static final Item BOMB_FLOWER = register("bomb_flower", new BombItem(new FabricItemSettings().maxCount(16), ZeldaEntityTypes.BOMB,  50, 3));
    public static final Item CHARGED_BOMB = register("charged_bomb_flower", new ChargedBombItem(new FabricItemSettings().maxCount(16), ZeldaEntityTypes.BOMB,  50, 5));
    public static final Item SUPER_BOMB = register("super_bomb", new BombItem(new FabricItemSettings().maxCount(16), ZeldaEntityTypes.BOMB,  85, 5));

    // MATERIALS ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item BLUE_TEKTITE_CHITIN = register("blue_tektite_chitin", new Item(new FabricItemSettings()));
    public static final Item BLUE_TEKTITE_SHELL = register("blue_tektite_shell", new Item(new FabricItemSettings()));
    public static final Item RED_TEKTITE_CHITIN = register("red_tektite_chitin", new Item(new FabricItemSettings()));
    public static final Item RED_TEKTITE_SHELL = register("red_tektite_shell", new Item(new FabricItemSettings()));

    // FLUIDS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item ENCHANTED_SPRING_WATER_BUCKET = register("enchanted_spring_water_bucket", new BucketItem(
            ZeldaFluids.ENCHANTED_SPRING_WATER, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1))
    );

    // REGISTRATION ////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, LegendOfSteve.id(id), item);
    }

    public static void register() {}
}
