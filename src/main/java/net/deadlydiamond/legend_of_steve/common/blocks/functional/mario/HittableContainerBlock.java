package net.deadlydiamond.legend_of_steve.common.blocks.functional.mario;

import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;

public class HittableContainerBlock extends AbstractHittableContainerBlock {
    public HittableContainerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected SoundEvent getInsertSound(ItemStack stack) {
        return ZeldaSounds.QUESTION_BLOCK_DEPOSIT;
    }

    @Override
    protected SoundEvent getHittingSound() {
        return ZeldaSounds.QUESTION_BLOCK_HIT;
    }

    @Override
    protected SoundEvent getEmptyingSound() {
        return ZeldaSounds.QUESTION_BLOCK_EMPTY_CONTENTS;
    }

    @Override
    protected boolean activatedByRedstone() {
        return true;
    }
}
