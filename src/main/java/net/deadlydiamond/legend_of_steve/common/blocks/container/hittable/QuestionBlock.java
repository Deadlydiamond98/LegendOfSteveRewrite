package net.deadlydiamond.legend_of_steve.common.blocks.container.hittable;

import net.deadlydiamond.legend_of_steve.init.ZeldaSounds;
import net.minecraft.sound.SoundEvent;

public class QuestionBlock extends HittableContainerBlock {

    public QuestionBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean startEmpty() {
        return true;
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
    protected SoundEvent getInsertSound() {
        return ZeldaSounds.QUESTION_BLOCK_DEPOSIT;
    }
}
