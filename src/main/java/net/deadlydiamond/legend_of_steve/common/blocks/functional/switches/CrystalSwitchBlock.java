package net.deadlydiamond.legend_of_steve.common.blocks.functional.switches;

import net.deadlydiamond.legend_of_steve.common.blocks.IModifiedOutlineRender;
import net.deadlydiamond.legend_of_steve.common.items.IExtraCanMine;
import net.deadlydiamond98.koalalib.common.blocks.interaction.IHitBlockAction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrystalSwitchBlock extends BlockWithEntity implements IHitBlockAction, IModifiedOutlineRender, IExtraCanMine {
    protected static final VoxelShape LOWER_BASE_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 10, 15);
    protected static final VoxelShape UPPER_BASE_SHAPE = Block.createCuboidShape(4, 10, 4, 12, 11, 12);

    protected static final VoxelShape DEBUG = Block.createCuboidShape(4, 12, 4, 12, 16, 12);

    protected static final VoxelShape BASE_SHAPE = VoxelShapes.union(LOWER_BASE_SHAPE, UPPER_BASE_SHAPE);
    protected static final VoxelShape FULL_SHAPE = VoxelShapes.union(BASE_SHAPE, DEBUG);;

    public CrystalSwitchBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return super.getTicker(world, state, type);
    }

    // COLLISION & INTERACTIONS ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean canMineBlock(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        HitResult hitResult = miner.raycast(miner.isCreative() ? 5 : 4.5, 0, false);
        if (hitResult instanceof BlockHitResult blockHitResult) {
            return !(hitResult.getPos().y - blockHitResult.getBlockPos().getY() > 0.69);
        }
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext ctx) {
            return ctx.getEntity() instanceof PlayerEntity ? BASE_SHAPE : FULL_SHAPE;
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getRenderedOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getOutlineShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FULL_SHAPE;
    }

    @Override
    public void attack(BlockState blockState, BlockPos blockPos, World world, PlayerEntity playerEntity) {
        playerEntity.sendMessage(Text.literal("HELLO WORLD"));
    }

    @Override
    public boolean allowAttackHolding() {
        return false;
    }
}
