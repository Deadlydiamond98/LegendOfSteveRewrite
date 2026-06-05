package net.deadlydiamond.legend_of_steve.client;

import net.deadlydiamond.legend_of_steve.common.world.states.SwitchBlockManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SwitchBlockRenderManager {
    private static int batchSize = 25;
    private static int intervalMs = 50;
    public static Thread thread;

    public static void start(MinecraftClient client, BlockPos pos) {
        stop();
        thread = new Thread(() -> {
            List<ChunkSectionPos> chunkSections = new ArrayList<>();
            List<BlockPos> positionsToCheck;

            synchronized (SwitchBlockManager.SWITCH_BLOCK_POSITIONS) {
                positionsToCheck = new ArrayList<>(SwitchBlockManager.SWITCH_BLOCK_POSITIONS);
                SwitchBlockManager.clearPositions();
            }

            positionsToCheck.forEach(pos1 -> {
                ChunkSectionPos section = ChunkSectionPos.from(pos1);
                if (!chunkSections.contains(section)) {
                    chunkSections.add(section);
                }
            });
            chunkSections.sort(Comparator.comparingDouble(pos2 -> pos2.getSquaredDistance(ChunkSectionPos.from(pos))));

            for (int i = 0; i < chunkSections.size(); i += batchSize) {
                int end = Math.min(i + batchSize, chunkSections.size());

                for (int j = i; j < end; j++) {
                    ChunkSectionPos chunk = chunkSections.get(j);
                    client.worldRenderer.scheduleChunkRender(chunk.getX(), chunk.getY(), chunk.getZ(), true);
                }

                if (i + batchSize < chunkSections.size()) {
                    try {
                        Thread.sleep(intervalMs);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });

        thread.start();
    }

    public static void stop() {
        if (thread != null) {
            thread.interrupt();
        }
    }
}
