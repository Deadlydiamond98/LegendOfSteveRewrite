package net.deadlydiamond.legend_of_steve.client.rendering.player;

import net.deadlydiamond.legend_of_steve.client.rendering.player.armpos.ArmPose;
import net.deadlydiamond.legend_of_steve.client.rendering.player.armpos.OverHeadArmPose;
import net.deadlydiamond.legend_of_steve.client.rendering.player.itemmodel.BombHeldItemRenderer;
import net.deadlydiamond.legend_of_steve.client.rendering.player.itemmodel.CustomHeldItemRenderer;

public class ZeldaPlayerRendering {

    public static final ArmPose OVER_HEAD_ARM_POSE = new OverHeadArmPose(true);
    public static final CustomHeldItemRenderer BOMB_ITEM_RENDERER = new BombHeldItemRenderer();

    public static void register() {}
}
