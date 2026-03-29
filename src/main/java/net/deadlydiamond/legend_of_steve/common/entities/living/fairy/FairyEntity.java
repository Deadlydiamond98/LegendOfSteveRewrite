package net.deadlydiamond.legend_of_steve.common.entities.living.fairy;

import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.WanderAroundFlyingGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.navigation.FairyEntityNavigation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class FairyEntity extends PathAwareEntity implements Flutterer {

    public FairyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(5, new WanderAroundFlyingGoal(this));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new FairyEntityNavigation(this, world);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10 : 0;
    }

    @Override
    public boolean isInAir() {
        return !this.isOnGround();
    }

    @Override protected void playStepSound(BlockPos pos, BlockState state) {}
    @Override protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {}

    public static DefaultAttributeContainer.Builder createCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48);
    }

    // GETTERS & SETTERS ///////////////////////////////////////////////////////////////////////////////////////////////
}
