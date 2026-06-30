package net.deadlydiamond.legend_of_steve.common.entities.projectile.bomb;

import net.deadlydiamond.legend_of_steve.common.entities.living.fish.BombfishEntity;
import net.deadlydiamond.legend_of_steve.common.entities.living.fish.BombfishVarients;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WaterBombEntity extends BombEntity {
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(WaterBombEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final float MAX_TAIL_ANGLE = 0.1625f;
    private static final float TAIL_GRAVITY = 0.05f;

    private float tailAngle = MAX_TAIL_ANGLE;
    private float prevTailAngle = MAX_TAIL_ANGLE;
    private float posDifference = 0;

    public WaterBombEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.setWaterDrag(0.8f);
        this.setBounciness(0.6f);
        this.setBuoyancy(0.025f);
    }

    @Override
    public void tick() {
        if (this.firstUpdate) {
            this.setColor(BombfishVarients.getRandom(getWorld()));
        }

        this.prevTailAngle = this.tailAngle;
        super.tick();

        this.tailAngle += TAIL_GRAVITY;

        this.posDifference = (float) (getPos().y - this.prevY);
        this.tailAngle += this.posDifference * 0.6f;
        this.tailAngle *= 0.95f;

        this.tailAngle = MathHelper.clamp(this.tailAngle, -MAX_TAIL_ANGLE, MAX_TAIL_ANGLE);
    }

    @Override
    protected void hitSurface() {
        if (MathHelper.abs(this.posDifference) > 0.05) {
            this.playSound(ZeldaSounds.BOMBFISH_FLOP, 1, 1);
        }
    }

    public float getTailAngle(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.prevTailAngle, this.tailAngle);
    }

    @Override
    protected void createBombFuseParticles() {}

    @Override
    public boolean isFuseInWater() {
        return false;
    }

    @Override
    public boolean shouldWaterBottleExtinguish() {
        return false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, 0);
    }

    public void setColor(int color) {
        this.dataTracker.set(COLOR, color);
    }

    public int getColor() {
        return this.dataTracker.get(COLOR);
    }
}
