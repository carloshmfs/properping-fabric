package dev.carloshmfs.mixin;

import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @ModifyConstant(method = "tick()V", constant = @Constant(intValue = 600))
    public int onTick(int constant) {
        return 20; // update ping display on each second
    }
}
