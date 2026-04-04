package net.deadlydiamond.legend_of_steve.common.entities.projectile;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaDamageTypes;
import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.deadlydiamond98.koalalib.common.entity.PhysicsItemProjectile;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ThrownPotEntity extends PhysicsItemProjectile {
    private boolean shattered;
    public Vec3d rotationClient = Vec3d.ZERO;
    public Vec3d rotationSpeedClient;

    public ThrownPotEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);

        this.setGravity(0.06f);
        this.setDrag(0.96f);
        this.setBounciness(0);
        this.setWaterDrag(0.75f);
        this.setBuoyancy(0);
        this.rotationSpeedClient = new Vec3d(
                world.getRandom().nextInt(5) * (world.getRandom().nextBoolean() ? 1 : -1),
                world.getRandom().nextInt(5) * (world.getRandom().nextBoolean() ? 1 : -1),
                world.getRandom().nextInt(5) * (world.getRandom().nextBoolean() ? 1 : -1)
        );
    }

    public ThrownPotEntity(World world, @Nullable Entity owner, ItemStack stack) {
        this(ZeldaEntityTypes.THROWN_POT, world);
        setItem(stack.copyWithCount(1));
        setOwner(owner);
        setPosition(owner.getEyePos().add(0, 0.25, 0));
        setYaw(owner.getBodyYaw());
        setVelocity(owner.getRotationVector().add(0, 0.1, 0));
    }

    protected void shatter() {
        if (!getWorld().isClient) {
            ItemStack stack = getStack();
            if (!this.shattered && stack.getItem() instanceof BlockItem blockItem) {
                BlockState blockState = getItemBlockState(stack, blockItem);
                Block.dropStacks(blockState, getWorld(), getBlockPos());
                dropBlockEntityItems(stack);

                BlockSoundGroup soundGroup = blockState.getSoundGroup();
                playSound(
                        blockState.getSoundGroup().getBreakSound(),
                        (soundGroup.getVolume() + 1) / 2,
                        soundGroup.getPitch() * 0.8f
                );
                this.shattered = true;
            }
            despawnProjectile();
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 3) {
            if (getStack().getItem() instanceof BlockItem blockItem) {
                ParticleEffect particleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, getItemBlockState(getStack(), blockItem));
                for(int i = 0; i < 8; ++i) {
                    this.getWorld().addParticle(
                            particleEffect, this.getX(), this.getY(), this.getZ(),
                            0.0 + (double)this.random.nextBetween(-5, 5) * 0.02, 0.1,
                            0.0 + (double)this.random.nextBetween(-5, 5) * 0.02
                    );
                }
            }
        }
    }

    @Override
    protected void hitFloor(Block block) {
        shatter();
    }

    @Override
    protected void hitWall(boolean hitXAxis, boolean hitZAxis) {
        shatter();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(ZeldaDamageTypes.of(entity.getWorld(), getOwner(), ZeldaDamageTypes.LOOT_POT), 3);
        shatter();
    }

    @Override
    protected Item getDefaultItem() {
        return ZeldaBlocks.LOOT_POT.asItem();
    }

    // TODO: THIS WILL BE REMOVED ONCE I PUSH FIX FOR KOALA LIB
    @Override
    protected void tickMovement() {
        this.velocityDirty = true;
        super.tickMovement();
    }

    // Block Helper Methods ////////////////////////////////////////////////////////////////////////////////////////////

    protected BlockState getItemBlockState(ItemStack stack, BlockItem blockItem) {
        BlockState blockState = blockItem.getBlock().getDefaultState();
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null) {
            NbtCompound nbtCompound2 = nbtCompound.getCompound("BlockStateTag");
            StateManager<Block, BlockState> stateManager = blockState.getBlock().getStateManager();

            for (String string : nbtCompound2.getKeys()) {
                Property<?> property = stateManager.getProperty(string);
                if (property != null) {
                    String string2 = nbtCompound2.get(string).asString();
                    blockState = with(blockState, property, string2);
                }
            }
        }
        return blockState;
    }

    private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
        return property.parse(name).map(value -> state.with(property, value)).orElse(state);
    }

    private void dropBlockEntityItems(ItemStack stack) {
        NbtCompound nbt = BlockItem.getBlockEntityNbt(stack);
        if (nbt != null) {
            // Loot Table //////////////////////////////////////////////////////////////////////////////////////////////
            if (nbt.contains("LootTable", NbtElement.STRING_TYPE)) {
                Identifier lootTableId = new Identifier(nbt.getString("LootTable"));
                long lootTableSeed = nbt.getLong("LootTableSeed");

                if (getWorld().getServer() != null) {
                    LootTable lootTable = getWorld().getServer().getLootManager().getLootTable(lootTableId);
                    if (getOwner() instanceof ServerPlayerEntity player) {
                        Criteria.PLAYER_GENERATES_CONTAINER_LOOT.trigger(player, lootTableId);
                    }

                    LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld)getWorld())
                            .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(getBlockPos()));
                    if (getOwner() instanceof ServerPlayerEntity player) {
                        builder.luck(player.getLuck()).add(LootContextParameters.THIS_ENTITY, player);
                    }

                    ObjectArrayList<ItemStack> itemStacks = lootTable.generateLoot(builder.build(LootContextTypes.CHEST), lootTableSeed);
                    if (!itemStacks.isEmpty()) {
                        getWorld().spawnEntity(new ItemEntity(getWorld(), getX(), getY(), getZ(), itemStacks.get(0)));
                    }
                }
            }

            // Storage /////////////////////////////////////////////////////////////////////////////////////////////////
            if (nbt.contains("Items", NbtElement.LIST_TYPE)) {
                NbtList nbtList = nbt.getList("Items", NbtElement.COMPOUND_TYPE);
                nbtList.stream().map(NbtCompound.class::cast).map(ItemStack::fromNbt).forEach(
                        content -> getWorld().spawnEntity(
                                new ItemEntity(getWorld(), getX(), getY(), getZ(), content)
                        )
                );
            }
        }
    }
}
