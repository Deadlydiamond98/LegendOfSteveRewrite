package net.deadlydiamond.legend_of_steve.client.rendering;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.AbstractBombEntity;
import net.deadlydiamond.legend_of_steve.common.items.projectile.BombItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.util.Optional;

public interface IBombRenderer {
    
    default Identifier getFallbackTexture() {
        return LegendOfSteve.id("textures/entity/bomb/bomb_flower.png");
    }
    
    default <T extends AbstractBombEntity> Identifier getBombTexture(T entity) {
        return getBombTexture(entity.getStack().getItem());
    }

    default Identifier getBombTexture(Item item) {
        Identifier type = Registries.ITEM.getId(item);
        Identifier texture = new Identifier(type.getNamespace(), getPath(type.getPath()));
        Optional<Resource> resource = MinecraftClient.getInstance().getResourceManager().getResource(texture);
        return resource.isPresent() ? texture : getFallbackTexture();
    }

    default <T extends AbstractBombEntity> String getPath(String path) {
        return "textures/entity/bomb/" + path + ".png";
    }

    default boolean hasTwoBombs(LivingEntity entity) {
        return isBomb(entity.getMainHandStack()) && isBomb(entity.getOffHandStack());
    }

    default boolean isBomb(ItemStack itemStack) {
        return itemStack.getItem() instanceof BombItem && itemStack.isIn(ZeldaTags.BOMBS);
    }

    // Charged Overlay Thing ///////////////////////////////////////////////////////////////////////////////////////////
    default <T extends Entity, M extends EntityModel<T>> VertexConsumer getChargedLayer(T entity, VertexConsumerProvider vertexConsumers, float tickDelta) {
        float f = entity.age + tickDelta;
        return vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(this.getEnergySwirlTexture(), getEnergySwirlX(f) % 1.0F, f * 0.01F % 1.0F));
    }

    default Identifier getEnergySwirlTexture() {
        return new Identifier("textures/entity/creeper/creeper_armor.png");
    }

    default float getEnergySwirlX(float partialAge) {
        return partialAge * 0.01F;
    }
}
