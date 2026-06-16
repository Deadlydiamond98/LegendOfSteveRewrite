package net.deadlydiamond.legend_of_steve.client.screens;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.client.screens.widgets.DungeonTableTextFieldWidget;
import net.deadlydiamond.legend_of_steve.common.screen_handlers.DungeonTableScreenHandler;
import net.deadlydiamond.legend_of_steve.networking.c2s.UpdateDungeonTableScreenC2SPacket;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DungeonTableScreen extends HandledScreen<DungeonTableScreenHandler> {
    private static final Identifier TEXTURE = LegendOfSteve.id("textures/gui/dungeon_table.png");

    private boolean narrow;
    private DungeonTableTextFieldWidget switchIdField;

    public DungeonTableScreen(DungeonTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 195;
        this.titleX = 8;
        this.playerInventoryTitleY = 100;
    }

    protected void init() {
        super.init();
        this.narrow = this.width < 379;
        this.x = (this.width - this.backgroundWidth) / 2;

        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;

        this.switchIdField = new DungeonTableTextFieldWidget(this.textRenderer, i + 95, j + 73, 64, 12, Text.empty());
        this.switchIdField.setVisible(false);
        this.switchIdField.setMaxLength(10);
        this.switchIdField.setChangedListener(this::onIDFieldChanged);
        this.addDrawableChild(this.switchIdField);
    }

    private void onIDFieldChanged(String text) {

    }

    @Override
    protected void handledScreenTick() {
        this.switchIdField.tick();
        this.switchIdField.setVisible(!this.handler.getOutput().isEmpty());

        if (!this.switchIdField.isVisible()) {
            this.switchIdField.setText("Global");
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }


    // Input Stuff /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.client.player.closeHandledScreen();
        }

        return this.switchIdField.keyPressed(keyCode, scanCode, modifiers) || this.switchIdField.isActive() || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY) {
        return (!this.narrow) && super.isPointWithinBounds(x, y, width, height, pointX, pointY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.switchIdField.isMouseOver(mouseX, mouseY)) {
            this.switchIdField.setFocused(false);
        }
        return this.narrow || super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        return mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.backgroundWidth) || mouseY >= (double)(top + this.backgroundHeight);
    }

    @Override
    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
    }
}
