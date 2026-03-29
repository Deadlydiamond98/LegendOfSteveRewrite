package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.common.entities.living.fairy.FairyEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;

public class ZeldaEntityAttributes {
    public static void register() {
//        register(ZeldaEntityTypes.FAIRY, FairyEntity.createCustomAttributes());
    }

    private static void register(EntityType<? extends LivingEntity> type, DefaultAttributeContainer.Builder builder) {
        FabricDefaultAttributeRegistry.register(type, builder);
    }
}
