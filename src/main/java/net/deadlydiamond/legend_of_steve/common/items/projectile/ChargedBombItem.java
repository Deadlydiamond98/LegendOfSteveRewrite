package net.deadlydiamond.legend_of_steve.common.items.projectile;

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

public class ChargedBombItem extends BombItem {
    public ChargedBombItem(Settings settings, EntityType<?> type, int fuse, int power) {
        super(settings, type, fuse, power);
    }

    @Override
    protected void initBomb(BombEntity bomb, ItemStack stack, LivingEntity owner) {
        super.initBomb(bomb, stack, owner);
        bomb.setCharged(true);
    }
}
