package dev.carloshmfs;

import dev.carloshmfs.networking.PingPongHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProperPingfabric implements ModInitializer {
	public static final String MOD_ID = "properping-fabric";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceLocation PING_S2C_PACKET_ID = new ResourceLocation(MOD_ID, "ping");
	public static final ResourceLocation PONG_C2S_PACKET_ID = new ResourceLocation(MOD_ID, "pong");

	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(PONG_C2S_PACKET_ID, (MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) -> {
			long originalTime = buf.readLong();
			PingPongHandler.handlePong(player, originalTime);
		});
	}
}