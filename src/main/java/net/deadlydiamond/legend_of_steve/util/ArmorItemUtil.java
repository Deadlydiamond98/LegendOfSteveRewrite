package net.deadlydiamond.legend_of_steve.util;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.UUID;

public class ArmorItemUtil {
    private static final EnumMap<ArmorItem.Type, UUID> MODIFIERS = Util.make(new EnumMap(ArmorItem.Type.class), uuidMap -> {
        uuidMap.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        uuidMap.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        uuidMap.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        uuidMap.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });

    public static Multimap<EntityAttribute, EntityAttributeModifier> createArmorAttributeModifiers(ArmorItem.Type armorType, double protection, double toughness, double knockbackResistance) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        UUID uUID = MODIFIERS.get(armorType);
        builder.put(
                EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uUID, "Armor modifier", protection, EntityAttributeModifier.Operation.ADDITION)
        );
        builder.put(
                EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                new EntityAttributeModifier(uUID, "Armor toughness", toughness, EntityAttributeModifier.Operation.ADDITION)
        );
        builder.put(
                EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                new EntityAttributeModifier(uUID, "Armor knockback resistance", knockbackResistance, EntityAttributeModifier.Operation.ADDITION)
        );
        return builder.build();
    }
}
