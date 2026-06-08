package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base;

import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.temp.IJumpIntoAction;
import net.deadlydiamond.legend_of_steve.common.items.IExtraCanMine;
import net.deadlydiamond.legend_of_steve.init.ZeldaDamageTypes;
import net.deadlydiamond.legend_of_steve.networking.s2c.question_block.UpdateBounceBlockHitS2CPacket;
import net.deadlydiamond98.koalalib.common.blocks.interaction.IHitBlockAction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface IBouncableBlock extends IJumpIntoAction, IHitBlockAction, IExtraCanMine {
    boolean canBounceBlock();
    BlockState getPostBounceState(BlockState originalState);

    default boolean canJumpInto(World world, BlockPos blockPos, BlockState blockState, @Nullable Entity entity) {
        return true;
    }

    default boolean canPunch(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        return true;
    }

    void afterBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction bouncedDirection, BounceType bounceType);

    default int getBounceTime() {
        return 5;
    }

    @Override
    default boolean canMineBlock(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !(miner.getMainHandStack().getItem() instanceof ToolItem);
    }

    @Override
    default void jumpIntoBlock(World world, BlockPos pos, BlockState state, @Nullable Entity entity) {
        if (canJumpInto(world, pos, state, entity) && canBounceBlock() && !world.isClient()) {
            bounceBlock(world, pos, state, entity, Direction.UP, BounceType.JUMP);
        }
    }

    @Override
    default void attack(BlockState blockState, BlockPos blockPos, World world, PlayerEntity playerEntity) {
        if (canPunch(world, blockPos, blockState, playerEntity) && canBounceBlock() && !world.isClient()) {
            HitResult hitResult = playerEntity.raycast(8, 0, false);
            if (hitResult instanceof BlockHitResult blockHitResult) {
                bounceBlock(world, blockPos, blockState, playerEntity, blockHitResult.getSide().getOpposite(), BounceType.ATTACK);
            }
        }
    }

    default void bounceBlock(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction direction, BounceType type) {
        if (!world.isClient()) {
            world.getPlayers().forEach(player -> UpdateBounceBlockHitS2CPacket.send(player, pos, owner, direction, type));
        }

        if (bouncePassengers()) {
            Vec3d bounceDirection = Vec3d.of(direction.getVector());

            world.getOtherEntities(null, new Box(pos).offset(bounceDirection.multiply(0.5))).forEach(target -> {
                target.setVelocity(target.getVelocity().add(bounceDirection.multiply(0.5)));
                target.velocityDirty = true;

                if (target instanceof LivingEntity living && dealBounceDamage(owner, living)) {
                    living.damage(ZeldaDamageTypes.of(world, owner, getBounceDamageType()), 2);
                }
            });
        }

        createBouncingBlock(world, pos, state, direction, owner, type);
    }

    default boolean bouncePassengers() {
        return true;
    }

    default boolean dealBounceDamage(Entity entity, LivingEntity living) {
        return entity != null;
    }

    default RegistryKey<DamageType> getBounceDamageType() {
        return ZeldaDamageTypes.QUESTION_BLOCK;
    }

    default void createBouncingBlock(World world, BlockPos pos, BlockState state, Direction direction, @Nullable Entity owner, BounceType type) {
        BouncingTransitionBlock.create(world, pos, state, getPostBounceState(state), direction, getBounceTime(), owner, type);
    }

    @Override
    default boolean allowAttackHolding() {
        return false;
    }
}
