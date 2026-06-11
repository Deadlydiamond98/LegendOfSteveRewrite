package net.deadlydiamond.legend_of_steve.util;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;

import java.util.Optional;

public class ZeldaModels {
    public static final Model LOOT_POT = template("loot_pot", TextureKey.ALL, TextureKey.PARTICLE);
    public static final Model SWORD_PEDESTAL = template("sword_pedestal", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.FRONT);
    public static final Model BRAZIER = template("brazier", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE);
    public static final Model BRAZIER_LIT = template("brazier_lit", "_lit", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.FIRE);
    public static final Model TALL_BRAZIER_BOTTOM = template("tall_brazier_bottom", "_bottom", TextureKey.BOTTOM, TextureKey.SIDE);

    public static final Model SWITCH_BLOCK = switchBlock("switch_block", TextureKey.ALL, TextureKey.PARTICLE);
    public static final Model SWITCH_SLAB = switchBlock("switch_slab", TextureKey.SIDE, TextureKey.TOP, TextureKey.PARTICLE);
    public static final Model SWITCH_SLAB_TOP = switchBlock("switch_slab_top", TextureKey.SIDE, TextureKey.TOP, TextureKey.PARTICLE);
    public static final Model SWITCH_SLAB_FULL = switchBlock("switch_slab_full", TextureKey.SIDE, TextureKey.TOP, TextureKey.PARTICLE);


    private static Model template(String id, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(LegendOfSteve.id("block/template_" + id)), Optional.empty(), requiredTextureKeys);
    }

    private static Model template(String id, String variant, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(LegendOfSteve.id("block/template_" + id)), Optional.of(variant), requiredTextureKeys);
    }

    private static Model switchBlock(String id, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(LegendOfSteve.id("block/switch/" + id)), Optional.empty(), requiredTextureKeys);
    }
}
