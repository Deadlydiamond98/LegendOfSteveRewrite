package net.deadlydiamond.legend_of_steve.common.blocks.deco.glowing;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class GlowingBlock extends Block implements IGlowingBlock {
    public static final BooleanProperty GLOWING = BooleanProperty.of("glowing");

    public GlowingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(GLOWING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(GLOWING);
    }

    @Override
    public BooleanProperty getGlowingProperty() {
        return GLOWING;
    }
}
