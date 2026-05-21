package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario;

import net.deadlydiamond.legend_of_steve.common.bes.container.single.SingleSlotBlockEntity;
import net.deadlydiamond.legend_of_steve.common.bes.container.single.HittableContainerBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.IJumpIntoAction;
import net.deadlydiamond.legend_of_steve.common.blocks.container.single.WaterloggableSingleSlotBlock;
import net.deadlydiamond.legend_of_steve.common.items.IExtraCanMine;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlockEntities;
import net.deadlydiamond.legend_of_steve.init.ZeldaDamageTypes;
import net.deadlydiamond.legend_of_steve.networking.s2c.question_block.UpdateBlockHitS2CPacket;
import net.deadlydiamond.legend_of_steve.util.ZeldaProperties;
import net.deadlydiamond98.koalalib.common.blocks.interaction.IHitBlockAction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
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
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractHittableContainerBlock extends WaterloggableSingleSlotBlock implements IJumpIntoAction, IHitBlockAction, IExtraCanMine {
    public static final BooleanProperty BOUNCING = ZeldaProperties.BOUNCING;
    public static final BooleanProperty HIT = ZeldaProperties.HIT;
    public static final BooleanProperty POWERED = Properties.POWERED;

    public AbstractHittableContainerBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HIT, startHit()).with(POWERED, false).with(BOUNCING, false));
    }

    protected boolean startHit() {
        return true;
    }
    protected abstract SoundEvent getHittingSound();
    protected abstract SoundEvent getEmptyingSound();

    // Inventory ///////////////////////////////////////////////////////////////////////////////////////////////////////

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
    protected SoundEvent getRemoveSound(ItemStack stack) {
        return null;
    }

    // Hopper Interaction //////////////////////////////////////////////////////////////////////////////////////////////

    public boolean hasHopperInteraction() {
        return true;
    }

    public boolean canInsertFromHopper(BlockState state, BlockPos pos, int slot, ItemStack stack) {
        return !state.get(BOUNCING);
    }

    public boolean canTransferToHopper(BlockState state, BlockPos pos, Inventory hopperInventory, int slot, ItemStack stack) {
        return !state.get(BOUNCING) && !state.get(HIT);
    }

    // Hitting Block ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (activatedByRedstone()) {
            if (!world.isClient()) {
                boolean bl = world.isReceivingRedstonePower(pos);
                boolean bl2 = state.get(POWERED);

                world.setBlockState(pos, state.with(POWERED, bl), Block.NO_REDRAW);

                if (bl && !bl2) {
                    hitBlock(world, pos, state, null, Direction.UP, true);
                }
            }
        }
    }

    protected abstract boolean activatedByRedstone();

    @Override
    public boolean canMineBlock(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (world.getBlockEntity(pos) instanceof HittableContainerBlockEntity questionBlock) {
            return (questionBlock.isEmpty() && !state.get(BOUNCING)) || !canAttackTrigger(state, pos, world, miner);
        }
        return true;
    }

    @Override
    public void jumpIntoBlock(World world, BlockPos pos, BlockState state, @Nullable Entity entity) {
        if (!world.isClient()) {
            hitBlock(world, pos, state, entity, Direction.UP, true);
        }
    }

    @Override
    public void attack(BlockState blockState, BlockPos blockPos, World world, PlayerEntity playerEntity) {
        if (!world.isClient() && canAttackTrigger(blockState, blockPos, world, playerEntity)) {
            HitResult hitResult = playerEntity.raycast(5, 0, false);
            if (hitResult instanceof BlockHitResult blockHitResult) {
                hitBlock(world, blockPos, blockState, playerEntity, blockHitResult.getSide().getOpposite(), false);
            }
        }
    }

    protected boolean canAttackTrigger(BlockState blockState, BlockPos blockPos, World world, PlayerEntity playerEntity) {
        return !(playerEntity.getMainHandStack().getItem() instanceof ToolItem);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient() && canProjectileTrigger(world, state, hit, projectile)) {
            hitBlock(world, hit.getBlockPos(), state, projectile, hit.getSide().getOpposite(), true);
        }
    }

    protected boolean canProjectileTrigger(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        return true;
    }

    public void hitBlock(World world, BlockPos pos, BlockState state, @Nullable Entity entity, Direction direction, boolean additionalHitSound) {
        if (entity != null && !((entity instanceof PlayerEntity) || (entity instanceof Ownable ownable && ownable.getOwner() instanceof PlayerEntity))) {
            if (!entity.canModifyAt(world, pos) && !world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return;
            }
        }

        if (!world.isClient()) {
            world.getPlayers().forEach(player -> UpdateBlockHitS2CPacket.send(player, pos, direction, additionalHitSound));
        }

        if (state.get(HIT)) {
            return;
        }

        if (world.getBlockEntity(pos) instanceof HittableContainerBlockEntity questionBlock) {
            questionBlock.setBounceDirection(direction);

            if (!state.get(HIT) && !state.get(BOUNCING)) {
                questionBlock.checkLootInteraction(entity instanceof PlayerEntity player ? player : null);
                world.setBlockState(pos, state.with(BOUNCING, true));
                questionBlock.markDirty();

                if (!world.isClient()) {
                    BlockSoundGroup group = getSoundGroup(state);

                    if (additionalHitSound) {
                        world.playSound(null, pos, group.getHitSound(), SoundCategory.BLOCKS,
                                (group.getVolume() + 1.0f) / 8.0f, group.getPitch() * 0.5f
                        );
                    }

                    if (getHittingSound() != null) {
                        world.playSound(null, pos, getHittingSound(), SoundCategory.BLOCKS, 1.5f, 1);
                    }
                }

                if (direction == Direction.UP && dealBounceDamage()) {
                    world.getOtherEntities(null, new Box(pos).offset(0, 0.5, 0)).forEach(target -> {
                        target.setVelocity(target.getVelocity().add(0, 0.5, 0));
                        target.velocityDirty = true;

                        if (target instanceof LivingEntity living) {
                            living.damage(ZeldaDamageTypes.of(world, entity, getBounceDamageType()), 2);
                        }
                    });
                }
            }
        }
    }

    protected boolean dealBounceDamage() {
        return true;
    }

    protected RegistryKey<DamageType> getBounceDamageType() {
        return ZeldaDamageTypes.QUESTION_BLOCK;
    }

    public void postBlockHit(World world, BlockPos pos, BlockState blockState, HittableContainerBlockEntity blockEntity) {
        tryDispensingContents(world, pos, blockState, blockEntity);
    }

    protected void tryDispensingContents(World world, BlockPos pos, BlockState blockState, HittableContainerBlockEntity blockEntity) {
        world.setBlockState(pos, blockState.with(HIT, true));
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

        blockEntity.clear();
        blockEntity.markDirty();
    }

    @Override
    public boolean allowAttackHolding() {
        return false;
    }

    // Rendering & Outline /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return state.get(BOUNCING) ? BlockRenderType.ENTITYBLOCK_ANIMATED : BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(BOUNCING, HIT, POWERED);
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

    // Block Entity ////////////////////////////////////////////////////////////////////////////////////////////////////

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ZeldaBlockEntities.HITTABLE_CONTAINER_BLOCK, HittableContainerBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HittableContainerBlockEntity(pos, state);
    }

    public int getBounceTimer() {
        return 8;
    }
}
