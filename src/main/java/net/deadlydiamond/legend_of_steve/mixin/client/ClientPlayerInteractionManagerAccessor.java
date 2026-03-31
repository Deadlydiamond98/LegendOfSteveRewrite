package net.deadlydiamond.legend_of_steve.mixin.client;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerInteractionManager.class)
public interface ClientPlayerInteractionManagerAccessor {
    @Accessor("blockBreakingCooldown")
    void legend_of_steve$setBlockBreakingCooldown(int cooldown);
}
