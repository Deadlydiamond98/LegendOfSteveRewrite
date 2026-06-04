package net.deadlydiamond.legend_of_steve.common.world.states;

import net.deadlydiamond.legend_of_steve.networking.s2c.switches.SyncSwitchBlocksS2CPacket;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.*;

public class SwitchBlockManager extends PersistentState {
    // This map is sent to the client for rendering (due to some de-syncing stuff)
    public static final Map<String, Boolean> SYNCED_SWITCH_GROUPS = new HashMap<>();
    public static final Set<BlockPos> SWITCH_BLOCK_POSITIONS = Collections.synchronizedSet(new HashSet<>());
    public static final Set<String> SWITCH_BLOCK_STRINGS = new HashSet<>();
    public static SwitchBlockManager INSTANCE = null;

    private final Map<String, Boolean> switchGroups = new HashMap<>();
    private final ServerWorld world;

    public SwitchBlockManager(ServerWorld world) {
        this.markDirty();
        this.world = world;
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

    private static SwitchBlockManager fromNbt(NbtCompound nbt, ServerWorld world) {
        SwitchBlockManager states = new SwitchBlockManager(world);

        if (nbt.contains("SwitchBlocks")) {
            NbtCompound switchNBT = nbt.getCompound("SwitchBlocks");
            for (String key : switchNBT.getKeys()) {
                boolean val = switchNBT.getBoolean(key);
                states.switchGroups.put(key, val);
            }
        }

        SyncSwitchBlocksS2CPacket.send(world, states.switchGroups);

        return states;
    }

    public static SwitchBlockManager getManager(ServerWorld world) {
        if (INSTANCE == null) {
            PersistentStateManager manager = world.getServer().getOverworld().getPersistentStateManager();
            String id = "legend_of_steve:switch_blocks";
            INSTANCE = manager.getOrCreate(
                    nbt -> SwitchBlockManager.fromNbt(nbt, world),
                    () -> new SwitchBlockManager(world), id
            );
        }
        return INSTANCE;
    }

    // STATIC GETTERS & SETTERS ////////////////////////////////////////////////////////////////////////////////////////

    public static boolean get(World world, String key) {
        if (world instanceof ServerWorld serverWorld) {
            return getManager(serverWorld).get(key);
        }
        return SYNCED_SWITCH_GROUPS.getOrDefault(key, true);
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

    // GETTERS & SETTERS ///////////////////////////////////////////////////////////////////////////////////////////////

    public boolean get(String key) {
        this.switchGroups.putIfAbsent(key, true);
        return this.switchGroups.get(key);
    }

    public void set(String key, boolean bl) {
        this.switchGroups.put(key, bl);
        this.markDirty();
        // When set, the switch Group is also updated on the Synced Switch Groups
        SYNCED_SWITCH_GROUPS.put(key, bl);
        SyncSwitchBlocksS2CPacket.send(this.world, key, bl);
    }

    public void trigger(String key) {
        this.set(key, !get(key));
    }

    public Map<String, Boolean> getAll() {
        return this.switchGroups;
    }

    // BLOCK POS STUFF /////////////////////////////////////////////////////////////////////////////////////////////////

    public static void reset() {
        SwitchBlockManager.SYNCED_SWITCH_GROUPS.clear();
        SwitchBlockManager.SWITCH_BLOCK_POSITIONS.clear();
        SwitchBlockManager.SWITCH_BLOCK_STRINGS.clear();
        SwitchBlockManager.INSTANCE = null;
    }

    public static void saveBlockPos(BlockPos pos) {
        if (!SWITCH_BLOCK_STRINGS.contains(pos.toString())) {
            SWITCH_BLOCK_POSITIONS.add(pos);
            SWITCH_BLOCK_STRINGS.add(pos.toString());
        }
    }

    public static void removePos(BlockPos pos) {
        SWITCH_BLOCK_POSITIONS.remove(pos);
        SWITCH_BLOCK_STRINGS.remove(pos.toString());
    }
}
