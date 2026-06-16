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
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class DungeonTableScreenHandlerTemp extends AbstractRecipeScreenHandler<RecipeInputInventory> {
    public static final int RESULT_X = 126;
    public static final int RESULT_Y = 49;

    public static final int GRID_X = 29;
    public static final int GRID_Y = 30;

    public static final int INVENTORY_X = 8;
    public static final int INVENTORY_Y = 126;

    private final RecipeInputInventory input;
    private final CraftingResultInventory result;
    private final ScreenHandlerContext context;
    private final PlayerEntity player;
    private String switchId = "";
    private boolean showTextBox;

    public DungeonTableScreenHandlerTemp(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public DungeonTableScreenHandlerTemp(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ZeldaScreenHandlers.DUNGEON_TABLE, syncId);
        this.input = new CraftingInventory(this, 3, 3);
        this.result = new CraftingResultInventory();
        this.context = context;
        this.player = playerInventory.player;

        this.addSlot(new DungeonTableResultSlot(playerInventory.player, this.input, this.result, 0, RESULT_X, RESULT_Y));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.input, j + i * 3, GRID_X + j * 19, GRID_Y + i * 19));
            }
        }

        createInventory(playerInventory, INVENTORY_X, INVENTORY_Y);
    }

    public void createInventory(PlayerInventory playerInventory, int startX, int startY) {
        // Inventory Slots
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, startX + j * 18, startY + i * 18));
            }
        }
        // Hotbar Slots
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, startX + i * 18, startY + 58));
        }
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
        updateResult(this, this.player.getWorld(), this.player, this.input, this.result);
    }

    public boolean shouldShowTextBox() {
        return this.showTextBox;
    }

    public void showTextbox(boolean bl) {
        this.showTextBox = bl;
    }

    protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory) {
        DungeonTableScreenHandlerTemp screenHandler = (DungeonTableScreenHandlerTemp) handler;

        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            ItemStack itemStack = ItemStack.EMPTY;

            Optional<DungeonTableRecipe> dungeonRecipe = world.getServer().getRecipeManager().getFirstMatch(DungeonTableRecipe.Type.INSTANCE, craftingInventory, world);

            Optional<CraftingRecipe> vanillaRecipe = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);

            if (dungeonRecipe.isPresent()) {

                DungeonTableRecipe recipe = dungeonRecipe.get();

                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, recipe)) {

                    ItemStack itemStack2 = recipe.craft(craftingInventory, world.getRegistryManager());

                    if (itemStack2.isItemEnabled(world.getEnabledFeatures())) {
                        itemStack = itemStack2;
                    }
                }

//                if (recipe.hasBindableId()) {
//                    screenHandler.showTextBox = true;
//
//                    if (!screenHandler.switchId.isEmpty()) {
//                        itemStack.getOrCreateNbt().putString("switchId", screenHandler.switchId);
//                    } else {
//                        itemStack.getOrCreateNbt().putString("switchId", "global");
//                    }
//                }
            } else if (vanillaRecipe.isPresent()) {

                CraftingRecipe craftingRecipe = vanillaRecipe.get();

                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {

                    ItemStack itemStack2 = craftingRecipe.craft(craftingInventory, world.getRegistryManager());

                    if (itemStack2.isItemEnabled(world.getEnabledFeatures())) {
                        itemStack = itemStack2;
                    }
                }
                screenHandler.showTextBox = false;
            } else {
                screenHandler.showTextBox = false;
            }

            resultInventory.setStack(0, itemStack);
            handler.setPreviousTrackedSlot(0, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result));
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        this.input.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    @Override
    public boolean matches(Recipe<? super RecipeInputInventory> recipe) {
        return recipe.matches(this.input, this.player.getWorld());
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ZeldaBlocks.DUNGEON_TABLE);
    }

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
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return 10;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != this.getCraftingResultSlotIndex();
    }
}
