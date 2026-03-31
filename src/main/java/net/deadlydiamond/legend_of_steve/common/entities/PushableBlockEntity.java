package net.deadlydiamond.legend_of_steve.common.entities;

import net.deadlydiamond.legend_of_steve.networking.s2c.pushable_block.AddBlockBreakCooldownS2CPacket;
import net.deadlydiamond.legend_of_steve.networking.s2c.pushable_block.UpdatePushableBlockBreakProgressS2CPacket;
import net.deadlydiamond98.koalalib.common.entity.IHitEntityAction;
import net.deadlydiamond98.koalalib.common.entity.LerpedMovmentEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class PushableBlockEntity extends LerpedMovmentEntity implements IHitEntityAction {
    private static final TrackedData<BlockState> BLOCK = DataTracker.registerData(PushableBlockEntity.class, TrackedDataHandlerRegistry.BLOCK_STATE);
    private ItemStack itemStack = ItemStack.EMPTY;
    public float breakingProgress;
    public float blockBreakingSoundCooldown;
    public int stopBreakingTimer;

    public PushableBlockEntity(EntityType<?> type, World world) {
        super(type, world);
        this.intersectionChecked = true;
    }

    // PUSHING /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void tick() {
        super.tick();

        this.move(MovementType.SELF, this.getVelocity());

        if (!getWorld().isClient()) {
            // Update Block
            if (getBlock() == null || getBlock().isAir()) {
                this.setBlock(Blocks.DIRT.getDefaultState());
            }

            if (this.breakingProgress > 0 && this.stopBreakingTimer++ > 2) {
                this.breakingProgress = 0;
                updateClientBreakingProgress();
            }

            // Move Block

            this.setVelocity(this.getVelocity().multiply(isTouchingWater() ? 0.6 : 0.98));

            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0, -0.04, 0));
            }

            if (this.isOnGround()) {
                float slipperiness = getWorld().getBlockState(getBlockPos().down()).getBlock().getSlipperiness();
                this.setVelocity(this.getVelocity().multiply(slipperiness, 1, slipperiness));
            }

            List<Entity> pushers = getWorld().getOtherEntities(this, this.getBoundingBox().expand(0.2f, -0.1f, 0.2f),
                    EntityPredicates.EXCEPT_SPECTATOR.and(entity -> entity instanceof PlayerEntity));

            pushers.forEach(entity -> {
                if (entity instanceof PlayerEntity player && this.isOnGround() &&
                        this.getPos().y <= player.getPos().getY() && player.isOnGround()) {
                    push(player);
                }
            });

            this.velocityDirty = true;
        }
    }

    public void push(PlayerEntity entity) {
        double e;
        if (this.isConnectedThroughVehicle(entity)) {
            return;
        }
        if (entity.noClip || this.noClip) {
            return;
        }
        double d = entity.getX() - this.getX();
        double f = MathHelper.absMax(d, e = entity.getZ() - this.getZ());
        if (f >= (double)0.01f) {
            f = Math.sqrt(f);
            d /= f;
            e /= f;
            double g = 1.0 / f;
            if (g > 1.0) {
                g = 1.0;
            }
            d *= g;
            e *= g;

            float multiplier = 0.025f;
            d *= multiplier;
            e *= multiplier;

            Direction direction = getDirection(entity, getPos());
            if (direction == Direction.EAST || direction == Direction.WEST) {
                this.setVelocity(this.getVelocity().add(-d, 0, 0));
            } else {
                this.setVelocity(this.getVelocity().add(0, 0, -e));
            }
        }
    }

    private Direction getDirection(PlayerEntity player, Vec3d center) {
        double dx = player.getX() - (center.getX());
        double dz = player.getZ() - (center.getZ());

        double angleRadians = Math.atan2(dz, dx);
        double angleDegrees = Math.toDegrees(angleRadians);

        angleDegrees = (angleDegrees + 360) % 360;

        Direction direction;

        if (angleDegrees >= 45 && angleDegrees < 135) {
            direction = Direction.SOUTH;
        } else if (angleDegrees >= 135 && angleDegrees < 225) {
            direction = Direction.WEST;
        } else if (angleDegrees >= 225 && angleDegrees < 315) {
            direction = Direction.NORTH;
        } else {
            direction = Direction.EAST;
        }
        return direction;
    }

    // MINING //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void attack(Entity entity, World world, PlayerEntity player) {
        player.swingHand(Hand.MAIN_HAND);

        if (world instanceof ServerWorld server) {
            this.breakingProgress += getBlock().calcBlockBreakingDelta(player, world, this.getBlockPos());

            Vec3d offset = this.getPos().offset(getDirection(player, this.getPos()), 0.51f);
            server.spawnParticles(
                    new BlockStateParticleEffect(ParticleTypes.BLOCK, getBlock()),
                    offset.x, offset.y + 0.5, offset.z, 1,
                    0.25, 0.25, 0.25, 0.1
            );

            if (player.isCreative()) {
                breakBlock(world, player);
            } else {
                if (this.blockBreakingSoundCooldown++ % 4 == 0) {
                    BlockSoundGroup soundGroup = getBlock().getSoundGroup();
                    world.playSound(null, this.getBlockPos(), soundGroup.getHitSound(), SoundCategory.BLOCKS,
                            (soundGroup.getVolume() + 1.0f) / 8.0f, soundGroup.getPitch() * 0.5f
                    );
                }

                updateClientBreakingProgress();
                this.stopBreakingTimer = 0;

                if (this.breakingProgress >= 1) {
                    breakBlock(world, player);
                }
            }
        }
    }

    public void breakBlock(World world, PlayerEntity player) {
        getWorld().sendEntityStatus(this, (byte) 3);
        if (player.canHarvest(getBlock()) && !this.isRemoved() && !player.isCreative()) {
            dropBlockItem();
        }
        BlockSoundGroup blockSoundGroup = getBlock().getSoundGroup();
        world.playSound(
                null, this.getBlockPos(), blockSoundGroup.getBreakSound(), SoundCategory.BLOCKS,
                (blockSoundGroup.getVolume() + 1) / 2, blockSoundGroup.getPitch() * 0.8f
        );
        AddBlockBreakCooldownS2CPacket.send((ServerPlayerEntity) player);
        this.discard();
    }

    public void dropBlockItem() {
        if (this.itemStack.isEmpty()) {
            Block.dropStacks(getBlock(), getWorld(), getBlockPos());
        } else {
            getWorld().spawnEntity(new ItemEntity(getWorld(), getX(), getY() + 0.5f, getZ(), this.itemStack));
        }
    }

    public int getBreakStage() {
        return this.breakingProgress > 0.0f ? Math.min(9, (int)(this.breakingProgress * 10.0f)) : -1;
    }

    private void updateClientBreakingProgress() {
        if (getWorld() instanceof ServerWorld server) {
            server.getPlayers().forEach(player -> {
                UpdatePushableBlockBreakProgressS2CPacket.send(player, this.breakingProgress, this);
            });
        }
    }


    @Override
    public boolean allowAttackHolding() {
        return true;
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == 3) {
            getWorld().addBlockBreakParticles(getBlockPos(), getBlock());
        }
    }

    public void setBlockBreakProgress(float breakingProgress) {
        this.breakingProgress = breakingProgress;
    }

    // OTHER ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(BLOCK, Blocks.DIRT.getDefaultState());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setBlock(NbtHelper.toBlockState(this.getWorld().createCommandRegistryWrapper(RegistryKeys.BLOCK), nbt.getCompound("BlockState")));
        NbtCompound nbtCompound = nbt.getCompound("Item");
        this.itemStack = ItemStack.fromNbt(nbtCompound);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("BlockState", NbtHelper.fromBlockState(getBlock()));
        if (!this.itemStack.isEmpty()) {
            nbt.put("Item", this.itemStack.writeNbt(new NbtCompound()));
        }
    }

    public BlockState getBlock() {
        return this.dataTracker.get(BLOCK);
    }

    public void setBlock(BlockState block) {
        this.dataTracker.set(BLOCK, block);
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
