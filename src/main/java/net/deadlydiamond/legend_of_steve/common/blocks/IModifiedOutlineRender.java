package net.deadlydiamond.legend_of_steve.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public interface IModifiedOutlineRender {
    VoxelShape getRenderedOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context);

    default Vec3i getOffset(BlockPos pos, BlockState state) {
        return Vec3i.ZERO;
    }
}
