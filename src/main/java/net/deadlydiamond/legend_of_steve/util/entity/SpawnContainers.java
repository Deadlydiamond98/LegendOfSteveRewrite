package net.deadlydiamond.legend_of_steve.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;

@SuppressWarnings("rawtypes")
public class SpawnContainers {

    public static <T extends Entity> SpawnContainer animal() {
        return ground(AnimalEntity::isValidNaturalSpawn);
    }

    public static <T extends Entity> SpawnContainer hostile() {
        return ground(HostileEntity::canSpawnInDark);
    }

    public static <T extends Entity> SpawnContainer ground(SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        return create(SpawnRestriction.Location.ON_GROUND, spawnPredicate);
    }

    public static <T extends Entity> SpawnContainer water(SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        return create(SpawnRestriction.Location.IN_WATER, spawnPredicate);
    }

    public static <T extends Entity> SpawnContainer create(SpawnRestriction.Location location, SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        return new SpawnContainer<>(location, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, spawnPredicate);
    }

    public static <T extends Entity> SpawnContainer create(SpawnRestriction.Location location, Heightmap.Type heightmap, SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        return new SpawnContainer<>(SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, spawnPredicate);
    }

    public record SpawnContainer<T extends Entity>(SpawnRestriction.Location location, Heightmap.Type heightmapType, SpawnRestriction.SpawnPredicate<T> predicate) {}
}
