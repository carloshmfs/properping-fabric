package dev.carloshmfs.networking.payload;

import dev.carloshmfs.ProperPingfabric;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PingS2CPayload(long originalTime, int averageLatency) implements CustomPayload {
    public static final Identifier PING_PAYLOAD_ID = Identifier.of(ProperPingfabric.MOD_ID, "ping");
    public static final CustomPayload.Id<PingS2CPayload> ID = new CustomPayload.Id<>(PING_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, PingS2CPayload> CODEC = PacketCodec.of(
        (value, buf) -> {
            buf.writeLong(value.originalTime);
            buf.writeInt(value.averageLatency);
        },
        buf -> new PingS2CPayload(buf.readLong(), buf.readInt())
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PingS2CPayload.ID;
    }
}
