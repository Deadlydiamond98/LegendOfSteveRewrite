package net.deadlydiamond.legend_of_steve.init;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public class ZeldaToolMaterials {

    public static final ZeldaToolMaterial MAGIC_SWORD = new ZeldaToolMaterial(
            MiningLevels.IRON, 300, 6.0f, 2.0f, 15, () -> Ingredient.ofItems(Items.IRON_INGOT)
    );

    public static class ZeldaToolMaterial implements ToolMaterial {
        private final int miningLvl;
        private final int durability;
        private final float miningSpeed;
        private final float damage;
        private final int enchantability;
        private final Supplier<Ingredient> repairItems;

        private ZeldaToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
            this.miningLvl = miningLevel;
            this.durability = itemDurability;
            this.miningSpeed = miningSpeed;
            this.damage = attackDamage;
            this.enchantability = enchantability;
            this.repairItems = repairIngredient;
        }

        @Override
        public int getDurability() {
            return this.durability;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return this.miningSpeed;
        }

        @Override
        public float getAttackDamage() {
            return this.damage;
        }

        @Override
        public int getMiningLevel() {
            return this.miningLvl;
        }

        @Override
        public int getEnchantability() {
            return this.enchantability;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return this.repairItems.get();
        }
    }
}
