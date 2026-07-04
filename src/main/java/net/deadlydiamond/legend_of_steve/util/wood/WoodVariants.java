package net.deadlydiamond.legend_of_steve.util.wood;

import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class WoodVariants {
    protected static final List<WoodVariant> VARIANTS = new ArrayList<>();

    // VANILLA
    public static final WoodVariant OAK = create(new WoodVariant("oak", Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_PLANKS, Blocks.OAK_STAIRS, Blocks.OAK_SLAB, Blocks.OAK_FENCE));
    public static final WoodVariant BIRCH = create(new WoodVariant("birch", Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_PLANKS, Blocks.BIRCH_STAIRS, Blocks.BIRCH_SLAB, Blocks.BIRCH_FENCE));
    public static final WoodVariant SPRUCE = create(new WoodVariant("spruce", Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_STAIRS, Blocks.SPRUCE_SLAB, Blocks.SPRUCE_FENCE));
    public static final WoodVariant JUNGLE = create(new WoodVariant("jungle", Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_STAIRS, Blocks.JUNGLE_SLAB, Blocks.JUNGLE_FENCE));
    public static final WoodVariant ACACIA = create(new WoodVariant("acacia", Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_PLANKS, Blocks.ACACIA_STAIRS, Blocks.ACACIA_SLAB, Blocks.ACACIA_FENCE));
    public static final WoodVariant DARK_OAK = create(new WoodVariant("dark_oak", Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_STAIRS, Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_FENCE));
    public static final WoodVariant CRIMSON = create(new WoodVariant("crimson", Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_STAIRS, Blocks.CRIMSON_SLAB, Blocks.CRIMSON_FENCE, false));
    public static final WoodVariant WARPED = create(new WoodVariant("warped", Blocks.WARPED_SIGN, Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_PLANKS, Blocks.WARPED_STAIRS, Blocks.WARPED_SLAB, Blocks.WARPED_FENCE, false));
    public static final WoodVariant MANGROVE = create(new WoodVariant("mangrove", Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG, Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_STAIRS, Blocks.MANGROVE_SLAB, Blocks.MANGROVE_FENCE));
    public static final WoodVariant BAMBOO = create(new WoodVariant("bamboo", Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK, Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_STAIRS, Blocks.BAMBOO_SLAB, Blocks.BAMBOO_FENCE, Blocks.BAMBOO_MOSAIC, Blocks.BAMBOO_MOSAIC_STAIRS, Blocks.BAMBOO_MOSAIC_SLAB, true));
    public static final WoodVariant CHERRY = create(new WoodVariant("cherry", Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG, Blocks.CHERRY_PLANKS, Blocks.CHERRY_STAIRS, Blocks.CHERRY_SLAB, Blocks.CHERRY_FENCE));
    // LEGEND OF STEVE
    public static final WoodVariant DEKU = create(new WoodVariant("deku", ZeldaBlocks.DEKU_WOOD, true));


    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    public static WoodVariant create(WoodVariant variant) {
        VARIANTS.add(variant);
        return variant;
    }

    public static List<WoodVariant> getAll() {
        return VARIANTS;
    }

    public static void register() {}
}
