package dev.carloshmfs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;

public class PingOverlay {
    public static final PingOverlay INSTANCE = new PingOverlay();
    public int averageLatency = 0;
    public final int pingColumnWidth;

    private final Minecraft minecraft;

    PingOverlay() {
        this.minecraft = Minecraft.getInstance();
        this.pingColumnWidth = this.minecraft.font.width(Component.translatable("multiplayer.status.ping", 999));
    }

    public static PingOverlay getInstance() {
        return INSTANCE;
    }

    public boolean render(GuiGraphics guiGraphics, int columnWidth, int x, int y, final PlayerInfo playerInfo) {
        if (this.minecraft.player == null) {
            return false;
        }

        int latency = this.minecraft.player.getGameProfile().getName().equalsIgnoreCase(playerInfo.getProfile().getName()) ? this.averageLatency : playerInfo.getLatency();
        Component component = Component.translatable("multiplayer.status.ping", latency);
        guiGraphics.drawString(this.minecraft.font, component, x + columnWidth - this.minecraft.font.width(component), y, this.getColorForLatency(latency));

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
