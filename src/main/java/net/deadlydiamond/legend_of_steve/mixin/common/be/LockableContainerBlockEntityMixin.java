package net.deadlydiamond.legend_of_steve.mixin.common.be;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.deadlydiamond.legend_of_steve.networking.c2s.RequestChestLockedStateC2SPacket;
import net.deadlydiamond.legend_of_steve.networking.s2c.UpdateChestLockedStateS2CPacket;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IBlockEntityLocking;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LockableContainerBlockEntity.class)
public abstract class LockableContainerBlockEntityMixin implements IBlockEntityLocking {
    @Unique @Nullable private ItemStack legend_of_steve$lockItem;

    @WrapMethod(method = "checkUnlocked(Lnet/minecraft/entity/player/PlayerEntity;)Z")
    private boolean legend_of_steve$checkUnlocked(PlayerEntity player, Operation<Boolean> original) {
        return (this.legend_of_steve$lockItem == null || this.legend_of_steve$lockItem.isEmpty()) && original.call(player);
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void legend_of_steve$readNbt(NbtCompound nbt, CallbackInfo ci) {
        this.legend_of_steve$lockItem = ItemStack.fromNbt(nbt.getCompound("LegendOfSteveLockItem"));
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void legend_of_steve$writeNbt(NbtCompound nbt, CallbackInfo ci) {
        if (this.legend_of_steve$lockItem != null) {
            nbt.put("LegendOfSteveLockItem", this.legend_of_steve$lockItem.writeNbt(new NbtCompound()));
        }
    }

    @Override
    public ItemStack legend_of_steve$getLockItem() {
        LockableContainerBlockEntity blockEntity = (LockableContainerBlockEntity) (Object) this;

        if (blockEntity.getWorld() == null) {
            return ItemStack.EMPTY;
        }

        if (this.legend_of_steve$lockItem == null) {
            this.legend_of_steve$lockItem = ItemStack.EMPTY;
            if (blockEntity.getWorld().isClient()) {
                RequestChestLockedStateC2SPacket.send(blockEntity.getPos());
            }
        }

        return this.legend_of_steve$lockItem;
    }

    @Override
    public void legend_of_steve$setLockItem(ItemStack lock) {
        this.legend_of_steve$lockItem = lock;

        LockableContainerBlockEntity blockEntity = (LockableContainerBlockEntity) (Object) this;
        legend_of_steve$updateLockClient();
        blockEntity.markDirty();
    }

    @Unique
    private void legend_of_steve$updateLockClient() {
        LockableContainerBlockEntity blockEntity = (LockableContainerBlockEntity) (Object) this;
        if (blockEntity.getWorld() instanceof ServerWorld server) {
            server.getPlayers().forEach(player -> UpdateChestLockedStateS2CPacket.send(player, blockEntity.getPos(), this.legend_of_steve$lockItem));
        }
    }
}
