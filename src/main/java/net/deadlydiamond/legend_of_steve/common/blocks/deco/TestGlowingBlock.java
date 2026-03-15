package net.deadlydiamond.legend_of_steve.common.blocks.deco;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class TestGlowingBlock extends Block {
    public static final BooleanProperty GLOWING = BooleanProperty.of("glowing");

    public TestGlowingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(GLOWING, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(GLOWING);
    }
}
