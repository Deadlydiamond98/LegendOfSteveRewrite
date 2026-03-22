package net.deadlydiamond.legend_of_steve.common.blocks.plant;

import net.deadlydiamond.legend_of_steve.common.bes.BombFlowerBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.IExplodedInteraction;
import net.deadlydiamond.legend_of_steve.common.blocks.IExtendedLootTable;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.BombEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.deadlydiamond.legend_of_steve.init.ZeldaItems;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond98.koalalib.util.IgnitionHelper;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class BombFlowerBlock extends HorizontalFacingBlock implements IExplodedInteraction, IExtendedLootTable, BlockEntityProvider {
    public static final VoxelShape LEAF_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 3, 16);
    public static final VoxelShape BOMB_AGE_1_SHAPE = Block.createCuboidShape(6, 2, 6, 10, 6, 10);
    public static final VoxelShape BOMB_AGE_2_SHAPE = Block.createCuboidShape(5, 2, 5, 11, 8, 11);
    public static final VoxelShape BOMB_AGE_3_SHAPE = Block.createCuboidShape(4, 2, 4, 12, 10, 12);

    public static final BooleanProperty CHARGED = BooleanProperty.of("charged");
    public static final IntProperty AGE = Properties.AGE_3;

    public BombFlowerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(FACING, Direction.NORTH).with(CHARGED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(AGE) == 3) {
            if (!attemptIgniteBomb(state, world, pos, player, hand)) {
                harvestBomb(state, world, pos, player);
            }
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    private boolean attemptIgniteBomb(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        if (IgnitionHelper.canUseIgniterOnBlock(state, world, pos, player, hand)) {
            playPrimedSound(world, pos);
            igniteBomb(world, pos, 1, player);
            return true;
        }
        return false;
    }

    private void harvestBomb(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        dropCustomStacks("bomb_flower_picked", state, world, pos);
        playPickedSound(world, pos);
        world.setBlockState(pos, state.with(AGE, 0).with(CHARGED, false));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (!world.getBlockState(pos.down()).isIn(ZeldaTags.BOMB_FLOWER_PLANTABLE)) {
            return false;
        }

        for (BlockPos blockPos : BlockPos.iterateOutwards(pos, 5, 3, 5)) {
            if (world.getFluidState(blockPos).isIn(FluidTags.LAVA)) {
                return true;
            }
        }

        return isInNether(world);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() <= getGrowChance(world, pos) && state.canPlaceAt(world, pos)) {
            world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1));
        }
    }

    private float getGrowChance(ServerWorld world, BlockPos pos) {
        // More Likely to grow if in the nether, less likely to grow with more skylight outside Nether
        float modifier = (world.getLightLevel(LightType.SKY, pos) - 8) / 250.0f;
        return isInNether(world) ? 0.2f : 0.1f - modifier;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && state.get(AGE) >= 3) {
            if (entity.isOnFire() && entity.canModifyAt(world, pos)) {
                Entity owner = entity;
                if (entity instanceof Ownable ownable) {
                    owner = ownable.getOwner();
                }
                playPrimedSound(world, pos);
                igniteBomb(world, pos, 1, owner);
            }
        }
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        onEntityCollision(state, world, hit.getBlockPos(), projectile);
    }

    @Override
    public void onExploded(World world, BlockPos blockPos, Explosion explosion, boolean fromBomb) {
        if (!fromBomb) {
            onBombExploded(world, blockPos, explosion);
        }
    }

    @Override
    public void onBombExploded(World world, BlockPos blockPos, Explosion explosion) {
        igniteBomb(world, blockPos, world.getRandom().nextBetween(4, 7), explosion.getCausingEntity());
    }

    private void igniteBomb(World world, BlockPos blockPos, int fuseDividend, @Nullable Entity owner) {
        BlockState state = world.getBlockState(blockPos);
        if (state.get(AGE) >= 3 && !world.isClient) {
            BombEntity bomb = new BombEntity(ZeldaEntityTypes.BOMB, world);
            bomb.setPosition(blockPos.toCenterPos().subtract(0, 0.375, 0));
            bomb.setYaw((state.get(FACING).getHorizontal() * 90) - 90);
            bomb.setItem(ZeldaItems.BOMB_FLOWER.getDefaultStack());
            bomb.setPower(3);
            bomb.setPrimed(true);
            bomb.setFuse(50 / fuseDividend);
            bomb.setOwner(owner);
            bomb.setCharged(state.get(CHARGED));
            world.spawnEntity(bomb);

            world.setBlockState(blockPos, state.with(AGE, 0).with(CHARGED, false));
        }
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AGE)) {
            case 1 -> VoxelShapes.union(LEAF_SHAPE, BOMB_AGE_1_SHAPE);
            case 2 -> VoxelShapes.union(LEAF_SHAPE, BOMB_AGE_2_SHAPE);
            case 3 -> VoxelShapes.union(LEAF_SHAPE, BOMB_AGE_3_SHAPE);
            default -> LEAF_SHAPE;
        };
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        if (state.get(AGE) >= 3) {
            return VoxelShapes.fullCube();
        }
        return super.getRaycastShape(state, world, pos);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite().rotateYCounterclockwise());
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, AGE, CHARGED);
    }

    public void playPickedSound(World world, BlockPos pos) {
        world.playSound(null, pos, ZeldaSounds.BOMB_HARVESTED, SoundCategory.BLOCKS, 1, 0.8f + world.random.nextFloat() * 0.4f);
    }

    public void playPrimedSound(World world, BlockPos pos) {
        world.playSound(null, pos, ZeldaSounds.BOMB_PRIMED, SoundCategory.BLOCKS, 0.4f, 0.8f);
    }

    private boolean isInNether(WorldView world) {
        return world.getDimension().equals(
                world.getRegistryManager().get(RegistryKeys.DIMENSION_TYPE).get(DimensionTypes.THE_NETHER)
        );
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BombFlowerBlockEntity(pos, state);
    }
}
