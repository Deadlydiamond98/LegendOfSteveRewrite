package net.deadlydiamond.legend_of_steve.common.items;

import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.deadlydiamond98.koalalib.common.items.interaction.PickupSoundItem;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EmeraldShardItem extends PickupSoundItem {
    private final SoundEvent useSound;
    private final Item item;

    public EmeraldShardItem(Settings settings, SoundEvent useSound, Item item) {
        super(settings, ZeldaSounds.EMERALD_SHARD_PICKED_UP);
        this.useSound = useSound;
        this.item = item;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int count = stack.getCount();

        if (count > 7 && !user.isCreative()) {

            if (world.isClient) {
                user.playSound(this.useSound, 1, 1);
            } else {
                ItemStack chunkStack = new ItemStack(this.item, count / 8);
                int decrementAmount = (count / 8) * 8;
                boolean decrement = true;

                if (decrementAmount == stack.getCount()) {
                    user.setStackInHand(hand, ItemStack.EMPTY);
                    decrement = false;
                }

                if (!user.giveItemStack(chunkStack)) {
                    ItemEntity itemEntity = user.dropItem(chunkStack, false);
                    if (itemEntity == null) {
                        itemEntity.resetPickupDelay();
                        itemEntity.setOwner(user.getUuid());
                    }
                    world.spawnEntity(itemEntity);
                }

                if (decrement) {
                    stack.decrement(decrementAmount);
                }
            }
            return TypedActionResult.success(stack);
        }

        return super.use(world, user, hand);
    }
}
