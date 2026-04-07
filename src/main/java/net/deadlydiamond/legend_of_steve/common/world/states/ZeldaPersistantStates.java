package net.deadlydiamond.legend_of_steve.common.world.states;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

public class ZeldaPersistantStates extends PersistentState {
    protected LockManager lockManager = new LockManager();

    public static ZeldaPersistantStates get(ServerWorld world) {
        return world.getServer().getOverworld().getPersistentStateManager().getOrCreate(
                ZeldaPersistantStates::fromNbt,
                ZeldaPersistantStates::new,
                LegendOfSteve.id("persistant_states").toString()
        );
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        this.lockManager.writeNbt(nbt);
        return nbt;
    }

    public static ZeldaPersistantStates fromNbt(NbtCompound nbt) {
        ZeldaPersistantStates states = new ZeldaPersistantStates();
        states.lockManager = LockManager.fromNbt(nbt);
        return states;
    }
}
