package net.deadlydiamond.legend_of_steve.common.entities.living.tektite;

import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.ApproachFromFarGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.ApproachFromNearGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.HopAtTargetGoal;
import net.deadlydiamond.legend_of_steve.common.entities.ai.goals.RandomlyHopGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class BlueTektiteEntity extends BaseTektiteEntity {
    public BlueTektiteEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(3, new HopAtTargetGoal(this, 1, 0.475, 25, 7, 4));
        this.goalSelector.add(4, new ApproachFromNearGoal(this, 0.5, 5));
        this.goalSelector.add(4, new ApproachFromFarGoal(this, 1.5, 5));

        this.goalSelector.add(7, new RandomlyHopGoal(this, 0.75, 0.475, 80));
        super.initGoals();
    }

    public static DefaultAttributeContainer.Builder attributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.175);
    }
}
