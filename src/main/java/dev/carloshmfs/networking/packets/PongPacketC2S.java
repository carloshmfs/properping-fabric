package dev.carloshmfs.networking.packets;

import dev.carloshmfs.ProperPingfabric;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class PongPacketC2S implements FabricPacket {
    public static final PacketType<PongPacketC2S> TYPE = PacketType.create(new ResourceLocation(ProperPingfabric.MOD_ID, "pong"), PongPacketC2S::new);

    public String example = "A message coming from the client";

    public PongPacketC2S(FriendlyByteBuf buf) {
    }

    @Override
    public void write(FriendlyByteBuf buf) {

    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
