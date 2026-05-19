package net.deadlydiamond.legend_of_steve.common.entities.ai.navigation;

import net.deadlydiamond.legend_of_steve.common.entities.living.tektite.BaseTektiteEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TektiteNavigation extends MobNavigation {
    public TektiteNavigation(BaseTektiteEntity entity, World world) {
        super(entity, world);
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new LandPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(true);
        this.nodeMaker.setCanSwim(true);
        return new PathNodeNavigator(this.nodeMaker, range);
    }

    @Override
    protected boolean canWalkOnPath(PathNodeType pathType) {
        return pathType == PathNodeType.WATER || super.canWalkOnPath(pathType);
    }

    @Override
    public boolean isValidPosition(BlockPos pos) {
        return this.world.getBlockState(pos).isOf(Blocks.WATER) || super.isValidPosition(pos);
    }
}
