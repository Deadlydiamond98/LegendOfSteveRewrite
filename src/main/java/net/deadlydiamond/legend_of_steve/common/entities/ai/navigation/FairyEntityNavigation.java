package net.deadlydiamond.legend_of_steve.common.entities.ai.navigation;

import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FairyEntityNavigation extends BirdNavigation {
    public FairyEntityNavigation(MobEntity mobEntity, World world) {
        super(mobEntity, world);
        this.setCanSwim(false);
    }

    @Override
    public boolean isValidPosition(BlockPos pos) {
        return !this.world.getBlockState(pos.down()).isAir();
    }
}
