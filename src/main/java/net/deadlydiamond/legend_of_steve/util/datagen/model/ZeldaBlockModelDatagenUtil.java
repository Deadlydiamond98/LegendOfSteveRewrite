package net.deadlydiamond.legend_of_steve.util.datagen.model;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.container.single.hittable.AbstractHittableContainerBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.ConnectedPillarBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.PillarType;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Text;

import java.util.Optional;

public class ZeldaBlockModelDatagenUtil {

    public static final Model LOOT_POT = new Model(Optional.of(LegendOfSteve.id("block/template_loot_pot")),
            Optional.empty(), TextureKey.ALL, TextureKey.PARTICLE
    );

    public static final Model SWORD_PEDESTAL = new Model(Optional.of(LegendOfSteve.id("block/template_sword_pedestal")),
            Optional.empty(), TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.FRONT
    );

    public static void registerPot(BlockStateModelGenerator blockStateModelGenerator, Block block, Block particle) {
        TextureMap textureMap = TextureMap.all(getPrefixedId(block, "loot_pot")).put(TextureKey.PARTICLE, TextureMap.all(particle).getTexture(TextureKey.PARTICLE));

        Identifier pot = LOOT_POT.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, pot));
    }

    public static void registerSwordPedestal(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        TextureMap textureMap = TextureMap.of(TextureKey.SIDE, TextureMap.getId(block))
                .put(TextureKey.TOP, TextureMap.getSubId(block, "_top"))
                .put(TextureKey.BOTTOM, TextureMap.getSubId(block, "_bottom"))
                .put(TextureKey.FRONT, TextureMap.getSubId(block, "_front"));

        Identifier pedestal = SWORD_PEDESTAL.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, pedestal))
                        .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
        );
    }

    public static void registerHittableBlock(BlockStateModelGenerator blockStateModelGenerator, Block block, @Nullable Block base) {
        boolean hasBase = base != null;
        Identifier regular = hasBase ? ModelIds.getBlockModelId(base) : TexturedModel.CUBE_ALL.upload(block, blockStateModelGenerator.modelCollector);
        Identifier empty = LegendOfSteve.id("block/empty_container_block");

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(
                        BlockStateVariantMap.create(AbstractHittableContainerBlock.HIT)
                                .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, empty))
                                .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, regular))
        ));
        if (hasBase) {
            blockStateModelGenerator.registerParentedItemModel(block, regular);
        }
    }

    public static void registerConnectedPillar(BlockStateModelGenerator blockStateModelGenerator, Block block, Block topBlock) {
        registerConnectedPillar(blockStateModelGenerator, block, TextureMap.getId(topBlock));
    }

    public static void registerConnectedPillar(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier topTexture) {
        Identifier single = Models.CUBE_COLUMN.upload(block, pillarTexture(block, "", topTexture), blockStateModelGenerator.modelCollector);
        Identifier middle = Models.CUBE_COLUMN.upload(block, "_middle", pillarTexture(block, "_middle", topTexture), blockStateModelGenerator.modelCollector);
        Identifier top = Models.CUBE_COLUMN.upload(block, "_top", pillarTexture(block, "_top", topTexture), blockStateModelGenerator.modelCollector);
        Identifier bottom = Models.CUBE_COLUMN.upload(block, "_bottom", pillarTexture(block, "_bottom", topTexture), blockStateModelGenerator.modelCollector);

        EnumProperty<PillarType> pillarType = ConnectedPillarBlock.PILLAR_TYPE;

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(
                BlockStateVariantMap.create(Properties.AXIS, pillarType)
                        // SINGLE
                        .register(Direction.Axis.X, PillarType.SINGLE,
                                BlockStateVariant.create().put(VariantSettings.MODEL, single).put(VariantSettings.X, VariantSettings.Rotation.R90)
                                        .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.Z, PillarType.SINGLE,
                                BlockStateVariant.create().put(VariantSettings.MODEL, single).put(VariantSettings.X, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.Y, PillarType.SINGLE,
                                BlockStateVariant.create().put(VariantSettings.MODEL, single))

                        // TOP
                        .register(Direction.Axis.X, PillarType.TOP,
                                BlockStateVariant.create().put(VariantSettings.MODEL, top).put(VariantSettings.X, VariantSettings.Rotation.R90)
                                        .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.Z, PillarType.TOP,
                                BlockStateVariant.create().put(VariantSettings.MODEL, top).put(VariantSettings.X, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.Y, PillarType.TOP,
                                BlockStateVariant.create().put(VariantSettings.MODEL, top))

                        // MIDDLE
                        .register(Direction.Axis.X, PillarType.MIDDLE,
                                BlockStateVariant.create().put(VariantSettings.MODEL, middle).put(VariantSettings.X, VariantSettings.Rotation.R90)
                                        .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.Z, PillarType.MIDDLE,
                                BlockStateVariant.create().put(VariantSettings.MODEL, middle).put(VariantSettings.X, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.Y, PillarType.MIDDLE,
                                BlockStateVariant.create().put(VariantSettings.MODEL, middle))

                        // Bottom
                        .register(Direction.Axis.X, PillarType.BOTTOM,
                                BlockStateVariant.create().put(VariantSettings.MODEL, bottom).put(VariantSettings.X, VariantSettings.Rotation.R90)
                                        .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.Z, PillarType.BOTTOM,
                                BlockStateVariant.create().put(VariantSettings.MODEL, bottom).put(VariantSettings.X, VariantSettings.Rotation.R90))
                        .register(Direction.Axis.Y, PillarType.BOTTOM,
                                BlockStateVariant.create().put(VariantSettings.MODEL, bottom))
        ));
    }

    public static TextureMap pillarTexture(Block block, String varient, Identifier top) {
        return TextureMap.of(TextureKey.SIDE, TextureMap.getSubId(block, varient)).put(TextureKey.END, top);
    }

    public static Identifier getPrefixedId(Block block, String prefix) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return identifier.withPrefixedPath("block/" + prefix + "/");
    }
}
