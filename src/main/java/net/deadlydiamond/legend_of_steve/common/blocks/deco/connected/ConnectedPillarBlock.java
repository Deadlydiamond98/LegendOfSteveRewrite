package net.deadlydiamond.legend_of_steve.common.blocks.deco.connected;

import net.minecraft.block.PillarBlock;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ConnectedPillarBlock extends PillarBlock {
    public static final Set<Supplier<Identifier>> PILLARS = new HashSet<>();

    public ConnectedPillarBlock(Settings settings) {
        this(settings, true);
    }

    public ConnectedPillarBlock(Settings settings, boolean parentedItem) {
        super(settings);
        addValue("block/");
        if (parentedItem) {
            addValue("item/");
        }
    }

    private void addValue(String prefix) {
        PILLARS.add(() -> Registries.BLOCK.getId(this).withPrefixedPath(prefix));
    }

    public static boolean isPresent(Identifier id) {
        for (Supplier<Identifier> pillar : PILLARS) {
            if (pillar.get().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
