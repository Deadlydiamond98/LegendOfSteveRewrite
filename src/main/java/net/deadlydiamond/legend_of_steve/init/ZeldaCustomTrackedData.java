package net.deadlydiamond.legend_of_steve.init;

import net.deadlydiamond.legend_of_steve.common.entities.living.FairyColor;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public class ZeldaCustomTrackedData {
    public static final TrackedDataHandler<FairyColor> FAIRY_COLOR = TrackedDataHandler.ofEnum(FairyColor.class);

    static {
        TrackedDataHandlerRegistry.register(FAIRY_COLOR);
    }
}
