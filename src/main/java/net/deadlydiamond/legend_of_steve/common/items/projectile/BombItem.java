package net.deadlydiamond.legend_of_steve.common.items.projectile;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.BombEntity;
import net.deadlydiamond.legend_of_steve.common.entities.bomb.IZeldaBomb;
import net.deadlydiamond.legend_of_steve.init.ZeldaTags;
import net.deadlydiamond98.koalalib.common.items.interaction.IAdvancedItemProperties;
import net.deadlydiamond98.koalalib.common.items.vanillamodified.projectile.CustomProjectileItem;
import net.deadlydiamond98.koalalib.init.KoalaLibSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class BombItem extends CustomProjectileItem implements IAdvancedItemProperties, IZeldaBomb {
    public static final List<Pair<Item, Integer>> COOLDOWNS = new ArrayList<>();
    private final TagKey<Block> breakable;
    private final int fuse;
    private final int power;

    public BombItem(Settings settings, EntityType<?> type, int fuse, int power) {
        this(settings, type, ZeldaTags.BOMB_BREAKABLE, fuse, power);
    }

    public BombItem(Settings settings, EntityType<?> type, TagKey<Block> breakable, int fuse, int power) {
        super(settings, type);
        this.breakable = breakable;
        this.fuse = fuse;
        this.power = power;
        COOLDOWNS.add(new Pair<>(this, 20));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        COOLDOWNS.forEach(pair -> user.getItemCooldownManager().set(pair.getA(), pair.getB()));
        return super.use(world, user, hand);
    }

    @Override
    public void initProjectile(Entity entity, ItemStack stack, LivingEntity owner, @Nullable Hand hand) {
        super.initProjectile(entity, stack, owner, hand);

        if (entity instanceof BombEntity bomb) {
            initBomb(bomb, stack, owner);

            lightOnThrow(owner, bomb);
        }
    }

    protected void initBomb(BombEntity bomb, ItemStack stack, LivingEntity owner) {
        bomb.setVelocity(bomb.getVelocity().multiply(0.6, 0.75, 0.6));
        bomb.setPosition(owner.getEyePos().add(0, 0.25f, 0));
        bomb.setFuse(this.fuse);
        bomb.setMaxFuse(this.fuse);
        bomb.setOwner(owner);
        bomb.setItem(this.getDefaultStack());
        bomb.setBreakableBlocks(this.breakable);
        bomb.setPower(this.power);
    }

    private void lightOnThrow(LivingEntity owner, BombEntity bomb) {
        ItemStack igniter = owner.getStackInHand(Hand.OFF_HAND);
        if (igniter.isOf(Items.FLINT_AND_STEEL)) {
            owner.getWorld().playSound(
                    null, owner.getBlockPos(),
                    KoalaLibSounds.TOOL_IGNITE, SoundCategory.PLAYERS,
                    0.4f, 0.8f
            );
            owner.getWorld().playSound(
                    null, owner.getBlockPos(),
                    SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS,
                    0.4f, 0.8f
            );
            if (owner instanceof PlayerEntity player && !player.isCreative()) {
                igniter.damage(1, owner, playerx -> playerx.sendToolBreakStatus(Hand.OFF_HAND));
            }
            bomb.setPrimed(true);
        }
    }

    @Override
    public void onItemEntityTick(ItemEntity entity, ItemStack stack) {
        IAdvancedItemProperties.super.onItemEntityTick(entity, stack);
        if (entity.isInLava()) {
            this.explode(entity);
            entity.discard();
        }
    }

    @Override
    public TagKey<Block> getBreakableBlocks() {
        return this.breakable;
    }

    @Override
    public float getPower() {
        return this.power;
    }
}
