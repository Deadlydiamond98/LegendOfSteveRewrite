package net.deadlydiamond.legend_of_steve.common.entities.ai.goals;

import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.BaseTektiteEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.registry.tag.FluidTags;

import java.util.EnumSet;

public class TektiteSwimGoal extends Goal {
    private final BaseTektiteEntity mob;
    
    // FUN FACT! Enabling the ability to walk on fluids fucks with the jump controllers ability to allow swimming, 
    // so Tektites need a custom goal for swimming!!!!! I spent a lot longer debugging why tektites wanted to just sit
    // there and drown this than I realistically needed to!

    public TektiteSwimGoal(BaseTektiteEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.JUMP));
        mob.getNavigation().setCanSwim(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.isTektiteSwimming = false;
    }

    @Override
    public boolean canStart() {
        return this.mob.isTouchingWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getSwimHeight() || this.mob.isInLava();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.mob.getRandom().nextFloat() < 0.8F) {
            this.mob.getJumpControl().setActive();
            this.mob.isTektiteSwimming = true;
        }
    }
}
