package dev.carloshmfs.mixin.client;

import dev.carloshmfs.PingOverlay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {
	@Inject(at = @At("HEAD"), method = "renderPingIcon(Lnet/minecraft/client/gui/GuiGraphics;IIILnet/minecraft/client/multiplayer/PlayerInfo;)V", cancellable = true)
	private void onRenderPingIcon(GuiGraphics guiGraphics, int columnWidth, int x, int y, PlayerInfo playerInfo, CallbackInfo info) {
		if (PingOverlay.getInstance().render(guiGraphics, columnWidth, x, y, playerInfo)) {
			info.cancel();
		}
	}
}