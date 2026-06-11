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
    private final MinecraftClient client;
    private boolean shouldReload;
    private int height = 0;

    public SwitchBlockAtlas(MinecraftClient client) {
        this.client = client;
    }

    public Sprite getSprite(String group, Identifier block) {
        Map<Identifier, SwitchSprite> spriteGroup = this.sprites.get(group);

        if (spriteGroup != null) {
            Sprite sprite = spriteGroup.get(block);
            if (sprite != null) {
                return spriteGroup.get(block);
            }
        }
        return null;
    }

    public static void reset() {
        if (INSTANCE != null) {
            INSTANCE.sprites = Map.of();
        }
    }

    // Sprite Creation & Updating //////////////////////////////////////////////////////////////////////////////////////
    
    public static void updateSprites(Map<String, Boolean> values) {
        if (INSTANCE != null) {
            INSTANCE.prepareSprites(values);
        }
    }

    public void prepareSprites(Map<String, Boolean> values) {
        List<PreparedSwitchSprite> preparedSprites = new ArrayList<>();
        values.forEach((group, isOn) -> preparedSprites.add(new PreparedSwitchSprite(group, isOn, getSpriteY(group))));
        updateSprites(preparedSprites);

        if (this.shouldReload) {
            this.shouldReload = false;
            this.client.worldRenderer.reload();
        }
    }

    private void updateSprites(List<PreparedSwitchSprite> preparedSprites) {
        TextureUtil.prepareImage(this.getGlId(), 0, getWidth(), getHeight());
        Map<String, Map<Identifier, SwitchSprite>> updatedSprites = new HashMap<>(this.sprites);

        preparedSprites.forEach(sprites -> updatedSprites.put(sprites.group(), getGroupSprites(sprites)));

        this.sprites = Collections.unmodifiableMap(updatedSprites);
        this.client.getTextureManager().registerTexture(SWITCH_ATLAS_TEXTURE, this);
    }

    private Map<Identifier, SwitchSprite> getGroupSprites(PreparedSwitchSprite preparedSprite) {
        Map<Identifier, SwitchSprite> groupSprites = new HashMap<>();

        for (int i = 0; i < SwitchTextures.TEXTURES.size(); i++) {
            SwitchTextures.SwitchTexture switchTexture = SwitchTextures.TEXTURES.get(i);
            Identifier spriteId = switchTexture.id();

            try {
                NativeImage textureImage = NativeImage.read(
                        this.client.getResourceManager().getResource(
                                switchTexture.texture(preparedSprite.isOn())
                        ).get().getInputStream()
                );

                SpriteDimensions dimensions = new SpriteDimensions(textureImage.getWidth(), textureImage.getHeight());
                SpriteContents contents = new SpriteContents(spriteId, dimensions, textureImage, AnimationResourceMetadata.EMPTY);
                SwitchSprite sprite = new SwitchSprite(SWITCH_ATLAS_TEXTURE, contents, getWidth(), getHeight(), i * 16, preparedSprite.yPos());

                sprite.upload();
                groupSprites.put(spriteId, sprite);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return groupSprites;
    }

    public int getSpriteY(String group) {
        Map<Identifier, SwitchSprite> spriteGroup = this.sprites.get(group);
        if (spriteGroup != null) {
            SwitchSprite sprite = spriteGroup.get(SwitchTextures.FALLBACK.id());
            if (sprite != null) {
                return sprite.getY();
            }
        }
        return updateHeight();
    }

    // Atlas Size Stuff ////////////////////////////////////////////////////////////////////////////////////////////////

    private int updateHeight() {
        this.shouldReload = true;
        this.height += 16;
        return this.height - 16;
    }

    private int getHeight() {
        return this.height;
    }

    private int getWidth() {
        return SwitchTextures.TEXTURES.size() * 16;
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
    
    // MISC ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void load(ResourceManager manager) {}

    @Override
    public void close() {
        this.sprites = Map.of();
    }

    // new class extending Sprite b/c it's protected, functions no differently
    protected static class SwitchSprite extends Sprite {
        public SwitchSprite(Identifier atlasId, SpriteContents contents, int atlasWidth, int atlasHeight, int x, int y) {
            super(atlasId, contents, atlasWidth, atlasHeight, x, y);
        }
    }

    // Stores information when updating/creating sprites so that new sprites go on a new row
    private record PreparedSwitchSprite(String group, boolean isOn, int yPos) {}
}
