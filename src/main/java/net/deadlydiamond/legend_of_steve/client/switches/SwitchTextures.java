package net.deadlydiamond.legend_of_steve.client.switches;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SwitchTextures {
    public static final SwitchTexture FALLBACK = create("red_switch_block");
    public static final List<SwitchTexture> TEXTURES = new ArrayList<>();

    static {
        add("switch_block");
        add("switch_slab");
    }

    // Helper Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    private static void add(String id) {
        TEXTURES.add(create("red_" + id));
        TEXTURES.add(create("blue_" + id));
    }

    private static SwitchTexture create(String id) {
        return new SwitchTexture(LegendOfSteve.id("block/switch/" + id));
    }

    public record SwitchTexture(Identifier id) {
        public Identifier texture(boolean isOn) {
            String type = isOn ? "_on" : "_off";
            return new Identifier(this.id.getNamespace(), "textures/" + this.id.getPath() + type + ".png");
        }
    }
}
