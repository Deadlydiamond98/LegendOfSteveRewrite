package net.deadlydiamond.legend_of_steve.common.entities.block;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class CrateEntity extends PushableBlockEntity {
    public CrateEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void applyGravity() {
        if (this.isSubmergedInWater()) {
            this.setVelocity(this.getVelocity().add(0, 0.02, 0));
        } else {
            super.applyGravity();
        }
    }
}
