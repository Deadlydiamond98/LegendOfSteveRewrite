package net.deadlydiamond.legend_of_steve.util;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PotionSet extends BrewingRecipeRegistry {

    public final Potion regular;
    public final @Nullable Potion stronger;
    public final @Nullable Potion longer;

    protected PotionSet(Potion regular, @Nullable Potion stronger, @Nullable Potion longer) {
        this.regular = regular;
        this.stronger = stronger;
        this.longer = longer;
    }

    public static PotionBuilder of(String name, StatusEffect... effects) {
        return of(name, 3600, 0, effects);
    }

    public static PotionBuilder of(String name, int time, StatusEffect... effects) {
        return of(name, time, 0, effects);
    }

    public static PotionBuilder of(String name, int time, int lvl, StatusEffect... effects) {
        return new PotionBuilder(name, time, lvl, false, effects);
    }

    public static PotionBuilder instant(String name, StatusEffect... effects) {
        return instant(name, 0, effects);
    }

    public static PotionBuilder instant(String name, int lvl, StatusEffect... effects) {
        return new PotionBuilder(name, 1, lvl, true, effects);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Recipe

    protected PotionSet addRecipes(Potion base, Item ingredient) {
        withRecipe(base, ingredient, this.regular);
        withRecipe(this.regular, Items.GLOWSTONE_DUST, this.stronger);
        withRecipe(this.regular, Items.REDSTONE, this.longer);
        return this;
    }

    public PotionSet withRecipe(Potion base, Item ingredient) {
        withRecipe(base, ingredient, this.regular);
        return this;
    }

    public PotionSet withRecipe(Item ingredient) {
        withRecipe(Potions.AWKWARD, ingredient);
        return this;
    }

    private void withRecipe(Potion base, Item ingredient, Potion result) {
        if (base != null && result != null) {
            BrewingRecipeRegistry.registerPotionRecipe(base, ingredient, result);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Registry

    protected static StatusEffectInstance[] getEffectsList(int time, int lvl, StatusEffect... effects) {
        List<StatusEffectInstance> effectInstances = new ArrayList<>();
        for (StatusEffect effect : effects) {
            effectInstances.add(new StatusEffectInstance(effect, time, lvl));
        }
        return effectInstances.toArray(new StatusEffectInstance[0]);
    }

    public static Potion register(String name, StatusEffectInstance... effects) {
        return Registry.register(Registries.POTION, LegendOfSteve.id(name), new Potion(effects));
    }

    public static class PotionBuilder {
        protected final String name;
        protected final int time;
        protected final int lvl;
        protected final StatusEffect[] effects;
        protected final boolean isInstant;

        private @Nullable Potion regular;
        private @Nullable Potion stronger;
        private @Nullable Potion longer;

        private PotionBuilder(String name, int time, int lvl, boolean isInstant, StatusEffect... effects) {
            this.regular = register(name, getEffectsList(time, lvl, effects));
            this.name = name;
            this.time = time;
            this.lvl = lvl;
            this.effects = effects.clone();
            this.isInstant = isInstant;
        }

        public PotionBuilder withStrong(int time, int lvl) {
            time = this.isInstant ? 1 : time;
            this.stronger = time > 0 ? register("strong_" + this.name, getEffectsList(time, lvl, this.effects)) : null;
            return this;
        }

        public PotionBuilder withStrong() {
            return withStrong((int)(this.time * 0.5), this.lvl + 1);
        }

        public PotionBuilder withLong(int time, int lvl) {
            if (!this.isInstant) {
                this.longer = time > 0 ? register("long_" + this.name, getEffectsList(time, lvl, this.effects)) : null;
            }
            return this;
        }

        public PotionBuilder withLong() {
            return withLong((int)(this.time * (8 / 3.0)), this.lvl);
        }

        public PotionBuilder withAll() {
            return withLong().withStrong();
        }

        public PotionSet create() {
            return new PotionSet(this.regular, this.stronger, this.longer);
        }

        public PotionSet withRecipe(Potion base, Item ingredient) {
            return create().addRecipes(base, ingredient);
        }

        public PotionSet withRecipe(Item ingredient) {
            return withRecipe(Potions.AWKWARD, ingredient);
        }
    }
}