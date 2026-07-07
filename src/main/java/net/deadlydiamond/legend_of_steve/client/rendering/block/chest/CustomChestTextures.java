package net.deadlydiamond.legend_of_steve.client.rendering.block.chest;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CustomChestTextures {
    public static final Map<Block, Function<ChestType, SpriteIdentifier>> TEXTURES = new HashMap<>();

    static {
        TEXTURES.put(ZeldaBlocks.RED_DUNGEON_CHEST, chestType -> createChestTextureId("red_dungeon_chest"));
        TEXTURES.put(ZeldaBlocks.BLUE_DUNGEON_CHEST, chestType -> createChestTextureId("blue_dungeon_chest"));
    }

    private static SpriteIdentifier createChestTextureId(String varient, ChestType type) {
        return getChestVariantTexture(type,
                createChestTextureId(varient),
                createChestTextureId(varient + "_left"),
                createChestTextureId(varient + "_right")
        );
    }

    private static SpriteIdentifier createChestTextureId(String variant) {
        return new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, LegendOfSteve.id("entity/chest/" + variant));
    }

    public static SpriteIdentifier getChestVariantTexture(ChestType type, SpriteIdentifier single, SpriteIdentifier left, SpriteIdentifier right) {
        return switch (type) {
            case LEFT -> left;
            case RIGHT -> right;
            default -> single;
        };
    }
}
