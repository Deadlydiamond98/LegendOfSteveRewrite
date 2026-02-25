package net.deadlydiamond.legend_of_steve.client;

import net.deadlydiamond.legend_of_steve.common.blocks.sign.ICustomSign;
import net.minecraft.block.Block;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SpriteIdentifierRegistry {
    private static final List<SpriteIdentifier> SPRITE_IDENTIFIERS = new ArrayList<>();

    public static void register(SpriteIdentifier sprite) {
        SPRITE_IDENTIFIERS.add(sprite);
    }

    public static void register(Block... blocks) {
        for (Block block : blocks) {
            if (block instanceof ICustomSign customSign) {
                register(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, customSign.getTexture()));
            }
        }
    }

    public static Collection<SpriteIdentifier> getSpriteIdentifiers() {
        return Collections.unmodifiableList(SPRITE_IDENTIFIERS);
    }
}
