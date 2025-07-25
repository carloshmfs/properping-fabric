package dev.carloshmfs;

import dev.carloshmfs.networking.packets.PingPacketS2C;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.player.LocalPlayer;

public class ProperPingfabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(PingPacketS2C.TYPE, (PingPacketS2C packet, LocalPlayer player, PacketSender responseSender) -> {
			ProperPingfabric.LOGGER.info(packet.example);
		});
	}
}