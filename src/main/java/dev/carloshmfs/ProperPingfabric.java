package dev.carloshmfs;

import dev.carloshmfs.networking.PingPongHandler;
import dev.carloshmfs.networking.packets.PongPacketC2S;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProperPingfabric implements ModInitializer {
	public static final String MOD_ID = "properping-fabric";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(PongPacketC2S.TYPE, (PongPacketC2S packet, ServerPlayer player, PacketSender responseSender) -> {
			ProperPingfabric.LOGGER.info(packet.example);
			PingPongHandler.handlePong(player);
		});
	}
}