package dev.carloshmfs.networking.payload;

import dev.carloshmfs.ProperPingfabric;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PongC2SPayload(long originalTime) implements CustomPayload {
    public static final Identifier PONG_PAYLOAD_ID = Identifier.of(ProperPingfabric.MOD_ID, "pong");
    public static final CustomPayload.Id<PongC2SPayload> ID = new CustomPayload.Id<>(PongC2SPayload.PONG_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, PongC2SPayload> CODEC = PacketCodec.of(
            (value, buf) -> buf.writeLong(value.originalTime()),
            buf -> new PongC2SPayload(buf.readLong())
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PongC2SPayload.ID;
    }
}
