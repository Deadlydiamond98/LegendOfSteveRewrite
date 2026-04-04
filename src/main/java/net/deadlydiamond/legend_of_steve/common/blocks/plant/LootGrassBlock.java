package net.deadlydiamond.legend_of_steve.common.blocks.plant;

import net.deadlydiamond.legend_of_steve.common.blocks.IExtendedLootTable;
import net.deadlydiamond.legend_of_steve.init.ZeldaAdvancements;
import net.deadlydiamond98.koalalib.common.blocks.interaction.IHitBlockAction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LootGrassBlock extends PlantBlock implements IExtendedLootTable, IHitBlockAction {
    public static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 8, 16);
    public static final IntProperty AGE = Properties.AGE_1;

    public LootGrassBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 1));
    }

    @Override
    public void attack(BlockState state, BlockPos pos, World world, PlayerEntity player) {
        if (world instanceof ServerWorld server && state.get(AGE) == 1 && holdingSword(player)) {
            world.playSound(null, pos, state.getSoundGroup().getBreakSound(), SoundCategory.BLOCKS, 1, 0.8f + world.random.nextFloat() * 0.4f);
            world.setBlockState(pos, state.with(AGE, 0));

            dropCustomStacks("loot_grass_cut", state, world, pos);
            ZeldaAdvancements.CUT_LOOT_GRASS.trigger(player);

            server.spawnParticles(
                    new BlockStateParticleEffect(ParticleTypes.BLOCK, state),
                    pos.getX(), pos.getY(), pos.getZ(), server.random.nextBetween(15, 20),
                    0.25, 0.25, 0.25, 0.1
            );
        }
    }

    private boolean holdingSword(PlayerEntity player) {
        return player.getMainHandStack().getItem() instanceof SwordItem;
    }

    @Override
    public boolean allowAttackHolding() {
        return true;
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        return !holdingSword(player) ? 1 : 0;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (random.nextInt(32) == 0) {
            world.setBlockState(pos, state.with(AGE, 1));
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) == 0;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
    }
}
