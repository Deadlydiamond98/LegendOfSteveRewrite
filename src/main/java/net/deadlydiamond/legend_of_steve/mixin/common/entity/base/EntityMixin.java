package net.deadlydiamond.legend_of_steve.mixin.common.entity.base;

import net.deadlydiamond.legend_of_steve.common.blocks.IJumpIntoAction;
import net.deadlydiamond.legend_of_steve.networking.s2c.question_block.JumpIntoBlockS2CPacket;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow protected abstract Vec3d adjustMovementForCollisions(Vec3d movement);

    // Upwards Block Collisions ////////////////////////////////////////////////////////////////////////////////////////

    @Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setPosition(DDD)V", ordinal = 1))
    private void legend_of_steve$move(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        World world = entity.getWorld();

        Vec3d vec3d = this.adjustMovementForCollisions(movement);

        if (!world.isClient() && vec3d != null && vec3d.getY() > 0) {
            Vec3d entityTopPos = entity.getPos().add(0, entity.getHeight(), 0);

            BlockHitResult hit = world.raycast(
                    new RaycastContext(
                            entityTopPos,
                            entityTopPos.add(vec3d).add(0, 0.001, 0),
                            RaycastContext.ShapeType.COLLIDER,
                            RaycastContext.FluidHandling.NONE,
                            entity
                    )
            );

            if (hit.getType() == HitResult.Type.BLOCK && hit.getSide() == Direction.DOWN) {
                BlockPos pos = hit.getBlockPos();
                BlockState state = world.getBlockState(pos);
                if (state.getBlock() instanceof IJumpIntoAction block) {
                    world.getPlayers().forEach(player -> JumpIntoBlockS2CPacket.send(player, pos, entity));
                    block.jumpIntoBlock(world, pos, state, entity);
                }
            }
        }
    }
}
