package net.deadlydiamond.legend_of_steve.common.entities.ai.goals.bombfish;

import net.deadlydiamond.legend_of_steve.common.entities.living.fish.BombfishEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BombfishIgniteGoal extends Goal {
    private final BombfishEntity bombfish;

    public BombfishIgniteGoal(BombfishEntity creeper) {
        this.bombfish = creeper;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.bombfish.getTarget();
        return this.bombfish.chasingTarget && !this.bombfish.isPrimed() && livingEntity != null && this.bombfish.squaredDistanceTo(livingEntity) < 9.0;
    }

    @Override
    public void start() {
        this.bombfish.setPrimed(true);
        this.bombfish.playPrimedSound();
    }
}
