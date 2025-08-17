package dev.carloshmfs;

import dev.carloshmfs.networking.payload.PingS2CPayload;
import dev.carloshmfs.networking.payload.PongC2SPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ProperPingfabricClient implements ClientModInitializer {
	private void registerListeners() {
		ClientPlayNetworking.registerGlobalReceiver(PingS2CPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				PingOverlay.getInstance().averageLatency = payload.averageLatency();
				ClientPlayNetworking.send(new PongC2SPayload(payload.originalTime()));
			});
		});
	}

	@Override
	public void onInitializeClient() {
		registerListeners();
	}
}