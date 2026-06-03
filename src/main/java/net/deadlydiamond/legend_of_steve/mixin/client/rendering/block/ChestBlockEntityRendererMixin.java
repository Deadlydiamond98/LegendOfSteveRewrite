package net.deadlydiamond.legend_of_steve.mixin.client.rendering.block;

import net.deadlydiamond.legend_of_steve.client.models.be.lock.ChestLockLeftModel;
import net.deadlydiamond.legend_of_steve.client.models.be.lock.ChestLockModel;
import net.deadlydiamond.legend_of_steve.client.models.be.lock.ChestLockRightModel;
import net.deadlydiamond.legend_of_steve.util.LockUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ChestBlockEntityRenderer.class)
public class ChestBlockEntityRendererMixin<T extends BlockEntity & LidOpenable> {
    @Unique private ChestLockModel legend_of_steve$lockModel;
    @Unique private ChestLockRightModel legend_of_steve$rightLockModel;
    @Unique private ChestLockLeftModel legend_of_steve$leftLockModel;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(BlockEntityRendererFactory.Context ctx, CallbackInfo ci) {
        this.legend_of_steve$lockModel = new ChestLockModel(ctx.getLayerModelPart(ChestLockModel.LAYER_LOCATION));
        this.legend_of_steve$rightLockModel = new ChestLockRightModel(ctx.getLayerModelPart(ChestLockRightModel.LAYER_LOCATION));
        this.legend_of_steve$leftLockModel = new ChestLockLeftModel(ctx.getLayerModelPart(ChestLockLeftModel.LAYER_LOCATION));
    }

    @Inject(method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void legend_of_steve$render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci, World world, boolean bl, BlockState blockState, ChestType chestType, Block block, AbstractChestBlock abstractChestBlock, boolean bl2, float f, DoubleBlockProperties.PropertySource propertySource, float g, int i, SpriteIdentifier spriteIdentifier, VertexConsumer vertexConsumer) {
        if (world != null) {
            matrices.push();

            ItemStack lock = LockUtil.getLockItemForBlock(entity, blockState, world, entity.getPos());

            if (!lock.isEmpty()) {
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
                matrices.translate(0.5, -1.5, -0.5);
                VertexConsumer lockVertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(legend_of_steve$getLockTexture(lock)));
                if (bl2) {
                    if (chestType == ChestType.LEFT) {
                        this.legend_of_steve$leftLockModel.render(matrices, lockVertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                    } else {
                        this.legend_of_steve$rightLockModel.render(matrices, lockVertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                } else {
                    this.legend_of_steve$lockModel.render(matrices, lockVertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }

            matrices.pop();
        }
    }

    @Unique
    private Identifier legend_of_steve$getLockTexture(ItemStack stack) {
        Identifier id = Registries.ITEM.getId(stack.getItem());
        return new Identifier(id.getNamespace(), "textures/entity/locks/chest_" + id.getPath() + ".png");
    }
}
