package net.deadlydiamond.legend_of_steve.events.common;

import net.deadlydiamond.legend_of_steve.common.bes.ILoadEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;

public class ZeldaServerBlockEntityEvents {

    public static void register() {
        ServerBlockEntityEvents.BLOCK_ENTITY_LOAD.register(ZeldaServerBlockEntityEvents::onLoad);
    }

    private static void onLoad(BlockEntity entity, ServerWorld serverWorld) {
        if (entity instanceof ILoadEvent loadEventEntity) {
            loadEventEntity.onLoad(serverWorld);
        }
    }
}
