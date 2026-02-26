package net.deadlydiamond.legend_of_steve.common.bes;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

public class GlowingBlockEntity extends BlockEntity {
    public GlowingBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.GLOWING_BLOCK, pos, state);
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}
