package dev.carloshmfs.networking.packets;

import dev.carloshmfs.ProperPingfabric;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class PingPacketS2C implements FabricPacket {
    public static final PacketType<PingPacketS2C> TYPE = PacketType.create(new ResourceLocation(ProperPingfabric.MOD_ID, "ping"), PingPacketS2C::new);

    public String example = "an example message sent to the client";

    public PingPacketS2C(FriendlyByteBuf buf) {
    }

    @Override
    public void write(FriendlyByteBuf buf) {

    }

    @Override
    public PacketType<PingPacketS2C> getType() {
        return TYPE;
    }
}
