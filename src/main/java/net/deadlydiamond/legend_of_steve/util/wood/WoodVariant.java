package net.deadlydiamond.legend_of_steve.util.wood;

import net.deadlydiamond98.koalalib.common.blocksets.WoodBlockset;
import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;



public class WoodVariant {
    private final String type;
    private final Block log, strippedLog, plank, stair, slab, fence;
    @Nullable private final Block mosaic, mosaicStairs, mosaicSlab;
    private final boolean flammable;

    public WoodVariant(String type, WoodBlockset blockset, boolean flammable) {
        this(type, blockset.log, blockset.strippedLog, blockset.plank, blockset.stair, blockset.slab, blockset.fence, flammable);
    }

    public WoodVariant(String type, Block log, Block strippedLog, Block plank, Block stair, Block slab, Block fence, boolean flammable) {
        this(type, log, strippedLog, plank, stair, slab, fence, null, null, null, flammable);
    }

    public WoodVariant(String type, Block log, Block strippedLog, Block plank, Block stair, Block slab, Block fence) {
        this(type, log, strippedLog, plank, stair, slab, fence, null, null, null, true);
    }

    public WoodVariant(String type, Block log, Block strippedLog, Block plank, Block stair, Block slab, Block fence,
                       @Nullable Block mosaic, @Nullable Block mosaicStairs, @Nullable Block mosaicSlab, boolean flammable) {
        this.type = type;

        this.log = log;
        this.strippedLog = strippedLog;
        this.plank = plank;
        this.stair = stair;
        this.slab = slab;
        this.fence = fence;

        this.mosaic = mosaic;
        this.mosaicStairs = mosaicStairs;
        this.mosaicSlab = mosaicSlab;

        this.flammable = flammable;
    }

    // GETTERS /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getType() {
        return this.type;
    }

    public boolean isFlammable() {
        return this.flammable;
    }

    // BLOCK GETTERS ///////////////////////////////////////////////////////////////////////////////////////////////////

    public Block getLog() {
        return this.log;
    }

    public Block getStrippedLog() {
        return this.strippedLog;
    }

    public Block getPlank() {
        return getPlank(false);
    }

    public Block getStair() {
        return getStair(false);
    }

    public Block getSlab() {
        return getSlab(false);
    }

    public Block getFence() {
        return this.fence;
    }

    // MOSAIC GETTERS //////////////////////////////////////////////////////////////////////////////////////////////////

    public Block getPlank(boolean useMozaic) {
        return getMozaic(useMozaic, this.plank, this.mosaic);
    }

    public Block getStair(boolean useMozaic) {
        return getMozaic(useMozaic, this.stair, this.mosaicStairs);
    }

    public Block getSlab(boolean useMozaic) {
        return getMozaic(useMozaic, this.slab, this.mosaicSlab);
    }

    private Block getMozaic(boolean useMozaic, Block regular, @Nullable Block mozaic) {
        return useMozaic && mozaic != null ? mozaic : regular;
    }
}
