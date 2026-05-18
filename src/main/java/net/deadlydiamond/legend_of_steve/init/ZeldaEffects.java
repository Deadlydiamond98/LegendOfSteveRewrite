package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.effects.ZeldaStatusEffect;
import net.deadlydiamond.legend_of_steve.util.PotionSet;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ZeldaEffects {

    // EFFECTS /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final StatusEffect PONDSTRIDING = register("pondstriding", new ZeldaStatusEffect(StatusEffectCategory.BENEFICIAL, 0x48bfd5));
    public static final StatusEffect WATER_WEIGHT = register("water_weight", new ZeldaStatusEffect(StatusEffectCategory.NEUTRAL, 0x75a1a9));
    public static final StatusEffect HOTSTRIDING = register("hotstriding", new ZeldaStatusEffect(StatusEffectCategory.BENEFICIAL, 0xd58f48));

    // POTIONS /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final PotionSet PONDSTRIDING_POTIONS = PotionSet.of("pondstriding", PONDSTRIDING).withLong()
            .withRecipe(ZeldaItems.BLUE_TEKTITE_SHELL);

    public static final PotionSet WATER_WEIGHT_POTIONS = PotionSet.of("water_weight", WATER_WEIGHT).withLong()
            .withRecipe(PONDSTRIDING_POTIONS.regular, Items.IRON_INGOT);

    public static final PotionSet HOTSTRIDING_POTIONS = PotionSet.of("hotstriding", 900, HOTSTRIDING).withLong()
            .withRecipe(Potions.FIRE_RESISTANCE, ZeldaItems.RED_TEKTITE_SHELL);


    // REGISTRATION ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static StatusEffect register(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, LegendOfSteve.id(name), effect);
    }

    public static void register() {}
}
