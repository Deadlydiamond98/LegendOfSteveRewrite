package net.deadlydiamond.legend_of_steve.util.entity;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A Modified Version of the FabricEntityTypeBuilder that adds some things that I wanted
 */

public class ZeldaEntityTypeBuilder<T extends Entity> extends FabricEntityTypeBuilder<T> {
    private final Class<T> usedEntityClass;

    // Attribute
    @Nullable
    private Supplier<DefaultAttributeContainer.Builder> defaultAttributeBuilder;

    // Spawn Restriction
    private SpawnRestriction.Location restrictionLocation;
    private Heightmap.Type restrictionHeightmap;
    private SpawnRestriction.SpawnPredicate<T> spawnPredicate;

    protected ZeldaEntityTypeBuilder(SpawnGroup spawnGroup, Class<T> entityClass) {
        super(spawnGroup, factory(entityClass));
        this.usedEntityClass = entityClass;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CUSTOM METHODS //////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ZeldaEntityTypeBuilder<T> dimensions(float width, float height) {
        return this.dimensions(EntityDimensions.fixed(width, height));
    }

    public ZeldaEntityTypeBuilder<T> dimensions(float size) {
        return this.dimensions(EntityDimensions.fixed(size, size));
    }

    public ZeldaEntityTypeBuilder<T> defaultAttributes(Supplier<DefaultAttributeContainer.Builder> defaultAttributeBuilder) {
        this.defaultAttributeBuilder = defaultAttributeBuilder;
        return this;
    }

    public ZeldaEntityTypeBuilder<T> spawnRestriction(SpawnRestriction.Location location, Heightmap.Type heightmap, SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        this.restrictionLocation = Objects.requireNonNull(location, "Location cannot be null.");
        this.restrictionHeightmap = Objects.requireNonNull(heightmap, "Heightmap type cannot be null.");
        this.spawnPredicate = Objects.requireNonNull(spawnPredicate, "Spawn predicate cannot be null.");
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // BUILD METHODS ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    @SuppressWarnings("unchecked")
    public EntityType<T> build() {
        final EntityType<T> type = super.build();

        if (LivingEntity.class.isAssignableFrom(this.usedEntityClass)) {
            boolean isMob = MobEntity.class.isAssignableFrom(this.usedEntityClass);
            if (isMob) {
                buildMob((EntityType<MobEntity>) type, (SpawnRestriction.SpawnPredicate<MobEntity>)this.spawnPredicate);
            }
            buildLiving((EntityType<LivingEntity>) type, isMob);
        }

        return type;
    }

    private <N extends LivingEntity> void buildLiving(EntityType<N> type, boolean isMob) {
        if (this.defaultAttributeBuilder == null) {
            this.defaultAttributeBuilder = getDefaultAttributes(isMob);
        }
        FabricDefaultAttributeRegistry.register(type, this.defaultAttributeBuilder.get());
    }

    private <N extends MobEntity> void buildMob(EntityType<N> type, SpawnRestriction.SpawnPredicate<N> spawnPredicate) {
        if (this.spawnPredicate != null) {
            SpawnRestriction.register(type, this.restrictionLocation, this.restrictionHeightmap, spawnPredicate);
        }
    }

    private Supplier<DefaultAttributeContainer.Builder> getDefaultAttributes(boolean isMob) {
        try {
            Method attributeMethod = this.usedEntityClass.getMethod("createCustomAttributes");
            Object result = attributeMethod.invoke(null);
            if (result instanceof DefaultAttributeContainer.Builder builder) {
                return () -> builder;
            }
        } catch (Exception e) {
            LegendOfSteve.LOGGER.info("createCustomAttributes() wasn't found for {}, {}", this.usedEntityClass.getName(), e);
        }

        if (isMob) {
            return MobEntity::createMobAttributes;
        } else {
            return LivingEntity::createLivingAttributes;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // INIT METHODS ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static <T extends Entity> ZeldaEntityTypeBuilder<T> create(Class<T> entityClass) {
        return new ZeldaEntityTypeBuilder<>(SpawnGroup.MISC, entityClass);
    }

    public static <T extends Entity> ZeldaEntityTypeBuilder<T> create(SpawnGroup spawnGroup, Class<T> entityClass) {
        return new ZeldaEntityTypeBuilder<>(spawnGroup, entityClass);
    }

    private static <T extends Entity> EntityType.EntityFactory<T> factory(Class<T> entityClass) {
        return (EntityType<T> type, World world) -> {
            try {
                return entityClass.getConstructor(EntityType.class, World.class).newInstance(type, world);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // OVERRIDDEN METHODS //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public ZeldaEntityTypeBuilder<T> spawnGroup(SpawnGroup group) {
        super.spawnGroup(group);
        return this;
    }

    @Override
    public <N extends T> FabricEntityTypeBuilder<N> entityFactory(EntityType.EntityFactory<N> factory) {
        throw new RuntimeException("ZeldaEntityTypeBuilder.entityFactory() should never be used");
    }

    @Override
    public ZeldaEntityTypeBuilder<T> disableSummon() {
        super.disableSummon();
        return this;
    }

    @Override
    public ZeldaEntityTypeBuilder<T> disableSaving() {
        super.disableSaving();
        return this;
    }

    @Override
    public ZeldaEntityTypeBuilder<T> fireImmune() {
        super.fireImmune();
        return this;
    }

    @Override
    public ZeldaEntityTypeBuilder<T> spawnableFarFromPlayer() {
        super.spawnableFarFromPlayer();
        return this;
    }

    @Override
    public ZeldaEntityTypeBuilder<T> dimensions(EntityDimensions dimensions) {
        super.dimensions(dimensions);
        return this;
    }

    /**
     * @deprecated use {@link ZeldaEntityTypeBuilder#trackRangeBlocks(int)}, {@link ZeldaEntityTypeBuilder#trackedUpdateRate(int)} and {@link ZeldaEntityTypeBuilder#forceTrackedVelocityUpdates(boolean)}
     */
    @Override
    @Deprecated
    public ZeldaEntityTypeBuilder<T> trackable(int trackRangeBlocks, int trackedUpdateRate) {
        super.trackable(trackRangeBlocks, trackedUpdateRate);
        return this;
    }

    /**
     * @deprecated use {@link ZeldaEntityTypeBuilder#trackRangeBlocks(int)}, {@link ZeldaEntityTypeBuilder#trackedUpdateRate(int)} and {@link ZeldaEntityTypeBuilder#forceTrackedVelocityUpdates(boolean)}
     */
    @Override
    @Deprecated
    public ZeldaEntityTypeBuilder<T> trackable(int trackRangeBlocks, int trackedUpdateRate, boolean forceTrackedVelocityUpdates) {
        super.trackable(trackRangeBlocks, trackedUpdateRate, forceTrackedVelocityUpdates);
        return this;
    }

    @Override
    public ZeldaEntityTypeBuilder<T> trackRangeChunks(int range) {
        super.trackRangeChunks(range);
        return this;
    }

    @Override
    public ZeldaEntityTypeBuilder<T> trackRangeBlocks(int range) {
        super.trackRangeBlocks(range);
        return this;
    }

    @Override
    public ZeldaEntityTypeBuilder<T> trackedUpdateRate(int rate) {
        super.trackedUpdateRate(rate);
        return this;
    }

    @Override
    public ZeldaEntityTypeBuilder<T> forceTrackedVelocityUpdates(boolean forceTrackedVelocityUpdates) {
        super.forceTrackedVelocityUpdates(forceTrackedVelocityUpdates);
        return this;
    }

    @Override
    public ZeldaEntityTypeBuilder<T> specificSpawnBlocks(Block... blocks) {
        super.specificSpawnBlocks(blocks);
        return this;
    }
}
