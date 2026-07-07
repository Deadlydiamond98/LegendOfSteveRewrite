package net.deadlydiamond.legend_of_steve.mixin.client.rendering.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.CustomChestTextures;
import net.deadlydiamond.legend_of_steve.common.bes.locks.LockedChestBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Mixin(ChestBlockEntityRenderer.class)
public class ChestBlockEntityRendererMixin {
    @Unique private final Map<Block, BlockEntity> legend_of_steve$lockedBlockEntities = new HashMap<>();

    @WrapOperation(method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getChestTextureId(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;"))
    private SpriteIdentifier legend_of_steve$render(BlockEntity blockEntity, ChestType type, boolean christmas, Operation<SpriteIdentifier> original) {
        // LOCKED CHEST TEXTURE
        if (blockEntity instanceof LockedChestBlockEntity lockedBlockEntity && lockedBlockEntity.getLockedBlock().getBlock() instanceof BlockWithEntity blockWithEntity) {
            BlockEntity blockEntityCopy = this.legend_of_steve$lockedBlockEntities.get(blockWithEntity);
            if (blockEntityCopy == null) {
                blockEntityCopy = blockWithEntity.createBlockEntity(BlockPos.ORIGIN, lockedBlockEntity.getLockedBlock());
                this.legend_of_steve$lockedBlockEntities.put(blockWithEntity, blockEntityCopy);
            }

            if (blockEntityCopy != null) {
                blockEntity = blockEntityCopy;
            }
        }

        // CUSTOM CHESTS
        Function<ChestType, SpriteIdentifier> customTexture = CustomChestTextures.TEXTURES.get(blockEntity.getCachedState().getBlock());
        if (customTexture != null) {
            return customTexture.apply(type);
        }

        return original.call(blockEntity, type, christmas);
    }

    @ModifyArgs(method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/ChestBlockEntityRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;FII)V"))
    private void legend_of_steve$render(Args args, @Local(argsOnly = true) BlockEntity blockEntity) {
        if (blockEntity instanceof LockedChestBlockEntity) {
            ModelPart latch = args.get(3);
            latch.visible = false;
        }
    }
}
