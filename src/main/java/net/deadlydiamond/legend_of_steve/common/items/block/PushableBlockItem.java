package net.deadlydiamond.legend_of_steve.common.items.block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.entities.block.PushableBlockEntity;
import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.deadlydiamond98.koalalib.common.items.other.ISpriteIconItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PushableBlockItem extends Item implements ISpriteIconItem {
    private final BlockState parentBlock;

    public PushableBlockItem(Settings settings, BlockState parentBlock) {
        super(settings);
        this.parentBlock = parentBlock;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemPlacementContext ctx = new ItemPlacementContext(context);
        if (canPlace(ctx, this.parentBlock) && ctx.canPlace()) {
            if (context.getWorld() instanceof ServerWorld server) {
                Entity entity = getEntityType().create(server, null, null, ctx.getBlockPos(), SpawnReason.EVENT, false, false);
                if (entity instanceof PushableBlockEntity pushBlock) {
                    setBlockProperties(pushBlock, server, ctx);
                }

                BlockSoundGroup soundGroup = this.parentBlock.getSoundGroup();
                ctx.getWorld().playSound(null, ctx.getBlockPos(), soundGroup.getPlaceSound(),
                        SoundCategory.BLOCKS, (soundGroup.getVolume() + 1.0f) / 2.0f, soundGroup.getPitch() * 0.8f);
                if (!ctx.getPlayer().getAbilities().creativeMode) {
                    ctx.getStack().decrement(1);
                }
            }
            return ActionResult.success(ctx.getWorld().isClient);
        }
        return super.useOnBlock(context);
    }

    protected EntityType<?> getEntityType() {
        return ZeldaEntityTypes.PUSHABLE_BLOCK;
    }

    protected void setBlockProperties(PushableBlockEntity pushBlock, ServerWorld server, ItemPlacementContext context) {
        pushBlock.setBlock(this.parentBlock.getBlock().getPlacementState(context));
        pushBlock.setItemStack(this.getDefaultStack());
        server.spawnEntityAndPassengers(pushBlock);
    }

    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        PlayerEntity playerEntity = context.getPlayer();
        ShapeContext shapeContext = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
        return (state.canPlaceAt(context.getWorld(), context.getBlockPos())) && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);
    }

    @Override
    public String getTranslationKey() {
        return this.parentBlock.getBlock().getTranslationKey();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.legend_of_steve.pushable").formatted(Formatting.GRAY));
    }

    @Override
    public boolean isBlock() {
        return true;
    }

    @Override
    public GUICorner getGUICorner() {
        return GUICorner.BOTTOM_LEFT;
    }

    @Override
    public Identifier getTexture(PlayerEntity player, ItemStack stack) {
        return LegendOfSteve.id("textures/item/icon/pushable.png");
    }
}
