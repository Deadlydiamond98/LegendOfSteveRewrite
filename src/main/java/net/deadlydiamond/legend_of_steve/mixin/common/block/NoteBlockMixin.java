package net.deadlydiamond.legend_of_steve.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.util.ZeldaNoteBlockSounds;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {

    @WrapOperation(method = "onSyncedBlockEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/sound/SoundCategory;FFJ)V"))
    private void legend_of_steve$onSyncedBlockEvent(World instance, @Nullable PlayerEntity player, double x, double y, double z, RegistryEntry<SoundEvent> soundEventRegistryEntry, SoundCategory category, float volume, float pitch, long l, Operation<Void> original, @Local BlockPos pos, @Local Instrument instrument) {
        SoundEvent customSound = ZeldaNoteBlockSounds.getCustomSound(instance, pos);
        if (!instrument.hasCustomSound() && customSound != null) {
            original.call(instance, player, x, y, z, RegistryEntry.of(customSound), category, volume, pitch, l);
        } else {
            original.call(instance, player, x, y, z, soundEventRegistryEntry, category, volume, pitch, l);
        }
    }
}
