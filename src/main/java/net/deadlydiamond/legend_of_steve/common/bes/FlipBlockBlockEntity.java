package net.deadlydiamond.legend_of_steve.common.bes;

import net.deadlydiamond.legend_of_steve.common.ZeldaProperties;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlipBlockBlockEntity extends BlockEntity {
    public static final int TURN_TIMER_MAX = 255;
    public static final int TURN_INCREMENT = 3;
    private int turnTimer = 0;

    public FlipBlockBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.FLIP_BLOCK, pos, state);
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, FlipBlockBlockEntity entity) {
        entity.tick(world, blockPos, state);
    }

    private void tick(World world, BlockPos blockPos, BlockState state) {
        if (state.get(ZeldaProperties.SPINNING)) {
            this.turnTimer += TURN_INCREMENT;

            if (this.turnTimer >= TURN_TIMER_MAX) {
                world.setBlockState(blockPos, state.with(ZeldaProperties.SPINNING, false));
                this.turnTimer = 0;
            }
        }
    }

    public float getTurnTimer(float tickDelta) {
        return this.turnTimer + (TURN_INCREMENT * tickDelta);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.turnTimer = nbt.getInt("turnTimer");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("turnTimer", this.turnTimer);
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}
