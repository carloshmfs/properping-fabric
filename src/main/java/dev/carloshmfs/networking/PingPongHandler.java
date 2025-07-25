package dev.carloshmfs.networking;

import dev.carloshmfs.networking.packets.PingPacketS2C;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.Util;
import net.minecraft.server.level.ServerPlayer;

public class PingPongHandler {
    public static void handlePing(final ServerPlayer player) {
        if (player == null) {
            return;
        }

        long currentTime = Util.getMillis();
        if (currentTime % 5 == 0) {
            ServerPlayNetworking.send(player, new PingPacketS2C(PacketByteBufs.empty()));
        }
    }
}
