package net.deadlydiamond.legend_of_steve.common.entities.bomb;

import net.deadlydiamond.legend_of_steve.common.blocks.secret.ISecretBlock;
import net.deadlydiamond.legend_of_steve.common.world.BombExplosionBehavior;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;

public interface IZeldaBomb {
    float getPower();

    default void explode(Entity entity) {
        World world = entity.getWorld();
        float power = getPower();

        if (world instanceof ServerWorld server) {
            world.createExplosion(
                    entity, null,
                    getExplosionBehavior(world),
                    entity.getX(), entity.getY(), entity.getZ(), power,
                    false, World.ExplosionSourceType.TNT
            );

            // TODO: Add Text Particle
        }
    }

    default ExplosionBehavior getExplosionBehavior(World world) {
        return new BombExplosionBehavior(world, this::canBreakBlock);
    }

    default TagKey<Block> getBreakableBlocks() {
        return ZeldaTags.BOMB_BREAKABLE;
    }

    default boolean canBreakBlock(Block block) {
        return block.getDefaultState().isIn(getBreakableBlocks()) || block instanceof ISecretBlock || block.getDefaultState().isOf(Blocks.TNT);
    }
}
