package net.deadlydiamond.legend_of_steve.util.datagen.model;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class IridescentBlockModelDatagenUtil {

    public static void registerIridescentBlock(BlockStateModelGenerator generator, Block block) {
        Identifier texturePath = getPrefixedId(block, "iridescent");
        TextureMap textureMap = TextureMap.all(texturePath).put(TextureKey.PARTICLE, texturePath);
        Identifier id = uploadIridescentModel(generator, ModelIds.getBlockModelId(block), Models.CUBE_ALL, textureMap);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, id));
    }

    public static void registerIridescentSlab(BlockStateModelGenerator generator, Block slab, Block base) {
        Identifier texturePath = getPrefixedId(base, "iridescent");
        TextureMap textureMap = TextureMap.all(texturePath).put(TextureKey.PARTICLE, texturePath);

        Identifier full = ModelIds.getBlockModelId(base);
        Identifier bottom = uploadIridescentModel(generator, ModelIds.getBlockModelId(slab), Models.SLAB, textureMap);
        Identifier top = uploadIridescentModel(generator, ModelIds.getBlockSubModelId(slab, "_top"), Models.SLAB_TOP, textureMap);

        generator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(slab, bottom, top, full));
    }

    public static void registerIridescentStairs(BlockStateModelGenerator generator, Block stairs, Block base) {
        Identifier texturePath = getPrefixedId(base, "iridescent");
        TextureMap textureMap = TextureMap.all(texturePath).put(TextureKey.PARTICLE, texturePath);

        Identifier reg = uploadIridescentModel(generator, ModelIds.getBlockModelId(stairs), Models.STAIRS, textureMap);
        Identifier inner = uploadIridescentModel(generator, ModelIds.getBlockSubModelId(stairs, "_inner"), Models.INNER_STAIRS, textureMap);
        Identifier outer = uploadIridescentModel(generator, ModelIds.getBlockSubModelId(stairs, "_outer"), Models.OUTER_STAIRS, textureMap);

        generator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(stairs, inner, reg, outer));
    }

    public static void registerIridescentWall(BlockStateModelGenerator generator, Block wall, Block base) {
        Identifier texturePath = getPrefixedId(base, "iridescent");
        TextureMap textureMap = TextureMap.all(texturePath).put(TextureKey.PARTICLE, texturePath);

        Identifier inv = uploadIridescentModel(generator, ModelIds.getBlockModelId(wall), Models.WALL_INVENTORY, textureMap);
        Identifier reg = uploadIridescentModel(generator, ModelIds.getBlockSubModelId(wall, "_post"), Models.TEMPLATE_WALL_POST, textureMap);
        Identifier side = uploadIridescentModel(generator, ModelIds.getBlockSubModelId(wall, "_side"), Models.TEMPLATE_WALL_SIDE, textureMap);
        Identifier tall = uploadIridescentModel(generator, ModelIds.getBlockSubModelId(wall, "_side_tall"), Models.TEMPLATE_WALL_SIDE_TALL, textureMap);

        generator.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(wall, reg, side, tall));
    }

    public static void registerIridescentPillar(BlockStateModelGenerator generator, Block block) {
        Identifier texturePath = getPrefixedId(block, "iridescent");
        TextureMap textureMap = new TextureMap().put(TextureKey.SIDE, texturePath)
                .put(TextureKey.END, texturePath.withSuffixedPath("_top"))
                .put(TextureKey.PARTICLE, texturePath);

        Identifier reg = uploadIridescentModel(generator, ModelIds.getBlockModelId(block), Models.CUBE_COLUMN, textureMap);
        Identifier horizontal = uploadIridescentModel(generator, ModelIds.getBlockSubModelId(block, "_horizontal"), Models.CUBE_COLUMN_HORIZONTAL, textureMap);

        generator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(block, reg, horizontal));
    }

    public static void registerIridescentButton(BlockStateModelGenerator generator, Block button, Block base) {
        Identifier texturePath = getPrefixedId(base, "iridescent");
        TextureMap textureMap = TextureMap.all(texturePath).put(TextureKey.PARTICLE, texturePath);

        Identifier reg = uploadIridescentModel(generator, ModelIds.getBlockModelId(button), Models.BUTTON, textureMap);
        Identifier pressed = uploadIridescentModel(generator, ModelIds.getBlockSubModelId(button, "_pressed"), Models.BUTTON_PRESSED, textureMap);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(button, reg, pressed));

        Identifier inv = uploadIridescentModel(generator, ModelIds.getBlockSubModelId(button, "_inventory"), Models.BUTTON_INVENTORY, textureMap);
        generator.registerParentedItemModel(button, inv);
    }

    public static void registerIridescentPressurePlate(BlockStateModelGenerator generator, Block plate, Block base) {
        Identifier texturePath = getPrefixedId(base, "iridescent");
        TextureMap textureMap = TextureMap.all(texturePath).put(TextureKey.PARTICLE, texturePath);

        Identifier reg = uploadIridescentModel(generator, ModelIds.getBlockModelId(plate), Models.PRESSURE_PLATE_UP, textureMap);
        Identifier pressed = uploadIridescentModel(generator, ModelIds.getBlockSubModelId(plate, "_down"), Models.PRESSURE_PLATE_DOWN, textureMap);

        generator.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(plate, reg, pressed));
    }

    private static Identifier uploadIridescentModel(BlockStateModelGenerator generator, Identifier modelID, Model model, TextureMap textureMap) {
        return model.upload(modelID, textureMap, generator.modelCollector, (id, textures) -> {
            JsonObject jsonObject = new JsonObject();
            model.parent.ifPresent(identifier -> jsonObject.addProperty("parent", identifier.toString()));
            if (!textures.isEmpty()) {
                JsonObject jsonObject2 = new JsonObject();
                textures.forEach((textureKey, texture) -> {
                    String name = textureKey.getName();
                    String path = ":" + texture.getPath();
                    if (!name.equals("particle")) {
                        path += "@legend_of_steve:textures/atlas/iridescent.png";
                        jsonObject2.addProperty(name, texture.getNamespace() + path);
                    }
                });
                jsonObject2.addProperty("particle", textureMap.getTexture(TextureKey.PARTICLE).toString());
                jsonObject.add("textures", jsonObject2);
            }
            return jsonObject;
        });
    }

    public static Identifier getPrefixedId(Block block, String prefix) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return identifier.withPrefixedPath("block/" + prefix + "/");
    }
}
