package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario;

import net.deadlydiamond.legend_of_steve.common.bes.container.QuestionBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.container.single.SingleSlotBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.container.single.SingleSlotBlock;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base.IBouncableBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.common.ZeldaProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class QuestionBlock extends SingleSlotBlock implements IBouncableBlock {
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty HIT = ZeldaProperties.HIT;

    public QuestionBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HIT, true).with(POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HIT, POWERED);
    }

    @Nullable @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new QuestionBlockEntity(pos, state);
    }

    // PLACING /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (world.getBlockEntity(pos) instanceof QuestionBlockEntity entity) {
            world.setBlockState(pos, state.with(HIT, entity.isHit()));
        }
    }

    // FILLING /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean canInsertItem(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, SingleSlotBlockEntity blockEntity) {
        boolean canInsert = super.canInsertItem(state, world, pos, player, hand, stack, blockEntity);
        if (canInsert && state.get(HIT)) {
            world.setBlockState(pos, state.with(HIT, false));
        }
        return canInsert;
    }

    @Override
    public boolean canRemoveItem(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, SingleSlotBlockEntity blockEntity) {
        return false;
    }

    // PROJECTILE INTERACTION //////////////////////////////////////////////////////////////////////////////////////////

    protected boolean canProjectileTrigger(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        return true;
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (canProjectileTrigger(world, state, hit, projectile) && canBounceBlock(world, hit.getBlockPos(), state) && !world.isClient()) {
            triggerBounce(world, hit.getBlockPos(), state, projectile, hit.getSide().getOpposite(), BounceType.PROJECTILE);
        }
    }

    // REDSTONE INTERACTION ////////////////////////////////////////////////////////////////////////////////////////////

    protected boolean canRedstoneTrigger(World world, BlockState state, BlockPos pos) {
        return true;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (canRedstoneTrigger(world, state, pos) && canBounceBlock(world, pos, state)) {
            if (!world.isClient()) {
                boolean bl = world.isReceivingRedstonePower(pos);
                boolean bl2 = state.get(POWERED);

                world.setBlockState(pos, state.with(POWERED, bl), Block.NO_REDRAW);

                if (bl && !bl2) {
                    triggerBounce(world, pos, state.with(POWERED, true), null, getRedstoneInputDirection(world, pos).getOpposite(), BounceType.REDSTONE);
                }
            }
        }
    }

    public Direction getRedstoneInputDirection(World world, BlockPos pos) {
        Direction recievedDirection = Direction.UP;
        int i = 0;

        for (Direction direction : DIRECTIONS) {
            int j = world.getEmittedRedstonePower(pos.offset(direction), direction);
            if (j >= 15) {
                return direction;
            }

            if (j > i) {
                i = j;
                recievedDirection = direction;
            }
        }

        return recievedDirection;
    }

    // HITTING /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public BlockState getPostBounceState(BlockState originalState) {
        return originalState.with(HIT, true);
    }

    @Override
    public boolean canBounceBlock(World world, BlockPos pos, BlockState state) {
        return !state.get(HIT);
    }

    @Override
    public boolean canPunchTrigger(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        return !blockState.get(HIT);
    }

    @Override
    public void beforeBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction direction, BounceType type) {}

    @Override
    public void afterBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction bouncedDirection, BounceType bounceType, @Nullable DefaultedList<ItemStack> inventory) {
        if (inventory != null) {
            emptyContents(world, pos, state, owner, bouncedDirection, bounceType, inventory);
        }
    }

    protected void emptyContents(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction bouncedDirection, BounceType bounceType, DefaultedList<ItemStack> inventory) {
        BlockPos depositPos = pos.up();

        if (!world.getBlockState(depositPos).isAir()) {
            Vec3d direction = Vec3d.of(bouncedDirection.getVector());
            depositPos = pos.add((int) direction.x, (int) direction.y, (int) direction.z);
            if (!world.getBlockState(depositPos).isAir()) {
                depositPos = pos;
                for (Direction value : Direction.values()) {
                    if (world.getBlockState(pos.offset(value)).isAir()) {
                        depositPos = pos.offset(value);
                        break;
                    }
                }
            }
        }

        for (ItemStack stack : inventory) {
            Vec3d difference = pos.toCenterPos().subtract(depositPos.toCenterPos());
            difference = difference.multiply(0.25).negate();

            Direction direction = Direction.fromVector((int) difference.getX(), (int) difference.getY(), (int) difference.getZ());

            Vec3d spawnPos = depositPos.toCenterPos().offset(Direction.DOWN, 0.25f);

            if (direction != null) {
                spawnPos = spawnPos.offset(direction, 0.25f);
            }

            if (stack.getItem() instanceof SpawnEggItem spawnEggItem) {
                if (world instanceof ServerWorld server) {
                    for (int i = 0; i < stack.getCount(); i++) {
                        Entity entity = spawnEggItem.getEntityType(stack.getNbt()).create(world);
                        if (entity != null) {
                            if (direction == Direction.DOWN) {
                                spawnPos = spawnPos.offset(direction, entity.getHeight());
                            }

                            entity.setPosition(spawnPos);
                            entity.setVelocity(difference.multiply(2));
                            entity.velocityDirty = true;
                            if (entity instanceof MobEntity mobEntity) {
                                mobEntity.initialize(server, world.getLocalDifficulty(mobEntity.getBlockPos()),
                                        SpawnReason.SPAWN_EGG, null, stack.getNbt());
                            }
                            world.spawnEntity(entity);
                        }
                    }
                }

            } else {
                ItemEntity item = new ItemEntity(
                        world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), stack,
                        difference.x, difference.y, difference.z
                );

                world.spawnEntity(item);
            }
        }

        if (getEmptyingSound() != null) {
            world.playSound(null, pos, getEmptyingSound(), SoundCategory.BLOCKS, 1, 1);
        }
    }

    // SOUNDS //////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Nullable
    protected SoundEvent getEmptyingSound() {
        return ZeldaSounds.QUESTION_BLOCK_EMPTY_CONTENTS;
    }

    @Override
    protected SoundEvent getInsertSound(ItemStack stack) {
        return ZeldaSounds.QUESTION_BLOCK_DEPOSIT;
    }

    @Override
    protected SoundEvent getRemoveSound(ItemStack stack) {
        return null;
    }
}
