package net.deadlydiamond.legend_of_steve.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.common.blocks.IExplodedInteraction;
import net.deadlydiamond.legend_of_steve.common.blocks.secret.ISecretBlock;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.IZeldaBomb;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaExplosion;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Predicate;

@Mixin(Explosion.class)
public class ExplosionMixin implements IZeldaExplosion {

    // Used for Bomb Explosions & Explosion Interactions

    @Unique private boolean zeldacraft$playJingle;
    @Unique private boolean zeldacraft$zeldaBomb;
    @Unique private Predicate<Block> zeldacraft$breakableBlocks;

    @Shadow @Final private World world;
    @Shadow @Final @Nullable private Entity entity;

    @WrapOperation(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"))
    private List<Entity> legend_of_steve$collectBlocksAndDamageEntities(World instance, Entity entity, Box box, Operation<List<Entity>> original) {
        if (this.zeldacraft$zeldaBomb) {
            return List.of();
        }
        return original.call(instance, entity, box);
    }

    @Inject(method = "affectWorld",  at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onDestroyedByExplosion(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/explosion/Explosion;)V"))
    private void legend_of_steve$onDestroyedByExplosion(boolean particles, CallbackInfo ci, @Local Block block) {
        if (block instanceof ISecretBlock) {
            this.zeldacraft$playJingle = true;
        }
    }

    @ModifyExpressionValue(method = "affectWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isAir()Z", ordinal = 0))
    private boolean legend_of_steve$isAir(boolean original, @Local Block block, @Local BlockPos blockPos) {
        if (this.zeldacraft$zeldaBomb) {
            boolean validBlock = !this.zeldacraft$breakableBlocks.test(block);
            if (block instanceof IExplodedInteraction interaction) {
                interaction.onBombExploded(this.world, blockPos, (Explosion) (Object) this);
            }
            return validBlock;
        } else if (block instanceof IExplodedInteraction interaction) {
            interaction.onExploded(this.world, blockPos, (Explosion) (Object) this);
        }
        return original;
    }

    @Inject(method = "affectWorld",  at = @At("TAIL"))
    private void legend_of_steve$affectWorld(boolean particles, CallbackInfo ci) {
        if (this.zeldacraft$playJingle) {
            this.world.playSound(null, this.entity.getBlockPos(), ZeldaSounds.SECRET_ROOM_JINGLE, SoundCategory.BLOCKS);
        }
    }

    @ModifyReturnValue(method = "getCausingEntity", at = @At("RETURN"))
    private LivingEntity legend_of_steve$getCausingEntity(LivingEntity original) {
        if (this.entity instanceof IZeldaBomb bomb && bomb.getBombOwner() instanceof LivingEntity living) {
            return living;
        }
        return original;
    }

    @Override
    public void legend_of_steve$setZeldaBombOrigin(boolean bl, Predicate<Block> tag) {
        this.zeldacraft$zeldaBomb = bl;
        this.zeldacraft$breakableBlocks = tag;
    }
}
