package net.deadlydiamond.legend_of_steve.mixin.client.rendering.entity;

import net.deadlydiamond.legend_of_steve.client.rendering.player.armor.CustomArmorFeatureRenderer;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityRenderer.class)
public abstract class BipedEntityRendererMixin<T extends MobEntity, M extends BipedEntityModel<T>> extends MobEntityRenderer<T, M> {
    public BipedEntityRendererMixin(EntityRendererFactory.Context context, M entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/BipedEntityModel;FFFF)V", at = @At("TAIL"))
    private void legend_of_steve$init(EntityRendererFactory.Context ctx, BipedEntityModel<T> model, float shadowRadius, float scaleX, float scaleY, float scaleZ, CallbackInfo ci) {
        this.addFeature(new CustomArmorFeatureRenderer<>(this, ctx));
    }
}
