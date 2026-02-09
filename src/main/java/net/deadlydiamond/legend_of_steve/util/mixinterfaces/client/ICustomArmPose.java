package net.deadlydiamond.legend_of_steve.util.mixinterfaces.client;

import net.deadlydiamond.legend_of_steve.client.rendering.player.armpos.ArmPose;
import org.jetbrains.annotations.Nullable;

public interface ICustomArmPose {
    void legend_of_steve$setCustomRightArmPose(@Nullable ArmPose armPose);
    void legend_of_steve$setCustomLeftArmPose(@Nullable ArmPose armPose);
}
