package net.deadlydiamond.legend_of_steve.datagen;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.util.datagen.model.IridescentBlockModelDatagenUtil;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaBlockModelDatagenUtil;
import net.deadlydiamond.legend_of_steve.util.datagen.model.ZeldaItemModelDatagenUtil;
import net.deadlydiamond98.koalalib.util.datagen.BlockModelDatagenUtil;
import net.deadlydiamond98.koalalib.util.datagen.ItemModelDatagenUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ZeldaModelDatagen extends FabricModelProvider {

    public ZeldaModelDatagen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {

        // LOOT POTS ///////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlockModelDatagenUtil.registerPot(generator, ZeldaBlocks.LOOT_POT, Blocks.TERRACOTTA);
        ZeldaBlocks.DYED_LOOT_POTS.generateModels(generator, (generator1, block, color) ->
                ZeldaBlockModelDatagenUtil.registerPot(
                        generator, block, Registries.BLOCK.get(new Identifier(color + "_terracotta"))
                )
        );

        // SWORD PEDESTALS /////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlockModelDatagenUtil.registerSwordPedestal(generator, ZeldaBlocks.STONE_SWORD_PEDESTAL);
        ZeldaBlockModelDatagenUtil.registerSwordPedestal(generator, ZeldaBlocks.DEEPSLATE_SWORD_PEDESTAL);
        ZeldaBlockModelDatagenUtil.registerSwordPedestal(generator, ZeldaBlocks.BLACKSTONE_SWORD_PEDESTAL);
        ZeldaBlockModelDatagenUtil.registerSwordPedestal(generator, ZeldaBlocks.QUARTZ_SWORD_PEDESTAL);
        ZeldaBlockModelDatagenUtil.registerSwordPedestal(generator, ZeldaBlocks.STRANGE_DIRT_SWORD_PEDESTAL);
        ZeldaBlockModelDatagenUtil.registerSwordPedestal(generator, ZeldaBlocks.STRANGE_BLUE_DIRT_SWORD_PEDESTAL);

        // BRAZIERS ////////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.STONE_BRAZIER_BLOCKSET.generateModels(generator);
        ZeldaBlocks.DEEPSLATE_BRAZIER_BLOCKSET.generateModels(generator);
        ZeldaBlocks.BLACKSTONE_BRAZIER_BLOCKSET.generateModels(generator);
        ZeldaBlocks.QUARTZ_BRAZIER_BLOCKSET.generateModels(generator);
        ZeldaBlocks.STRANGE_DIRT_BRAZIER_BLOCKSET.generateModels(generator);
        ZeldaBlocks.STRANGE_BLUE_DIRT_BRAZIER_BLOCKSET.generateModels(generator);

        // CRATE ///////////////////////////////////////////////////////////////////////////////////////////////////////
        generator.registerSimpleCubeAll(ZeldaBlocks.CRATE);
        generator.registerParentedItemModel(ZeldaBlocks.CRATE_ITEM, ModelIds.getBlockModelId(ZeldaBlocks.CRATE));
        
        // CHISELED PLANKS /////////////////////////////////////////////////////////////////////////////////////////////
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_OAK_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_BIRCH_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_SPRUCE_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_JUNGLE_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_ACACIA_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_DARK_OAK_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_CRIMSON_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_WARPED_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_MANGROVE_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_BAMBOO_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_CHERRY_PLANKS);
        generator.registerSimpleCubeAll(ZeldaBlocks.CHISELED_DEKU_PLANKS);

        // DUNGEONCITE /////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.BROWN_DUNGEONCITE.generateModels(generator);

        // FAIRY LAMP //////////////////////////////////////////////////////////////////////////////////////////////////
        generator.registerSimpleCubeAll(ZeldaBlocks.PINK_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.RED_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.ORANGE_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.YELLOW_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.GREEN_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.BLUE_FAIRY_LAMP);
        generator.registerSimpleCubeAll(ZeldaBlocks.PURPLE_FAIRY_LAMP);

        // FAIRY MARBLE ////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.FAIRY_MARBLE.generateModels(generator);
        ZeldaBlocks.COBBLED_FAIRY_MARBLE.generateModels(generator);
        ZeldaBlocks.POLISHED_FAIRY_MARBLE.generateModels(generator);
        ZeldaBlocks.FAIRY_MARBLE_BRICKS.generateModels(generator);
        ZeldaBlocks.MOSSY_FAIRY_MARBLE_BRICKS.generateModels(generator);
        ZeldaBlocks.FAIRY_MARBLE_TILES.generateModels(generator);

        IridescentBlockModelDatagenUtil.registerIridescentPillar(generator, ZeldaBlocks.FAIRY_MARBLE_PILLAR);
        IridescentBlockModelDatagenUtil.registerIridescentBlock(generator, ZeldaBlocks.SMOOTH_FAIRY_MARBLE);
        IridescentBlockModelDatagenUtil.registerIridescentBlock(generator, ZeldaBlocks.CRACKED_FAIRY_MARBLE_BRICKS);

        // MASTER //////////////////////////////////////////////////////////////////////////////////////////////////////
        generator.registerSimpleCubeAll(ZeldaBlocks.MASTER_BLOCK);
        generator.registerSimpleCubeAll(ZeldaBlocks.MASTER_SCRAP_BLOCK);
        generator.registerSimpleCubeAll(ZeldaBlocks.MASTER_ORE);
        generator.registerSimpleCubeAll(ZeldaBlocks.DEEPSLATE_MASTER_ORE);
        ZeldaBlocks.MASTER_PLATE.generateModels(generator, true);
        ZeldaBlocks.MASTER_BRICK.generateModels(generator);
        ZeldaBlocks.MASTER_TILE.generateModels(generator);
        generator.registerSimpleCubeAll(ZeldaBlocks.CUT_MASTER_PLATE);
        BlockModelDatagenUtil.registerPillar(generator, ZeldaBlocks.MASTER_PILLAR);
        generator.registerDoor(ZeldaBlocks.MASTER_DOOR);
        generator.registerOrientableTrapdoor(ZeldaBlocks.MASTER_TRAPDOOR);
        // STRANGE DIRT //////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.STRANGE_DIRT.generateModels(generator);
        ZeldaBlocks.STRANGE_DIRT_BRICKS.generateModels(generator);
        ZeldaBlocks.POLISHED_STRANGE_DIRT.generateModels(generator, true);
        ZeldaBlocks.REINFORCED_STRANGE_DIRT.generateModels(generator);
        ZeldaBlockModelDatagenUtil.registerConnectedPillar(generator, ZeldaBlocks.STRANGE_DIRT_PILLAR, ZeldaBlocks.POLISHED_STRANGE_DIRT.base);
        ZeldaBlocks.STRANGE_BLUE_DIRT.generateModels(generator);
        ZeldaBlocks.STRANGE_BLUE_DIRT_BRICKS.generateModels(generator);
        ZeldaBlocks.POLISHED_BLUE_STRANGE_DIRT.generateModels(generator, true);
        ZeldaBlocks.REINFORCED_STRANGE_BLUE_DIRT.generateModels(generator);
        ZeldaBlockModelDatagenUtil.registerConnectedPillar(generator, ZeldaBlocks.STRANGE_BLUE_DIRT_PILLAR, ZeldaBlocks.POLISHED_BLUE_STRANGE_DIRT.base);

        // TEKTILES ////////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.RED_TEKTILES.generateModels(generator);
        ZeldaBlocks.SMALL_RED_TEKTILES.generateModels(generator);
        ZeldaBlocks.RED_TEKTILE_BRICKS.generateModels(generator);
        ZeldaBlocks.BLUE_TEKTILES.generateModels(generator);
        ZeldaBlocks.SMALL_BLUE_TEKTILES.generateModels(generator);
        ZeldaBlocks.BLUE_TEKTILE_BRICKS.generateModels(generator);

        // SWITCH BLOCKS ///////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.RED_SWITCH_BLOCKS.generateModels(generator);
        ZeldaBlocks.BLUE_SWITCH_BLOCKS.generateModels(generator);

        // WOOD ////////////////////////////////////////////////////////////////////////////////////////////////////////
        ZeldaBlocks.DEKU_WOOD.generateModels(generator);
        generator.registerFlowerPotPlant(ZeldaBlocks.DEKU_SAPLING, ZeldaBlocks.POTTED_DEKU_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

        ZeldaBlocks.CHISELED_OAK_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_BIRCH_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_SPRUCE_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_JUNGLE_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_ACACIA_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_DARK_OAK_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_CRIMSON_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_WARPED_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_MANGROVE_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_BAMBOO_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_CHERRY_BRICKS.generateModels(generator);
        ZeldaBlocks.CHISELED_DEKU_BRICKS.generateModels(generator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        ItemModelDatagenUtil.registerGenerated(
                itemModelGenerator,
                ZeldaItems.BOMB_FLOWER_SEEDS,
                ZeldaItems.BOMB,
                ZeldaItems.SUPER_BOMB,
                ZeldaItems.BOMB_FLOWER,
                ZeldaItems.DEKU_NUT,
                ZeldaItems.EMERALD_SHARD,
                ZeldaItems.EMERALD_CHUNK,
                ZeldaItems.BLUE_TEKTITE_CHITIN,
                ZeldaItems.BLUE_TEKTITE_SHELL,
                ZeldaItems.RED_TEKTITE_CHITIN,
                ZeldaItems.RED_TEKTITE_SHELL,
                ZeldaItems.RAW_MASTER_ORE,
                ZeldaItems.MASTER_SCRAP,
                ZeldaItems.MASTER_INGOT,
                ZeldaItems.SWITCH_CORE,
                ZeldaItems.ENCHANTED_SPRING_WATER_BUCKET,
                ZeldaItems.MUSIC_DISC_LEGEND,
                ZeldaItems.DISC_FRAGMENT_LEGEND,
                ZeldaItems.COPPER_KEY,
                ZeldaItems.IRON_KEY,
                ZeldaItems.GOLD_KEY,
                ZeldaItems.BOSS_KEY,
                ZeldaItems.COPPER_LOCK,
                ZeldaItems.IRON_LOCK,
                ZeldaItems.GOLD_LOCK,
                ZeldaItems.BOSS_LOCK
        );

        ItemModelDatagenUtil.registerHandheld(
                itemModelGenerator,
                ZeldaItems.MAGIC_SWORD
        );

        ItemModelDatagenUtil.registerSpawnEggs(itemModelGenerator, ZeldaEntityTypes.SPAWN_EGGS.toArray(Item[]::new));

        ZeldaItemModelDatagenUtil.registerFairyBottle(itemModelGenerator, ZeldaItems.FAIRY_BOTTLE);

        ZeldaItemModelDatagenUtil.registerQuiver(itemModelGenerator, ZeldaItems.QUIVER);
        ZeldaItemModelDatagenUtil.registerQuiver(itemModelGenerator, ZeldaItems.GILDED_QUIVER);
        ZeldaItemModelDatagenUtil.registerQuiver(itemModelGenerator, ZeldaItems.NETHERITE_QUIVER);

        ZeldaItemModelDatagenUtil.registerBombBag(itemModelGenerator, ZeldaItems.BOMB_BAG);
        ZeldaItemModelDatagenUtil.registerBombBag(itemModelGenerator, ZeldaItems.GILDED_BOMB_BAG);
        ZeldaItemModelDatagenUtil.registerBombBag(itemModelGenerator, ZeldaItems.NETHERITE_BOMB_BAG);
    }
}
