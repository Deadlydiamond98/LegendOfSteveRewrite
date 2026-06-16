package net.deadlydiamond.legend_of_steve.common.screen_handlers;

import net.deadlydiamond.legend_of_steve.common.recipes.DungeonTableRecipe;
import net.deadlydiamond.legend_of_steve.common.screen_handlers.slot.DungeonTableResultSlot;
import net.deadlydiamond.legend_of_steve.init.ZeldaBlocks;
import net.deadlydiamond.legend_of_steve.init.ZeldaScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class DungeonTableScreenHandler extends ScreenHandler {

    private final RecipeInputInventory input = new CraftingInventory(this, 3, 3);
    private final CraftingResultInventory result = new CraftingResultInventory();
    private final ScreenHandlerContext context;
    private final PlayerEntity player;

    public DungeonTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public DungeonTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ZeldaScreenHandlers.DUNGEON_TABLE, syncId);
        this.context = context;
        this.player = playerInventory.player;

        createSlots(playerInventory);
        createInventory(playerInventory, 8, 113);
    }

    // SLOTS ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void createSlots(PlayerInventory playerInventory) {
        int resultX = 119;
        int resultY = 49;
        int gridX = 21;
        int gridY = 30;

        this.addSlot(new DungeonTableResultSlot(playerInventory.player, this.input, this.result, 0, resultX, resultY));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.input, j + i * 3, gridX + j * 19, gridY + i * 19));
            }
        }
    }

    public void createInventory(PlayerInventory playerInventory, int startX, int startY) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, startX + j * 18, startY + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, startX + i * 18, startY + 58));
        }
    }

    // RECIPE CRAFTING /////////////////////////////////////////////////////////////////////////////////////////////////

    protected static void updateResult(DungeonTableScreenHandler handler, World world, PlayerEntity playerEntity, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (!world.isClient()) {
            ServerPlayerEntity player = (ServerPlayerEntity) playerEntity;
            RecipeManager recipeManager = world.getRecipeManager();
            ItemStack itemStack = ItemStack.EMPTY;

            Optional<DungeonTableRecipe> dungeonTableRecipe = recipeManager.getFirstMatch(
                    DungeonTableRecipe.Type.INSTANCE, craftingInventory, world
            );

            if (dungeonTableRecipe.isPresent()) {
                DungeonTableRecipe recipe = dungeonTableRecipe.get();

                if (resultInventory.shouldCraftRecipe(world, player, recipe)) {
                    ItemStack itemStack2 = recipe.craft(craftingInventory, world.getRegistryManager());

                    if (itemStack2.isItemEnabled(world.getEnabledFeatures())) {
                        itemStack = itemStack2;
                    }
                }
            }

            resultInventory.setStack(0, itemStack);
            handler.setPreviousTrackedSlot(0, itemStack);
            player.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
        }
    }

    public ItemStack getOutput() {
        return this.result.getStack(0);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result));
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }


    // SLOT INSERTION //////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == 0) {
                this.context.run((world, pos) -> {
                    itemStack2.getItem().onCraft(itemStack2, world, player);
                });
                if (!this.insertItem(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (slot >= 10 && slot < 46) {
                if (!this.insertItem(itemStack2, 1, 10, false)) {
                    if (slot < 37) {
                        if (!this.insertItem(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot2.onTakeItem(player, itemStack2);
            if (slot == 0) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ZeldaBlocks.DUNGEON_TABLE);
    }
}
