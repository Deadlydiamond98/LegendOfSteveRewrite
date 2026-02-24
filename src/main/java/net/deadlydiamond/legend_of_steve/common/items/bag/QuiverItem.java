package net.deadlydiamond.legend_of_steve.common.items.bag;

import com.google.common.collect.Multimap;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.util.ArmorItemUtil;
import net.deadlydiamond98.koalalib.common.items.vanillamodified.CustomBundleItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class QuiverItem extends CustomBundleItem implements Equipment {
    public static final List<Item> QUIVERS = new ArrayList<>();
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private final SoundEvent equipSound;

    public QuiverItem(Settings settings, int maxStorage, ArmorMaterial material, SoundEvent equipSound, TagKey<Item> acceptedItems) {
        super(settings, maxStorage, true, acceptedItems);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);

        int protection = material.getProtection(ArmorItem.Type.CHESTPLATE);
        protection = material == ArmorMaterials.NETHERITE ? (int) Math.round(protection * 0.75) : protection;
        this.attributeModifiers = ArmorItemUtil.createArmorAttributeModifiers(
                ArmorItem.Type.CHESTPLATE,
                protection,
                material.getToughness(),
                material.getKnockbackResistance()
        );
        this.equipSound = equipSound;

        QUIVERS.add(this);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return this.equipAndSwap(this, world, user, hand);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == this.getSlotType() ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    public static ItemStack getFilledQuiver(MobEntity mob, Random random, Item quiverItem) {
        ItemStack quiver = quiverItem.getDefaultStack();
        if (quiverItem instanceof QuiverItem) {
            addToBundle(quiver, new ItemStack(Items.ARROW, random.nextBetween(3, 17)));
            if (mob instanceof StrayEntity) {
                addTippedArrows(Potions.SLOWNESS, quiver, random);
            }
            if (mob instanceof PiglinEntity) {
                addToBundle(quiver, new ItemStack(Items.SPECTRAL_ARROW, random.nextBetween(0, 6)));
            }
        }
        return quiver;
    }

    protected static void addTippedArrows(Potion potion, ItemStack quiver, Random random) {
        ItemStack strayArrows = new ItemStack(Items.TIPPED_ARROW, random.nextBetween(0, 6));
        PotionUtil.setPotion(strayArrows, potion);
        addToBundle(quiver, strayArrows);
    }
}
