package net.deadlydiamond.legend_of_steve.common.bes.locks;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.locks.lock.LockedBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class LockedBlockEntity extends BlockEntity implements ILockedBlockEntity {
    protected BlockState lockedBlock = Blocks.AIR.getDefaultState();
    protected NbtCompound wrappedNBT = new NbtCompound();

    public LockedBlockEntity(BlockPos pos, BlockState state) {
        super(ZeldaBlockEntities.LOCKED_BLOCK, pos, state);
    }

    @Override
    public BlockState getLockedBlock() {
        if (this.lockedBlock.getBlock() instanceof LockedBlock) {
            return Blocks.AIR.getDefaultState();
        }

        return this.lockedBlock;
    }

    @Override
    public void setLockedBlock(BlockState lockedBlock) {
        this.lockedBlock = lockedBlock;
        markDirty();
    }

    @Override
    public NbtCompound getWrappedNBT() {
        return this.wrappedNBT;
    }

    @Override
    public void setWrappedNBT(NbtCompound nbt) {
        this.wrappedNBT = nbt;
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        RegistryEntryLookup<Block> registryEntryLookup = this.world != null ?
                this.world.createCommandRegistryWrapper(RegistryKeys.BLOCK) :
                Registries.BLOCK.getReadOnlyWrapper();

        setLockedBlock(NbtHelper.toBlockState(registryEntryLookup, nbt.getCompound("LockedBlock")));
        setWrappedNBT(nbt.getCompound("WrappedNbt"));
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put("LockedBlock", NbtHelper.fromBlockState(getLockedBlock()));
        nbt.put("WrappedNbt", getWrappedNBT());
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Override
    public @Nullable Object getRenderData() {
        return this.lockedBlock;
    }
}
