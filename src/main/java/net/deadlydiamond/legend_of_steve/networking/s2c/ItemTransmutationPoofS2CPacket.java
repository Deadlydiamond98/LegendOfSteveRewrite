package net.deadlydiamond.legend_of_steve.networking.s2c;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.deadlydiamond.legend_of_steve.common.particles.SparkParticleEffect;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class ItemTransmutationPoofS2CPacket {
    public static final Identifier ID = LegendOfSteve.id("item_transmutation_poof");

    public static void send(World world, ItemEntity entity) {
        world.getEntitiesByClass(PlayerEntity.class, entity.getBoundingBox().expand(50), player -> true)
                .forEach(player -> ItemTransmutationPoofS2CPacket.send(
                        (ServerPlayerEntity) player, entity.getPos().add(0, 0.25, 0))
                );
    }

    public static void send(ServerPlayerEntity player, Vec3d pos) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVector3f(pos.toVector3f());
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            Vector3f pos = buf.readVector3f();
            client.execute(() -> {
                if (client.world != null) {
                    SparkParticleEffect.createSparks(client.world, SparkParticleEffect.SOUL, new Vec3d(pos), 25);
                }
            });
        }
    }
}
