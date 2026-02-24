package net.deadlydiamond.legend_of_steve.common.blocks.plant;

import net.deadlydiamond.legend_of_steve.common.blocks.IExtendedLootTable;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FruitingLeaves extends LeavesBlock implements IExtendedLootTable {
    private final Block pickedBlock;

    public FruitingLeaves(Settings settings, Block pickedBlock) {
        super(settings);
        this.pickedBlock = pickedBlock;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        dropCustomStacks(Registries.BLOCK.getId(this.asBlock()).getPath() + "_picked", state, world, pos);
        playPickedSound(world, pos);
        world.setBlockState(pos, pickedBlock.getStateWithProperties(state));
        return ActionResult.success(world.isClient);
    }

    public void playPickedSound(World world, BlockPos pos) {
        world.playSound(null, pos, ZeldaSounds.BOMB_HARVESTED, SoundCategory.BLOCKS, 1, 0.8f + world.random.nextFloat() * 0.4f);
    }
}
