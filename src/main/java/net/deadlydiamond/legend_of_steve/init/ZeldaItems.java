package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.items.EmeraldShardItem;
import net.deadlydiamond.legend_of_steve.common.items.FairyBottleItem;
import net.deadlydiamond.legend_of_steve.common.items.SwitchCore;
import net.deadlydiamond.legend_of_steve.common.items.locking.KeyItem;
import net.deadlydiamond.legend_of_steve.common.items.locking.LockItem;
import net.deadlydiamond.legend_of_steve.common.items.bag.BombBagItem;
import net.deadlydiamond.legend_of_steve.common.items.bag.QuiverItem;
import net.deadlydiamond.legend_of_steve.common.items.locking.OldKeyItem;
import net.deadlydiamond.legend_of_steve.common.items.locking.OldLockItem;
import net.deadlydiamond.legend_of_steve.common.items.projectile.DekuNutItem;
import net.deadlydiamond.legend_of_steve.common.items.projectile.explosive.BombItem;
import net.deadlydiamond.legend_of_steve.common.items.projectile.explosive.ChargedBombItem;
import net.deadlydiamond.legend_of_steve.common.items.projectile.explosive.WaterBombItem;
import net.deadlydiamond.legend_of_steve.common.items.sword.BeamSwordItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Rarity;

public class ZeldaItems {

    // SWORDS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item MAGIC_SWORD = register("magic_sword", new BeamSwordItem(ZeldaToolMaterials.MAGIC_SWORD, 3, -2.4F, new FabricItemSettings()));

    // BOMB ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item BOMB_FLOWER = register("bomb_flower", new BombItem(new FabricItemSettings().maxCount(16), ZeldaEntityTypes.BOMB,  50, 3));
    public static final Item CHARGED_BOMB_FLOWER = register("charged_bomb_flower", new ChargedBombItem(new FabricItemSettings().rarity(Rarity.RARE).maxCount(16), ZeldaEntityTypes.BOMB,  50, 5));
    public static final Item BOMB = register("bomb", new BombItem(new FabricItemSettings().maxCount(16), ZeldaEntityTypes.BOMB,  50, 3));
    public static final Item SUPER_BOMB = register("super_bomb", new BombItem(new FabricItemSettings().maxCount(16), ZeldaEntityTypes.BOMB,  85, 5));
    public static final Item WATER_BOMB = register("bombfish", new WaterBombItem(new FabricItemSettings().maxCount(16), false));
    public static final Item CHARGED_WATER_BOMB = register("charged_bombfish", new WaterBombItem(new FabricItemSettings().rarity(Rarity.RARE).maxCount(16), true));

    public static final Item DEKU_NUT = register("deku_nut", new DekuNutItem(new FabricItemSettings().maxCount(16)));

    // BAGS ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // BOMB BAG
    public static final Item BOMB_BAG = register("bomb_bag", new BombBagItem(new FabricItemSettings(), 80));
    public static final Item GILDED_BOMB_BAG = register("gilded_bomb_bag", new BombBagItem(new FabricItemSettings(), 160));
    public static final Item NETHERITE_BOMB_BAG = register("netherite_bomb_bag", new BombBagItem(new FabricItemSettings().fireproof(), 320));

    // QUIVER
    public static final Item QUIVER = registerQuiver("quiver", 160, ArmorMaterials.LEATHER, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    public static final Item GILDED_QUIVER = registerQuiver("gilded_quiver", 320, ArmorMaterials.LEATHER, SoundEvents.ITEM_ARMOR_EQUIP_GOLD);
    public static final Item NETHERITE_QUIVER = registerQuiver("netherite_quiver", 160, ArmorMaterials.NETHERITE, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE);

    // FOODS ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final Item PUMPKIN_SOUP = register("pumpkin_soup", new StewItem(new FabricItemSettings().maxCount(1).food(
            new FoodComponent.Builder().hunger(8).saturationModifier(0.6f).build()
    )));

    // MATERIALS ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // EMERALD SHARD
    public static final Item EMERALD_CHUNK = register("emerald_chunk", new EmeraldShardItem(
            new FabricItemSettings(), ZeldaSounds.EMERALD_SHARD_CONDENSE_FURTHER, Items.EMERALD
    ));
    public static final Item EMERALD_SHARD = register("emerald_shard", new EmeraldShardItem(
            new FabricItemSettings(), ZeldaSounds.EMERALD_SHARD_CONDENSE, EMERALD_CHUNK
    ));
    // TEKTITE CHITIN
    public static final Item BLUE_TEKTITE_CHITIN = register("blue_tektite_chitin", new Item(new FabricItemSettings()));
    public static final Item BLUE_TEKTITE_SHELL = register("blue_tektite_shell", new Item(new FabricItemSettings()));
    public static final Item RED_TEKTITE_CHITIN = register("red_tektite_chitin", new Item(new FabricItemSettings()));
    public static final Item RED_TEKTITE_SHELL = register("red_tektite_shell", new Item(new FabricItemSettings()));
    // MASTER ORE
    public static final Item RAW_MASTER_ORE = register("raw_master_ore", new Item(new FabricItemSettings()));
    public static final Item MASTER_SCRAP = register("master_scrap", new Item(new FabricItemSettings()));
    public static final Item MASTER_INGOT = register("master_ingot", new Item(new FabricItemSettings().fireproof()));
    // SWITCH CORE
    public static final Item SWITCH_CORE = register("switch_core", new SwitchCore(new FabricItemSettings().rarity(Rarity.RARE)));
    // FAIRY BOTTLE
    public static final Item FAIRY_BOTTLE = register("fairy_bottle", new FairyBottleItem(ZeldaEntityTypes.FAIRY));

    // FLUIDS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item SPRING_WATER_BUCKET = register("enchanted_spring_water_bucket", new BucketItem(
            ZeldaFluids.ENCHANTED_SPRING_WATER, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1))
    );

    // MUSIC DISCS /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item DISC_FRAGMENT_LEGEND = register("disc_fragment_legend", new DiscFragmentItem(new FabricItemSettings()));
    public static final Item MUSIC_DISC_LEGEND = register("music_disc_legend", new MusicDiscItem(
            16, ZeldaSounds.MUSIC_DISC_LEGEND, new FabricItemSettings().rarity(Rarity.RARE).maxCount(1), 86
    ));

    // PLANTS //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item BOMB_FLOWER_SEEDS = register("bomb_flower_seeds", new BlockItem(ZeldaBlocks.BOMB_FLOWER, new FabricItemSettings()));
    public static final Item SILENT_PRINCESS_BULB = register("silent_princess_bulb", new AliasedBlockItem(ZeldaBlocks.SILENT_PRINCESS_CROP, new FabricItemSettings()));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REGISTRATION ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Item registerBomb(String id, int fuse, float power) {
        return register(id, new BombItem(new FabricItemSettings().maxCount(16), ZeldaEntityTypes.BOMB, fuse, power));
    }

    public static Item registerQuiver(String id, int maxStorage, ArmorMaterial material, SoundEvent equipSound) {
        FabricItemSettings settings = new FabricItemSettings();
        return register(id, new QuiverItem(material == ArmorMaterials.NETHERITE ? settings.fireproof() : settings, maxStorage, material, equipSound));
    }

    public static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, LegendOfSteve.id(id), item);
    }

    public static void register() {
        CompostingChanceRegistry.INSTANCE.add(ZeldaItems.BOMB_FLOWER_SEEDS, 0.85f);
        CompostingChanceRegistry.INSTANCE.add(ZeldaItems.BOMB_FLOWER, 0.3f);
    }
}
