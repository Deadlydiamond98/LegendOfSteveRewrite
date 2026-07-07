package net.deadlydiamond.legend_of_steve.client;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CustomChestTextures {
    public static final Map<Block, Function<ChestType, SpriteIdentifier>> TEXTURES = new HashMap<>();

    static {
        createChestTexture(ZeldaBlocks.RED_DUNGEON_CHEST, "red_dungeon_chest", true);
        createChestTexture(ZeldaBlocks.BLUE_DUNGEON_CHEST, "blue_dungeon_chest", true);
    }

    public static void createChestTexture(Block block, String type, boolean doubleChest) {
        TEXTURES.put(block, doubleChest ? chestType -> createChestTextureId(type, chestType) :
                chestType -> createChestTextureId(type)
        );
    }

    // SPRITE IDS //////////////////////////////////////////////////////////////////////////////////////////////////////

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

    // VANILLA HELPERS /////////////////////////////////////////////////////////////////////////////////////////////////
    public static SpriteIdentifier getVanillaChestTexture(BlockState state, ChestType type, boolean christmas) {
        if (state.isOf(Blocks.ENDER_CHEST)) {
            return TexturedRenderLayers.ENDER;
        } else if (christmas) {
            return CustomChestTextures.getChestVariantTexture(type, TexturedRenderLayers.CHRISTMAS, TexturedRenderLayers.CHRISTMAS_LEFT, TexturedRenderLayers.CHRISTMAS_RIGHT);
        }
        return state.isOf(Blocks.TRAPPED_CHEST) ?
                CustomChestTextures.getChestVariantTexture(type, TexturedRenderLayers.TRAPPED, TexturedRenderLayers.TRAPPED_LEFT, TexturedRenderLayers.TRAPPED_RIGHT) :
                CustomChestTextures.getChestVariantTexture(type, TexturedRenderLayers.NORMAL, TexturedRenderLayers.NORMAL_LEFT, TexturedRenderLayers.NORMAL_RIGHT);
    }

    public static SpriteIdentifier getChestVariantTexture(ChestType type, SpriteIdentifier single, SpriteIdentifier left, SpriteIdentifier right) {
        return switch (type) {
            case LEFT -> left;
            case RIGHT -> right;
            default -> single;
        };
    }
}
