package dev.carloshmfs.networking;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.carloshmfs.LatencyInfo;
import dev.carloshmfs.ProperPingfabric;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.time.Duration;
import java.util.UUID;

public class PingPongHandler {
    private static final Cache<UUID, LatencyInfo> latencyInfoCache = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofSeconds(35)).build();

    public static void handlePing(final ServerPlayer player) {
        if (player == null) {
            return;
        }

        UUID playerUUID = player.getUUID();
        LatencyInfo latencyInfo = getPlayerLatencyInfo(playerUUID);

        long currentTime = Util.getMillis();
        if (currentTime - latencyInfo.pingTime >= 1000 && !latencyInfo.isPending) {
            latencyInfo.isPending = true;
            latencyInfo.pingTime = currentTime;
            latencyInfo.challenge = currentTime;

            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeLong(currentTime);
            ServerPlayNetworking.send(player, ProperPingfabric.PING_S2C_PACKET_ID, buf);

            latencyInfoCache.put(playerUUID, latencyInfo);
        }
    }

    public static void handlePong(final ServerPlayer player, long originalTime) {
        int latency = (int) (Util.getMillis() - originalTime);
        UUID playerUUID = player.getUUID();
        LatencyInfo latencyInfo = getPlayerLatencyInfo(playerUUID);

        if (latencyInfo.isPending && latencyInfo.challenge == originalTime) {
            latencyInfo.isPending = false;
            latencyInfo.RTT_QUEUE.add(latency);
            player.latency = latencyInfo.calculateAverageLatency();
            ProperPingfabric.LOGGER.info(player.getName().getString() + " PING: " + player.latency + "ms");
        }
    }

    private static LatencyInfo getPlayerLatencyInfo(UUID playerUUID) {
        LatencyInfo lantencyInfo = latencyInfoCache.getIfPresent(playerUUID);
        return lantencyInfo != null ? lantencyInfo : new LatencyInfo();
    }
}
