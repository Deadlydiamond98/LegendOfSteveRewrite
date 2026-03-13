package net.deadlydiamond.legend_of_steve.common.bes;

import net.deadlydiamond.legend_of_steve.client.rendering.block.baked.BakedBlockEntityRenderer;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GlowingBlockEntity extends BlockEntity {
    public boolean renderDirty = true;

    public GlowingBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.GLOWING_BLOCK, pos, state);
    }

    @Override
    public void markRemoved() {
        if (world != null && world.isClient) {
            BakedBlockEntityRenderer.Manager.markForRebuild(getPos());
        }
        super.markRemoved();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        renderDirty = true;
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
}
