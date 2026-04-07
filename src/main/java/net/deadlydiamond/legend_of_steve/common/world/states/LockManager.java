package net.deadlydiamond.legend_of_steve.common.world.states;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class LockManager {
    private final Map<BlockPos, LockedBlockEntry> lockedBlocks = new HashMap<>();

    public static boolean isUnlocked(World world, BlockPos pos) {
        if (world instanceof ServerWorld serverWorld) {
            return ZeldaPersistantStates.get(serverWorld).lockManager.lockedBlocks.get(pos) == null;
        }
        return true;
    }

    public static void unlockBlock(World world, BlockPos pos) {
        if (world instanceof ServerWorld serverWorld) {
            ZeldaPersistantStates.get(serverWorld).lockManager.lockedBlocks.remove(pos);
            ZeldaPersistantStates.get(serverWorld).markDirty();
        }
    }

    public static void lockBlock(World world, BlockPos pos, BlockState state, Item lock) {
        if (world instanceof ServerWorld serverWorld) {
            ZeldaPersistantStates.get(serverWorld).lockManager.lockedBlocks.putIfAbsent(pos, new LockedBlockEntry(state, lock));
            ZeldaPersistantStates.get(serverWorld).markDirty();
        }
    }

    // NBT STUFF ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public void writeNbt(NbtCompound nbt) {

        NbtCompound lockManager = new NbtCompound();
        NbtList entries = new NbtList();

        lockedBlocks.forEach((pos, lockedBlockEntry) -> {
            NbtCompound entry = new NbtCompound();
            entry.put("Pos", NbtHelper.fromBlockPos(pos));
            lockedBlockEntry.writeNbt(entry);
            entries.add(entry);
        });

        lockManager.put("Entries", entries);

        nbt.put("LockManager", lockManager);
    }

    public static LockManager fromNbt(NbtCompound nbt) {
        LockManager manager = new LockManager();

        NbtCompound lockManager = nbt.getCompound("LockManager");
        NbtList entries = lockManager.getList("Entries", NbtElement.COMPOUND_TYPE);


        for (int i = 0; i < entries.size(); i++) {
            NbtCompound entry = entries.getCompound(i);
            BlockPos pos = NbtHelper.toBlockPos(entry.getCompound("Pos"));
            manager.lockedBlocks.put(pos, LockedBlockEntry.fromNbt(entry));
        }

        return manager;
    }

    public record LockedBlockEntry(BlockState state, Item lock) {
        public void writeNbt(NbtCompound nbt) {
            NbtCompound entry = new NbtCompound();
            entry.put("State", NbtHelper.fromBlockState(state()));
            entry.put("Lock", lock().getDefaultStack().writeNbt(new NbtCompound()));
            nbt.put("LockedBlock", entry);
        }

        public static LockedBlockEntry fromNbt(NbtCompound nbt) {
            NbtCompound entry = nbt.getCompound("LockedBlock");
            BlockState state = NbtHelper.toBlockState(Registries.BLOCK.getReadOnlyWrapper(), entry.getCompound("State"));
            Item lock = ItemStack.fromNbt(entry.getCompound("Lock")).getItem();
            return new LockedBlockEntry(state, lock);
        }
    }
}
