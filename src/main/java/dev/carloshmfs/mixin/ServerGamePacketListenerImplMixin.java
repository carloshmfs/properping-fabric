package dev.carloshmfs.mixin;

import dev.carloshmfs.networking.PingPongHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerGamePacketListenerImplMixin {
	@Shadow
	public ServerPlayerEntity player;

	@Inject(at = @At("TAIL"), method = "tick()V")
	private void onTick(CallbackInfo info) {
		PingPongHandler.handlePing(this.player);
	}
}