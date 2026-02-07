package net.deadlydiamond.legend_of_steve.common.entities.bomb;

import net.deadlydiamond.legend_of_steve.common.blocks.secret.ISecretBlock;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond.legend_of_steve.util.mixinterfaces.IZeldaExplosion;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public interface IZeldaBomb {
    float getPower();

    default TagKey<Block> getBreakableBlocks() {
        return ZeldaTags.BOMB_BREAKABLE;
    }

    default Entity getBombOwner() {
        return null;
    }

    default void explode(Entity entity) {
        World world = entity.getWorld();
        float power = getPower();

        if (world instanceof ServerWorld server) {
            world.createExplosion(entity, entity.getX(), entity.getY(), entity.getZ(), power, false, World.ExplosionSourceType.NONE);

            // Second Explosion that applies a value that only breaks blocks that can be broken
            Explosion explosion = new Explosion(world, entity, null, null, entity.getX(), entity.getY(), entity.getZ(), power, false, Explosion.DestructionType.DESTROY);
            ((IZeldaExplosion) explosion).legend_of_steve$setZeldaBombOrigin(true, this::canBreakBlock);
            explosion.collectBlocksAndDamageEntities();
            explosion.affectWorld(false);

            // TODO: Add Text Particle
        }
    }

    default boolean canBreakBlock(Block block) {
        return block.getDefaultState().isIn(getBreakableBlocks()) || block instanceof ISecretBlock || block.getDefaultState().isOf(Blocks.TNT);
    }
}
