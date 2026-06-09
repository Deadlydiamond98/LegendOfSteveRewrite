package net.deadlydiamond.legend_of_steve.client;

import com.mojang.blaze3d.platform.TextureUtil;
import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.switches.SwitchTextures;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.*;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SwitchBlockAtlasBackup extends AbstractTexture implements DynamicTexture {
    @Nullable public static SwitchBlockAtlasBackup INSTANCE;
    public static final Identifier SWITCH_ATLAS = LegendOfSteve.id("switch_atlas");
    private Map<Identifier, SwitchSprite> sprites = Map.of();
    private final MinecraftClient client;

    public SwitchBlockAtlasBackup(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void load(ResourceManager manager) {}

    public static void updateSwitchSprites(String group, boolean bl) {
        if (INSTANCE != null) {
            INSTANCE.updateSprites(group, bl);
        }
    }
    
    public void updateSprites(String group, boolean bl) {
        TextureUtil.prepareImage(this.getGlId(), 0, getWidth(), getHeight());
        Map<Identifier, SwitchSprite> updatedSprites = new HashMap<>(this.sprites);

        for (int i = 0; i < SwitchTextures.TEXTURES.size(); i++) {
            SwitchTextures.SwitchTexture switchTexture = SwitchTextures.TEXTURES.get(i);
            Identifier spriteId = switchTexture.getID(group);
            
            try {
                NativeImage textureImage = NativeImage.read(
                        this.client.getResourceManager().getResource(switchTexture.getTexture(bl)).get().getInputStream()
                );

                SpriteDimensions dimensions = new SpriteDimensions(textureImage.getWidth(), textureImage.getHeight());
                SpriteContents contents = new SpriteContents(spriteId, dimensions, textureImage, AnimationResourceMetadata.EMPTY);
                SwitchSprite sprite = new SwitchSprite(SWITCH_ATLAS, contents, getWidth(), getHeight(), i * 16, 0);

                sprite.upload();
                updatedSprites.put(spriteId, sprite);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.sprites = Collections.unmodifiableMap(updatedSprites);
        this.client.getTextureManager().registerTexture(SWITCH_ATLAS, this);
    }

    public Sprite getSprite(String group, Identifier block) {
        return this.sprites.getOrDefault(block.withSuffixedPath("_" + group), this.sprites.get(LegendOfSteve.id("block/red_switch_block_global")));
    }
    
    private int getHeight() {
        return 32;
    }
    
    private int getWidth() {
        return SwitchTextures.TEXTURES.size() * 16;
    }

    @Override
    public void close() {
        this.sprites = Map.of();
    }

    @Override
    public void save(Identifier id, Path path) {
        String string = id.toUnderscoreSeparatedString();
        TextureUtil.writeAsPNG(path, string, this.getGlId(), 0, 32, 32);
    }

    protected static class SwitchSprite extends Sprite {
        public SwitchSprite(Identifier atlasId, SpriteContents contents, int atlasWidth, int atlasHeight, int x, int y) {
            super(atlasId, contents, atlasWidth, atlasHeight, x, y);
        }
    }
}
