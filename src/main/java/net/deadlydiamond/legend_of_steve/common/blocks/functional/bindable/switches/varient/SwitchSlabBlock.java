package net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.switches.varient;

import net.deadlydiamond.legend_of_steve.common.bes.switches.SwitchBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.BoundBlockUtil;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.switches.ISwitchBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SwitchSlabBlock extends SlabBlock implements ISwitchBlock {
    private final boolean startOn;

    public SwitchSlabBlock(Settings settings, boolean startOn) {
        super(settings);
        this.startOn = startOn;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.getBlockEntity(pos) instanceof SwitchBlockEntity switchBlock) {
            switchBlock.init(world, pos, state, itemStack);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return isOn(world, pos) ? super.getCollisionShape(state, world, pos, context) : VoxelShapes.empty();
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return BoundBlockUtil.applyGroupOnPickStack(super.getPickStack(world, pos, state), world, pos);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        BoundBlockUtil.addTooltip(stack, tooltip);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (isOn(world, pos) && random.nextFloat() < 0.25) {
            createSwitchParticle(world, pos, state.getOutlineShape(world, pos), 0.125f, startOn(), true);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
    }

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.onSyncedBlockEvent(type, data);
    }

    @Override
    public boolean startOn() {
        return this.startOn;
    }
}
