package net.deadlydiamond.legend_of_steve.client.switches;

import com.mojang.blaze3d.platform.TextureUtil;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.*;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SwitchBlockAtlas extends AbstractTexture implements DynamicTexture {
    public static final Identifier SWITCH_ATLAS_TEXTURE = LegendOfSteve.id("switch_atlas");
    @Nullable public static SwitchBlockAtlas INSTANCE;

    private Map<String, Map<Identifier, SwitchSprite>> sprites = Map.of();

    private Map<Identifier, SwitchSprite> spritesTemp = Map.of();
    private final MinecraftClient client;
    private int height = 0;

    public SwitchBlockAtlas(MinecraftClient client) {
        this.client = client;
    }

//    public static void updateSprites(Map<String, Boolean> values) {
//        if (INSTANCE != null) {
//            INSTANCE.prepareSprites(values);
//        }
//    }
//
//    public void prepareSprites(Map<String, Boolean> values) {
//        List<String> newValues = new ArrayList<>();
//        int prevHeight = this.height;
//
//        values.forEach((s, aBoolean) -> {
//            if (!this.sprites.containsKey(s)) {
//                newValues.add(s);
//            }
//        });
//        this.height += 16 * newValues.size();
//        updateSprites(values,  newValues, prevHeight);
//    }
//
//    public void updateSprites(Map<String, Boolean> values, List<String> newValues, int prevHeight) {
//        TextureUtil.prepareImage(this.getGlId(), 0, getWidth(), getHeight());
//        Map<String, Map<Identifier, SwitchSprite>> updatedSprites = new HashMap<>(this.sprites);
//
//        values.forEach((switchID, isOn) -> {
//
//        });
//
//        this.sprites = Collections.unmodifiableMap(updatedSprites);
//        this.client.getTextureManager().registerTexture(SWITCH_ATLAS_TEXTURE, this);
//    }
//
//    public SwitchSprite createSprite(SwitchTextures.SwitchTexture texture) {
//
//    }

    @Override
    public void load(ResourceManager manager) {}

    public static void updateSwitchSprites(String group, boolean bl) {
        if (INSTANCE != null) {
            INSTANCE.updateSprites(group, bl);
        }
    }
    
    public void updateSprites(String group, boolean bl) {
        int atlasY = getAtlasY(group);
        TextureUtil.prepareImage(this.getGlId(), 0, getWidth(), getHeight());
        Map<String, Map<Identifier, SwitchSprite>> updatedSprites = new HashMap<>(this.sprites);
        Map<Identifier, SwitchSprite> groupSprites = new HashMap<>();

        for (int i = 0; i < SwitchTextures.TEXTURES.size(); i++) {
            SwitchTextures.SwitchTexture switchTexture = SwitchTextures.TEXTURES.get(i);
            Identifier spriteId = switchTexture.getID(group);
            
            try {
                NativeImage textureImage = NativeImage.read(
                        this.client.getResourceManager().getResource(switchTexture.getTexture(bl)).get().getInputStream()
                );

                SpriteDimensions dimensions = new SpriteDimensions(textureImage.getWidth(), textureImage.getHeight());
                SpriteContents contents = new SpriteContents(spriteId, dimensions, textureImage, AnimationResourceMetadata.EMPTY);
                SwitchSprite sprite = new SwitchSprite(SWITCH_ATLAS_TEXTURE, contents, getWidth(), getHeight(), i * 16, atlasY);

                sprite.upload();
                groupSprites.put(spriteId, sprite);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        updatedSprites.put(group, groupSprites);

        this.sprites = Collections.unmodifiableMap(updatedSprites);
        this.client.getTextureManager().registerTexture(SWITCH_ATLAS_TEXTURE, this);
    }

    public int getAtlasY(String group) {
        SwitchTextures.SwitchTexture potentialTexture = SwitchTextures.TEXTURES.get(0);
        Map<Identifier, SwitchSprite> sprites = this.sprites.get(group);
        if (sprites == null) {
            this.height += 16;
            return this.height - 16;
        }
        Sprite possibleSprite = sprites.get(potentialTexture.getID(group));
        if (possibleSprite == null) {
            this.height += 16;
            return this.height - 16;
        }
        return possibleSprite.getY();
    }

    public Sprite getSprite(String group, Identifier block) {
        Map<Identifier, SwitchSprite> sprites = this.sprites.get(group);

        if (sprites != null) {
            return sprites.get(block.withSuffixedPath("_" + group));
        }

        return this.sprites.get(group).get(LegendOfSteve.id("block/red_switch_block_global"));

//        return this.spritesTemp.getOrDefault(block.withSuffixedPath("_" + group), this.spritesTemp.get(LegendOfSteve.id("block/red_switch_block_global")));
    }
    
    private int getHeight() {
        return this.height;
    }
    
    private int getWidth() {
        return SwitchTextures.TEXTURES.size() * 16;
    }

    @Override
    public void close() {
        this.sprites = Map.of();
    }

    protected static class SwitchSprite extends Sprite {
        public SwitchSprite(Identifier atlasId, SpriteContents contents, int atlasWidth, int atlasHeight, int x, int y) {
            super(atlasId, contents, atlasWidth, atlasHeight, x, y);
        }
    }

    // Debug Saving Stuff //////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void save(Identifier id, Path path) {
        String string = id.toUnderscoreSeparatedString();
        TextureUtil.writeAsPNG(path, string, this.getGlId(), 0, this.getWidth(), this.getHeight());
        dumpAtlasInfos(path, string, this.sprites);
    }

    private static void dumpAtlasInfos(Path path, String id, Map<String, Map<Identifier, SwitchSprite>> sprites) {
        Path path2 = path.resolve(id + ".txt");

        try {
            Writer writer = Files.newBufferedWriter(path2);

            try {
                for (Map.Entry<String, Map<Identifier, SwitchSprite>> groupEntry : sprites.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()) {
                    Map<Identifier, SwitchSprite> groupSprites = groupEntry.getValue();
                    String group = groupEntry.getKey();
                    for (Map.Entry<Identifier, SwitchSprite> entry : groupSprites.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()) {
                        SwitchSprite sprite = entry.getValue();
                        writer.write(
                                String.format(
                                        Locale.ROOT,
                                        "%s ------ %s\tx=%d\ty=%d\tw=%d\th=%d%n",
                                        group,
                                        entry.getKey(),
                                        sprite.getX(),
                                        sprite.getY(),
                                        sprite.getContents().getWidth(),
                                        sprite.getContents().getHeight()
                                )
                        );
                    }
                }
            } catch (Throwable var9) {
                try {
                    writer.close();
                } catch (Throwable var8) {
                    var9.addSuppressed(var8);
                }

                throw var9;
            }

            writer.close();
        } catch (IOException var10) {
            LegendOfSteve.LOGGER.warn("Failed to write file {}", path2, var10);
        }
    }
}
