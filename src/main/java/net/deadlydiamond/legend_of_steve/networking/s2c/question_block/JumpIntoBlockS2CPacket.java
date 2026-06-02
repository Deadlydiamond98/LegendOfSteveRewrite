package net.deadlydiamond.legend_of_steve.networking.s2c.question_block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.IJumpIntoAction;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class JumpIntoBlockS2CPacket {
    public static final Identifier ID = LegendOfSteve.id("jump_into_block");

    public static void send(PlayerEntity player, BlockPos pos, Entity entity) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeInt(entity.getId());

        ServerPlayNetworking.send((ServerPlayerEntity) player, ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            BlockPos pos = buf.readBlockPos();
            int entityID = buf.readInt();

            client.execute(() -> {
                if (client.world != null) {
                    BlockState blockState = client.world.getBlockState(pos);
                    if (blockState.getBlock() instanceof IJumpIntoAction block) {
                        block.jumpIntoBlock(client.world, pos, blockState, client.world.getEntityById(entityID));
                    }
                }
            });
        }
    }
}
