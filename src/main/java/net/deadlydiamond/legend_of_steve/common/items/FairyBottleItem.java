package net.deadlydiamond.legend_of_steve.common.items;

import net.deadlydiamond.legend_of_steve.common.entities.living.FairyColor;
import net.deadlydiamond.legend_of_steve.common.entities.living.IBottleable;
import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class FairyBottleItem extends Item {
    private final EntityType<?> entityType;

    public FairyBottleItem(EntityType<? extends MobEntity> entityType) {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON).recipeRemainder(Items.GLASS_BOTTLE));
        this.entityType = entityType;
    }

    @Override
    public void postProcessNbt(NbtCompound nbt) {
        super.postProcessNbt(nbt);
        if (!nbt.contains("FairyColor")) {
            FairyColor.BLUE.writeNbt(nbt);
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);
            PlayerEntity player = context.getPlayer();

            BlockPos blockPos2;
            if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                blockPos2 = blockPos;
            } else {
                blockPos2 = blockPos.offset(direction);
            }

            if (spawnEntity(serverWorld, itemStack, blockPos2)) {
                itemStack.decrement(1);

                if (player != null && !player.isCreative()) {
                    player.giveItemStack(getEmptiedStack());
                }
                playEmptySound(world, blockPos);
                world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
            }

            return ActionResult.CONSUME;
        }
        return ActionResult.SUCCESS;
    }

    private void playEmptySound(World world, BlockPos blockPos) {
        world.playSound(null, blockPos, ZeldaSounds.EMPTY_BOTTLE, SoundCategory.PLAYERS, 1, 1);
    }

    public static ItemStack getEmptiedStack() {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    private boolean spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        if (this.entityType.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false) instanceof IBottleable bottleable) {
            bottleable.copyDataFromNbt(stack.getOrCreateNbt());
            return true;
        }
        return false;
    }

    public static FairyColor getFairyColor(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains("FairyColor")) {
            return FairyColor.readNbt(nbt);
        }
        return FairyColor.BLUE;
    }
}
