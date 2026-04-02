package net.deadlydiamond.legend_of_steve.util.datagen.model;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.ConnectedPillarBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.deco.PillarType;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;

public class ZeldaBlockModelDatagenUtil {

    public static final Model LOOT_POT = new Model(Optional.of(LegendOfSteve.id("block/template_loot_pot")),
            Optional.empty(), TextureKey.ALL, TextureKey.PARTICLE
    );

    public static void registerPot(BlockStateModelGenerator blockStateModelGenerator, Block block, Block particle) {
        TextureMap textureMap = TextureMap.all(getPrefixedId(block, "loot_pot")).put(TextureKey.PARTICLE, TextureMap.all(particle).getTexture(TextureKey.PARTICLE));

        Identifier pot = LOOT_POT.upload(block, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, pot));
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

        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
                // SINGLE
                .with(When.create().set(Properties.AXIS, Direction.Axis.X).set(pillarType, PillarType.SINGLE),
                        BlockStateVariant.create().put(VariantSettings.MODEL, single).put(VariantSettings.X, VariantSettings.Rotation.R90)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                .with(When.create().set(Properties.AXIS, Direction.Axis.Z).set(pillarType, PillarType.SINGLE),
                        BlockStateVariant.create().put(VariantSettings.MODEL, single).put(VariantSettings.X, VariantSettings.Rotation.R90))

                .with(When.create().set(Properties.AXIS, Direction.Axis.Y).set(pillarType, PillarType.SINGLE),
                        BlockStateVariant.create().put(VariantSettings.MODEL, single))

                // TOP
                .with(When.create().set(Properties.AXIS, Direction.Axis.X).set(pillarType, PillarType.TOP),
                        BlockStateVariant.create().put(VariantSettings.MODEL, top).put(VariantSettings.X, VariantSettings.Rotation.R90)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                .with(When.create().set(Properties.AXIS, Direction.Axis.Z).set(pillarType, PillarType.TOP),
                        BlockStateVariant.create().put(VariantSettings.MODEL, top).put(VariantSettings.X, VariantSettings.Rotation.R90))

                .with(When.create().set(Properties.AXIS, Direction.Axis.Y).set(pillarType, PillarType.TOP),
                        BlockStateVariant.create().put(VariantSettings.MODEL, top))

                // MIDDLE
                .with(When.create().set(Properties.AXIS, Direction.Axis.X).set(pillarType, PillarType.MIDDLE),
                        BlockStateVariant.create().put(VariantSettings.MODEL, middle).put(VariantSettings.X, VariantSettings.Rotation.R90)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                .with(When.create().set(Properties.AXIS, Direction.Axis.Z).set(pillarType, PillarType.MIDDLE),
                        BlockStateVariant.create().put(VariantSettings.MODEL, middle).put(VariantSettings.X, VariantSettings.Rotation.R90))

                .with(When.create().set(Properties.AXIS, Direction.Axis.Y).set(pillarType, PillarType.MIDDLE),
                        BlockStateVariant.create().put(VariantSettings.MODEL, middle))

                // Bottom
                .with(When.create().set(Properties.AXIS, Direction.Axis.X).set(pillarType, PillarType.BOTTOM),
                        BlockStateVariant.create().put(VariantSettings.MODEL, bottom).put(VariantSettings.X, VariantSettings.Rotation.R90)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                .with(When.create().set(Properties.AXIS, Direction.Axis.Z).set(pillarType, PillarType.BOTTOM),
                        BlockStateVariant.create().put(VariantSettings.MODEL, bottom).put(VariantSettings.X, VariantSettings.Rotation.R90))

                .with(When.create().set(Properties.AXIS, Direction.Axis.Y).set(pillarType, PillarType.BOTTOM),
                        BlockStateVariant.create().put(VariantSettings.MODEL, bottom))
        );
    }

    public static TextureMap pillarTexture(Block block, String varient, Identifier top) {
        return TextureMap.of(TextureKey.SIDE, TextureMap.getSubId(block, varient)).put(TextureKey.END, top);
    }

    public static Identifier getPrefixedId(Block block, String prefix) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return identifier.withPrefixedPath("block/" + prefix + "/");
    }
}
