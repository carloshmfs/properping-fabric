package dev.carloshmfs;

import dev.carloshmfs.networking.PingPongHandler;
import dev.carloshmfs.networking.payload.PingS2CPayload;
import dev.carloshmfs.networking.payload.PongC2SPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProperPingfabric implements ModInitializer {
	public static final String MOD_ID = "properping-fabric";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private void registerPackets() {
		PayloadTypeRegistry.playS2C().register(PingS2CPayload.ID, PingS2CPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(PongC2SPayload.ID, PongC2SPayload.CODEC);
	}

	private void registerListeners() {
		ServerPlayNetworking.registerGlobalReceiver(PongC2SPayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				PingPongHandler.handlePong(context.player(), payload.originalTime());
			});
		});
	}

	@Override
	public void onInitialize() {
		registerPackets();
		registerListeners();
	}
}