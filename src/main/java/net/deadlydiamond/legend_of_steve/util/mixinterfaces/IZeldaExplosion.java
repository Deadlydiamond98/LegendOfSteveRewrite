package net.deadlydiamond.legend_of_steve.util.mixinterfaces;

import net.minecraft.block.Block;

import java.util.function.Predicate;

public interface IZeldaExplosion {
    void legend_of_steve$setZeldaBombOrigin(boolean bl, Predicate<Block> tag);
}
