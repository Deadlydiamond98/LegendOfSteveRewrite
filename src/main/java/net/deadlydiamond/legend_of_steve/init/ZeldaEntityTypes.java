package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.BombEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;

public class ZeldaEntityTypes {

    public static final EntityType<BombEntity> BOMB = register("bomb", createMisc(BombEntity.class, 0.5f, 0.5f));

    // REGISTRATION METHODS ////////////////////////////////////////////////////////////////////////////////////////////

    public static <T extends Entity> EntityType<T> register(String name, FabricEntityTypeBuilder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, LegendOfSteve.id(name), builder.build());
    }

    public static <T extends Entity> FabricEntityTypeBuilder<T> createMon(Class<T> entityClass, float width, float height) {
        return create(SpawnGroup.MONSTER, entityClass, width, height);
    }

    public static <T extends Entity> FabricEntityTypeBuilder<T> createCre(Class<T> entityClass, float width, float height) {
        return create(SpawnGroup.CREATURE, entityClass, width, height);
    }

    public static <T extends Entity> FabricEntityTypeBuilder<T> createMisc(Class<T> entityClass, float width, float height) {
        return create(SpawnGroup.MISC, entityClass, width, height);
    }

    public static <T extends Entity> FabricEntityTypeBuilder<T> create(SpawnGroup group, Class<T> entityClass, float width, float height) {
        return FabricEntityTypeBuilder.create(group, factory(entityClass)).dimensions(EntityDimensions.fixed(width, height));
    }

    public static <T extends Entity> EntityType.EntityFactory<T> factory(Class<T> entityClass) {
        return (EntityType<T> type, World world) -> {
            try {
                return entityClass.getConstructor(EntityType.class, World.class).newInstance(type, world);
            } catch (Exception ignored) {
                return null;
            }
        };
    }

    public static void register() {}
}
