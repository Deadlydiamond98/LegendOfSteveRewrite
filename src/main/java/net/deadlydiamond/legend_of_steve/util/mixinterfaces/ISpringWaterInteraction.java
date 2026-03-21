package net.deadlydiamond.legend_of_steve.util.mixinterfaces;

import net.minecraft.entity.Entity;

public interface ISpringWaterInteraction {
    boolean legend_of_steve$isInSpringWater();
    void legend_of_steve$tickSpringWaterInteraction(Entity entity, int submergedTime);
}
