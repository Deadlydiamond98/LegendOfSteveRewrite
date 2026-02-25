package net.deadlydiamond.legend_of_steve.common.blocks.sign;

import net.minecraft.block.HangingSignBlock;
import net.minecraft.block.WallHangingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class CustomWallHangingSignBlock extends WallHangingSignBlock implements ICustomSign {
    private final Identifier texture;

    public CustomWallHangingSignBlock(Settings settings, WoodType woodType, Identifier texture) {
        super(settings, woodType);
        this.texture = texture;
    }

    @Override
    public WoodType getWoodType() {
        return WoodType.OAK;
    }

    @Override
    public Identifier getTexture() {
        return this.texture;
    }
}
