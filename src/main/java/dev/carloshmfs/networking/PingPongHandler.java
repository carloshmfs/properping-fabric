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
        if (currentTime % 5 == 0) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeLong(currentTime);
            ServerPlayNetworking.send(player, ProperPingfabric.PING_S2C_PACKET_ID, buf);
        }
    }

    public static void handlePong(final ServerPlayer player, long originalTime) {
        long latency = Util.getMillis() - originalTime;
        UUID playerUUID = player.getUUID();
        LatencyInfo latencyInfo = getPlayerLatencyInfo(playerUUID);

    }

    private static LatencyInfo getPlayerLatencyInfo(UUID playerUUID) {
        LatencyInfo lantencyInfo = latencyInfoCache.getIfPresent(playerUUID);
        return lantencyInfo != null ? lantencyInfo : new LatencyInfo();
    }
}
