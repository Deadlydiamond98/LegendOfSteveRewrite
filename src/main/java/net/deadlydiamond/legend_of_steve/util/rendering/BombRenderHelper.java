package net.deadlydiamond.legend_of_steve.util.rendering;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.AbstractBombEntity;
import net.deadlydiamond.legend_of_steve.common.items.bag.BombBagItem;
import net.deadlydiamond.legend_of_steve.common.items.projectile.explosive.BombItem;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class BombRenderHelper {

    /**
     * Bunch of repetitive Methods that were being used for rendering bombs
     */

    // TEXTURE GETTER METHODS //////////////////////////////////////////////////////////////////////////////////////////

    public static Identifier getFallbackTexture() {
        return LegendOfSteve.id("textures/entity/bomb/bomb.png");
    }

    public static <T extends AbstractBombEntity> Identifier getBombTexture(T entity) {
        return getBombTexture(entity.getStack().getItem());
    }

    public static Identifier getBombTexture(Item item) {
        Identifier type = Registries.ITEM.getId(item);
        Identifier texture = new Identifier(type.getNamespace(), getPath(type.getPath()));
        Optional<Resource> resource = MinecraftClient.getInstance().getResourceManager().getResource(texture);
        return resource.isPresent() ? texture : getFallbackTexture();
    }

    public static <T extends AbstractBombEntity> String getPath(String path) {
        return "textures/entity/bomb/" + path + ".png";
    }

    // ITEM CHECK METHODS //////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean canShowNicerBombModel(ItemStack stack, LivingEntity entity) {
        return isBombTypeItem(stack) && !hasTwoBombs(entity);
    }

    public static boolean hasTwoBombs(LivingEntity entity) {
        return isBombTypeItem(entity.getMainHandStack()) && isBombTypeItem(entity.getOffHandStack());
    }

    public static boolean isBombTypeItem(ItemStack itemStack) {
        return isBomb(itemStack) || isBombBagWithBomb(itemStack);
    }

    public static boolean isBomb(ItemStack itemStack) {
        return itemStack.getItem() instanceof BombItem && itemStack.isIn(ZeldaTags.BOMBS);
    }

    public static boolean isBombBagWithBomb(ItemStack itemStack) {
        if (itemStack.getItem() instanceof BombBagItem) {
            ItemStack bombStack = BombBagItem.getFirstStack(itemStack);
            return isBomb(bombStack);
        }
        return false;
    }

    // Charged Overlay Thing ///////////////////////////////////////////////////////////////////////////////////////////

    public static <T extends Entity> VertexConsumer getChargedLayer(T entity, VertexConsumerProvider vertexConsumers, float tickDelta) {
        float f = entity.age + tickDelta;
        return vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(getEnergySwirlTexture(), getEnergySwirlX(f) % 1.0F, f * 0.01F % 1.0F));
    }

    public static Identifier getEnergySwirlTexture() {
        return new Identifier("textures/entity/creeper/creeper_armor.png");
    }

    public static float getEnergySwirlX(float partialAge) {
        return partialAge * 0.01F;
    }
}
