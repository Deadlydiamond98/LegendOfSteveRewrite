package net.deadlydiamond.legend_of_steve.common.blocks.functional.bindable.switches.toggle;

import net.deadlydiamond.legend_of_steve.common.bes.switches.CrystalSwitchBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.Nullable;

public class CrystalSwitchCollisions {
    // BASE SHAPES /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final VoxelShape LOWER_BASE_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 10, 15);
    public static final VoxelShape UPPER_BASE_SHAPE = Block.createCuboidShape(4, 10, 4, 12, 11, 12);
    public static final VoxelShape BASE_SHAPE = VoxelShapes.union(LOWER_BASE_SHAPE, UPPER_BASE_SHAPE);

    // ORB SHAPES //////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final VoxelShape[] TOP_ORB_SHAPES;
    public static final VoxelShape[] BOTTOM_ORB_SHAPES;
    public static final int MAX_ANGLE = 45;

    static {
        TOP_ORB_SHAPES = new VoxelShape[MAX_ANGLE + 1];
        BOTTOM_ORB_SHAPES = new VoxelShape[MAX_ANGLE + 1];

        for (int i = 0; i <= MAX_ANGLE; i++) {
            TOP_ORB_SHAPES[i] = createOrbShape(8, i, 0, 5);
            BOTTOM_ORB_SHAPES[i] = VoxelShapes.union(BASE_SHAPE, createOrbShape(8, i, 13, 16));
        }
    }

    // MISC SHAPES /////////////////////////////////////////////////////////////////////////////////////////////////////
    // Used for Projectile Collisions
    public static final VoxelShape PROJECTILE_COLLISION_BASE_SHAPE = VoxelShapes.union(BASE_SHAPE, BOTTOM_ORB_SHAPES[0]);
    // Used for Sparkle Particles
    public static final VoxelShape ENTIRE_ORB_SHAPE = Block.createCuboidShape(4, 13, 4, 12, 21, 12);

    // HELPER METHODS //////////////////////////////////////////////////////////////////////////////////////////////////

    public static VoxelShape createOrbShape(double baseBounds, int angle, double minY, double maxY) {
        double rotation = Math.toRadians(angle);
        double size = baseBounds * (Math.abs(Math.cos(rotation)) + Math.abs(Math.sin(rotation)));

        double min = (16 - size) / 2;
        double max = min + size;

        return Block.createCuboidShape(min, minY, min, max, maxY, max);
    }

    public static VoxelShape getOrbShape(@Nullable BlockEntity blockEntity, boolean bottom) {
        if (blockEntity instanceof CrystalSwitchBlockEntity switchBlock) {
            int yaw = Math.round(Math.abs(((switchBlock.OrbYaw + 45) % 90) - 45));
            return bottom ? BOTTOM_ORB_SHAPES[yaw] : TOP_ORB_SHAPES[yaw];
        }
        return bottom ? BOTTOM_ORB_SHAPES[0] : TOP_ORB_SHAPES[0];
    }
}
