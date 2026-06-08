package net.deadlydiamond.legend_of_steve.common.bes;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base.IBouncableBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.networking.s2c.question_block.UpdateBounceBlockPostHitS2CPacket;
import net.deadlydiamond98.koalalib.util.KoalaNbtHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BouncingBlockEntity extends BlockEntity {
    public static final float RETURN_SPEED = -0.5f;
    public static final float BOUNCE = 0.25f;
    public static final int BOUNCE_TIME = 5;

    private Vec3d bouncePos = Vec3d.ZERO;
    private Vec3d prevBouncePos = Vec3d.ZERO;
    private Vec3d bounceDirection;
    private int bounceTimer, bounceTimerMax;
    private float bounceMoveSpeed = BOUNCE;
    protected BlockState startState, endState;

    @Nullable private UUID ownerUuid;
    @Nullable private Entity owner;

    protected BounceType bounceType;

    public BouncingBlockEntity(BlockPos pos, BlockState state, BlockState startState, BlockState endState,
                               Direction direction, int bounceTimer, @Nullable Entity owner, BounceType type) {
        super(ZeldaBlockEntities.BOUNCING_BLOCK, pos, state);
        setBounceDirection(direction);

        this.startState = startState;
        this.endState = endState;

        this.bounceTimer = bounceTimer;
        this.bounceTimerMax = bounceTimer;

        setOwner(owner);
        this.bounceType = type;
    }

    public BouncingBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Direction.UP, BOUNCE_TIME, null, BounceType.UNKNOWN);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BouncingBlockEntity entity) {
        entity.tick(world, pos, state);
    }

    protected void tick(World world, BlockPos pos, BlockState state) {
        this.prevBouncePos = this.bouncePos;

        if (this.bounceTimer-- <= 0) {
            if (!world.isClient() && this.bounceTimer < -1) {
                world.setBlockState(pos, this.endState);
                if (this.startState.getBlock() instanceof IBouncableBlock block) {
                    Direction direction = Direction.fromVector(
                            (int) this.bounceDirection.x,
                            (int) this.bounceDirection.y,
                            (int) this.bounceDirection.z
                    );

                    world.getPlayers().forEach(player -> UpdateBounceBlockPostHitS2CPacket.send(
                            player, pos, this.startState, this.endState, getOwner(), direction, this.bounceType
                    ));
                    block.afterBounce(world, pos, this.endState, getOwner(), direction, this.bounceType);
                }
            }
            this.bouncePos = Vec3d.ZERO;
            this.bounceMoveSpeed = 0;
        } else {
            this.bouncePos = this.bouncePos.add(this.bounceDirection.multiply(this.bounceMoveSpeed));
            this.bounceMoveSpeed += RETURN_SPEED / this.bounceTimerMax;
        }
        markDirty();
    }

    public VoxelShape getCollision(BlockView world, BlockPos pos) {
        VoxelShape voxelShape = getRenderedBlock().getCollisionShape(world, pos);
        return voxelShape.offset(this.bouncePos.x, this.bouncePos.y, this.bouncePos.z);
    }

    public BlockState getRenderedBlock() {
        return (this.bounceTimer <= 0) ? this.endState : this.startState;
    }

    protected void setBounceDirection(Direction direction) {
        Vec3i dir = direction.getVector();
        this.bounceDirection = new Vec3d(dir.getX(), dir.getY(), dir.getZ());
    }

    public Vec3d getBouncePos(float delta) {
        double d = MathHelper.lerp(delta, this.prevBouncePos.x, this.bouncePos.x);
        double e = MathHelper.lerp(delta, this.prevBouncePos.y, this.bouncePos.y);
        double f = MathHelper.lerp(delta, this.prevBouncePos.z, this.bouncePos.z);
        return new Vec3d(d, e, f);
    }

    public void setOwner(@Nullable Entity entity) {
        if (entity != null) {
            this.ownerUuid = entity.getUuid();
            this.owner = entity;
        }
    }

    @Nullable
    public Entity getOwner() {
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        } else if (this.ownerUuid != null && this.getWorld() instanceof ServerWorld) {
            this.owner = ((ServerWorld)this.getWorld()).getEntity(this.ownerUuid);
            return this.owner;
        } else {
            return null;
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        RegistryEntryLookup<Block> registryEntryLookup = this.world != null ?
                this.world.createCommandRegistryWrapper(RegistryKeys.BLOCK) :
                Registries.BLOCK.getReadOnlyWrapper();

        this.startState = NbtHelper.toBlockState(registryEntryLookup, nbt.getCompound("StartingState"));
        this.endState = NbtHelper.toBlockState(registryEntryLookup, nbt.getCompound("EndState"));

        this.bouncePos = KoalaNbtHelper.vec3dFromNBT(nbt.getCompound("BouncePos"));
        this.bounceDirection = KoalaNbtHelper.vec3dFromNBT(nbt.getCompound("BounceDirection"));

        this.bounceTimer = nbt.getInt("BounceTimer");
        this.bounceTimerMax = nbt.getInt("BounceTimerMax");
        this.bounceMoveSpeed = nbt.getFloat("BounceMoveSpeed");

        this.bounceType = BounceType.values()[nbt.getInt("BounceType")];

        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
            this.owner = null;
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put("StartingState", NbtHelper.fromBlockState(this.startState));
        nbt.put("EndState", NbtHelper.fromBlockState(this.endState));

        nbt.put("BouncePos", KoalaNbtHelper.vec3dToNBT(this.bouncePos));
        nbt.put("BounceDirection", KoalaNbtHelper.vec3dToNBT(this.bounceDirection));

        nbt.putInt("BounceTimer", this.bounceTimer);
        nbt.putInt("BounceTimerMax", this.bounceTimerMax);
        nbt.putFloat("BounceMoveSpeed", this.bounceMoveSpeed);

        nbt.putInt("BounceType", this.bounceType.ordinal());

        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}
