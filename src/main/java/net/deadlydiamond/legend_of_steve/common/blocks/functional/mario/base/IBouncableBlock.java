package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base;

import net.deadlydiamond.legend_of_steve.common.bes.BouncingBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.common.items.IExtraCanMine;
import net.deadlydiamond.legend_of_steve.init.ZeldaAdvancements;
import net.deadlydiamond.legend_of_steve.init.ZeldaDamageTypes;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond.legend_of_steve.mixin.common.be.LootableContainerBlockEntityInvoker;
import net.deadlydiamond.legend_of_steve.networking.s2c.question_block.UpdateBounceBlockHitS2CPacket;
import net.deadlydiamond98.koalalib.common.blocks.interaction.IHitBlockAction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface IBouncableBlock extends IJumpIntoAction, IHitBlockAction, IExtraCanMine {

    // Getters /////////////////////////////////////////////////////////////////////////////////////////////////////////
    BlockState getPostBounceState(BlockState originalState);

    default int getBounceTime() {
        return 5;
    }

    @Nullable
    default SoundEvent getHittingSound() {
        return ZeldaSounds.QUESTION_BLOCK_HIT;
    }

    @Nullable
    default DefaultedList<ItemStack> getInventory(World world, BlockPos pos, @Nullable PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof LootableContainerBlockEntity container) {
            container.checkLootInteraction(player);
            return ((LootableContainerBlockEntityInvoker) container).legend_of_steve$getInvStackList();
        }
        return null;
    }

    // Configuration ///////////////////////////////////////////////////////////////////////////////////////////////////

    boolean canBounceBlock(World world, BlockPos pos, BlockState state);

    default boolean canJumpInto(World world, BlockPos blockPos, BlockState blockState, @Nullable Entity entity) {
        return true;
    }

    default boolean canPunchTrigger(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        return true;
    }

    default boolean triggersConcussionAdvancement(World world, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    // Passenger Damage & Velocity /////////////////////////////////////////////////////////////////////////////////////

    default boolean bouncePassengers() {
        return true;
    }

    default boolean dealBounceDamage(Entity entity, LivingEntity living) {
        return entity != null;
    }

    default RegistryKey<DamageType> getBounceDamageType() {
        return ZeldaDamageTypes.QUESTION_BLOCK;
    }

    // Bounce & Post Bounce Actions ////////////////////////////////////////////////////////////////////////////////////

    void beforeBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction direction, BounceType type);
    void afterBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction bouncedDirection, BounceType bounceType, @Nullable DefaultedList<ItemStack> inventory);

    default void triggerBounce(World world, BlockPos pos, BlockState state, @Nullable Entity owner, Direction direction, BounceType type) {
        if (!world.isClient()) {
            world.getPlayers().forEach(player -> UpdateBounceBlockHitS2CPacket.send(player, pos, owner, direction, type));

            // Trigger Concussion Advancement
            if (owner instanceof PlayerEntity player && triggersConcussionAdvancement(world, pos, state) && type == BounceType.JUMP) {
                ZeldaAdvancements.MINOR_CONCUSSION.trigger(player);
            }
        }

        // Bounce Mobs on other end of bounce Direction
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

        if (getHittingSound() != null) {
            world.playSound(null, pos, getHittingSound(), SoundCategory.BLOCKS, 1.5f, 1);
        }

        beforeBounce(world, pos, state, owner, direction, type);
        createBouncingBlock(world, pos, state, direction, owner, type);
    }

    default void createBouncingBlock(World world, BlockPos pos, BlockState state, Direction direction, @Nullable Entity owner, BounceType type) {
        DefaultedList<ItemStack> inventory = getInventory(world, pos, owner instanceof PlayerEntity player ? player : null);
        BouncingBlockEntity.create(world, pos, state, getPostBounceState(state), direction, getBounceTime(), owner, type, inventory);
    }

    // Overridden //////////////////////////////////////////////////////////////////////////////////////////////////////

    // Jumping

    @Override
    default void jumpIntoBlock(World world, BlockPos pos, BlockState state, @Nullable Entity entity) {
        if (canJumpInto(world, pos, state, entity) && canBounceBlock(world, pos, state) && !world.isClient()) {
            triggerBounce(world, pos, state, entity, Direction.UP, BounceType.JUMP);
        }
    }

    // Attacking

    @Override
    default boolean canMineBlock(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !canPunchTrigger(world, pos, state, miner) || miner.getMainHandStack().getItem() instanceof ToolItem;
    }

    @Override
    default void attack(BlockState state, BlockPos pos, World world, PlayerEntity playerEntity) {
        if (canPunchTrigger(world, pos, state, playerEntity) && canBounceBlock(world, pos, state) && !world.isClient()) {
            HitResult hitResult = playerEntity.raycast(8, 0, false);
            if (hitResult instanceof BlockHitResult blockHitResult) {
                triggerBounce(world, pos, state, playerEntity, blockHitResult.getSide().getOpposite(), BounceType.ATTACK);
            }
        }
    }

    @Override
    default boolean allowAttackHolding() {
        return false;
    }
}
