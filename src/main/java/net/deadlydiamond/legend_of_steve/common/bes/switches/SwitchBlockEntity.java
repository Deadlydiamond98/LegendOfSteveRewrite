package net.deadlydiamond.legend_of_steve.common.bes.switches;

import net.deadlydiamond.legend_of_steve.common.bes.grouping.BoundGroupBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.switches.ISwitchBlock;
import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.networking.s2c.switches.SwitchToggleS2CPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SwitchBlockEntity extends BoundGroupBlockEntity {
    public boolean firstTick = true;
    private boolean updateMesh = true;
    protected int triggerCooldown;
    private boolean isOn;

    public SwitchBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.SWITCH_BLOCK, pos, state);
    }
    
    protected SwitchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static <T extends SwitchBlockEntity> void tick(World world, BlockPos pos, BlockState state, T entity) {
        entity.tick(world, pos, state);
    }

    public void init(World world, BlockPos pos, BlockState state) {
        syncSwitchState();
        updateListeners();
        this.firstTick = false;
    }

    protected void tick(World world, BlockPos pos, BlockState state) {
        if (this.firstTick) {
            init(world, pos, state);
            this.firstTick = false;
        }

        if (this.updateMesh) {
            if (getWorld().isClient()) {
                world.updateListeners(pos, null, null, 0);
            }
            this.updateMesh = false;
        }

        syncSwitchState();
        this.triggerCooldown--;
    }

    protected void syncSwitchState() {
        if (!getWorld().isClient()) {
            boolean bl = isInverted() != getWorldOnState();

            if (bl != isOn()) {
                this.setIsOn(bl);
                if (getCachedState().getBlock() instanceof ISwitchBlock switchBlock) {
                    switchBlock.onSwitchTriggered(getWorld(), getPos(), getCachedState(), this, bl);

                    getWorld().getPlayers().forEach(player -> {
                        if (player.squaredDistanceTo(pos.toCenterPos()) <= 100) {
                            SwitchToggleS2CPacket.send(player, getPos(), bl);
                        }
                    });
                }
            }
        }
    }

    // World On State //////////////////////////////////////////////////////////////////////////////////////////////////

    public void triggerSwitch() {
        triggerSwitch(0);
    }

    public void triggerSwitch(int cooldown) {
        if (getTriggerCooldown() <= 0) {
            SwitchBlockManager.trigger(getWorld(), getGroupID());
            setTriggerCooldown(cooldown);
        }
    }

    public boolean getWorldOnState() {
        return SwitchBlockManager.get(getWorld(), getGroupID());
    }

    // Getters & Setters ///////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isOn() {
        return this.isOn;
    }

    protected void setIsOn(boolean isOn) {
        this.isOn = isOn;
        this.updateListeners();
    }

    public boolean isInverted() {
        return getCachedState().getBlock() instanceof ISwitchBlock switchBlock && !switchBlock.startOn();
    }

    public void setTriggerCooldown(int cooldown) {
        this.triggerCooldown = cooldown;
    }

    public int getTriggerCooldown() {
        return this.triggerCooldown;
    }

    // NBT /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.isOn = nbt.getBoolean("IsCrystalSwitchOn");
        this.updateMesh = nbt.getBoolean("UpdateMesh");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("IsCrystalSwitchOn", this.isOn);
        nbt.putBoolean("UpdateMesh", this.updateMesh);
    }

    // MISC ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    protected void updateListeners() {
        this.updateMesh = true;
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public @Nullable Object getRenderData() {
        return this.isOn();
    }
}
