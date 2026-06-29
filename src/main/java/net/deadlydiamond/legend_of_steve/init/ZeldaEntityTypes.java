package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.block.CrateEntity;
import net.deadlydiamond.legend_of_steve.common.entities.block.PushableBlockEntity;
import net.deadlydiamond.legend_of_steve.common.entities.living.fish.BombfishEntity;
import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.ArurodaEntity;
import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.BaseTektiteEntity;
import net.deadlydiamond.legend_of_steve.common.entities.living.fairy.FairyEntity;
import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.BlueTektiteEntity;
import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.RedTektiteEntity;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.DekuNutProjectileEntity;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.SwordBeamEntity;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.ThrownPotEntity;
import net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb.BombEntity;
import net.deadlydiamond.legend_of_steve.util.entity.ZeldaEntityTypeBuilder;
import net.deadlydiamond.legend_of_steve.util.entity.ZeldaSpawn;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ZeldaEntityTypes {
    public static final List<Item> SPAWN_EGGS = new ArrayList<>();

    // PROJECTILE ENTITIES /////////////////////////////////////////////////////////////////////////////////////////////
    public static final EntityType<BombEntity> BOMB = register("bomb", BombEntity.class, 0.5f);
    public static final EntityType<DekuNutProjectileEntity> DEKU_NUT = register("deku_nut", DekuNutProjectileEntity.class, 0.5f);
    public static final EntityType<SwordBeamEntity> SWORD_BEAM = register("sword_beam", SwordBeamEntity.class, 0.5f);
    public static final EntityType<ThrownPotEntity> THROWN_POT = register("thrown_pot", ThrownPotEntity.class, 0.5f);

    // LIVING ENTITIES /////////////////////////////////////////////////////////////////////////////////////////////////
    public static final EntityType<ArurodaEntity> ARURODA = registerMob("aruroda", ArurodaEntity.class, 0.9f,
            SpawnGroup.MONSTER, ArurodaEntity::attributes, ArurodaEntity.spawnRestriction(),
            0x0d0e25, 0x602182
    );
    public static final EntityType<BombfishEntity> BOMBFISH = registerMob("bombfish", BombfishEntity.class, 0.85f, 0.6f,
            SpawnGroup.MONSTER, BombfishEntity::attributes, BombfishEntity.spawnRestriction(),
            0x03747a, 0xbb2e2e
    );
    public static final EntityType<FairyEntity> FAIRY = registerMob("fairy", FairyEntity.class, 0.4f,
            SpawnGroup.AMBIENT, FairyEntity::attributes, FairyEntity.spawnRestriction(),
            0xffffff, 0x5d8fc2
    );
    public static final EntityType<BlueTektiteEntity> BLUE_TEKTITE = registerMob("blue_tektite", BlueTektiteEntity.class, 0.9f,
            SpawnGroup.MONSTER, BlueTektiteEntity::attributes, BaseTektiteEntity.spawnRestriction(),
            0x2e55b2, 0xf76e24
    );
    public static final EntityType<RedTektiteEntity> RED_TEKTITE = registerMob("red_tektite", RedTektiteEntity.class, 0.9f,
            SpawnGroup.MONSTER, RedTektiteEntity::attributes, BaseTektiteEntity.spawnRestriction(),
            0x831f4b, 0xf76e24
    );

    // MISC ENTITIES ///////////////////////////////////////////////////////////////////////////////////////////////////
    public static final EntityType<PushableBlockEntity> PUSHABLE_BLOCK = register("pushable_block", PushableBlockEntity.class, 1);
    public static final EntityType<CrateEntity> CRATE = register("crate", CrateEntity.class, 1);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REGISTRATION METHODS ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static <T extends MobEntity> EntityType<T> registerMob(
            String name, Class<T> entityClass, float size, SpawnGroup spawnGroup, Supplier<DefaultAttributeContainer.Builder> attributes,
            ZeldaSpawn spawn, int primaryColor, int secondaryColor
    ) {
        return registerMob(name, entityClass, size, size, spawnGroup, attributes, spawn, primaryColor, secondaryColor);
    }

    public static <T extends MobEntity> EntityType<T> registerMob(
            String name, Class<T> entityClass, float x, float y, SpawnGroup spawnGroup, Supplier<DefaultAttributeContainer.Builder> attributes,
            ZeldaSpawn spawn, int primaryColor, int secondaryColor
    ) {
        EntityType<T> type = register(name, builder(entityClass, x).dimensions(x, y).spawnGroup(spawnGroup).defaultAttributes(attributes).spawnRestriction(spawn));
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
