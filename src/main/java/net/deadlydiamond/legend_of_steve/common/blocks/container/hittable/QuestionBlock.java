package net.deadlydiamond.legend_of_steve.common.blocks.container.hittable;

import net.deadlydiamond.legend_of_steve.common.bes.container.single.hittable_block.QuestionBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.container.single.SingleSlotBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.IJumpIntoAction;
import net.deadlydiamond.legend_of_steve.common.blocks.container.WaterloggableSingleSlotBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.init.ZeldaDamageTypes;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.networking.s2c.question_block.UpdateProjectileHitS2CPacket;
import net.deadlydiamond98.koalalib.common.blocks.interaction.IHitBlockAction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class QuestionBlock extends WaterloggableSingleSlotBlock implements IJumpIntoAction, IHitBlockAction {
    public static final BooleanProperty BOUNCING = BooleanProperty.of("bouncing");
    public static final BooleanProperty HIT = BooleanProperty.of("hit");

    public QuestionBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HIT, true).with(BOUNCING, false));
    }

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

    @Override
    protected SoundEvent getInsertSound() {
        return ZeldaSounds.LOOT_POT_DEPOSIT;
    }

    @Override
    protected SoundEvent getRemoveSound() {
        return ZeldaSounds.LOOT_POT_WITHDRAW;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BOUNCING, HIT);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return state.get(BOUNCING) ? BlockRenderType.ENTITYBLOCK_ANIMATED : BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeldaBlockEntities.QUESTION_BLOCK, QuestionBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new QuestionBlockEntity(pos, state);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        super.onProjectileHit(world, state, hit, projectile);
        if (!world.isClient()) {
            hitBlock(world, hit.getBlockPos(), state, projectile, hit.getSide().getOpposite(), true);
            world.getPlayers().forEach(player -> UpdateProjectileHitS2CPacket.send(player, hit.getBlockPos(), hit.getSide().getOpposite(), true));
        }
    }

    @Override
    public void attack(BlockState blockState, BlockPos blockPos, World world, PlayerEntity playerEntity) {
        if (!(playerEntity.isCreative() || playerEntity.canHarvest(blockState))) {
            HitResult hitResult = playerEntity.raycast(5, 0, false);
            if (hitResult instanceof BlockHitResult blockHitResult) {
                hitBlock(world, blockPos, blockState, playerEntity, blockHitResult.getSide().getOpposite(), false);
            }
        }
    }

    @Override
    public void jumpIntoBlock(World world, BlockPos pos, BlockState state, @Nullable Entity entity) {
        hitBlock(world, pos, state, entity, Direction.UP, true);
    }

    public void hitBlock(World world, BlockPos pos, BlockState state, @Nullable Entity entity, Direction direction, boolean playOtherHitSound) {
        if (world.getBlockEntity(pos) instanceof QuestionBlockEntity questionBlock) {
            questionBlock.setBounceDirection(direction);

            if (!state.get(HIT) && !state.get(BOUNCING)) {
                questionBlock.checkLootInteraction(entity instanceof PlayerEntity player ? player : null);
                world.setBlockState(pos, state.with(BOUNCING, true));
                questionBlock.markDirty();

                if (!world.isClient()) {
                    BlockSoundGroup group = getSoundGroup(state);

                    if (playOtherHitSound) {
                        world.playSound(null, pos, group.getHitSound(), SoundCategory.BLOCKS,
                                (group.getVolume() + 1.0f) / 8.0f, group.getPitch() * 0.5f
                        );
                    }

                    world.playSound(null, pos, ZeldaSounds.QUESTION_BLOCK_HIT, SoundCategory.BLOCKS, 1, 1);
                }

                if (direction == Direction.UP) {
                    world.getOtherEntities(null, new Box(pos).offset(0, 0.5, 0)).forEach(target -> {
                        target.setVelocity(target.getVelocity().add(0, 0.5, 0));
                        target.velocityDirty = true;

                        if (target instanceof LivingEntity living) {
                            living.damage(ZeldaDamageTypes.of(world, entity, ZeldaDamageTypes.QUESTION_BLOCK), 2);
                        }
                    });
                }
            }
        }
    }

    public void emptyContents(World world, BlockPos pos, BlockState blockState, QuestionBlockEntity blockEntity) {
        world.setBlockState(pos, blockState.with(QuestionBlock.BOUNCING, false).with(QuestionBlock.HIT, true));
        BlockPos depositPos = pos.up();

        if (!world.getBlockState(depositPos).isAir()) {
            Vec3d direction = blockEntity.getDepositDirection();
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

        for (ItemStack stack : blockEntity.getInvStackList()) {
            Vec3d difference = pos.toCenterPos().subtract(depositPos.toCenterPos());
            Direction direction = Direction.fromVector((int) difference.getX(), (int) difference.getY(), (int) difference.getZ());
            Vec3d itemPos = depositPos.toCenterPos();
            if (direction != null) {
                itemPos = itemPos.offset(direction, 0.25f);
            }

            difference = difference.multiply(0.25).negate();

            ItemEntity item = new ItemEntity(
                    world, itemPos.getX(), itemPos.getY(), itemPos.getZ(), stack,
                    difference.x, difference.y, difference.z
            );

            world.spawnEntity(item);
        }
        world.playSound(null, pos, ZeldaSounds.QUESTION_BLOCK_EMPTY_CONTENTS, SoundCategory.BLOCKS, 1, 1);

        blockEntity.clear();
    }

    @Override
    public boolean allowAttackHolding() {
        return false;
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return state.get(BOUNCING) ? VoxelShapes.empty() : super.getCullingShape(state, world, pos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(BOUNCING) ? VoxelShapes.empty() : super.getOutlineShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }
}
