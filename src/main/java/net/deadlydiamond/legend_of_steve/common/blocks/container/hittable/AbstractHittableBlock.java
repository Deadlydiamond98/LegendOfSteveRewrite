package net.deadlydiamond.legend_of_steve.common.blocks.container.hittable;

import net.deadlydiamond.legend_of_steve.common.bes.container.single.SingleSlotBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.IJumpIntoAction;
import net.deadlydiamond.legend_of_steve.common.blocks.container.WaterloggableSingleSlotBlock;
import net.deadlydiamond.legend_of_steve.networking.s2c.question_block.UpdateProjectileHitS2CPacket;
import net.deadlydiamond98.koalalib.common.blocks.interaction.IHitBlockAction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractHittableBlock extends WaterloggableSingleSlotBlock implements IJumpIntoAction, IHitBlockAction {
    public static final BooleanProperty BOUNCING = BooleanProperty.of("bouncing");
    public static final BooleanProperty HIT = BooleanProperty.of("hit");

    public AbstractHittableBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HIT, startEmpty()).with(BOUNCING, false));
    }

    protected abstract boolean startEmpty();

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
    protected SoundEvent getRemoveSound() {
        return null;
    }

    // Hitting Block ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void jumpIntoBlock(World world, BlockPos pos, BlockState state, @Nullable Entity entity) {
        hitBlock(world, pos, state, entity, Direction.UP, true);
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
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient()) {
            hitBlock(world, hit.getBlockPos(), state, projectile, hit.getSide().getOpposite(), true);
            world.getPlayers().forEach(player -> UpdateProjectileHitS2CPacket.send(player, hit.getBlockPos(), hit.getSide().getOpposite(), true));
        }
    }

    public void hitBlock(World world, BlockPos pos, BlockState state, @Nullable Entity entity, Direction direction, boolean additionalHitSound) {

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
        builder.add(BOUNCING, HIT);
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
