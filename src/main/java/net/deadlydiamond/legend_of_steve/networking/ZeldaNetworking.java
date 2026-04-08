package net.deadlydiamond.legend_of_steve.networking;

import net.deadlydiamond.legend_of_steve.networking.c2s.GuiScrollItemActionC2SPacket;
import net.deadlydiamond.legend_of_steve.networking.c2s.HudScrollItemActionC2SPacket;
import net.deadlydiamond.legend_of_steve.networking.c2s.RequestChestLockedStateC2SPacket;
import net.deadlydiamond.legend_of_steve.networking.s2c.ItemTransmutationPoofS2CPacket;
import net.deadlydiamond.legend_of_steve.networking.s2c.UpdateChestLockedStateS2CPacket;
import net.deadlydiamond.legend_of_steve.networking.s2c.question_block.JumpIntoBlockS2CPacket;
import net.deadlydiamond.legend_of_steve.networking.s2c.UpdateEntityStunS2CPacket;
import net.deadlydiamond.legend_of_steve.networking.s2c.pushable_block.AddBlockBreakCooldownS2CPacket;
import net.deadlydiamond.legend_of_steve.networking.s2c.pushable_block.UpdatePushableBlockBreakProgressS2CPacket;
import net.deadlydiamond.legend_of_steve.networking.s2c.question_block.UpdateBlockHitS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ZeldaNetworking {

    public static void registerC2SReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(GuiScrollItemActionC2SPacket.ID, GuiScrollItemActionC2SPacket.Handler::receive);
        ServerPlayNetworking.registerGlobalReceiver(HudScrollItemActionC2SPacket.ID, HudScrollItemActionC2SPacket.Handler::receive);
        ServerPlayNetworking.registerGlobalReceiver(RequestChestLockedStateC2SPacket.ID, RequestChestLockedStateC2SPacket.Handler::receive);
    }

    public static class Client {
        public static void registerS2CReceivers() {
            ClientPlayNetworking.registerGlobalReceiver(ItemTransmutationPoofS2CPacket.ID, ItemTransmutationPoofS2CPacket.Handler::receive);
            ClientPlayNetworking.registerGlobalReceiver(UpdateEntityStunS2CPacket.ID, UpdateEntityStunS2CPacket.Handler::receive);
            ClientPlayNetworking.registerGlobalReceiver(UpdatePushableBlockBreakProgressS2CPacket.ID, UpdatePushableBlockBreakProgressS2CPacket.Handler::receive);
            ClientPlayNetworking.registerGlobalReceiver(AddBlockBreakCooldownS2CPacket.ID, AddBlockBreakCooldownS2CPacket.Handler::receive);
            ClientPlayNetworking.registerGlobalReceiver(JumpIntoBlockS2CPacket.ID, JumpIntoBlockS2CPacket.Handler::receive);
            ClientPlayNetworking.registerGlobalReceiver(UpdateBlockHitS2CPacket.ID, UpdateBlockHitS2CPacket.Handler::receive);
            ClientPlayNetworking.registerGlobalReceiver(UpdateChestLockedStateS2CPacket.ID, UpdateChestLockedStateS2CPacket.Handler::receive);
        }
    }
}
