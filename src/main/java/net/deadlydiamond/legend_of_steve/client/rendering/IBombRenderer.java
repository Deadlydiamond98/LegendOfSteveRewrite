package net.deadlydiamond.legend_of_steve.client.rendering;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.BombEntity;
import net.deadlydiamond.legend_of_steve.common.items.projectile.BombItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.util.Optional;

public interface IBombRenderer {
    
    default Identifier getFallbackTexture() {
        return LegendOfSteve.id("textures/entity/bomb/bomb.png");
    }
    
    default <T extends BombEntity> Identifier getBombTexture(T entity) {
        return getBombTexture(entity.getStack().getItem());
    }

    default Identifier getBombTexture(Item item) {
        Identifier type = Registries.ITEM.getId(item);
        Identifier texture = new Identifier(type.getNamespace(), getPath(type.getPath()));
        Optional<Resource> resource = MinecraftClient.getInstance().getResourceManager().getResource(texture);
        return resource.isPresent() ? texture : getFallbackTexture();
    }

    default <T extends BombEntity> String getPath(String path) {
        return "textures/entity/bomb/" + path + ".png";
    }

    default boolean hasTwoBombs(LivingEntity entity) {
        return isBomb(entity.getMainHandStack()) && isBomb(entity.getOffHandStack());
    }

    default boolean isBomb(ItemStack itemStack) {
        return itemStack.getItem() instanceof BombItem && itemStack.isIn(ZeldaTags.BOMBS);
    }
}
