package net.deadlydiamond.legend_of_steve.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.common.blocks.IExplodedInteraction;
import net.deadlydiamond.legend_of_steve.common.blocks.secret.ISecretBlock;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.IZeldaBomb;
import net.deadlydiamond.legend_of_steve.common.world.BombExplosionBehavior;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    /**
     * Mixin Used to play the Secret Room Jingle when secret bricks are blown up.
     * This is done via Mixin so that it will only play the sound once.
     */

    @Unique private boolean legend_of_steve$playJingle;

    @Shadow @Final private World world;
    @Shadow @Final private ExplosionBehavior behavior;
    @Shadow @Final @Nullable private Entity entity;

    @Inject(method = "affectWorld",  at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onDestroyedByExplosion(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/explosion/Explosion;)V"))
    private void legend_of_steve$onDestroyedByExplosion(boolean particles, CallbackInfo ci, @Local Block block) {
        if (block instanceof ISecretBlock) {
            this.legend_of_steve$playJingle = true;
        }
    }

    @ModifyExpressionValue(method = "affectWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isAir()Z", ordinal = 0))
    private boolean legend_of_steve$isAir(boolean original, @Local Block block, @Local BlockPos blockPos) {
        if (block instanceof IExplodedInteraction interaction) {
            interaction.onExploded(this.world, blockPos, (Explosion) (Object) this, this.behavior instanceof BombExplosionBehavior);
        }
        return original;
    }

    @Inject(method = "affectWorld",  at = @At("TAIL"))
    private void legend_of_steve$affectWorld(boolean particles, CallbackInfo ci) {
        if (this.legend_of_steve$playJingle) {
            this.world.playSound(null, this.entity.getBlockPos(), ZeldaSounds.SECRET_ROOM_JINGLE, SoundCategory.BLOCKS);
        }
    }
}
