package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.bes.BombFlowerBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.BouncingBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.container.single.QuestionBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.container.single.*;
import net.deadlydiamond.legend_of_steve.common.bes.switches.CrystalSwitchBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.container.MasterBarrelBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.switches.SwitchBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ZeldaBlockEntities {

    // STORAGE BLOCK ENTITIES //////////////////////////////////////////////////////////////////////////////////////////

    public static final BlockEntityType<MasterBarrelBlockEntity> MASTER_BARREL = register("master_barrel", MasterBarrelBlockEntity::new,
            ZeldaBlocks.MASTER_BARREL
    );

    public static final BlockEntityType<QuestionBlockEntity> QUESTION_BLOCK = register("question_block", QuestionBlockEntity::new,
            ZeldaBlocks.QUESTION_BLOCK,
            ZeldaBlocks.BLUE_QUESTION_BLOCK,
            ZeldaBlocks.INVISIBLE_QUESTION_BLOCK,
            ZeldaBlocks.STRANGE_DIRT_BRICKS.container,
            ZeldaBlocks.STRANGE_BLUE_DIRT_BRICKS.container
    );

    public static final BlockEntityType<LootPotBlockEntity> LOOT_POT = register("loot_pot", LootPotBlockEntity::new,
            ZeldaBlocks.DYED_LOOT_POTS.getAll(ZeldaBlocks.LOOT_POT)
    );

    public static final BlockEntityType<SwordPedestalBlockEntity> SWORD_PEDESTAL = register("sword_pedestal", SwordPedestalBlockEntity::new,
            ZeldaBlocks.STONE_SWORD_PEDESTAL,
            ZeldaBlocks.DEEPSLATE_SWORD_PEDESTAL,
            ZeldaBlocks.BLACKSTONE_SWORD_PEDESTAL,
            ZeldaBlocks.QUARTZ_SWORD_PEDESTAL,
            ZeldaBlocks.FAIRY_MARBLE_SWORD_PEDESTAL,
            ZeldaBlocks.STRANGE_DIRT_SWORD_PEDESTAL,
            ZeldaBlocks.STRANGE_BLUE_DIRT_SWORD_PEDESTAL
    );

    // SWITCH BLOCK ENTITIES ///////////////////////////////////////////////////////////////////////////////////////////

    public static final BlockEntityType<SwitchBlockEntity> SWITCH_BLOCK = register("switch_block", SwitchBlockEntity::new,
            ZeldaBlocks.RED_SWITCH_BLOCKS.getAll(ZeldaBlocks.BLUE_SWITCH_BLOCKS.getAll())
    );

    public static final BlockEntityType<CrystalSwitchBlockEntity> CRYSTAL_SWITCH = register("crystal_switch", CrystalSwitchBlockEntity::new,
            ZeldaBlocks.CRYSTAL_SWITCH
    );

    // OTHER BLOCK ENTITIES ////////////////////////////////////////////////////////////////////////////////////////////

    public static final BlockEntityType<BombFlowerBlockEntity> BOMB_FLOWER = register("bomb_flower", BombFlowerBlockEntity::new, ZeldaBlocks.BOMB_FLOWER);

    public static final BlockEntityType<BouncingBlockEntity> BOUNCING_BLOCK = register("bouncing_block", BouncingBlockEntity::new, ZeldaBlocks.BOUNCING_BLOCK);

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, LegendOfSteve.id(name), FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }

    public static void register() {}
}