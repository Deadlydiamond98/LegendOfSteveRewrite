package net.deadlydiamond.legend_of_steve.client.models.block.transforms;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class AxisQuadTransform extends AbstractTransform {
    private final Direction.Axis axis;

    public AxisQuadTransform(BlockState state) {
        this(state.contains(Properties.AXIS) ? state.get(Properties.AXIS) : Direction.Axis.Y);
    }

    public AxisQuadTransform(Direction.Axis axis) {
        this.axis = axis;
    }

    @Override
    public QuadVec getQuadVecTranslations(QuadVec quadVec) {
        switch (this.axis) {
            case X -> quadVec.rotateCentered(Direction.Axis.Z, 90);
            case Z -> quadVec.rotateCentered(Direction.Axis.X, 90);
        }

        return quadVec;
    }

    @Override
    public Direction getCullDirection(Direction originalDirection) {
        return switch (this.axis) {
            case X -> originalDirection.rotateClockwise(Direction.Axis.Z);
            case Z -> originalDirection.rotateClockwise(Direction.Axis.X);
            default -> originalDirection;
        };
    }
}
