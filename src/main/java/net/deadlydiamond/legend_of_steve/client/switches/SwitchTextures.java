package net.deadlydiamond.legend_of_steve.client.switches;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.util.Identifier;

import java.util.List;

public class SwitchTextures {
    public static final List<SwitchTexture> TEXTURES = List.of(
            create("red_switch_block"),
            create("blue_switch_block")
    );



    private static SwitchTexture create(String id) {
        return new SwitchTexture(LegendOfSteve.id("block/" + id));
    }


    public record SwitchTexture(Identifier id) {
        public Identifier getID(String group) {
            return id().withSuffixedPath("_" + group);
        }

        public Identifier getTexture(boolean isOn) {
            String type = isOn ? "_on" : "_off";
            return new Identifier(this.id.getNamespace(), "textures/" + this.id.getPath() + type + ".png");
        }
    }
}
