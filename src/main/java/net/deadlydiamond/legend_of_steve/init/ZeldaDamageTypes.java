package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ZeldaDamageTypes {
    public static final RegistryKey<DamageType> LOOT_POT = register("loot_pot_projectile");
    public static final RegistryKey<DamageType> QUESTION_BLOCK = register("question_block_hit");

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return of(world, null, key);
    }

    public static DamageSource of(World world, @Nullable Entity attacker, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), attacker);
    }

    private static RegistryKey<DamageType> register(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, LegendOfSteve.id(name));
    }
}
