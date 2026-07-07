package net.deadlydiamond.legend_of_steve.mixin.client.rendering.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.deadlydiamond.legend_of_steve.client.CustomChestTextures;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(ChestBlockEntityRenderer.class)
public class ChestBlockEntityRendererMixin {

    // Renders Custom Chest Textures
    @WrapOperation(method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getChestTextureId(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;"))
    private SpriteIdentifier legend_of_steve$render(BlockEntity blockEntity, ChestType type, boolean christmas, Operation<SpriteIdentifier> original) {
        Function<ChestType, SpriteIdentifier> customTexture = CustomChestTextures.TEXTURES.get(blockEntity.getCachedState().getBlock());
        if (customTexture != null) {
            return customTexture.apply(type);
        }
        return original.call(blockEntity, type, christmas);
    }
}
