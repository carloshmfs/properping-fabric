package dev.carloshmfs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;

public class PingOverlay {
    public static final PingOverlay INSTANCE = new PingOverlay();
    public int averageLatency = 0;

    private final Minecraft minecraft;

    PingOverlay() {
        this.minecraft = Minecraft.getInstance();
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
        guiGraphics.drawString(this.minecraft.font, component, x + columnWidth , y, 0x00FF00);

        return true;
    }
}
