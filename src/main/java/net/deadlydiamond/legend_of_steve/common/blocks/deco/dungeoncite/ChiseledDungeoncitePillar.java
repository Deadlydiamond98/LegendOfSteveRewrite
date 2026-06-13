package net.deadlydiamond.legend_of_steve.common.blocks.deco.dungeoncite;

import net.deadlydiamond.legend_of_steve.common.blocks.deco.ConnectedPillarBlock;
import net.deadlydiamond98.koalalib.common.blocks.advancement.IAdvancementNeeded;
import net.deadlydiamond98.koalalib.init.KoalaLibBlockProperties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ChiseledDungeoncitePillar extends ConnectedPillarBlock implements IAdvancementNeeded {
    public static final BooleanProperty PLAYERMADE = KoalaLibBlockProperties.PLAYER_MADE_PROPERTY;
    private final String advancementID;

    public ChiseledDungeoncitePillar(AbstractBlock.Settings settings, String advancementID) {
        super(settings);
        this.advancementID = advancementID;
        setDefaultState(this.getDefaultState().with(PLAYERMADE, false));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(PLAYERMADE, this.isPlayerPlaced(ctx));
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        return !this.hasAdvancment(player, this.advancementID) && !(Boolean)state.get(PLAYERMADE) ? -1 : super.calcBlockBreakingDelta(state, player, world, pos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(PLAYERMADE);
    }
}
