package dev.carloshmfs.networking;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.carloshmfs.LatencyInfo;
import dev.carloshmfs.ProperPingfabric;
import dev.carloshmfs.networking.payload.PingS2CPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Util;
import java.time.Duration;
import java.util.UUID;

public class PingPongHandler {
    private static final Cache<UUID, LatencyInfo> latencyInfoCache = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofSeconds(35)).build();

    public static void handlePing(final ServerPlayerEntity player) {
        if (player == null) {
            return;
        }

        UUID playerUUID = player.getUuid();
        LatencyInfo latencyInfo = getPlayerLatencyInfo(playerUUID);

        long currentTime = Util.getMeasuringTimeMs();
        if (currentTime - latencyInfo.pingTime >= 1000 && !latencyInfo.isPending) {
            latencyInfo.isPending = true;
            latencyInfo.pingTime = currentTime;
            latencyInfo.challenge = currentTime;

            ServerPlayNetworking.send(player, new PingS2CPayload(currentTime, latencyInfo.averageLatency));

            latencyInfoCache.put(playerUUID, latencyInfo);
        }
    }

    public static void handlePong(final ServerPlayerEntity player, long originalTime) {
        int latency = (int) (Util.getMeasuringTimeMs() - originalTime);
        UUID playerUUID = player.getUuid();
        LatencyInfo latencyInfo = getPlayerLatencyInfo(playerUUID);

        if (latencyInfo.isPending && latencyInfo.challenge == originalTime) {
            latencyInfo.isPending = false;
            latencyInfo.RTT_QUEUE.add(latency);
            latencyInfo.calculateAverageLatency();
        }
    }

    private static LatencyInfo getPlayerLatencyInfo(UUID playerUUID) {
        LatencyInfo lantencyInfo = latencyInfoCache.getIfPresent(playerUUID);
        return lantencyInfo != null ? lantencyInfo : new LatencyInfo();
    }
}
