package net.deadlydiamond.legend_of_steve.util.datagen.model;

import net.deadlydiamond.legend_of_steve.common.blocks.deco.connected.TriforceTileBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.connected.TriforceType;
import net.deadlydiamond.legend_of_steve.util.ZeldaModels;
import net.minecraft.block.Block;
import net.minecraft.block.enums.ChestType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class ZeldaBlockModelDatagenUtil {

    // CONTAINERS //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void registerPot(BlockStateModelGenerator blockStateModelGenerator, Block block, Block particle) {
        TextureMap textureMap = TextureMap.all(getPrefixedId(block, "loot_pot")).put(TextureKey.PARTICLE, TextureMap.all(particle).getTexture(TextureKey.PARTICLE));

        Identifier pot = ZeldaModels.LOOT_POT.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, pot));
    }

    public static void registerSwordPedestal(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        TextureMap textureMap = TextureMap.of(TextureKey.SIDE, TextureMap.getId(block))
                .put(TextureKey.TOP, TextureMap.getSubId(block, "_top"))
                .put(TextureKey.BOTTOM, TextureMap.getSubId(block, "_bottom"))
                .put(TextureKey.FRONT, TextureMap.getSubId(block, "_front"));

        Identifier pedestal = ZeldaModels.SWORD_PEDESTAL.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, pedestal))
                        .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
        );
    }

    // LOCKS ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void registerLockBlock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        TextureMap textureMap = TextureMap.of(TextureKey.ALL, getPrefixedId(block, "locks"));

        Identifier lock = ZeldaModels.LOCK_BLOCK.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, lock))
                        .coordinate(BlockStateModelGenerator.createNorthDefaultRotationStates())
        );
    }

    public static void registerChestLockBlock(BlockStateModelGenerator blockStateModelGenerator, Block block, Block textureSource) {
        TextureMap textureMap = TextureMap.of(TextureKey.ALL, getPrefixedId(textureSource, "locks"));

        Identifier single = ZeldaModels.CHEST_LOCK_BLOCK.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        Identifier left = ZeldaModels.LEFT_CHEST_LOCK_BLOCK.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        Identifier right = ZeldaModels.RIGHT_CHEST_LOCK_BLOCK.upload(block, textureMap, blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(Properties.CHEST_TYPE)
                                .register(ChestType.SINGLE, BlockStateVariant.create().put(VariantSettings.MODEL, single))
                                .register(ChestType.RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, left))
                                .register(ChestType.LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, right))
                        ).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
        );
    }

    public static void registerDoorLockBlock(BlockStateModelGenerator blockStateModelGenerator, Block block, Block textureSource) {
        TextureMap textureMap = TextureMap.of(TextureKey.ALL, getPrefixedId(textureSource, "locks"));

        Identifier bottom = ZeldaModels.DOOR_LOCK_BOTTOM.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        Identifier top = ZeldaModels.DOOR_LOCK_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createDoorBlockState(
                block, bottom, bottom, bottom, bottom, top, top, top, top
        ));
    }

    // BRAZIERS ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void registerBrazier(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier fireTexture) {
        registerBrazierParented(blockStateModelGenerator, block, block, fireTexture);
    }

    public static void registerBrazierParented(BlockStateModelGenerator blockStateModelGenerator, Block block, Block parent, Identifier fireTexture) {
        TextureMap unlitTextureMap = TextureMap.of(TextureKey.SIDE, TextureMap.getId(parent))
                .put(TextureKey.TOP, TextureMap.getSubId(parent, "_top"))
                .put(TextureKey.BOTTOM, TextureMap.getSubId(parent, "_bottom"));

        TextureMap litTextureMap = TextureMap.of(TextureKey.SIDE, TextureMap.getId(parent))
                .put(TextureKey.TOP, TextureMap.getSubId(block, "_top_lit"))
                .put(TextureKey.BOTTOM, TextureMap.getSubId(parent, "_bottom"))
                .put(TextureKey.FIRE, fireTexture);

        Identifier unlit = ZeldaModels.BRAZIER.upload(block, unlitTextureMap, blockStateModelGenerator.modelCollector);
        Identifier lit = ZeldaModels.BRAZIER_LIT.upload(block, litTextureMap, blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(
                BlockStateVariantMap.create(Properties.LIT)
                        .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, unlit))
                        .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, lit))
        ));
        blockStateModelGenerator.registerItemModel(block.asItem());
    }

    public static void registerTallBrazier(BlockStateModelGenerator blockStateModelGenerator, Block block, Block parent, Block litParent, Identifier fireTexture) {
        TextureMap unlitTextureMap = TextureMap.of(TextureKey.SIDE, TextureMap.getId(parent))
                .put(TextureKey.TOP, TextureMap.getSubId(parent, "_top"))
                .put(TextureKey.BOTTOM, TextureMap.getSubId(parent, "_bottom"));

        TextureMap litTextureMap = TextureMap.of(TextureKey.SIDE, TextureMap.getId(parent))
                .put(TextureKey.TOP, TextureMap.getSubId(litParent, "_top_lit"))
                .put(TextureKey.BOTTOM, TextureMap.getSubId(parent, "_bottom"))
                .put(TextureKey.FIRE, fireTexture);

        TextureMap bottomTextureMap = TextureMap.of(TextureKey.SIDE, TextureMap.getSubId(parent, "_side"))
                .put(TextureKey.BOTTOM, TextureMap.getSubId(parent, "_bottom"));

        Identifier unlit = ZeldaModels.BRAZIER.upload(block, unlitTextureMap, blockStateModelGenerator.modelCollector);
        Identifier lit = ZeldaModels.BRAZIER_LIT.upload(block, litTextureMap, blockStateModelGenerator.modelCollector);
        Identifier bottom = ZeldaModels.TALL_BRAZIER_BOTTOM.upload(block, bottomTextureMap, blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(
                BlockStateVariantMap.create(Properties.LIT, Properties.DOUBLE_BLOCK_HALF)
                        .register(false, DoubleBlockHalf.UPPER, BlockStateVariant.create().put(VariantSettings.MODEL, unlit))
                        .register(true, DoubleBlockHalf.UPPER, BlockStateVariant.create().put(VariantSettings.MODEL, lit))
                        .register(false, DoubleBlockHalf.LOWER, BlockStateVariant.create().put(VariantSettings.MODEL, bottom))
                        .register(true, DoubleBlockHalf.LOWER, BlockStateVariant.create().put(VariantSettings.MODEL, bottom))
        ));
        blockStateModelGenerator.registerItemModel(block.asItem());
    }

    // DECORATIVE //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void registerTile(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        TextureMap textureMap = (new TextureMap()).put(TextureKey.TOP, TextureMap.getId(block))
                .put(TextureKey.SIDE, TextureMap.getSubId(block, "_side"))
                .put(TextureKey.BOTTOM, TextureMap.getSubId(block, "_bottom"));

        Identifier reg = ZeldaModels.TILE.upload(block, textureMap, blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier
                .create(block, BlockStateVariant.create().put(VariantSettings.MODEL, reg))
                .coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates())
        );
    }

    public static void registerTriforceTile(BlockStateModelGenerator blockStateModelGenerator, Block parent, Block block) {
        Identifier single = getTriforceTileModel(parent, block, "single", blockStateModelGenerator);
        Identifier topLeft = getTriforceTileModel(parent, block, "top_left", blockStateModelGenerator);
        Identifier topRight = getTriforceTileModel(parent, block, "top_right", blockStateModelGenerator);
        Identifier bottomLeft = getTriforceTileModel(parent, block, "bottom_left", blockStateModelGenerator);
        Identifier bottomRight = getTriforceTileModel(parent, block, "bottom_right", blockStateModelGenerator);

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(TriforceTileBlock.TRIFORCE_TYPE)
                        .register(TriforceType.SINGLE, BlockStateVariant.create().put(VariantSettings.MODEL, single))
                        .register(TriforceType.DOUBLE_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, topLeft))
                        .register(TriforceType.DOUBLE_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, topRight))
                        .register(TriforceType.BIG_TOP_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, topLeft))
                        .register(TriforceType.BIG_TOP_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, topRight))
                        .register(TriforceType.BIG_BOTTOM_LEFT, BlockStateVariant.create().put(VariantSettings.MODEL, bottomLeft))
                        .register(TriforceType.BIG_BOTTOM_RIGHT, BlockStateVariant.create().put(VariantSettings.MODEL, bottomRight)))
                .coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates()));
        blockStateModelGenerator.registerParentedItemModel(block, single);
    }

    public static Identifier getTriforceTileModel(Block parent, Block block, String suffix, BlockStateModelGenerator blockStateModelGenerator) {
        TextureMap textureMap = new TextureMap().put(TextureKey.TOP, TextureMap.getSubId(block, "_" + suffix))
                .put(TextureKey.SIDE, TextureMap.getSubId(parent, "_side"))
                .put(TextureKey.BOTTOM, TextureMap.getSubId(parent, "_bottom"));
        return ZeldaModels.TILE.upload(block, "_" + suffix, textureMap, blockStateModelGenerator.modelCollector);
    }

    public static void registerConnectedPillar(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        Identifier id = Registries.BLOCK.getId(block).withPrefixedPath("block/");
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, id)));
    }

    // MISC ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Identifier getPrefixedId(Block block, String prefix) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return identifier.withPrefixedPath("block/" + prefix + "/");
    }
}
