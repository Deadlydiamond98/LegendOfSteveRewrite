package net.deadlydiamond.legend_of_steve.common.bes.switches;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrystalSwitchBlockEntity extends SwitchBlockEntity {
    public float OrbYaw;
    public int ticks;

    public CrystalSwitchBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.CRYSTAL_SWITCH, pos, state);
    }

    public static <T extends CrystalSwitchBlockEntity> void tick(World world, BlockPos pos, BlockState state, T entity) {
        entity.tick(world, pos, state);
    }

    protected void tick(World world, BlockPos pos, BlockState state) {
        if (this.firstTick) {
            init(world, pos, state);
            this.firstTick = false;
        }

        this.OrbYaw += 0.5f;
        this.OrbYaw %= 360;
        this.ticks++;

        syncSwitchState(this.updateChunk);
        this.triggerCooldown--;
        this.updateChunk = false;
    }

    @Override
    public boolean isOn() {
        return this.isOn;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.OrbYaw = nbt.getFloat("OrbYaw");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putFloat("OrbYaw", this.OrbYaw);
    }
}
