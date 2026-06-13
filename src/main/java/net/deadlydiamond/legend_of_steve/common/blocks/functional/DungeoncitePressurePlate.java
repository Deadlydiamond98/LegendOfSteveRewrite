package net.deadlydiamond.legend_of_steve.common.blocks.functional;

import net.deadlydiamond.legend_of_steve.common.blocks.deco.oriented.OrientablePressurePlateBlock;
import net.deadlydiamond.legend_of_steve.common.entities.block.PushableBlockEntity;
import net.deadlydiamond98.koalalib.common.blocks.advancement.IAdvancementNeeded;
import net.deadlydiamond98.koalalib.init.KoalaLibBlockProperties;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DungeoncitePressurePlate extends OrientablePressurePlateBlock implements IAdvancementNeeded {
    public static final BooleanProperty PLAYERMADE = KoalaLibBlockProperties.PLAYER_MADE_PROPERTY;
    private final String advancementID;

    public DungeoncitePressurePlate(AbstractBlock.Settings settings, BlockSetType blockSetType, Identifier advancementID) {
        this(settings, blockSetType, advancementID.toString());
    }

    public DungeoncitePressurePlate(Settings settings, BlockSetType blockSetType, String advancementID) {
        super(settings, blockSetType);
        this.advancementID = advancementID;
        this.setDefaultState(this.getDefaultState().with(PLAYERMADE, false));
    }

    @Override
    protected int getRedstoneOutput(World world, BlockPos pos) {
        int players = getEntityCount(world, BOX.offset(pos), PlayerEntity.class);
        int boxes = getEntityCount(world, BOX.offset(pos), PushableBlockEntity.class);
        return (players + boxes) > 0 ? 15 : 0;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        return state != null ? state.with(PLAYERMADE, this.isPlayerPlaced(ctx)) : null;
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        return !this.hasAdvancment(player, this.advancementID) && !(Boolean)state.get(PLAYERMADE) ? -1.0F : super.calcBlockBreakingDelta(state, player, world, pos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(PLAYERMADE);
    }
}