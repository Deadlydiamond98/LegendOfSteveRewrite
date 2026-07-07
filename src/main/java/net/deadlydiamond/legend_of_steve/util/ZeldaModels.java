package net.deadlydiamond.legend_of_steve.util;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ZeldaModels {

    // BUILTIN /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final TexturedModel.Factory BUILTIN_ITEM = TexturedModel.makeFactory(TextureMap::particle, new Model(Optional.of(new Identifier("block/block")), Optional.empty(), TextureKey.PARTICLE));
    public static final TexturedModel.Factory BUILTIN_CHEST = TexturedModel.makeFactory(TextureMap::particle, block("custom_chest", TextureKey.PARTICLE));

    // ITEMS ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final Model KEY = item("key", TextureKey.LAYER0);

    // BLOCKS //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final Model BRAZIER = template("brazier", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE);
    public static final Model BRAZIER_LIT = template("brazier_lit", "_lit", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.FIRE);
    public static final Model TALL_BRAZIER_BOTTOM = template("tall_brazier_bottom", "_bottom", TextureKey.BOTTOM, TextureKey.SIDE);

    public static final Model DUNGEONCITE_PRESSURE_PLATE = block("dungeoncite_pressure_plate_up", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE);
    public static final Model DUNGEONCITE_PRESSURE_PLATE_DOWN = block("dungeoncite_pressure_plate_down", "_down", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE);

    public static final Model DOOR_LOCK_BOTTOM = block("locks/door_lock_bottom", "_bottom", TextureKey.ALL);
    public static final Model DOOR_LOCK_TOP = block("locks/door_lock_top", "_top", TextureKey.ALL);
    public static final Model CHEST_LOCK_BLOCK = block("locks/chest_lock_block", TextureKey.ALL);
    public static final Model LEFT_CHEST_LOCK_BLOCK = block("locks/chest_lock_block_double_left", "_double_left", TextureKey.ALL);
    public static final Model RIGHT_CHEST_LOCK_BLOCK = block("locks/chest_lock_block_double_right", "_double_right", TextureKey.ALL);
    public static final Model LOCK_BLOCK = block("locks/lock_block", TextureKey.ALL);

    public static final Model LOOT_POT = template("loot_pot", TextureKey.ALL, TextureKey.PARTICLE);

    public static final Model SWORD_PEDESTAL = template("sword_pedestal", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.FRONT);

    public static final Model TILE = template("tile", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE);

    // SWITCH BLOCKS ///////////////////////////////////////////////////////////////////////////////////////////////////

    public static final Model SWITCH_BLOCK = switchBlock("switch_block", TextureKey.ALL, TextureKey.PARTICLE);
    public static final Model SWITCH_SLAB = switchBlock("switch_slab", TextureKey.SIDE, TextureKey.TOP, TextureKey.PARTICLE);
    public static final Model SWITCH_SLAB_TOP = switchBlock("switch_slab_top", TextureKey.SIDE, TextureKey.TOP, TextureKey.PARTICLE);
    public static final Model SWITCH_SLAB_FULL = switchBlock("switch_slab_full", TextureKey.SIDE, TextureKey.TOP, TextureKey.PARTICLE);

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    // TODO: remove template methods & change model names to accommodate

    private static Model template(String id, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(LegendOfSteve.id("block/template_" + id)), Optional.empty(), requiredTextureKeys);
    }

    private static Model template(String id, String variant, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(LegendOfSteve.id("block/template_" + id)), Optional.of(variant), requiredTextureKeys);
    }

    private static Model switchBlock(String id, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(LegendOfSteve.id("block/switch/" + id)), Optional.empty(), requiredTextureKeys);
    }

    // BASE HELPER /////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Model item(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(LegendOfSteve.id("item/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(LegendOfSteve.id("block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    private static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(LegendOfSteve.id("block/" + parent)), Optional.of(variant), requiredTextureKeys);
    }
}
