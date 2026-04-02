package net.deadlydiamond.legend_of_steve.common.bes.container.single.hittable_block;

import net.deadlydiamond.legend_of_steve.common.bes.container.single.SingleSlotBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.container.hittable.QuestionBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class QuestionBlockEntity extends SingleSlotBlockEntity {
    public static final float RETURN_SPEED = -0.0625f;
    public static final float BOUNCE = 0.25f;
    private Vec3d bouncePos, prevBouncePos, bounceDirection;
    private float bounceMoveSpeed;
    private int bounceTimer;
    private int resetTimer;

    public QuestionBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ZeldaBlockEntities.QUESTION_BLOCK, blockPos, blockState);
        this.bouncePos = Vec3d.ZERO;
        this.prevBouncePos = Vec3d.ZERO;
        this.bounceDirection = Vec3d.ZERO;
    }

    private void tick(World world, BlockPos pos, BlockState blockState) {
        this.prevBouncePos = this.bouncePos;

        if (blockState.get(QuestionBlock.BOUNCING)) {
            this.resetTimer = 2;
            if (this.bounceTimer <= -1) {
                this.bounceMoveSpeed = BOUNCE;
                this.bounceTimer = 8;
            } else {
                this.bouncePos = this.bouncePos.add(this.bounceDirection.multiply(this.bounceMoveSpeed));
                this.bounceMoveSpeed += RETURN_SPEED;
                if (this.bounceTimer-- <= 0) {
                    if (!world.isClient()) {
                        if (blockState.getBlock() instanceof QuestionBlock questionBlock) {
                            questionBlock.emptyContents(world, pos, blockState, this);
                        }
                    } else {
                        this.bouncePos = Vec3d.ZERO;
                        this.bounceMoveSpeed = 0;
                    }
                }
            }
        } else {
            this.bouncePos = Vec3d.ZERO;
            this.prevBouncePos = Vec3d.ZERO;
            this.bounceDirection = Vec3d.ZERO;
            this.bounceMoveSpeed = 0;
            this.bounceTimer = -1;
            this.resetTimer--;
        }
    }

    public Vec3d getDepositDirection() {
        return this.bounceDirection;
    }

    public void setBounceDirection(Direction direction) {
        Vec3i dir = direction.getVector();
        this.bounceDirection = new Vec3d(dir.getX(), dir.getY(), dir.getZ());
        this.resetTimer = 2;
    }

    public Vec3d getBouncePos(float delta) {
        double d = MathHelper.lerp(delta, this.prevBouncePos.x, this.bouncePos.x);
        double e = MathHelper.lerp(delta, this.prevBouncePos.y, this.bouncePos.y);
        double f = MathHelper.lerp(delta, this.prevBouncePos.z, this.bouncePos.z);
        return new Vec3d(d, e, f);
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, QuestionBlockEntity entity) {
        entity.tick(world, pos, blockState);
    }

    public boolean shouldRenderHit() {
        return this.resetTimer >= 0;
    }
}
