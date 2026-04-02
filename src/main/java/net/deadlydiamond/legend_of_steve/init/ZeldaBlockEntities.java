package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.bes.BombFlowerBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.container.MasterBarrelBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.container.single.LootPotBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.container.single.HittableContainerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ZeldaBlockEntities {

    // RENDERING BLOCK ENTITIES ////////////////////////////////////////////////////////////////////////////////////////

    public static final BlockEntityType<BombFlowerBlockEntity> BOMB_FLOWER = register("bomb_flower", BombFlowerBlockEntity::new,
            ZeldaBlocks.BOMB_FLOWER
    );

    // STORAGE BLOCK ENTITIES //////////////////////////////////////////////////////////////////////////////////////////

    public static final BlockEntityType<MasterBarrelBlockEntity> MASTER_BARREL = register("master_barrel", MasterBarrelBlockEntity::new,
            ZeldaBlocks.MASTER_BARREL
    );

    public static final BlockEntityType<LootPotBlockEntity> LOOT_POT = register("loot_pot", LootPotBlockEntity::new,
            ZeldaBlocks.DYED_LOOT_POTS.getAll(ZeldaBlocks.LOOT_POT)
    );

    public static final BlockEntityType<HittableContainerBlockEntity> HITTABLE_CONTAINER_BLOCK = register("hittable_container_block", HittableContainerBlockEntity::new,
            ZeldaBlocks.QUESTION_BLOCK,
            ZeldaBlocks.STRANGE_DIRT_BRICKS.container,
            ZeldaBlocks.STRANGE_BLUE_DIRT_BRICKS.container
    );

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, LegendOfSteve.id(name), FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }

    public static void register() {}
}