package net.deadlydiamond.legend_of_steve.common.bes;

import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrystalSwitchBlockEntity extends BlockEntity {
    public static final String DEFAULT_GROUP = "Global";

    public boolean firstTick = true;
    private boolean isOn;
    public int ticks;
    public float OrbYaw;
    protected int triggerCooldown;

    public CrystalSwitchBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.CRYSTAL_SWITCH, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, CrystalSwitchBlockEntity entity) {
        entity.tick(world, pos, blockState);
    }

    protected void tick(World world, BlockPos pos, BlockState blockState) {
        if (this.firstTick) {
            this.updateListeners();
            this.firstTick = false;
        }
        syncOnState();

        this.OrbYaw += 0.5f;
        this.OrbYaw %= 360;
        this.ticks++;
        this.triggerCooldown--;
    }

    public void syncOnState() {
        if (getWorld() instanceof ServerWorld serverWorld) {
            boolean isWorldOn = SwitchBlockManager.getManager(serverWorld).get(DEFAULT_GROUP);
            if (SwitchBlockManager.getManager(serverWorld).get(DEFAULT_GROUP) != this.isOn) {
                updateOnState(isWorldOn, false);
            }
        }
    }

    public void updateOnState(boolean isOn, boolean updatePersistentState) {
        if (getWorld() instanceof ServerWorld serverWorld) {
            if (updatePersistentState) {
                SwitchBlockManager.getManager(serverWorld).set(DEFAULT_GROUP, isOn);
            } else {
                this.isOn = isOn;
                this.updateListeners();
            }
        }
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setTriggerCooldown(int cooldown) {
        this.triggerCooldown = cooldown;
    }

    public int getTriggerCooldown() {
        return this.triggerCooldown;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.OrbYaw = nbt.getFloat("OrbYaw");
        this.isOn = nbt.getBoolean("IsOn");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putFloat("OrbYaw", this.OrbYaw);
        nbt.putBoolean("IsOn", this.isOn);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}
