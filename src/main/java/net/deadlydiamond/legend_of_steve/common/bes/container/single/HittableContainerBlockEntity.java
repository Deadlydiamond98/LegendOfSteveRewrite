package net.deadlydiamond.legend_of_steve.common.bes.container.single;

import net.deadlydiamond.legend_of_steve.common.blocks.container.single.hittable.AbstractHittableContainerBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.util.ZeldaProperties;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class HittableContainerBlockEntity extends SingleSlotBlockEntity {
    public static final float RETURN_SPEED = -0.5f;
    public static final float BOUNCE = 0.25f;
    private Vec3d bouncePos, prevBouncePos, bounceDirection;
    private float bounceMoveSpeed;
    private int bounceTimer;
    private int resetTimer;

    public HittableContainerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ZeldaBlockEntities.HITTABLE_CONTAINER_BLOCK, blockPos, blockState);
        this.bouncePos = Vec3d.ZERO;
        this.prevBouncePos = Vec3d.ZERO;
        this.bounceDirection = Vec3d.ZERO;
    }

    private void tick(World world, BlockPos pos, BlockState blockState) {
        this.prevBouncePos = this.bouncePos;

        if (blockState.get(ZeldaProperties.BOUNCING)) {
            this.resetTimer = 1;
            if (this.bounceTimer <= -1) {
                this.bounceMoveSpeed = BOUNCE;
                this.bounceTimer = timerMax();
            } else {
                this.bouncePos = this.bouncePos.add(this.bounceDirection.multiply(this.bounceMoveSpeed));
                this.bounceMoveSpeed += RETURN_SPEED / timerMax();
                if (this.bounceTimer-- <= 0) {
                    if (!world.isClient()) {
                        BlockState bounceStop = blockState.with(ZeldaProperties.BOUNCING, false);
                        world.setBlockState(pos, bounceStop);
                        getBlock().postBlockHit(world, pos, bounceStop, this);
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

    public static void tick(World world, BlockPos pos, BlockState blockState, HittableContainerBlockEntity entity) {
        entity.tick(world, pos, blockState);
    }

    protected int timerMax() {
        return getBlock().getBounceTimer();
    }

    public boolean shouldRenderHit() {
        return this.resetTimer >= 0;
    }

    @Override
    public boolean insertStack(ItemStack stack) {
        return super.insertStack(stack);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) {
        return false;
    }

    @Override
    protected AbstractHittableContainerBlock getBlock() {
        return (AbstractHittableContainerBlock) this.getCachedState().getBlock();
    }
}
