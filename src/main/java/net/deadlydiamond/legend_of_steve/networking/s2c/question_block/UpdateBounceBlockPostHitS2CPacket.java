package net.deadlydiamond.legend_of_steve.networking.s2c.question_block;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.BounceType;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.mario.base.IBouncableBlock;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class UpdateBounceBlockPostHitS2CPacket {
    public static final Identifier ID = LegendOfSteve.id("update_bounce_block_post_hit");

    public static void send(PlayerEntity player, BlockPos pos, BlockState startState, BlockState endState, @Nullable Entity owner, Direction direction, BounceType type) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeRegistryValue(Block.STATE_IDS, startState);
        buf.writeRegistryValue(Block.STATE_IDS, endState);
        buf.writeBlockPos(pos);
        buf.writeEnumConstant(direction);
        buf.writeEnumConstant(type);
        buf.writeNullable(owner != null ? owner.getId() : null, PacketByteBuf::writeVarInt);

        ServerPlayNetworking.send((ServerPlayerEntity) player, ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            BlockState startState = buf.readRegistryValue(Block.STATE_IDS);
            BlockState endState = buf.readRegistryValue(Block.STATE_IDS);
            BlockPos pos = buf.readBlockPos();
            Direction direction = buf.readEnumConstant(Direction.class);
            BounceType type = buf.readEnumConstant(BounceType.class);
            Integer entityID = buf.readNullable(PacketByteBuf::readVarInt);

            client.execute(() -> {
                if (client.world != null) {
                    if (startState.getBlock() instanceof IBouncableBlock block) {
                        block.afterBounce(client.world, pos, endState, entityID != null ? client.world.getEntityById(entityID) : null, direction, type);
                    }
                }
            });
        }
    }
}
