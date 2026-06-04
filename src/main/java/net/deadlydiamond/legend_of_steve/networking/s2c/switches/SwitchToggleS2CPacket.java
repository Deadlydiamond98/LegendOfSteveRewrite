package net.deadlydiamond.legend_of_steve.networking.s2c.switches;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.bes.switches.SwitchBlockEntity;
import net.deadlydiamond.legend_of_steve.common.blocks.functional.switches.ISwitchBlock;
import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.*;

public class SwitchToggleS2CPacket {
    public static final Identifier ID = LegendOfSteve.id("toggle_switch_event");

    public static void send(PlayerEntity player, BlockPos pos, boolean bl) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeBoolean(bl);
        ServerPlayNetworking.send((ServerPlayerEntity) player, ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            BlockPos pos = buf.readBlockPos();
            boolean bl = buf.readBoolean();

            client.execute(() -> {
                World world = client.world;

                if (world != null) {
                    BlockState state = world.getBlockState(pos);
                    if (state.getBlock() instanceof ISwitchBlock switchBlock && switchBlock.getBlockEntity(world, pos) instanceof SwitchBlockEntity switchBlockEntity) {
                        switchBlock.onSwitchTriggered(world, pos, state, switchBlockEntity, bl);


                        new Thread(() -> {
                            List<BlockPos> positionsToCheck;
                            synchronized (SwitchBlockManager.SWITCH_BLOCK_POSITIONS) {
                                positionsToCheck = new ArrayList<>(SwitchBlockManager.SWITCH_BLOCK_POSITIONS);
                            }

                            List<BlockPos> toRemove = new ArrayList<>();

                            for (BlockPos pos1 : positionsToCheck) {
                                BlockState state1 = world.getBlockState(pos1);
                                if (!(state1.getBlock() instanceof ISwitchBlock)) {
                                    toRemove.add(pos1);
                                }
                            }

                            client.execute(() -> {
                                for (BlockPos pos1 : positionsToCheck) {
                                    client.worldRenderer.scheduleBlockRenders(
                                            pos1.getX(), pos1.getY(), pos1.getZ(),
                                            pos1.getX(), pos1.getY(), pos1.getZ()
                                    );
                                }
                            });

                            synchronized (SwitchBlockManager.SWITCH_BLOCK_POSITIONS) {
                                toRemove.forEach(SwitchBlockManager::removePos);
                            }
                        }).start();
                    }
                }
            });
        }
    }
}
