package dev.carloshmfs.networking;

import dev.carloshmfs.ProperPingfabric;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class PingPongHandler {
    public static void handlePing(final ServerPlayer player) {
        if (player == null) {
            return;
        }

        long currentTime = Util.getMillis();
        if (currentTime % 5 == 0) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeLong(currentTime);
            ServerPlayNetworking.send(player, ProperPingfabric.PING_S2C_PACKET_ID, buf);
        }
    }

    public static void handlePong(final ServerPlayer player, long originalTime) {
        long latency = Util.getMillis() - originalTime;

        ProperPingfabric.LOGGER.info("PING: " + latency);
    }
}
