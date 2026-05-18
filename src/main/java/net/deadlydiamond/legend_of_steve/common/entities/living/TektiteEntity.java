package net.deadlydiamond.legend_of_steve.common.entities.living;

import net.deadlydiamond.legend_of_steve.common.entities.ai.navigation.TektiteNavigation;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;

public class TektiteEntity extends HostileEntity {
    public final AnimationState idleAnimationState = new AnimationState();

    public TektiteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
//        this.goalSelector.add(4, new TektiteHopGoal(this, 1, 0.475, 20));

        this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.5f));

        this.goalSelector.add(7, new WanderAroundGoal(this, 1, 60));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            updateAnimations();
        }

        super.tick();
    }

    private void updateAnimations() {
        boolean isIdle = true;
        this.idleAnimationState.setRunning(isIdle, this.age);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new TektiteNavigation(this, world);
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return state.isIn(FluidTags.WATER);
    }

    public static DefaultAttributeContainer.Builder attributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.175);
    }
}
