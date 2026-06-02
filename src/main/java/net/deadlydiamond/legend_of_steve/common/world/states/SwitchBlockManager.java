package net.deadlydiamond.legend_of_steve.common.world.states;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class SwitchBlockManager extends PersistentState {
    private final Map<String, Boolean> switchGroups = new HashMap<>();

    public SwitchBlockManager() {
        this.markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound switchNBT = new NbtCompound();
        for (Map.Entry<String, Boolean> entry : switchGroups.entrySet()) {
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
                states.switchGroups.put(key, switchNBT.getBoolean(key));
            }
        }

        return states;
    }

    public static boolean get(World world, String key) {
        if (world instanceof ServerWorld serverWorld) {
            return getManager(serverWorld).get(key);
        }
        return true;
    }

    public static void set(World world, String key, boolean bl) {
        if (world instanceof ServerWorld serverWorld) {
            getManager(serverWorld).set(key, bl);
        }
    }

    public static void trigger(World world, String key) {
        if (world instanceof ServerWorld serverWorld) {
            getManager(serverWorld).trigger(key);
        }
    }

    public static SwitchBlockManager getManager(ServerWorld world) {
        PersistentStateManager manager = world.getServer().getOverworld().getPersistentStateManager();
        String id = "legend_of_steve:switch_blocks";
        return manager.getOrCreate(SwitchBlockManager::fromNbt, SwitchBlockManager::new, id);
    }

    public boolean get(String key) {
        switchGroups.putIfAbsent(key, true);
        return switchGroups.get(key);
    }

    public void set(String key, boolean bl) {
        switchGroups.put(key, bl);
        this.markDirty();
    }

    public void trigger(String key) {
        this.set(key, !get(key));
    }
}
