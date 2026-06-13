package net.deadlydiamond.legend_of_steve.client.models.block.connected;

import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class ConnectedTextureTypes {
    public enum Pillar {
        SINGLE, TOP, MIDDLE, BOTTOM, END;
    }

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    private static String getTexturePath(String path, String location) {
        if (path.startsWith("item/")) {
            path = path.replaceFirst("item/", "block/");
        }

        if (path.startsWith("block/")) {
            path = path.replaceFirst("block/", "block/connected/" + location + "/");
        }

        return path;
    }

    public static <E extends Enum<E>> List<SpriteIdentifier> getSprites(Identifier blockID, Class<E> enumType) {
        Enum<E>[] textures = enumType.getEnumConstants();

        String path = getTexturePath(blockID.getPath(), enumType.getSimpleName().toLowerCase());

        String[] strings = new String[textures.length];
        for (int i = 0; i < textures.length; i++) {
            strings[i] = textures[i].name().toLowerCase();
        }

        return Arrays.stream(strings).map(type -> new SpriteIdentifier(
                PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                new Identifier(blockID.getNamespace(), path + "_" + type)
        )).toList();
    }
}
