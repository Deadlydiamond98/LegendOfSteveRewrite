package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.bes.BombFlowerBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.GlowingBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ZeldaBlockEntities {
    public static final BlockEntityType<BombFlowerBlockEntity> BOMB_FLOWER = register("bomb_flower", BombFlowerBlockEntity::new,
            ZeldaBlocks.BOMB_FLOWER
    );

    public static final BlockEntityType<GlowingBlockEntity> GLOWING_BLOCK = register("glowing_block", GlowingBlockEntity::new,
//            ZeldaBlocks.ENCHANTED_SPRING_WATER,
            ZeldaBlocks.PINK_FAIRY_LAMP,
            ZeldaBlocks.RED_FAIRY_LAMP,
            ZeldaBlocks.ORANGE_FAIRY_LAMP,
            ZeldaBlocks.YELLOW_FAIRY_LAMP,
            ZeldaBlocks.GREEN_FAIRY_LAMP,
            ZeldaBlocks.BLUE_FAIRY_LAMP,
            ZeldaBlocks.PURPLE_FAIRY_LAMP
    );

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, LegendOfSteve.id(name), FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }

    public static void register() {}
}