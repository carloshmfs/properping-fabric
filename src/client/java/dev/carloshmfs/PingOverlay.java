package dev.carloshmfs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;

public class PingOverlay {
    public static final PingOverlay INSTANCE = new PingOverlay();
    public int averageLatency = 0;
    public final int pingColumnWidth;

    private final MinecraftClient minecraft;

    PingOverlay() {
        this.minecraft = MinecraftClient.getInstance();
        this.pingColumnWidth = this.minecraft.textRenderer.getWidth(String.valueOf(999));
    }

    public static PingOverlay getInstance() {
        return INSTANCE;
    }

    public boolean render(DrawContext drawContext, int columnWidth, int x, int y, final PlayerListEntry playerListEntry) {
        if (this.minecraft.player == null) {
            return false;
        }

        int latency = this.minecraft.player.getGameProfile().getName().equalsIgnoreCase(playerListEntry.getProfile().getName()) ? this.averageLatency : playerListEntry.getLatency();
        drawContext.drawText(
            this.minecraft.textRenderer,
            String.valueOf(latency),
            x + columnWidth - this.minecraft.textRenderer.getWidth(String.valueOf(latency)),
            y,
            this.getColorForLatency(latency),
            true
        );

        return true;
    }

    private int getColorForLatency(int latency) {
        if (latency >= 50 && latency <= 100) {
            return 0xbde023;
        }

        if (latency >= 100 && latency <= 150) {
            return 0xe0ae23;
        }

        if (latency > 150) {
            return 0xff0000;
        }

        return 0x00ff00;
    }
}
