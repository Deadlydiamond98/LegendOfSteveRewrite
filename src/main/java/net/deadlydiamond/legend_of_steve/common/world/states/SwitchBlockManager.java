package net.deadlydiamond.legend_of_steve.common.world.states;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.HashMap;
import java.util.Map;

public class SwitchBlockManager extends PersistentState {
    private static final Map<String, Boolean> SWITCH_GROUPS = new HashMap<>();

    public SwitchBlockManager() {
        this.markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound switchNBT = new NbtCompound();
        for (Map.Entry<String, Boolean> entry : SWITCH_GROUPS.entrySet()) {
            switchNBT.putBoolean(entry.getKey(), entry.getValue());
        }
        nbt.put("SwitchBlocks", switchNBT);

        return nbt;
    }

    private static SwitchBlockManager fromNbt(NbtCompound nbt) {
        SwitchBlockManager states = new SwitchBlockManager();

        if (nbt.contains("SwitchBlocks")) {
            NbtCompound switchNBT = nbt.getCompound("SwitchBlocks");
            for (String key : switchNBT.getKeys()) {
                SWITCH_GROUPS.put(key, switchNBT.getBoolean(key));
            }
        }

        return states;
    }

    public static SwitchBlockManager getManager(ServerWorld world) {
        PersistentStateManager manager = world.getServer().getOverworld().getPersistentStateManager();
        String id = "legend_of_steve:switch_blocks";
        return manager.getOrCreate(SwitchBlockManager::fromNbt, SwitchBlockManager::new, id);
    }

    public boolean get(String key) {
        SWITCH_GROUPS.putIfAbsent(key, true);
        return SWITCH_GROUPS.get(key);
    }

    public void set(String key, boolean bl) {
        SWITCH_GROUPS.put(key, bl);
        this.markDirty();
    }

    public void trigger(String key) {
        this.set(key, !get(key));
    }
}
