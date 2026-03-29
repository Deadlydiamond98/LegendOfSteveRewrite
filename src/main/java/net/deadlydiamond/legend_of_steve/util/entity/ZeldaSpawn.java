package net.deadlydiamond.legend_of_steve.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;

public record ZeldaSpawn(SpawnRestriction.Location location, Heightmap.Type heightmapType, SpawnRestriction.SpawnPredicate<?> predicate) {
    public static final ZeldaSpawn NONE = create(null, null, null);
    public static final ZeldaSpawn DEFAULT = ground(MobEntity::canMobSpawn);
    public static final ZeldaSpawn ANIMAL = ground(AnimalEntity::isValidNaturalSpawn);
    public static final ZeldaSpawn HOSTILE = ground(HostileEntity::canSpawnInDark);

    public static <T extends Entity> ZeldaSpawn ground(SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        return create(SpawnRestriction.Location.ON_GROUND, spawnPredicate);
    }

    public static <T extends Entity> ZeldaSpawn water(SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        return create(SpawnRestriction.Location.IN_WATER, spawnPredicate);
    }

    public static <T extends Entity> ZeldaSpawn lava(SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        return create(SpawnRestriction.Location.IN_LAVA, spawnPredicate);
    }

    public static <T extends Entity> ZeldaSpawn anywhere(SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        return create(SpawnRestriction.Location.NO_RESTRICTIONS, spawnPredicate);
    }

    public static <T extends Entity> ZeldaSpawn create(SpawnRestriction.Location location, SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        return new ZeldaSpawn(location, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, spawnPredicate);
    }

    public static <T extends Entity> ZeldaSpawn create(SpawnRestriction.Location location, Heightmap.Type heightmap, SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        return new ZeldaSpawn(SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, spawnPredicate);
    }

    public boolean isValid() {
        return predicate != null;
    }
}
