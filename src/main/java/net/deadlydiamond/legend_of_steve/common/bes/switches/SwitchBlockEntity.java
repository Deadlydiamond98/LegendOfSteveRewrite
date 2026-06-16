package net.deadlydiamond.legend_of_steve.common.bes.switches;

import net.deadlydiamond.legend_of_steve.common.bes.ILoadEvent;
import net.deadlydiamond.legend_of_steve.common.bes.grouping.BoundGroupBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.BoundBlockUtil;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.switches.ISwitchBlock;
import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.networking.s2c.switches.SwitchToggleS2CPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SwitchBlockEntity extends BoundGroupBlockEntity implements ILoadEvent {
    public boolean firstTick = true;
    protected int triggerCooldown;
    protected boolean isOn;

    public SwitchBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.SWITCH_BLOCK, pos, state);
    }
    
    protected SwitchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void init(World world, BlockPos pos, BlockState state, @Nullable ItemStack stack) {
        if (stack != null) {
            String groupID = BoundBlockUtil.getBlockGroup(stack);
            setGroupID(groupID);
        }

        SwitchBlockManager.sync(world, this.getGroupID());
        syncSwitchState();
        updateListeners();
        this.firstTick = false;
    }

    protected void syncSwitchState() {
        if (!getWorld().isClient()) {
            boolean bl = isInverted() != getWorldOnState();

            if (bl != isOn()) {
                this.setIsOn(bl);
                if (getCachedState().getBlock() instanceof ISwitchBlock switchBlock) {
                    switchBlock.onSwitchTriggered(getWorld(), getPos(), getCachedState(), this, bl);

                    getWorld().getPlayers().forEach(player -> {
                        SwitchToggleS2CPacket.send(player, getPos(), bl);
                    });
                }
            }
        }
    }

    // World On State //////////////////////////////////////////////////////////////////////////////////////////////////

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
        return isInverted() != getWorldOnState();
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

    // GROUP ID ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onLoad(World world) {
        SwitchBlockManager.sync(world, getGroupID());
    }

    // NBT /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.isOn = nbt.getBoolean("IsCrystalSwitchOn");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("IsCrystalSwitchOn", this.isOn);
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
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    @Nullable
    @Override
    public Object getRenderData() {
        return getGroupID();
    }
}
