package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.living.fairy.FairyEntity;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.DekuNutProjectileEntity;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.ThrownPotEntity;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.BombEntity;
import net.deadlydiamond.legend_of_steve.util.entity.ZeldaEntityTypeBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class ZeldaEntityTypes {
    public static final List<Item> SPAWN_EGGS = new ArrayList<>();

    // PROJECTILE ENTITIES /////////////////////////////////////////////////////////////////////////////////////////////
    public static final EntityType<BombEntity> BOMB = register("bomb", BombEntity.class, 0.5f);
    public static final EntityType<DekuNutProjectileEntity> DEKU_NUT = register("deku_nut", DekuNutProjectileEntity.class, 0.5f);
    public static final EntityType<ThrownPotEntity> THROWN_POT = register("thrown_pot", ThrownPotEntity.class, 0.5f);

    // LIVING ENTITIES /////////////////////////////////////////////////////////////////////////////////////////////////
    public static final EntityType<FairyEntity> FAIRY = registerMob(
            "fairy", FairyEntity.class, 0.4f, SpawnGroup.AMBIENT, 0xffffff, 0x5d8fc2
    );

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REGISTRATION METHODS ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static <T extends MobEntity> EntityType<T> registerMonster(String name, Class<T> entityClass, float size, int primaryColor, int secondaryColor) {
        return registerMob(name, entityClass, size, SpawnGroup.MONSTER, primaryColor, secondaryColor);
    }

    public static <T extends MobEntity> EntityType<T> registerCreature(String name, Class<T> entityClass, float size, int primaryColor, int secondaryColor) {
        return registerMob(name, entityClass, size, SpawnGroup.CREATURE, primaryColor, secondaryColor);
    }

    public static <T extends MobEntity> EntityType<T> registerMob(String name, Class<T> entityClass, float size, SpawnGroup spawnGroup, int primaryColor, int secondaryColor) {
        EntityType<T> type = register(name, builder(entityClass, size).spawnGroup(spawnGroup));
        registerEgg(name, type, primaryColor, secondaryColor);
        return type;
    }

    public static <T extends Entity> EntityType<T> register(String name, Class<T> entityClass, float size) {
        return register(name, builder(entityClass, size));
    }

    public static <T extends Entity> EntityType<T> register(String name, ZeldaEntityTypeBuilder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, LegendOfSteve.id(name), builder.build());
    }

    public static <T extends MobEntity> void registerEgg(String name, EntityType<T> type, int primaryColor, int secondaryColor) {
        Item egg = ZeldaItems.register(name + "_spawn_egg", new SpawnEggItem(type, primaryColor, secondaryColor, new FabricItemSettings()));
        SPAWN_EGGS.add(egg);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> entries.add(egg));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // BUILDER HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static <T extends Entity> ZeldaEntityTypeBuilder<T> builder(Class<T> entity, float size) {
        return ZeldaEntityTypeBuilder.create(entity).dimensions(size);
    }

    public static void register() {}
}
