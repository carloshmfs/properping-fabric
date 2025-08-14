package dev.carloshmfs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;

public class ProperPingfabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
			ClientPlayNetworking.registerGlobalReceiver(ProperPingfabric.PING_S2C_PACKET_ID, (Minecraft client2, ClientPacketListener handler2, FriendlyByteBuf buf, PacketSender responseSender) -> {
				long originalTime = buf.readLong();
				FriendlyByteBuf packetBuf = PacketByteBufs.create();
				packetBuf.writeLong(originalTime);

				ClientPlayNetworking.send(ProperPingfabric.PONG_C2S_PACKET_ID, packetBuf);
			});
		}));
	}
}