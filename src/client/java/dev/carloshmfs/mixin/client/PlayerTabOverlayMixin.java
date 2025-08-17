package dev.carloshmfs.mixin.client;

import dev.carloshmfs.PingOverlay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public class PlayerTabOverlayMixin {
	@Inject(at = @At("HEAD"), method = "renderLatencyIcon(Lnet/minecraft/client/gui/DrawContext;IIILnet/minecraft/client/network/PlayerListEntry;)V", cancellable = true)
	private void onRenderPingIcon(DrawContext drawContext, int columnWidth, int x, int y, PlayerListEntry playerEntry, CallbackInfo info) {
		if (PingOverlay.getInstance().render(drawContext, columnWidth, x, y, playerEntry)) {
			info.cancel();
		}
	}

	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"), index = 0)
	private int overwriteTablistColumnWidth(int vanillaWidth) {
		return vanillaWidth + PingOverlay.getInstance().pingColumnWidth;
	}
}