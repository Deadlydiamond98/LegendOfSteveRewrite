package net.deadlydiamond.legend_of_steve.common.items.block;

import net.deadlydiamond.legend_of_steve.init.ZeldaEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;

public class CrateItem extends PushableBlockItem {
    public CrateItem(Settings settings, BlockState parentBlock) {
        super(settings, parentBlock);
    }

    @Override
    protected EntityType<?> getEntityType() {
        return ZeldaEntityTypes.CRATE;
    }
}
