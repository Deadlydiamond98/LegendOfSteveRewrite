package net.deadlydiamond.legend_of_steve.util.datagen.model;

import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

public class ZeldaBlockModelDatagenUtil {
    public static void registerFence(BlockStateModelGenerator blockModelGenerators, Block fence, Block base) {
        TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(base);
        TextureMap textureMap = texturedModel.getTextures();

        Models.FENCE_INVENTORY.uploadWithoutVariant(fence, "", textureMap, blockModelGenerators.modelCollector);
        Identifier post = Models.FENCE_POST.upload(fence, textureMap, blockModelGenerators.modelCollector);
        Identifier side = Models.FENCE_SIDE.upload(fence, textureMap, blockModelGenerators.modelCollector);
        blockModelGenerators.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(fence, post, side));
    }

    public static void registerFenceGate(BlockStateModelGenerator blockModelGenerators, Block gate, Block base) {
        TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(base);
        TextureMap textureMap = texturedModel.getTextures();

        Identifier open = Models.TEMPLATE_FENCE_GATE_OPEN.upload(gate, textureMap, blockModelGenerators.modelCollector);
        Identifier shut = Models.TEMPLATE_FENCE_GATE.upload(gate, textureMap, blockModelGenerators.modelCollector);
        Identifier wallOpen = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(gate, textureMap, blockModelGenerators.modelCollector);
        Identifier wallShut = Models.TEMPLATE_FENCE_GATE_WALL.upload(gate, textureMap, blockModelGenerators.modelCollector);

        blockModelGenerators.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(gate, open, shut, wallOpen, wallShut, true));
    }

    public static void registerButton(BlockStateModelGenerator blockModelGenerators, Block button, Block base) {
        TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(base);
        TextureMap textureMap = texturedModel.getTextures();

        Identifier reg = Models.BUTTON.upload(button, textureMap, blockModelGenerators.modelCollector);
        Identifier pressed = Models.BUTTON_PRESSED.upload(button, textureMap, blockModelGenerators.modelCollector);
        blockModelGenerators.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(button, reg, pressed));

        Identifier item = Models.BUTTON_INVENTORY.upload(button, textureMap, blockModelGenerators.modelCollector);
        blockModelGenerators.registerParentedItemModel(button, item);
    }

    public static void registerPressurePlate(BlockStateModelGenerator blockModelGenerators, Block plate, Block base) {
        TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(base);
        TextureMap textureMap = texturedModel.getTextures();

        Identifier reg = Models.PRESSURE_PLATE_UP.upload(plate, textureMap, blockModelGenerators.modelCollector);
        Identifier pressed = Models.PRESSURE_PLATE_DOWN.upload(plate, textureMap, blockModelGenerators.modelCollector);

        blockModelGenerators.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(plate, reg, pressed));
    }

    public static void registerSign(BlockStateModelGenerator blockStateModelGenerator, Block sign, Block base) {
        TextureMap textureMapping = TextureMap.texture(base);
        Identifier resourceLocation = Models.PARTICLE.upload(sign, textureMapping, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(sign, resourceLocation));
        blockStateModelGenerator.registerItemModel(sign.asItem());
    }
}
