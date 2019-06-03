package com.glowingpigeon.columbiare.state;

public abstract class TransitionCriterion implements Criterion {
    private Section nextSection;

    public Section getNextSection() {
        return isMet() ? nextSection : null;
    }

}