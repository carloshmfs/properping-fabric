package dev.carloshmfs.mixin;

import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerManager.class)
public class PlayerListMixin {
    @ModifyConstant(method = "updatePlayerLatency()V", constant = @Constant(intValue = 600))
    public int onTick(int constant) {
        return 20; // update ping display on each second
    }
}
